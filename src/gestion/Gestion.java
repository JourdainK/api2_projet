package gestion;

import two_three.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.printListTaxis;
import static utilitaires.Utilitaire.saisie;

public class Gestion {
    private List<Client> clientsList;
    private List<Taxi> taxisList;
    private List<Adresse> adressesList;
    private List<Location> locationsList;
    private Scanner sc = new Scanner(System.in);
    private GestionTaxi gTaxi = new GestionTaxi();

    public Gestion() {

        clientsList = new ArrayList<>();
        taxisList = new ArrayList<>();
        adressesList = new ArrayList<>();
        locationsList = new ArrayList<>();
        taxisList = gTaxi.getAllTaxis();
        //TODO testing classes > for all classes declare instances to test each classes
        menu();

    }

    public void menu() {
        String option = "\n1 - Menu client\n2 - Menu Taxi\n3 - Menu Adresses\n4 - Menu Locations\n5 - Quitter";
        String choiceMenu1;
        int choiceMenu;


        System.out.println(" -- Taxi service --");

        do {
            do {
                System.out.println(option);
                System.out.println("Votre choix : ");
                choiceMenu1 = saisie("[1-5]", "Veuillez saisir un nombre compris entre 1 et 5.\nVotre choix : ");
                choiceMenu = Integer.parseInt(choiceMenu1);
            } while (choiceMenu < 1 || choiceMenu > 5);

            switch (choiceMenu) {
                //TODO develop methods for these
                case 1 -> menuClient();
                case 2 -> taxisList = gTaxi.menuTaxi();
                case 3 -> printListTaxis(taxisList);
                case 4 -> System.out.println("Menu Locations");
                case 5 -> System.out.println("Au revoir");
                default -> System.out.println("Erreur de saisie.\n");
            }
        } while (choiceMenu != 5);

    }

    public void menuClient() {
        String optionClient = "\n1 - Enregistrer un client\n2 - Voir les locations d'un client\n3 - Voir les informations d'un client\n4 - Retour au menu précédent";
        String choiceMenuCli1;
        int choiceMenuCli;

        System.out.println("-- Menu Client --");
        do {
            do {
                System.out.println(optionClient);
                System.out.println("Votre choix : ");
                choiceMenuCli1 = saisie("[1-4]", "Veuillez saisir un nombre compris entre 1 et 4\nVotre choix : ");
                choiceMenuCli = Integer.parseInt(choiceMenuCli1);
            } while (choiceMenuCli < 1 || choiceMenuCli > 4);

            switch (choiceMenuCli) {
                //TODO develop methods for these
                case 1 -> System.out.println("Enregistrer un client");
                case 2 -> System.out.println("Voir les locations d'un client");
                case 3 -> System.out.println("Voir les infos d'un client");
                default -> System.out.println("Erreur de saisie\n");
            }
        } while (choiceMenuCli != 4);
    }

    public static void main(String[] args) {
        Gestion g = new Gestion();
    }
}
