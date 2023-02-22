package exercices1JDBC;

import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SQL02 {
    public void ex02(){
        Scanner sc = new Scanner(System.in);
        Connection dbConnect = DBConnection.getConnection();
        if(dbConnect==null){
            System.exit(1);
        }
        System.out.println("Connexion établie");
        try(PreparedStatement pstm2 = dbConnect.prepareStatement("SELECT * FROM APITAXI WHERE ID_TAXI = ?")){
            System.out.println("Saisir l'id du taxi : ");
            String idInput1;
            int idInput;
            do{
                idInput1 = saisie("[0-9]*", "Veuillez saisir un nombre entier , positif.\nSaisir l'id du taxi : ");
                idInput = Integer.parseInt(idInput1);
            }while(idInput<0);
            pstm2.setInt(1,idInput);
            boolean check = false;
            try(ResultSet rs = pstm2.executeQuery()){
                while(rs.next()){
                    check = true;
                    int id = rs.getInt("ID_TAXI");
                    String immat = rs.getString("IMMATRICULATION");
                    int pasger = rs.getInt("NBREMAXPASSAGERS");
                    double prixKm = rs.getDouble("PRIXKM");
                    System.out.println("--- taxi trouvé ----");
                    System.out.println("id : " + id + "\t\tImmatriculation : " + immat);
                    System.out.println("Nombre de passagers maximum : " + pasger + "\t\tPrix au kilomètre : " + prixKm + "\n\n");
                }
                if(!check){
                    System.out.println("Taxi inconnu");
                }

        } catch (SQLException e) {
            System.out.println("Erreur Query SQl " + e);
        }

        }
        catch (SQLException e){
            System.out.println("Erreur SQL " + e);
        }

        DBConnection.closeConnection();

    }

    public String saisie(String regex, String message) {
        Scanner sc = new Scanner(System.in);
        boolean check = false;
        String phrase;
        do {
            phrase = sc.nextLine();
            if (phrase.matches(regex)) {
                check = true;
            } else {
                System.out.println(message);
            }
        } while (!check);
        return phrase;
    }
}


