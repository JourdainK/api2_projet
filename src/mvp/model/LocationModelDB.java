package mvp.model;

import myconnections.DBConnection;
import oracle.jdbc.proxy.annotation.Pre;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LocationModelDB implements DAOLocation,LocationSpecial{
    private static final Logger logger = LogManager.getLogger(LocationModelDB.class);
    private Connection dbConnect;

    public LocationModelDB(){
        dbConnect = DBConnection.getConnection();
        if(dbConnect==null){
            logger.error("Erreur de connexion, Location");
            System.exit(1);
        }
        else{
            logger.info("Connexion étable - Location");
        }
    }

    @Override
    public Location addLocation(Location location) {
        String insertQuery = "INSERT INTO apilocation(dateloc, kmtotal, nbrpassagers, id_taxi, id_adresse, id_adresse_1, id_client) VALUES (?,?,?,?,?,?,?)";
        String query = "SELECT id_location FROM APILOCATION WHERE dateloc = ? AND kmtotal = ? AND nbrpassagers = ? AND id_taxi =? AND id_adresse = ? AND id_adresse_1 = ? AND id_client = ?";
        try(PreparedStatement pstm1 = dbConnect.prepareStatement(insertQuery);
            PreparedStatement pstm2 = dbConnect.prepareStatement(query);){
            pstm1.setString(1,location.getDateLoc());
            pstm1.setInt(2,location.getKmTotal());
            pstm1.setInt(3,location.getNbrePassagers());
            pstm1.setInt(4,location.getVehicule().getIdTaxi());
            pstm1.setInt(5,location.getAdrDebut().getIdAdr());
            pstm1.setInt(6,location.getAdrFin().getIdAdr());
            pstm1.setInt(7,location.getClient().getIdclient());

            int line = pstm1.executeUpdate();

            if(line==1){
                pstm2.setString(1,location.getDateLoc());
                pstm2.setInt(2,location.getKmTotal());
                pstm2.setInt(3,location.getNbrePassagers());
                pstm2.setInt(4,location.getVehicule().getIdTaxi());
                pstm2.setInt(5,location.getAdrDebut().getIdAdr());
                pstm2.setInt(6,location.getAdrFin().getIdAdr());
                pstm2.setInt(7,location.getClient().getIdclient());
                ResultSet rs = pstm2.executeQuery();
                if(rs.next()){
                    int idLoc = rs.getInt("id_location");
                    location.setIdLoc(idLoc);
                    return location;
                }
                else return null;
            }else {
                logger.error("Erreur lors de l'ajout/création d'une location");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de location : " + e);
            //to delete
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean removeLocation(Location location) {
        String query = "DELETE FROM APILOCATION WHERE id_location = ?";
        //TODO pick up here
        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){

        }catch (SQLException e){

        }

        return false;
    }

    @Override
    public Location updateLocation(Location location) {
        return null;
    }

    @Override
    public Location readLocation(int idLocation) {
        return null;
    }

    @Override
    public List<Location> getAllLocation() {
        return null;
    }

    @Override
    public boolean addClient(Location loc, Client client) {
        return false;
    }

    @Override
    public boolean addAdresse(Adresse adresse) {
        return false;
    }
}
