package mvp.model.location;

import mvp.model.DAO;
import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;


public class LocationModelDB implements DAO<Location>, LocationSpecial {
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
    public Location add(Location location) {
        String insertQuery = "INSERT INTO apilocation(dateloc, kmtotal, nbrpassagers, id_taxi, id_adresse, id_adresse_1, id_client) VALUES (?,?,?,?,?,?,?)";
        String query = "SELECT MAX(id_location) FROM APILOCATION WHERE dateloc = ? AND id_client = ?";
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
                pstm2.setInt(2,location.getClient().getIdclient());
                ResultSet rs = pstm2.executeQuery();
                if(rs.next()){
                    int idLoc = rs.getInt("id_location");
                    location.setIdLoc(idLoc);
                    return location;
                }
                else return null;
            }else {
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

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setInt(1,location.getIdLoc());
            int line = pstm.executeUpdate();
            if(line!=0) return true;
            else return false;
        }catch (SQLException e){
            logger.error("Erreur lors de la suppression de la location : " + e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Location update(Location location) {
        String update = "UPDATE apilocation SET dateloc=?, kmtotal=?, nbrpassagers=?,total=?,id_taxi=?, id_adresse=?, id_adresse_1=?, id_client=? WHERE id_location=?";
        //check Date.valueOf
        try(PreparedStatement pstm = dbConnect.prepareStatement(update)){
            pstm.setDate(1,(Date.valueOf(location.getDateLoc())));
            pstm.setInt(2,location.getKmTotal());
            pstm.setInt(3,location.getNbrePassagers());
            pstm.setBigDecimal(4, BigDecimal.valueOf(location.getTotal()));
            pstm.setInt(5,location.getVehicule().getIdTaxi());
            pstm.setInt(6,location.getAdrDebut().getIdAdr());
            pstm.setInt(7,location.getAdrFin().getIdAdr());
            pstm.setInt(8,location.getClient().getIdclient());
            pstm.setInt(9,location.getIdLoc());

            int line = pstm.executeUpdate();
            if(line!=0) return read(location);
            else return null;
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour de la location : " + e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Location read(Location locat) {
        Location location;
        String query = "SELECT * FROM apilocation WHERE id_location=?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setInt(1,locat.getIdLoc());
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                int idLoc = rs.getInt("ID_LOCATION");
                String dateloc = String.valueOf(rs.getDate("DATELOC"));
                int kmtot = rs.getInt("KMTOTAL");
                int nbrpass = rs.getInt("NBRPASSAGERS");
                double total = rs.getDouble("TOTAL");
                int idTaxi = rs.getInt("ID_TAXI");
                int idAdreDepart = rs.getInt("ID_ADRESSE");
                int idAdreFin = rs.getInt("ID_ADRESS_1");
                int idClient = rs.getInt("ID_CLIENT");
                try{
                    location = new Location.LocationBuilder().setIdLoc(idLoc)
                            .setDateLoc(dateloc).setKmTot(kmtot).setNbrePassagers(nbrpass)
                            .setTotal(total).build();
                }catch (Exception e){

                }
                /*

                //TODO check waht to do ? add object ?
                try{
                    Client cliLocat = readClientStat(idClient);
                }catch (Exception f){
                    logger.error("Erreur lors de la recupération du client de la commande n° " + idLoc + " Erreur : " + f);
                    f.printStackTrace();
                }

                 */



            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de la location : " + e);
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public Location readbyId(int id){
        //TODO readbyId -> Location
        return null;
    }

    @Override
    public List<Location> getAll() {
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

    //TODO special static return location by id -> import client _> getAll
}
