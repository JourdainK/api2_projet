package mvp.model.client;

import mvp.model.DAO;
import mvp.model.taxi.TaxiModelDB;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClientModelDB implements DAO<Client>, SpecialClient {
    private static Connection dbConnect;
    private static final Logger logger = LogManager.getLogger(TaxiModelDB.class);

    public ClientModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            logger.info("Erreur de connexion (client)");

            System.exit(1);
        }
    }

    @Override
    public Client add(Client client) {
        //new client come with no idclient -> return created one in DB , WITH id client
        String insertQuery = "INSERT INTO APITCLIENT(mail, nom_cli, prenom_cli, tel) VALUES (?,?,?,?)";
        String query = "SELECT * FROM APITCLIENT WHERE mail = ?";

        try (PreparedStatement pstm1 = dbConnect.prepareStatement(insertQuery);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query);
        ) {
            pstm1.setString(1, client.getMail());
            pstm1.setString(2, client.getNom());
            pstm1.setString(3, client.getPrenom());
            pstm1.setString(4, client.getTel());

            int line = pstm1.executeUpdate();
            if (line == 1) {
                pstm2.setString(1, client.getMail());
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int idclient = rs.getInt(1);
                    client.setIdClient(idclient);
                    return client;
                } else {
                    return null;
                }
            } else {
                logger.error("Erreur lors de l'ajout d'un nouveau client  " + client.getNom());
                return null;
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout d'un client \tSQL : " + e);
            return null;
        }

    }

    @Override
    public boolean remove(Client client) {
        String query = "DELETE FROM APITCLIENT WHERE ID_CLIENT = ? AND MAIL = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, client.getIdclient());
            pstm.setString(2, client.getMail());
            int line = pstm.executeUpdate();

            if (line != 0) return true;
            else return false;

        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression du client " + client.getIdclient() + " " + client.getNom());
            return false;
        }

    }

    @Override
    public Client update(Client client) {
        String update = "UPDATE apitclient SET mail=?, nom_cli=?, prenom_cli=?, tel=? WHERE id_client=?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(update)) {
            pstm.setString(1, client.getMail());
            pstm.setString(2, client.getNom());
            pstm.setString(3, client.getPrenom());
            pstm.setString(4, client.getTel());
            pstm.setInt(5, client.getIdclient());

            int line = pstm.executeUpdate();

            if (line != 0) return read(client);
            else return null;
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour du client : " + client.getIdclient() + " " + client.getNom());
            return null;
        }

    }

    @Override
    public Client read(Client client) {
        Client cli;
        String query = "SELECT * FROM APITCLIENT WHERE id_client = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, client.getIdclient());
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
                    List<Location> locations = getLocationsOfClient(cli);
                    cli.setListLocations(locations);
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
    public Client readbyId(int idClient) {
        Client cli;
        String query = "SELECT * FROM APITCLIENT WHERE id_client = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, idClient);
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
                    List<Location> locations = getLocationsOfClient(cli);
                    cli.setListLocations(locations);
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
    public List<Client> getAll() {
        List<Client> lClients = new ArrayList<>();
        Client tmpClient;
        String query = "SELECT * FROM apitclient ORDER BY id_client";

        try (Statement stmt = dbConnect.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {
            while (rs.next()) {
                int idcli = rs.getInt("ID_CLIENT");
                String mail = rs.getString("MAIL");
                String nomCLi = rs.getString("NOM_CLI");
                String prenCli = rs.getString("PRENOM_CLI");
                String telCli = rs.getString("TEL");
                try {
                    tmpClient = new Client.ClientBuilder()
                            .setIdClient(idcli)
                            .setMail(mail)
                            .setNom(nomCLi)
                            .setPrenom(prenCli)
                            .setTel(telCli)
                            .build();
                    List<Location> lLocCli = getLocationsOfClient(tmpClient);
                    tmpClient.setListLocations(lLocCli);
                    lClients.add(tmpClient);
                } catch (Exception e) {
                    logger.error("Erreur lors du listing clients : " + e);
                    //to delete
                    e.printStackTrace();

                }
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des clients : " + e);
        }

        return lClients;
    }

    @Override
    public List<Location> getLocationsOfClient(Client client) {
        List<Location> lLocations = new ArrayList<>();
        Location tmpLocation;
        String query = "SELECT * FROM apilocation WHERE id_client = ? ORDER BY id_location";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, client.getIdclient());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                int idLoc = rs.getInt("ID_LOCATION");
                LocalDate dateLoc = LocalDate.parse(rs.getString("DATELOC"));
                int kmtot = rs.getInt("KMTOT");
                int nbrPass = rs.getInt("NBRPASSAGERS");
                double prix = rs.getDouble("TOTAL");
                int idVeh = rs.getInt("ID_TAXI");
                int idDepart = rs.getInt("ID_ADRESSE");
                int idArrivee = rs.getInt("ID_ADRESSE_1");
                int idCli = rs.getInt("ID_CLIENT");
                String dateRet = rs.getString("DATE_RETOUR");
                Client cli = readbyId(idCli);
                Taxi taxi = getTaxiByID(idVeh);
                Adresse adrDepart = getAdresseByID(idDepart);
                Adresse adrArrivee = getAdresseByID(idArrivee);
                try {
                    tmpLocation = new Location.LocationBuilder()
                            .setIdLoc(idLoc)
                            .setDateLoc(dateLoc)
                            .setKmTot(kmtot)
                            .setNbrePassagers(nbrPass)
                            .setTotal(prix)
                            .setVehicule(taxi)
                            .setAdrDebut(adrDepart)
                            .setAdrFin(adrArrivee)
                            .setClient(cli)
                            .build();
                    lLocations.add(tmpLocation);
                } catch (Exception e) {
                    logger.error("Erreur lors du listing des locations : " + e);
                    //to delete
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des locations : " + e);
        }
        return lLocations;
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

}
