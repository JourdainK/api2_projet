package mvp.model.location;

import mvp.model.DAO;
import myconnections.DBConnection;
import oracle.jdbc.OracleTypes;
import oracle.jdbc.proxy.annotation.Pre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;


public class LocationModelDB implements DAO<Location>, LocationSpecial {
    private static final Logger logger = LogManager.getLogger(LocationModelDB.class);
    private Connection dbConnect;

    public LocationModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            logger.error("Erreur de connexion, Location");
            System.exit(1);
        }
    }

    @Override
    public Location add(Location location) {
        String insertQuery = "INSERT INTO apilocation(dateloc, kmtotal, nbrpassagers, id_taxi, id_adresse, id_adresse_1, id_client) VALUES (?,?,?,?,?,?,?)";
        String query = "SELECT MAX(id_location) FROM APILOCATION WHERE dateloc = ? AND id_client = ?";
        try (PreparedStatement pstm1 = dbConnect.prepareStatement(insertQuery);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query);) {
            pstm1.setDate(1, Date.valueOf(location.getDateLoc()));
            pstm1.setInt(2, location.getKmTotal());
            pstm1.setInt(3, location.getNbrePassagers());
            pstm1.setInt(4, location.getVehicule().getIdTaxi());
            pstm1.setInt(5, location.getAdrDebut().getIdAdr());
            pstm1.setInt(6, location.getAdrFin().getIdAdr());
            pstm1.setInt(7, location.getClient().getIdclient());

            int line = pstm1.executeUpdate();

            if (line == 1) {
                pstm2.setDate(1, Date.valueOf(location.getDateLoc()));
                pstm2.setInt(2, location.getClient().getIdclient());
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int idLoc = rs.getInt(1);
                    location.setId(idLoc);
                    location.getVehicule().getListTaxiLoc().add(location);
                    return location;
                } else return null;
            } else {
                logger.error("Erreur lors de la récupération d'une location");
                return null;
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération d'une location" + e);
            //to delete
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean remove(Location location) {
        String query = "DELETE FROM APILOCATION WHERE id_location = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, location.getIdLoc());
            int line = pstm.executeUpdate();
            if (line != 0) return true;
            else return false;
        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression de la location : " + e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Location update(Location location) {
        String update = "UPDATE apilocation SET dateloc=?, kmtotal=?, nbrpassagers=?,id_taxi=?, id_adresse=?, id_adresse_1=?, id_client=? WHERE id_location=?";
        //check Date.valueOf
        try (PreparedStatement pstm = dbConnect.prepareStatement(update)) {
            pstm.setDate(1, (Date.valueOf(location.getDateLoc())));
            pstm.setInt(2, location.getKmTotal());
            pstm.setInt(3, location.getNbrePassagers());
            //TOTAL n'est pas modifiable, le total étant calculé par un trigger dans la base de données
            // read() will give back the price
            pstm.setInt(4, location.getVehicule().getIdTaxi());
            pstm.setInt(5, location.getAdrDebut().getIdAdr());
            pstm.setInt(6, location.getAdrFin().getIdAdr());
            pstm.setInt(7, location.getClient().getIdclient());
            pstm.setInt(8, location.getIdLoc());
            int line = pstm.executeUpdate();
            if (line != 0) return read(location);
            else return null;
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour de la location : " + e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Location read(Location locat) {
        Location location = null;
        String query = "SELECT * FROM apilocation WHERE id_location=?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, locat.getIdLoc());
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int idLoc = rs.getInt("ID_LOCATION");
                LocalDate dateloc = rs.getDate("DATELOC").toLocalDate();
                int kmtot = rs.getInt("KMTOTAL");
                int nbrpass = rs.getInt("NBRPASSAGERS");
                double total = rs.getDouble("TOTAL");
                int idTaxi = rs.getInt("ID_TAXI");
                int idAdreDepart = rs.getInt("ID_ADRESSE");
                int idAdreFin = rs.getInt("ID_ADRESSE_1");
                int idClient = rs.getInt("ID_CLIENT");
                try {
                    Taxi taxi = getTaxiByID(idTaxi);
                    Client cli = getClientById(idClient);
                    Adresse adrD = getAdresseByID(idAdreDepart);
                    Adresse adrF = getAdresseByID(idAdreFin);
                    location = new Location.LocationBuilder().setIdLoc(idLoc)
                            .setDateLoc(dateloc).setKmTot(kmtot).setNbrePassagers(nbrpass)
                            .setTotal(total).setVehicule(taxi).setClient(cli).setAdrDebut(adrD).setAdrFin(adrF).build();

                } catch (Exception e) {
                    logger.error("Erreur lors de la lecture du client : " + e);
                    e.printStackTrace();
                    return null;
                }

            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de la location : " + e);
            e.printStackTrace();
            return null;
        }
        return location;
    }


    @Override
    public Location readbyId(int id) {
        Location location = null;
        String query = "SELECT * FROM apilocation WHERE id_location=?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int idLoc = rs.getInt("ID_LOCATION");
                String dateloc = String.valueOf(rs.getDate("DATELOC"));
                int kmtot = rs.getInt("KMTOTAL");
                int nbrpass = rs.getInt("NBRPASSAGERS");
                double total = rs.getDouble("TOTAL");
                int idTaxi = rs.getInt("ID_TAXI");
                int idAdreDepart = rs.getInt("ID_ADRESSE");
                int idAdreFin = rs.getInt("ID_ADRESSE_1");
                int idClient = rs.getInt("ID_CLIENT");
                try {
                    Taxi taxi = getTaxiByID(idTaxi);
                    Client cli = getClientById(idClient);
                    Adresse adrD = getAdresseByID(idAdreDepart);
                    Adresse adrF = getAdresseByID(idAdreFin);
                    location = new Location.LocationBuilder().setIdLoc(idLoc)
                            .setDateLoc(LocalDate.parse(dateloc)).setKmTot(kmtot).setNbrePassagers(nbrpass)
                            .setTotal(total).setVehicule(taxi).setClient(cli).setAdrDebut(adrD).setAdrFin(adrF).build();
                } catch (Exception e) {
                    logger.error("Erreur lors de la lecture du client : " + e);
                    e.printStackTrace();
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de la location : " + e);
            e.printStackTrace();
            return null;
        }
        return location;
    }

    @Override
    public List<Location> getAll() {
        List<Location> ldatas = new ArrayList<>();
        Location tmpLoc;
        String query = "SELECT * FROM APILOCATION ORDER BY id_location";

        try (Statement stmt = dbConnect.createStatement();) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int idLoc = rs.getInt(1);
                String dateloc = String.valueOf(rs.getDate(2));
                int kmtot = rs.getInt(3);
                int nbrPass = rs.getInt(4);
                double tot = rs.getDouble(5);
                int idTaxi = rs.getInt(6);
                int adrDep = rs.getInt(7);
                int adrFin = rs.getInt(8);
                int idCli = rs.getInt(9);
                try {
                    Taxi taxi = getTaxiByID(idTaxi);
                    Client cli = getClientById(idCli);
                    Adresse adrD = getAdresseByID(adrDep);
                    Adresse adrF = getAdresseByID(adrFin);
                    tmpLoc = new Location.LocationBuilder()
                            .setIdLoc(idLoc)
                            .setDateLoc(LocalDate.parse(dateloc))
                            .setKmTot(kmtot)
                            .setNbrePassagers(nbrPass)
                            .setTotal(tot)
                            .setVehicule(taxi)
                            .setClient(cli)
                            .setAdrDebut(adrD)
                            .setAdrFin(adrF).build();

                    ldatas.add(tmpLoc);

                } catch (Exception e) {
                    logger.error("Erreur lors de la récupération des locations : " + e);
                    e.printStackTrace();
                    System.err.println("Erreur, les locations n'ont pas été récupérées par le programme");
                }

            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des taxis : " + e);
            System.err.println("Erreur Sql : " + e);
            e.printStackTrace();
        }

        return ldatas;
    }


    @Override
    public Client getClientById(int id) {
        Client cli;
        String query = "SELECT * FROM APITCLIENT WHERE id_client = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int idCli = rs.getInt("ID_CLIENT");
                String mail = rs.getString("MAIL");
                String nom = rs.getString("NOM_CLI");
                String pren = rs.getString("PRENOM_CLI");
                String tel = rs.getString("TEL");
                try {
                    cli = new Client.ClientBuilder()
                            .setIdClient(idCli)
                            .setMail(mail)
                            .setNom(nom)
                            .setPrenom(pren)
                            .setTel(tel)
                            .build();
                    return cli;
                } catch (Exception e) {
                    logger.error("Erreur lors de la lecture du client  : " + e);
                    //TO DELETE
                    e.printStackTrace();
                    return null;
                }
            } else return null;

        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche SQL :" + e);
            return null;
        }

    }

    @Override
    public Adresse getAdresseByID(int id) {
        Adresse tmpAdr;
        String query = "SELECT * FROM APIADRESSE WHERE ID_ADRESSE = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int idAdres = rs.getInt(1);
                int cp = rs.getInt(2);
                String localite = rs.getString(3);
                String rue = rs.getString(4);
                String num = rs.getString(5);
                try {
                    tmpAdr = new Adresse.AdresseBuilder()
                            .setCp(cp).setLocalite(localite).setIdAdr(idAdres).setNum(num)
                            .setRue(rue).build();
                    return tmpAdr;
                } catch (Exception e) {
                    logger.error("Erreur lors de la recherche de l'adresse");
                    e.printStackTrace();
                    return null;
                }
            } else return null;

        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de l'adresse");
            return null;
        }

    }

    @Override
    public Taxi getTaxiByID(int id) {
        Taxi tmpTaxi;
        String query = "SELECT * FROM APITAXI WHERE ID_TAXI = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int taxiId = rs.getInt(1);
                String immat = rs.getString(2);
                int nbrPassMax = rs.getInt(3);
                double prixKm = rs.getDouble(4);
                try {
                    tmpTaxi = new Taxi.TaxiBuilder()
                            .setIdTaxi(taxiId)
                            .setImmatriculation(immat)
                            .setPrixKm(prixKm)
                            .setNbreMaxPassagers(nbrPassMax)
                            .build();
                    return tmpTaxi;
                } catch (Exception e) {
                    logger.error("Erreur lors de la recherche d'un taxi : " + e);
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la lecture du client (" + id + ") Erreur SQL : " + e);
            return null;
        }

    }

    @Override
    public List<Location> getAllLocatSamePlace() {
        Set<Location> Slocat = new HashSet<>();
        Location tmpLoc;
        String query = "SELECT * FROM API_LOCAT_SAME_ADR";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int id_loc = rs.getInt(1);
                LocalDate dateloc = rs.getDate(2).toLocalDate();
                int nbrPass = rs.getInt(3);
                int kmtot = rs.getInt(4);
                double prixtot = rs.getDouble(6);
                int idTaxi = rs.getInt(7);
                int adrDep = rs.getInt(9);
                int adrFin = rs.getInt(11);
                int idCli = rs.getInt(13);
                Client tmpcli = getClientById(idCli);
                Taxi taxi = getTaxiByID(idTaxi);
                Adresse adrD = getAdresseByID(adrDep);
                Adresse adrF = getAdresseByID(adrFin);
                try {
                    tmpLoc = new Location.LocationBuilder()
                            .setIdLoc(id_loc)
                            .setDateLoc(dateloc)
                            .setKmTot(kmtot)
                            .setNbrePassagers(nbrPass)
                            .setTotal(prixtot)
                            .setVehicule(taxi)
                            .setClient(tmpcli)
                            .setAdrDebut(adrD)
                            .setAdrFin(adrF).build();
                    Slocat.add(tmpLoc);
                } catch (Exception f) {
                    logger.error("Erreur lors de la récupération des locations (à la même adresse) : " + f);
                    f.printStackTrace();
                    System.err.println("Erreur, les locations n'ont pas été récupérées par le programme");
                }
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des locations (à la même adresse) : " + e);
            e.printStackTrace();
            System.err.println("Erreur, les locations n'ont pas été récupérées par le programme");

            return null;
        }

        List<Location> lLocations = new ArrayList<>(Slocat);
        return lLocations;
    }

    @Override
    public HashMap<List<Location>, Double> getAllLocatSamePlaceWithPrice(LocalDate date) {
        HashMap<List<Location>, Double> map = new HashMap<>();
        List<Location> listLoc = new ArrayList<>();

        try (CallableStatement cs = dbConnect.prepareCall("{?=call get_locat_day(?,?)}");) {
            double total = 0;
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setDate(2, Date.valueOf(date));
            cs.setDouble(3, total);
            cs.registerOutParameter(2, OracleTypes.DATE);
            cs.execute();
            ResultSet resultSet = (ResultSet) cs.getObject(1);
            total = cs.getDouble(3);
            while (resultSet.next()) {
                int id_loc = resultSet.getInt("ID_LOCATION");
                Location location = readbyId(id_loc);
                listLoc.add(location);
            }
            map.put(listLoc, total);

        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des locations (à la même adresse) : " + e);
            e.printStackTrace();
            System.err.println("Erreur, les locations n'ont pas été récupérées par le programme");
            return null;
        }

        return map;
    }

    @Override
    public double getTotalLocat(int id) {
        double price=0;

        try (CallableStatement cs = dbConnect.prepareCall("{call get_tot_locat(?,?)}")) {
            cs.setInt(1,id);
            cs.setDouble(2,price);
            cs.registerOutParameter(2, OracleTypes.DOUBLE);
            cs.execute();
            double total = cs.getDouble(2);

            return total;
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des locations (à la même adresse) : " + e);
            e.printStackTrace();
            System.err.println("Erreur, les locations n'ont pas été récupérées par le programme");
            return 0;
        }
    }

    @Override
    public List<Taxi> getTaxiByNbrPass(int nbrPass) {
        Set<Taxi> sTaxis = new HashSet<>();

        try(CallableStatement cs = dbConnect.prepareCall("{?=call get_taxis_nbrpass(?)}")){
            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.setInt(2, nbrPass);
            cs.execute();
            ResultSet rs = (ResultSet) cs.getObject(1);
            while(rs.next()){
                int idTaxi = rs.getInt(1);
                String immat = rs.getString(2);
                int nbrPassMax = rs.getInt(3);
                double prixKm = rs.getDouble(4);
                try {
                    Taxi tmpTaxi = new Taxi.TaxiBuilder()
                            .setIdTaxi(idTaxi)
                            .setImmatriculation(immat)
                            .setPrixKm(prixKm)
                            .setNbreMaxPassagers(nbrPassMax)
                            .build();
                    sTaxis.add(tmpTaxi);
                } catch (Exception e) {
                    logger.error("Erreur lors de la récupération des taxis : " + e);
                    e.printStackTrace();
                    System.err.println("Erreur, les taxis n'ont pas été récupérés par le programme");
                }
            }
            List<Taxi> lTaxis = new ArrayList<>(sTaxis);
            return lTaxis;
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des taxis : " + e);
            e.printStackTrace();
            System.err.println("Erreur, les taxis n'ont pas été récupérés par le programme");
            return null;
        }

    }

}
