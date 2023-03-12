package gestion;

import two_three.Taxi;
import utilitaires.SQLTaxiAllHashMap;
import myconnections.DBConnection;

import java.sql.*;
import java.util.*;

import static utilitaires.Utilitaire.saisie;

public class GestionTaxi {
    //TODO ask teacher if returned list is a good way to keep the List updated (in Gestion) after handling taxis in this menu ?
    //GestionTaxi initiated upon creation of menu() in Gestion (Global handler) > list called from getAllTaxis > then reupdated each time you close the menu
    //CRUD inside menuTaxi -> handled from a SQL request that build a hashmap (key-> ID , Value -> IMMATRICULATION (UNIQUE))
    public List<Taxi> menuTaxi() {
        List<String> optionTaxi = new ArrayList<>(Arrays.asList("1. Voir les taxi", "2. Ajouter un taxi", "3. Effacer un taxi", "4. Modifier un taxi", "5. Voir les clients d'un taxi choisi", "6. Total de km parcourus d'un taxi choisi", "7. Voir les locations d'un taxi choisi", "8. Retour au menu précédent"));
        String choiceMenTax1;
        int choiceMenTax = -1, i = 1;
        StringBuilder errMsg = new StringBuilder("Erreur veuillez saisir un nombre compris entre 1 et " + optionTaxi.size());
        String errMesg = errMsg.toString();
        List<Taxi> listTaxis;

        System.out.println("-- Menu Taxi --");
        do {
            do {
                System.out.println("");
                for (String o : optionTaxi) {
                    System.out.println(o);
                }
                System.out.println("\nVotre choix : ");
                choiceMenTax1 = saisie("[0-9]*", errMesg);
                choiceMenTax = Integer.parseInt(choiceMenTax1);
                if (choiceMenTax < 1 || choiceMenTax > optionTaxi.size()) {
                    System.out.println(errMesg);
                }
            } while (choiceMenTax < 1 || choiceMenTax > optionTaxi.size());

            switch (choiceMenTax) {
                //TODO develop methods for these
                case 1 -> seeAllTaxi();
                case 2 -> createTaxi();
                case 3 -> deleteTaxi();
                case 4 -> modifTaxi();
                case 5 -> System.out.println("Voirs client d'1 taxi");
                case 6 -> System.out.println("tot km d'un Taxi");
                case 7 -> System.out.println("All locations d'un taxi");
            }

        } while (choiceMenTax != 8);

        listTaxis = getAllTaxis();

        return listTaxis;
    }

    public void seeAllTaxi() {

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

    public void createTaxi() {
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
            System.out.println(nl1 + " ligne insérée\n");
            pstm2.setString(1,immat);
            pstm2.setInt(2,maxPass);
            pstm2.setDouble(3,prixkm);

            try(ResultSet rs = pstm2.executeQuery()){
                if(rs.next()){
                    int nc = rs.getInt("ID_TAXI");
                    System.out.println("Numéro du taxi inséré : " + nc + "\t\tImmatriculation : " + rs.getString("IMMATRICULATION") + "\t\tPassagers maximum : " + rs.getInt("NBREMAXPASSAGERS") + "\t\tPrix au km : " + rs.getDouble("PRIXKM"));
                    System.out.println("\n\n");
                }
            }catch(SQLException e){
                System.out.println("Erreur SQL : " + e);
            }



        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }

        DBConnection.closeConnection();


    }


    private void deleteTaxi() {
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

    private void modifTaxi() {
        //TODO menu choix taxi-> renvoi immatriculation -> menu : modif X , Y ou Z (no modif ID !)
        String chosenTaxiImmat;
        chosenTaxiImmat = getImmatChosenTaxi();
        System.out.println("Test : " + chosenTaxiImmat);
    }

    public void printMapTaxis(Map<Integer, String> map) {
        for (Map.Entry<Integer, String> set : map.entrySet()) {
            System.out.println("ID : " + set.getKey() + "\tImmatriculation : " + set.getValue());
        }
    }

    public String getImmatChosenTaxi() {
        String chosenImmat;
        Map<Integer, String> allTaxis = new HashMap<>();
        SQLTaxiAllHashMap getMap = new SQLTaxiAllHashMap();
        allTaxis = getMap.getTaxis();
        int choixTaxi = -1;
        String choixTaxi1;
        int confirm = -1;
        String confirm1;

        System.out.println("-- Choix d'un Taxi --");
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
        chosenImmat = allTaxis.get(choixTaxi).toString();

        return chosenImmat;
    }

    public List<Taxi> getAllTaxis(){
        List<Taxi> lTaxis = new ArrayList<>();
        Connection dbConnect = DBConnection.getConnection();
        Taxi tmpTaxi;

        if(dbConnect == null){
            System.exit(1);
        }
        System.out.println("Connexion établie");
        String query = "SELECT * FROM APITAXI ORDER BY ID_TAXI";

        try(Statement stmt = dbConnect.createStatement();
            ResultSet rs = stmt.executeQuery(query)){

            boolean found = false;
            while(rs.next()){
                found = true;
                int tmpId, maxPass;
                String immat;
                double prixkm;

                tmpId = rs.getInt("ID_TAXI");
                maxPass = rs.getInt("NBREMAXPASSAGERS");
                immat = rs.getString("IMMATRICULATION");
                prixkm = rs.getDouble("PRIXKM");

                tmpTaxi = new Taxi(tmpId,maxPass,immat,prixkm);
                lTaxis.add(tmpTaxi);
            }

        }catch (SQLException e){
            System.out.println("Erreur SQL : " + e);
        }

        DBConnection.closeConnection();

        return lTaxis;

    }

}
