package mvp.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Taxi;
import myconnections.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaxiModelDB implements DAOTaxi{
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
    public Taxi addTaxi(Taxi taxi) {
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
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e);
            return null;
        }
    }

    @Override
    public boolean removeTaxi(Taxi taxi) {
        String query = "DELETE FROM APITAXI WHERE ID_TAXI = ? AND IMMATRICULATION = ?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setInt(1, taxi.getIdTaxi());
            pstm.setString(2,taxi.getImmatriculation());
            int nl = pstm.executeUpdate();

            if(nl!=0) return true;
            else return false;
        }catch (SQLException e){
            System.err.println("Erreur SQL : " + e);
            return false;
        }
    }

    @Override
    public List<Taxi> getTaxis() {
        List<Taxi> ltaxis = new ArrayList<>();
        Taxi tmpTaxi;
        String query = "SELECT * FROM APITAXI ORDER BY ID_TAXI";

        try (Statement stmt = dbConnect.createStatement();
             ResultSet rs = stmt.executeQuery(query);){
            while(rs.next()){
                int idclient = rs.getInt(1);
                String immat = rs.getString(2);
                int nbrMaxPas = rs.getInt(3);
                double prixKm = rs.getDouble(4);
                tmpTaxi = new Taxi(idclient,nbrMaxPas,immat,prixKm);
                ltaxis.add(tmpTaxi);

            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e);
        }

        tmpTaxi = null;
        return ltaxis;
    }

    public Map<Integer, String> getMapTaxis(){
        Map<Integer, String> taxis = new HashMap<>();

        String query = "SELECT * FROM APITAXI ORDER BY ID_TAXI";
        try (Statement stmt = dbConnect.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("ID_TAXI");
                String immat = rs.getString("IMMATRICULATION");
                taxis.put(id, immat);
            }
            return taxis;

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
            return null;
        }
    }


    public boolean modifTaxi(Taxi taxi){
        String update = "UPDATE apitaxi set immatriculation=?,nbreMaxPassagers =?, prixKm=? WHERE idTaxi =?";
        int n=0;

        try(PreparedStatement pstm = dbConnect.prepareStatement(update)){
            pstm.setString(1,taxi.getImmatriculation());
            pstm.setInt(2,taxi.getNbreMaxPassagers());
            pstm.setDouble(3,taxi.getPrixKm());
            pstm.setInt(4,taxi.getIdTaxi());
            n = pstm.executeUpdate();



        }catch (SQLException e){
            logger.error("Erreur lors de la modification du taxi " +  taxi.getIdTaxi() + " Erreur SQL : " + e);
        }

        if(n!=0) return true;
        else return false;
    }


    public Taxi readTaxi(int idTaxi){
        String query = "SELECT * FROM APITAXI WHERE idTaxi = ?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setInt(1, idTaxi);
            ResultSet rs = pstm.executeQuery(query);
            if(rs.next()){

            }
        }catch(SQLException e){
            logger.error("Erreur lors de la lecture du client (" + idTaxi + ") Erreur SQL : " + e);
        }


    }

    //TODO add methods asked by teacher's homework
    //TODO Test all
}
