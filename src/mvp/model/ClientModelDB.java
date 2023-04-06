package mvp.model;

import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientModelDB implements DAOClient{
    private Connection dbConnect;

    private static final Logger logger = LogManager.getLogger(TaxiModelDB.class);

    public ClientModelDB(){
        dbConnect = DBConnection.getConnection();
        if(dbConnect==null){
            logger.info("Erreur de connexion (client)");

            System.exit(1);
        }
    }

    @Override
    public Client addClient(Client client) {
        //new client come with no idclient -> return created one in DB , WITH id client
        String insertQuery = "INSERT INTO APITCLIENT(mail, nom_cli, prenom_cli, tel) VALUES (?,?,?,?)";
        String query = "SELECT * FROM APITCLIENT WHERE mail = ?";

        try(PreparedStatement pstm1 = dbConnect.prepareStatement(insertQuery);
            PreparedStatement pstm2 = dbConnect.prepareStatement(query);
        ){
            pstm1.setString(1,client.getMail());
            pstm1.setString(2,client.getNom());
            pstm1.setString(3, client.getPrenom());
            pstm1.setString(4, client.getTel());

            int line = pstm1.executeUpdate();
            if(line==1){
                pstm2.setString(1,client.getMail());
                ResultSet rs = pstm2.executeQuery();
                if(rs.next()){
                    int idclient = rs.getInt(1);
                    client.setIdClient(idclient);
                    return client;
                }
                else {
                    return null;
                }
            }
            else {
                logger.error("Erreur lors de l'ajout d'un nouveau client  " + client.getNom());
                return null;
            }
        }catch (SQLException e){
            logger.error("Erreur lors de l'ajout d'un client \tSQL : " + e);
            return null;
        }
        //TODO hybride -> when crud LOCAT DONE -> get all LOCAT of that client
    }

    @Override
    public boolean removeClient(Client client) {
        String query = "DELETE FROM APITCLIENT WHERE ID_CLIENT = ? AND MAIL = ?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setInt(1,client.getIdclient());
            pstm.setString(2,client.getMail());
            int line = pstm.executeUpdate();

            if(line!=0) return true;
            else return false;

        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression du client " + client.getIdclient() + " " + client.getNom());
            return false;
        }

    }

    @Override
    public Client updateClient(Client client) {
        String update = "UPDATE apitclient SET mail=?, nom_cli=?, prenom_cli=?, tel=? WHERE id_client=?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(update)){
            pstm.setString(1,client.getMail());
            pstm.setString(2,client.getNom());
            pstm.setString(3,client.getPrenom());
            pstm.setString(4,client.getTel());
            pstm.setInt(5,client.getIdclient());

            int line = pstm.executeUpdate();

            if(line!=0) return readClient(client.getIdclient());
            else return null;
        }catch (SQLException e){
            logger.error("Erreur lors de la mise à jour du client : " + client.getIdclient()  + " " + client.getNom());
            return null;
        }

    }

    @Override
    public Client readClient(int idClient) {
        Client cli;
        String query = "SELECT * FROM APITCLIENT WHERE id_client = ?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            //TODO !!!!!!!!a

        }catch (SQLException e){
            logger.error("Erreur lors de la recherche SQL :"  + e );
        }

        return null;
    }

    @Override
    public List<Client> getClients() {
        List<Client> lClients = new ArrayList<>();
        Client tmpClient;
        String query = "SELECT * FROM apitclient ORDER BY id_client";

        try (Statement stmt = dbConnect.createStatement();
            ResultSet rs = stmt.executeQuery(query);){
            while(rs.next()){
                int idcli = rs.getInt("ID_CLIENT");
                String mail = rs.getString("MAIL");
                String nomCLi = rs.getString("NOM_CLI");
                String prenCli = rs.getString("PRENOM_CLI");
                String telCli = rs.getString("TEL");
                tmpClient = new Client(idcli,mail,nomCLi,prenCli,telCli);
                lClients.add(tmpClient);
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des clients : " + e);
        }

        return lClients;
    }

    //TODO specials clients
}