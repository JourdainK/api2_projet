package exercices1JDBC;

import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SQL01 {

    public void ex01(){
        Scanner sc = new Scanner(System.in);
        Connection dbConnect = DBConnection.getConnection();
        if(dbConnect == null){
            System.exit(1);
        }
        System.out.println("Connexion établie");
        try(PreparedStatement pstm = dbConnect.prepareStatement("SELECT * FROM APITCLIENT WHERE NOM_CLI = ?")){
            System.out.println("Nom du client recherché : ");
            String nomrech = sc.nextLine();
            pstm.setString(1,nomrech);
            boolean checkQuery = false;

            try(ResultSet rs = pstm.executeQuery()){
                while(rs.next()){
                    checkQuery = true;
                    int id = rs.getInt("ID_CLIENT");
                    String mail = rs.getString("MAIL");
                    String nom = rs.getString("NOM_CLI");
                    String prenom = rs.getString("PRENOM_CLI");
                    String tel = rs.getString("TEL");
                    System.out.println(" --- Client trouvé ---");
                    System.out.println("id : " + id + "\tMail : " + mail + "\nNom : " + nom + "\t\tprénom : " + prenom + "\ntéléphone : " + tel);
                }
            }
            catch (SQLException e) {
                System.out.println("Erreur query Sql : " + e);
            }
        }
        catch (SQLException e){
            System.out.println("Erreur SQL : " + e);
        }
        DBConnection.closeConnection();
    }

    public static void main(String[] args) {
        SQL01 test = new SQL01();
        test.ex01();
    }
}
