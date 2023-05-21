package exercices1JDBC;

import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SQLTaxiAllHashMap {
    private Map<Integer, String> taxis = new HashMap<>();

    public SQLTaxiAllHashMap() {
        Connection dbconnect = DBConnection.getConnection();
        if (dbconnect == null) {
            System.exit(1);
        }

        String query = "SELECT * FROM APITAXI ORDER BY ID_TAXI";
        try (Statement stmt = dbconnect.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("ID_TAXI");
                String immat = rs.getString("IMMATRICULATION");
                taxis.put(id, immat);
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }


    }

    public Map<Integer, String> getTaxis() {
        return taxis;
    }
}
