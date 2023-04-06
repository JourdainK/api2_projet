package mvp.model;

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

public class TaxiModelDB implements DAOTaxi, TaxiSpecial{
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
    public Taxi updateTaxi(Taxi taxi) {
        String query = "UPDATE apitaxi SET immatriculation=?, nbremaxpassagers=?, prixkm=? WHERE id_taxi=?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setString(1,taxi.getImmatriculation());
            pstm.setInt(2,taxi.getNbreMaxPassagers());
            pstm.setDouble(3,taxi.getPrixKm());
            pstm.setInt(4,taxi.getIdTaxi());
            int v = pstm.executeUpdate();
            if(v!=0) return readTaxi(taxi.getIdTaxi());
            else return null;
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour (" + taxi.getIdTaxi() + ") , Erreur SQL + " + e);
            return null;
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
                int idtaxi = rs.getInt(1);
                String immat = rs.getString(2);
                int nbrMaxPas = rs.getInt(3);
                double prixKm = rs.getDouble(4);
                tmpTaxi = new Taxi(idtaxi,nbrMaxPas,immat,prixKm);
                ltaxis.add(tmpTaxi);

            }

        } catch (SQLException e) {
            System.err.println("Erreur SQL : " + e);
            logger.error("Erreur lors de la récupération des taxis : " + e);
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


    // fixed the problem : https://stackoverflow.com/questions/5963472/java-sql-sqlexception-fail-to-convert-to-internal-representation
    public Taxi readTaxi(int idTaxi) {
        Taxi tmpTaxi;
        String query = "SELECT * FROM APITAXI WHERE ID_TAXI = ?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setInt(1, idTaxi);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                int taxiId = rs.getInt(1);
                String immat = rs.getString(2);
                int nbrPassMax = rs.getInt(3);
                double prixKm = rs.getDouble(4);
                tmpTaxi = new Taxi(taxiId, nbrPassMax, immat, prixKm);
                return tmpTaxi;
            }
            else {
                return null;
            }
        } catch (SQLException e) {
            logger.error("Erreur lors de la lecture du client (" + idTaxi + ") Erreur SQL : " + e);
            return  null;
        }

    }

    @Override
    public List<Taxi> taxisUtilisés(Client client) {
        List<Taxi> taxisUsed = new ArrayList<>();
        Set<Integer> idTaxis = new HashSet<>();

        int idClient = client.getIdclient();
        String query1 = "SELECT * FROM api_taxi_used WHERE id_client = ?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query1)){
            pstm.setInt(1,idClient);
            ResultSet rs = pstm.executeQuery(query1);
            if(rs.next()){
                idTaxis.add(rs.getInt("ID_TAXI"));
            }
        } catch (SQLException e){
            logger.error("Erreur lors de la requête taxi utilisé : " + e);
        }
        Taxi tmpTaxi;
        for(Integer s : idTaxis){
            tmpTaxi = readTaxi(s);
            taxisUsed.add(tmpTaxi);
        }

        return taxisUsed;
    }

    //TODO ? built client , adr all , adr retour....
    @Override
    public List<Location> allLocTaxi(Taxi taxi) {
        int idTaxi = taxi.getIdTaxi();
        Location tmpLoc;
        List<Location> lLoc = new ArrayList<>();
        String query1 = "SELECT * FROM apilocation WHERE id_taxi = ?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query1)){
            pstm.setInt(1,idTaxi);
            ResultSet rs = pstm.executeQuery(query1);

            if(rs.next()){
                int idLoc = rs.getInt(1);
                LocalDate dateloc = rs.getDate(2).toLocalDate();
                int kmTotal = rs.getInt(3);
                int nbrPass = rs.getInt(4);
                //BigDecimal tot = rs.getBigDecimal(5);
                double total = rs.getInt(5);
                int idTax = rs.getInt(6);
                int adAll = rs.getInt(7);
                int adRet = rs.getInt(8);
                int idCli = rs.getInt(9);
                Taxi tax = readTaxi(idTax);

                //for each Locat ? pretty heavy for one deman -> check if better way
                //tmpLoc = new Location(idLoc,kmTotal,nbrPass,dateloc.toString(),tax,)
                //dev them int thei modelDB -> import -> use method here ?
                //DO crud modelDB all -> then special all



            }

        } catch (SQLException e){
            logger.error("Erreur lors de la recherche des locations d'un taxi : " + e);
        }

        return null;
    }

    @Override
    public List<Adresse> allAdressTaxi(Taxi taxi) {
        return null;
    }
}
