package exercices1JDBC;

import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static utilitaires.Utilitaire.saisie;

public class SQLTaxiCreate {

    public SQLTaxiCreate() {
        Scanner sc = new Scanner(System.in);
        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("Connexion établie");

        String query1 = "INSERT INTO APITAXI(immatriculation,nbremaxpassagers,prixkm)"
                + "VALUES (?,?,?)";

        String query2 = "SELECT * FROM APITAXI WHERE immatriculation=? AND nbremaxpassagers=? AND prixkm=?";

        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2);) {

            System.out.println(" --- Nouveau Taxi ---");
            System.out.println("Saisir l'immatriculation : ");
            String immat = saisie("^[T]{1}\\-([L]{1}||[X]{1})[A-Z]{2}\\-[0-9]{3}$", "Erreur de saisie, veuillez saisir une immatriculation de type 'T-XXX-000' ou 'T-LXX-000'\nSaisir l'immatriculation : ");
            int maxPass = -1;
            do {
                System.out.println("Saisir le nombre de passagers maximum : ");
                String maxPass1 = saisie("[0-9]{1,3}", "Erreur de saisie ");
                maxPass = Integer.parseInt(maxPass1);
            } while (maxPass < 1);

            double prixkm = -1;
            do{
                System.out.println("Saisir prix au km :");
                String prixkm1 = saisie("[0-9]{0,10}[.][0-9]{0,2}|[0-9]{0,10}", "Erreur de saisie, veuillez saisir un nombre réel (séparée d'un point) supérieur à 0\nSaisir le prix au km : ");
                prixkm = Double.parseDouble(prixkm1);
                if(prixkm <= 0){
                    System.out.println("Erreur, le prix au km doit étre supérieur à 0");
                }
            }while(prixkm <= 0);


            pstm1.setString(1,immat);
            pstm1.setInt(2,maxPass);
            pstm1.setDouble(3,prixkm);

            int nl1 = pstm1.executeUpdate();
            System.out.println(nl1 + " ligne insérée");
            pstm2.setString(1,immat);
            pstm2.setInt(2,maxPass);
            pstm2.setDouble(3,prixkm);

            try(ResultSet rs = pstm2.executeQuery()){
                if(rs.next()){
                    int nc = rs.getInt("ID_TAXI");
                    System.out.println("Numéro du taxi inséré : " + nc + "\t\tImmatriculation : " + rs.getString("IMMATRICULATION") + "\t\tPassagers maximum : " + rs.getInt("NBREMAXPASSAGERS") + "\t\tPrix au km : " + rs.getDouble("PRIXKM"));
                }
            }catch(SQLException e){
                System.out.println("Erreur SQL : " + e);
            }



        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }

        DBConnection.closeConnection();


    }

}

