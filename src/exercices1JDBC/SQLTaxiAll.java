package exercices1JDBC;

import myconnections.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class SQLTaxiAll {

    public SQLTaxiAll() {

        Scanner sc = new Scanner(System.in);
        Connection dbconnect = DBConnection.getConnection();
        if (dbconnect == null) {
            System.exit(1);
        }
        System.out.println("Connexion établie");
        String query = "SELECT * FROM APITAXI ORDER BY ID_TAXI";
        try (Statement stmt = dbconnect.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.println("ID : " + rs.getInt("ID_TAXI") + "\t\tImmatriculation : " + rs.getString("IMMATRICULATION") + "\t\tNombre de passagers maximum : " + rs.getInt("NBREMAXPASSAGERS") + "\t\tPrix au kilomètre : " + rs.getDouble("PRIXKM"));
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }
        DBConnection.closeConnection();

    }
}
