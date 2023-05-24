package mvp.model.taxi;

import mvp.model.DAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;
import myconnections.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class TaxiModelDB implements DAO<Taxi>, TaxiSpecial {
    private Connection dbConnect;
    private static final Logger logger = LogManager.getLogger(TaxiModelDB.class);

    public TaxiModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            //System.err.println("Erreur de connexion ");
            logger.info("Erreur de connexion");

            System.exit(1);
        }
    }


    @Override
    public Taxi add(Taxi taxi) {
        String query1 = "INSERT INTO APITAXI(immatriculation,nbremaxpassagers,prixkm)"
                + "VALUES (?,?,?)";
        String query2 = "SELECT * FROM APITAXI WHERE immatriculation=? AND nbremaxpassagers=? AND prixkm=?";

        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setString(1, taxi.getImmatriculation());
            pstm1.setInt(2, taxi.getNbreMaxPassagers());
            pstm1.setDouble(3, taxi.getPrixKm());
            int nl1 = pstm1.executeUpdate();
            if (nl1 == 1) {
                pstm2.setString(1, taxi.getImmatriculation());
                pstm2.setInt(2, taxi.getNbreMaxPassagers());
                pstm2.setDouble(3, taxi.getPrixKm());
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int idTaxi = rs.getInt(1);
                    taxi.setIdTaxi(idTaxi);
                    return taxi;
                } else {
                    System.err.println("Record introuvable");
                    return null;
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e);
            return null;
        }
    }

    @Override
    public boolean remove(Taxi taxi) {
        String query = "DELETE FROM APITAXI WHERE ID_TAXI = ? AND IMMATRICULATION = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, taxi.getIdTaxi());
            pstm.setString(2, taxi.getImmatriculation());
            int nl = pstm.executeUpdate();

            if (nl != 0) return true;
            else return false;
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e);
            return false;
        }
    }

    @Override
    public Taxi update(Taxi taxi) {
        String query = "UPDATE apitaxi SET immatriculation=?, nbremaxpassagers=?, prixkm=? WHERE id_taxi=?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, taxi.getImmatriculation());
            pstm.setInt(2, taxi.getNbreMaxPassagers());
            pstm.setDouble(3, taxi.getPrixKm());
            pstm.setInt(4, taxi.getIdTaxi());
            int v = pstm.executeUpdate();
            if (v != 0) return readbyId(taxi.getIdTaxi());
            else return null;
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour (" + taxi.getIdTaxi() + ") , Erreur SQL + " + e);
            return null;
        }

    }


    @Override
    public List<Taxi> getAll() {
        List<Taxi> ltaxis = new ArrayList<>();
        Taxi tmpTaxi;
        String query = "SELECT * FROM APITAXI ORDER BY ID_TAXI";

        try (Statement stmt = dbConnect.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next()) {
                int idtaxi = rs.getInt(1);
                String immat = rs.getString(2);
                int nbrMaxPas = rs.getInt(3);
                double prixKm = rs.getDouble(4);
                try {
                    tmpTaxi = new Taxi.TaxiBuilder()
                            .setIdTaxi(idtaxi)
                            .setImmatriculation(immat)
                            .setPrixKm(prixKm)
                            .setNbreMaxPassagers(nbrMaxPas)
                            .build();
                    tmpTaxi.setLocations(allLocTaxi(tmpTaxi));
                    ltaxis.add(tmpTaxi);
                } catch (Exception e) {
                    logger.error("Erreur lors de la récupération des taxis : " + e);
                    e.printStackTrace();
                }

            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e);
            logger.error("Erreur lors de la récupération des taxis : " + e);
        }

        tmpTaxi = null;
        return ltaxis;
    }

    @Override
    public Taxi read(Taxi taxi) {
        Taxi taxiTmp;   // taxi à retourner
        String query = "SELECT * FROM APITAXI WHERE ID_TAXI = ? AND IMMATRICULATION = ?";
        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, taxi.getIdTaxi());
            pstm.setString(2, taxi.getImmatriculation());
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    int idTaxi = rs.getInt(1);
                    String immat = rs.getString(2);
                    int nbrPassMax = rs.getInt(3);
                    double prixKm = rs.getDouble(4);
                    try {
                        taxiTmp = new Taxi.TaxiBuilder()
                                .setIdTaxi(idTaxi)
                                .setImmatriculation(immat)
                                .setPrixKm(prixKm)
                                .setNbreMaxPassagers(nbrPassMax)
                                .build();
                        taxiTmp.setLocations(allLocTaxi(taxiTmp));
                        return taxiTmp;
                    } catch (Exception e) {
                        logger.error("Erreur lors de la récupération du taxi : " + e);
                        e.printStackTrace();
                    }
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération du taxi : " + e);
            System.err.println("Erreur SQL : " + e);
        }
        return null;
    }

    // fixed the problem : https://stackoverflow.com/questions/5963472/java-sql-sqlexception-fail-to-convert-to-internal-representation
    @Override
    public Taxi readbyId(int idTaxi) {
        Taxi tmpTaxi;
        String query = "SELECT * FROM APITAXI WHERE ID_TAXI = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, idTaxi);
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
                    List<Location> locatTaxi = allLocTaxi(tmpTaxi);
                    tmpTaxi.setLocations(locatTaxi);
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
            logger.error("Erreur lors de la lecture du client (" + idTaxi + ") Erreur SQL : " + e);
            return null;
        }

    }

    @Override
    public List<Taxi> taxisUtilisés(Client client) {
        List<Taxi> taxisUsed = new ArrayList<>();
        Set<Integer> idTaxis = new HashSet<>();

        int idClient = client.getIdclient();
        String query1 = "SELECT * FROM API_TAXI_USED WHERE id_client = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query1)) {
            pstm.setInt(1, idClient);
            ResultSet rs = pstm.executeQuery(query1);
            if (rs.next()) {
                idTaxis.add(rs.getInt("ID_TAXI"));
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la requête taxi utilisé : " + e);
        }
        Taxi tmpTaxi;
        for (Integer s : idTaxis) {
            tmpTaxi = readbyId(s);
            taxisUsed.add(tmpTaxi);
        }

        return taxisUsed;
    }

    //TODO delete and use method of class or use it
    @Override
    public List<Location> allLocTaxi(Taxi taxi) {
        Location tmpLoc;
        List<Location> lLoc = new ArrayList<>();
        Set<Location> sLoc = new HashSet<>();
        String query1 = "SELECT * FROM apilocation WHERE id_taxi = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query1)) {
            pstm.setInt(1, taxi.getIdTaxi());
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                int idLoc = rs.getInt(1);
                String dateloc = String.valueOf(rs.getDate(2));
                int kmTotal = rs.getInt(3);
                int nbrPass = rs.getInt(4);
                //BigDecimal tot = rs.getBigDecimal(5);
                double total = rs.getDouble(5);
                int idTax = rs.getInt(6);
                int adAll = rs.getInt(7);
                int adRet = rs.getInt(8);
                int idCli = rs.getInt(9);
                Taxi taxiLoc = getTaxiByID(idTax);
                Adresse adrAll = getAdresseByID(adAll);
                Adresse adrRet = getAdresseByID(adRet);
                Client cliLoc = getClientById(idCli);
                try {
                    tmpLoc = new Location.LocationBuilder()
                            .setIdLoc(idLoc)
                            .setDateLoc(LocalDate.parse(dateloc))
                            .setKmTot(kmTotal)
                            .setNbrePassagers(nbrPass)
                            .setTotal(total)
                            .setVehicule(taxiLoc)
                            .setAdrDebut(adrAll)
                            .setAdrFin(adrRet)
                            .setClient(cliLoc)
                            .build();
                    sLoc.add(tmpLoc);
                } catch (Exception e) {
                    logger.error("Erreur lors de la recherche des locations d'un taxi : " + e);
                }

            }
            taxi.setLocation(lLoc);

        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche des locations d'un taxi : " + e);
        }

        lLoc = new ArrayList<>(sLoc);

        return lLoc;
    }

    //TODO delete and use method of class or use it
    @Override
    public List<Adresse> allAdressTaxi(Taxi taxi) {
        return null;
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
    public Map<Integer, String> getTaxisMap() {
        Map<Integer, String> taxis = new HashMap<>();
        String query = "SELECT * FROM APITAXI ORDER BY ID_TAXI";
        try (Statement stmt = dbConnect.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                int id = rs.getInt("ID_TAXI");
                String immat = rs.getString("IMMATRICULATION");
                taxis.put(id, immat);
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);

        }
        return taxis;
    }

    //TODO use this / or use class -> list -> filter list + stream ? (java 8)
    @Override
    public List<Client> getClientOfTaxi(Taxi taxi) {
        try (CallableStatement cs = dbConnect.prepareCall("{? = call client_by_taxi(?)}")) {

            cs.registerOutParameter(1, Types.REF_CURSOR);
            cs.setInt(2, taxi.getIdTaxi());
            cs.execute();

            ResultSet rs = (ResultSet) cs.getObject(1);
            Set<Client> sClients = new HashSet<>();
            List<Client> clients;

            while (rs.next()) {
                int id = rs.getInt("id_client");
                String nom = rs.getString("nom_cli");
                String prenom = rs.getString("prenom_cli");
                String mail = rs.getString("mail");
                String tel = rs.getString("tel");

                try {
                    Client cli = new Client.ClientBuilder()
                            .setIdClient(id)
                            .setNom(nom)
                            .setPrenom(prenom)
                            .setMail(mail)
                            .setTel(tel)
                            .build();
                    sClients.add(cli);
                } catch (Exception e) {
                    logger.error("Erreur lors de la lecture du client: " + e);
                    e.printStackTrace();
                }
            }

            clients = new ArrayList<>(sClients);
            return clients;
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
            return null;
        }
    }

}
