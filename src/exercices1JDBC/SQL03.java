package exercices1JDBC;

import myconnections.DBConnection;

import java.sql.*;
import java.util.Scanner;

public class SQL03 {
    public void ex01(){
        Scanner sc = new Scanner(System.in);
        String immatric;
        Connection dbConnect = DBConnection.getConnection();
        if(dbConnect==null){
            System.exit(1);
        }
        System.out.println("Connexion établie");
        System.out.println("Saisir l'immatriculation du taxi : ");
        immatric = saisie("^[T]{1}\\-([L]{1}||[X]{1})[A-Z]{2}\\-[0-9]{3}$","Erreur, veuillez saisir une immatriculation du type (T-XXX-000)\nSaisir l'immatriculation du taxi : ");
        String query = "SELECT * FROM apitaxi WHERE IMMATRICULATION = '" + immatric + "'";
        //System.out.println(query);
        try(Statement stmt = dbConnect.createStatement();
            ResultSet rs = stmt.executeQuery(query);)
        {
            boolean trouve = false;
            while(rs.next()){
                trouve=true;
                int idChosenTaxi = rs.getInt("ID_TAXI");
                String immatri = rs.getString("IMMATRICULATION");
                System.out.println("test idchosentaxi " + idChosenTaxi);
                System.out.println("Test immatri "  + immatri);
                System.out.println("-- Liste des clients ayant utilisé le taxi  " + immatric + "\tNuméro d'identification : " + idChosenTaxi);
                String query2 = "SELECT * FROM API_TAXI_USED WHERE ID_TAXI = " + idChosenTaxi;
                //System.out.println(query2);
                try(Statement stmt2 = dbConnect.createStatement();
                    ResultSet rs2 = stmt2.executeQuery(query2);)
                {
                    boolean checkClient = false;
                    while(rs2.next()){
                        checkClient = true;
                        String nom, prenom;
                        int idCli;


                        idCli = rs2.getInt("ID_CLIENT");
                        nom = rs2.getString("NOM_CLI");

                        prenom = rs2.getString("PRENOM_CLI");

                        System.out.println("\tNom : " + nom + "\tPrenom : " + prenom + "\tNuméro client : " + idCli);

                    }

                }catch (SQLException e2){
                    System.out.println("Erreur SQL => " + e2);
                }

            }
            if(!trouve){
                System.out.println("Taxi inconnu");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
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

    public static void main(String[] args) {
        SQL03 s3 = new SQL03();
        s3.ex01();
    }
}
