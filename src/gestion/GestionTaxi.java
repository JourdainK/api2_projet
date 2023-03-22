package gestion;

import two_three.Location;
import two_three.Taxi;
import utilitaires.DateValidator;
import utilitaires.DateValidatorUsingDateTimeFormatter;
import utilitaires.SQLTaxiAllHashMap;
import myconnections.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static utilitaires.Utilitaire.saisie;

public class GestionTaxi {
    private static final Logger logger = LogManager.getLogger(GestionTaxi.class);

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
                    logger.info("choix du taxi entrée (test logger)");
                }
            } while (choiceMenTax < 1 || choiceMenTax > optionTaxi.size());

            switch (choiceMenTax) {
                case 1 -> seeAllTaxi();
                case 2 -> createTaxi();
                case 3 -> deleteTaxi();
                case 4 -> modifTaxi();
                case 5 -> seeClientChosenTAxi();
                case 6 -> seeKmNpriceTot();
                case 7 -> seeAllLocatDate();
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
        logger.info("Connexion établie - création taxi");
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
            do {
                System.out.println("Saisir prix au km :");
                String prixkm1 = saisie("[0-9]{0,10}[.][0-9]{0,2}|[0-9]{0,10}", "Erreur de saisie, veuillez saisir un nombre réel (séparée d'un point) supérieur à 0\nSaisir le prix au km : ");
                prixkm = Double.parseDouble(prixkm1);
                if (prixkm <= 0) {
                    System.out.println("Erreur, le prix au km doit étre supérieur à 0");
                }
            } while (prixkm <= 0);


            pstm1.setString(1, immat);
            pstm1.setInt(2, maxPass);
            pstm1.setDouble(3, prixkm);

            int nl1 = pstm1.executeUpdate();
            System.out.println(nl1 + " ligne insérée\n");
            pstm2.setString(1, immat);
            pstm2.setInt(2, maxPass);
            pstm2.setDouble(3, prixkm);

            try (ResultSet rs = pstm2.executeQuery()) {
                if (rs.next()) {
                    int nc = rs.getInt("ID_TAXI");
                    System.out.println("Numéro du taxi inséré : " + nc + "\t\tImmatriculation : " + rs.getString("IMMATRICULATION") + "\t\tPassagers maximum : " + rs.getInt("NBREMAXPASSAGERS") + "\t\tPrix au km : " + rs.getDouble("PRIXKM"));
                    System.out.println("\n\n");
                }
            } catch (SQLException e) {
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
        int choiceModif;
        String choiceModif1;
        String chosenTaxiImmat;
        List<String> lOptions = new ArrayList<>(Arrays.asList("1. Modifier l'immatriculation", "2. Modifier le nombre de passagers maximum", "3. Modifier le Prix au kilomètre", "4.retour au menu précédent"));
        chosenTaxiImmat = getImmatChosenTaxi();

        System.out.println("\n -- Modification du taxi " + chosenTaxiImmat + " --");

        do {
            System.out.println("");
            for (String o : lOptions) {
                System.out.println(o);
            }
            System.out.print("Votre choix : ");
            choiceModif1 = saisie("[1-4]{1}", "Erreur veuillez saisir un nombre compris entre 1 et 4\nVotre choix : ");
            choiceModif = Integer.parseInt(choiceModif1);

            switch (choiceModif) {
                case 1 -> modifImmatTaxi(chosenTaxiImmat);
                case 2 -> modifNbMaxPass(chosenTaxiImmat);
                case 3 -> modifPrixKm(chosenTaxiImmat);
            }

        } while (choiceModif != 4);
    }

    public void modifImmatTaxi(String immat) {
        String newImmat;
        Map<Integer, String> taxis = new SQLTaxiAllHashMap().getTaxis();

        do {
            System.out.println("Saisir la nouvelle immatriculation : ");
            newImmat = saisie("^[T]{1}\\-([L]{1}||[X]{1})[A-Z]{2}\\-[0-9]{3}$", "Erreur de saisie, veuillez saisir une immatriculation de type 'T-XXX-000' ou 'T-LXX-000'\nSaisir l'immatriculation : ");
            if (taxis.containsValue(newImmat)) {
                System.out.println("Erreur, cette immatriculation (" + newImmat + ") est déja enregistrée dans le système, veuillez saisir une autre immatriculation");
            }
        } while (taxis.containsValue(newImmat));


        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("Connexion établie");
        String query = "UPDATE apitaxi SET immatriculation = ?  WHERE immatriculation = ?";

        try (PreparedStatement psmt = dbConnect.prepareStatement(query);) {
            psmt.setString(1, newImmat);
            psmt.setString(2, immat);

            int nl = psmt.executeUpdate();
            if (nl == 0) System.out.println("Erreur, la modification n'as pas été prise en compte");
            System.out.println(nl + " ligne mise à jour\n");

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }

        DBConnection.closeConnection();

    }

    public void modifNbMaxPass(String immat) {
        int newNbrMax;
        String newNbrMax1;

        System.out.println("Saisir le nouveau nombre maximum de passagers : ");
        do {
            newNbrMax1 = saisie("[0-9]*", "Erreur, veuillez saisir un nombre");
            newNbrMax = Integer.parseInt(newNbrMax1);
            if (newNbrMax <= 0) {
                System.out.println("Erreur, le nombre saisi doit être supérieur à 0");
            }
        } while (newNbrMax <= 0);

        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        String query = "UPDATE apitaxi SET nbremaxpassagers = ? WHERE immatriculation = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {

            pstm.setInt(1, newNbrMax);
            pstm.setString(2, immat);
            int nl = pstm.executeUpdate();
            if (nl == 0) {
                System.out.println("Erreur, la modification n'as pas été prise en compte");
            }
            System.out.println("\n" + nl + " ligne mise à jour");

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }

        DBConnection.closeConnection();
    }


    public void modifPrixKm(String immat) {
        double newPrice;
        String newPrice1;

        do {
            System.out.println("Saisir le nouveau prix au km : ");
            newPrice1 = saisie("^[0-9]{0,10}[.][0-9]{0,2}|[0-9]{0,10}$", "Erreur de saisie, veuillez saisir un nombre réel (séparée d'un point) supérieur à 0\nSaisir le prix au km : ");
            newPrice = Double.parseDouble(newPrice1);
            if (newPrice <= 0) System.out.println("Erreur, le prix doit être supérieur à 0");
        } while (newPrice <= 0);

        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        String query = "UPDATE apitaxi SET prixkm = ? WHERE immatriculation = ?";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setDouble(1, newPrice);
            pstm.setString(2, immat);
            int nl = pstm.executeUpdate();
            if (nl == 0) System.out.println("Erreur, la modification n'as pas été prise en compte");
            System.out.println("\n" + nl + " ligne mise à jour");

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }

        DBConnection.closeConnection();
    }

    public void seeClientChosenTAxi() {
        String chosenImmat;
        int i = 1;
        chosenImmat = getImmatChosenTaxi();

        System.out.println("\n -- Client(s) ayant utilisé le taxi " + chosenImmat + " --");

        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("Connexion établie");
        String query = "SELECT * FROM API_TAXI_USED WHERE immatriculation = ? ORDER BY id_client";

        try (PreparedStatement pstm = dbConnect.prepareStatement(query)) {
            pstm.setString(1, chosenImmat);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    int idClient = rs.getInt(1);
                    String nomCli = rs.getString(2);
                    String prenCli = rs.getString(3);

                    System.out.println(i + " - ID client : " + idClient + "\t\tNom : " + nomCli + "\t\tPrénom : " + prenCli);
                    i++;
                }

            } catch (SQLException f) {
                System.out.println("Erreur SQL : " + f);
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }

        DBConnection.closeConnection();
    }

    public void seeKmNpriceTot() {
        String chosenImmat;

        chosenImmat = getImmatChosenTaxi();

        System.out.println(" -- Total des locations et total des kilomètre parcourus du taxi " + chosenImmat + " --");

        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("Connection établie");
        String query1 = "SELECT * FROM api_locat_simple WHERE immatriculation = ?";
        String query2 = "SELECT * FROM api_totkm_taxi WHERE immatriculation = ?";

        try (PreparedStatement pstm1 = dbConnect.prepareStatement(query1);
             PreparedStatement pstm2 = dbConnect.prepareStatement(query2)) {

            pstm1.setString(1, chosenImmat);
            pstm2.setString(1, chosenImmat);
            double tot = 0;

            try (ResultSet rs1 = pstm1.executeQuery()) {
                while (rs1.next()) {

                    tot = tot + rs1.getDouble(7);

                }
            } catch (SQLException f) {
                System.out.println("Erreur SQL : " + f);
            }
            System.out.println("Total des locations : " + tot + " €");

            int totKm = 0;
            try (ResultSet rs2 = pstm2.executeQuery()) {
                while (rs2.next()) {
                    totKm += rs2.getInt(3);
                }
                System.out.println("Total km parcourus : " + totKm);
            } catch (SQLException g) {
                System.out.println("Erreur SQL : " + g);
            }


        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }

        DBConnection.closeConnection();
    }

    public void seeAllLocatDate() {
        Map<Integer, String> taxis = new SQLTaxiAllHashMap().getTaxis();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String choiceId1;
        int choiceId;
        String dateStart;
        String dateEnd;
        boolean checkStart, checkEnd;

        do {
            printMapTaxis(taxis);
            System.out.println("Choisir un l'ID d'un taxi : ");
            choiceId1 = saisie("[0-9]*", "Erreur, veuillez saisir un nombre");
            choiceId = Integer.parseInt(choiceId1);
        } while (!taxis.containsKey(choiceId));

        do {
            System.out.println(" \n -- Saisie de la période -- ");
            System.out.print("\nSaisir la date de départ : ");
            dateStart = saisie("[0-9]{2}[.][0-9]{2}[.][0-9]{4}", "Veuillez saisir une date au format dd.MM.yyyy");
            DateValidator verif = new DateValidatorUsingDateTimeFormatter();
            checkStart = verif.isValid(dateStart);
            if (!checkStart) {
                System.out.println("Veuillez entrer une date valide");
            }
        } while (!checkStart);
        LocalDate start = LocalDate.parse(dateStart, format);

        do {
            System.out.println(" \n -- Saisie de la période -- ");
            System.out.print("\nSaisir la date de fin : ");
            dateEnd = saisie("[0-9]{2}[.][0-9]{2}[.][0-9]{4}", "Veuillez saisir une date au format dd.MM.yyyy");
            DateValidator verif = new DateValidatorUsingDateTimeFormatter();
            checkEnd = verif.isValid(dateEnd);
            if (!checkEnd) {
                System.out.println("Veuillez entrer une date valide");
            }
        } while (!checkEnd);
        LocalDate end = LocalDate.parse(dateEnd, format);

        Connection dbConnect = DBConnection.getConnection();
        if (dbConnect == null) {
            System.exit(1);
        }
        System.out.println("Connection établie");
        String query = "SELECT * FROM apilocation WHERE id_taxi = " + choiceId;

        try (Statement stmt = dbConnect.createStatement();
             ResultSet rs = stmt.executeQuery(query);) {

            while (rs.next()) {
                LocalDate dateLoc;
                dateLoc = rs.getDate("DATELOC").toLocalDate();

                if ((dateLoc.isAfter(start) && dateLoc.isBefore(end)) || (dateLoc.isEqual(start) || dateLoc.isEqual(end))) {
                    System.out.println("ID location : " + rs.getInt("ID_LOCATION") + "\t\tDate : " + rs.getDate("DATELOC"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL :  " + e);
        }

        DBConnection.closeConnection();


    }

    public void printMapTaxis(Map<Integer, String> map) {
        System.out.println("");
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
        System.out.print("\nSaisir l'ID du taxi : ");

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

    public List<Taxi> getAllTaxis() {
        List<Taxi> lTaxis = new ArrayList<>();
        Connection dbConnect = DBConnection.getConnection();
        Taxi tmpTaxi;

        if (dbConnect == null) {
            System.exit(1);
        }
        //System.out.println("Connexion établie");
        String query = "SELECT * FROM APITAXI ORDER BY ID_TAXI";

        try (Statement stmt = dbConnect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                int tmpId, maxPass;
                String immat;
                double prixkm;

                tmpId = rs.getInt("ID_TAXI");
                maxPass = rs.getInt("NBREMAXPASSAGERS");
                immat = rs.getString("IMMATRICULATION");
                prixkm = rs.getDouble("PRIXKM");

                tmpTaxi = new Taxi(tmpId, maxPass, immat, prixkm);
                lTaxis.add(tmpTaxi);
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e);
        }

        DBConnection.closeConnection();

        return lTaxis;

    }

}
