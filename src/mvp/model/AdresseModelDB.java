package mvp.model;

import myconnections.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresseModelDB implements DAOAdresse, AdresseSpecial {
    private Connection dbConnect;
    private static final Logger logger = LogManager.getLogger(AdresseModelDB.class);

    public AdresseModelDB() {
        dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            logger.info("Erreur de connection");
            System.exit(1);
        }
    }

    @Override
    public Adresse addAdresse(Adresse adresse) {
        String query1 = "INSERT INTO APIADRESSE(cp,localite,rue,num)" + "VALUES(?,?,?,?)";
        String query2 = "SELECT * FROM APIADRESSE WHERE cp=? AND localite=? AND rue=? AND num=?";

        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);
        ) {
            pstm1.setInt(1, adresse.getCp());
            pstm1.setString(2, adresse.getLocalite());
            pstm1.setString(3, adresse.getRue());
            pstm1.setString(4, adresse.getNum());
            int nl1 = pstm1.executeUpdate();

            if (nl1 == 1) {
                pstm2.setInt(1, adresse.getCp());
                pstm2.setString(2, adresse.getLocalite());
                pstm2.setString(3, adresse.getRue());
                pstm2.setString(4, adresse.getNum());
                ResultSet rs = pstm2.executeQuery();
                if (rs.next()) {
                    int idAdresse = rs.getInt("ID_ADRESSE");
                    adresse.setIdAdr(idAdresse);
                    return adresse;
                } else {
                    logger.info("Error");
                    return null;
                }
            } else return null;

        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout d'une adresse");
            return null;
        }

    }

    @Override
    public boolean removeAdresse(Adresse adresse) {
        String query = "DELETE FROM APIADRESSE WHERE ID_ADRESSE = ?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setInt(1,adresse.getIdAdr());
            int nl = pstm.executeUpdate();

            if(nl!=0) return true;
            else return false;
        } catch (SQLException e) {
            logger.error("Erreur lors de l'effacement d'une adresse");
            return false;
        }
    }

    @Override
    public Adresse updateAdresse(Adresse adresse) {
        String query = "UPDATE APIADRESSE SET cp=?, localite=?,rue=?,num=? WHERE id_adresse = ?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setInt(1,adresse.getCp());
            pstm.setString(2,adresse.getLocalite());
            pstm.setString(3,adresse.getRue());
            pstm.setString(4,adresse.getNum());
            pstm.setInt(5,adresse.getIdAdr());
            int nl = pstm.executeUpdate();
            if(nl!=0) return readAdresse(adresse.getIdAdr());
            else return null;
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour d'une adresse");
            return null;
        }

    }

    @Override
    public Adresse readAdresse(int idAdresse) {
        Adresse tmpAdr;
        String query = "SELECT * FROM APIADRESSE WHERE ID_ADRESSE = ?";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setInt(1,idAdresse);
            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                int idAdres = rs.getInt(1);
                int cp = rs.getInt(2);
                String localite = rs.getString(3);
                String rue = rs.getString(4);
                String num = rs.getString(5);
                tmpAdr = new Adresse(idAdres,cp,localite,rue,num);
                return tmpAdr;
            }
            else return null;

        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche de l'adresse");
            return null;
        }

    }

    @Override
    public List<Adresse> getAdresses() {
        List<Adresse> lAdresses = new ArrayList<>();
        Adresse adr;
        String query = "SELECT * FROM apiadresse ORDER BY id_adresse";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                int idAdre = rs.getInt(1);
                int cp = rs.getInt(2);
                String local = rs.getString(3);
                String rue = rs.getString(4);
                String num = rs.getString(5);
                adr = new Adresse(idAdre,cp,local,rue,num);
                lAdresses.add(adr);
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des adresses");
            return null;
        }

        return lAdresses;
    }

    @Override
    public List<Adresse> getAdressesByCP(int cp){
        List<Adresse> lAdresses = new ArrayList<>();
        Adresse adr;
        String query = "SELECT * FROM apiadresse WHERE cp=? ORDER BY id_adresse";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setInt(1,cp);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                int idAdre = rs.getInt(1);
                int cpost = rs.getInt(2);
                String local = rs.getString(3);
                String rue = rs.getString(4);
                String num = rs.getString(5);
                adr = new Adresse(idAdre,cpost,local,rue,num);
                lAdresses.add(adr);
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des adresses par code postal");
            return null;
        }

        return lAdresses;
    }

    @Override
    public List<Adresse> getAdressesByLocalite(String localite){
        List<Adresse> lAdresses = new ArrayList<>();
        Adresse adr;
        String query = "SELECT * FROM apiadresse WHERE LOWER(localite)=LOWER(?) ORDER BY id_adresse";

        try(PreparedStatement pstm = dbConnect.prepareStatement(query)){
            pstm.setString(1,localite);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                int idAdre = rs.getInt(1);
                int cpost = rs.getInt(2);
                String local = rs.getString(3);
                String rue = rs.getString(4);
                String num = rs.getString(5);
                adr = new Adresse(idAdre,cpost,local,rue,num);
                lAdresses.add(adr);
            }

        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des adresses par localité");
            return null;
        }

        return lAdresses;
    }
}
