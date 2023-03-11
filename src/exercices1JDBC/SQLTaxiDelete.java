package exercices1JDBC;

import myconnections.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static utilitaires.Utilitaire.printMapTaxis;
import static utilitaires.Utilitaire.saisie;

public class SQLTaxiDelete {

    public SQLTaxiDelete() {
        Map<Integer, String> allTaxis = new HashMap<>();
        SQLTaxiAllHashMap getMap = new SQLTaxiAllHashMap();
        allTaxis = getMap.getTaxis();
        int choixTaxi = -1;
        String choixTaxi1;
        int confirm = -1;
        String confirm1;

        System.out.println("-- Effacer un Taxi --");
        printMapTaxis(allTaxis);
        System.out.print("Saisir l'ID du taxi : ");
        do {
            choixTaxi1 = saisie("[0-9]*", "Erreur, veuillez saisir un nombre.");
            choixTaxi = Integer.parseInt(choixTaxi1);
            if (!allTaxis.containsKey(choixTaxi)) {
                System.out.println("Erreur, saisir un ID présent dans la liste !");
            }
        } while (!allTaxis.containsKey(choixTaxi));

        System.out.print("Taxi choisi : ");
        System.out.println("\tID : " + choixTaxi + "\t\tImmatriculation : " + allTaxis.get(choixTaxi).toString());
        String chosenImmat = allTaxis.get(choixTaxi).toString();

        do {
            System.out.println("Confirmer l'éffacement de " + chosenImmat + "\n1. Confirmer\n2. Annuler\nVotre choix : ");
            confirm1 = saisie("[1-2]{1}", "Erreur, veuillez saisir 1 pour Confirmer\t2 pour annuler\nVotre choix : ");
            confirm = Integer.parseInt(confirm1);
        } while (confirm < 1 || confirm > 2);

        if (confirm == 1) {
            Connection dbConnect = DBConnection.getConnection();
            String query = "DELETE FROM APITAXI WHERE ID_TAXI = ? AND IMMATRICULATION = ?";
            try (PreparedStatement pstm = dbConnect.prepareStatement(query);) {
                pstm.setInt(1, choixTaxi);
                pstm.setString(2, chosenImmat);

                int nbrligne = pstm.executeUpdate();

                System.out.println("Ligne(s) éffacée(s) : " + nbrligne);

            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e);
            }
            DBConnection.closeConnection();
        } else {
            System.out.println("\nEffacement annulé\n");
        }


    }
}
