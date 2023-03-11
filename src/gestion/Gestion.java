package gestion;

import exercices1JDBC.SQLTaxiAll;
import exercices1JDBC.SQLTaxiCreate;
import exercices1JDBC.SQLTaxiDelete;
import two_three.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.saisie;

public class Gestion {
    private List<Client> clientsList;
    private List<Taxi> taxisList;
    private List<Adresse> adressesList;
    private List<Location> locationsList;
    private Scanner sc = new Scanner(System.in);

    public Gestion(){

        clientsList = new ArrayList<>();
        taxisList = new ArrayList<>();
        adressesList = new ArrayList<>();
        locationsList = new ArrayList<>();
        //TODO testing classes > for all classes declare instances to test each classes
        menu();

    }

    public void menu(){
        String option = "\n1 - Menu client\n2 - Menu Taxi\n3 - Menu Adresses\n4 - Menu Locations\n5 - Quitter";
        String choiceMenu1;
        int choiceMenu;

        System.out.println(" -- Taxi service --");

        do{
            do{
                System.out.println(option);
                System.out.println("Votre choix : ");
                choiceMenu1 = saisie("[1-5]","Veuillez saisir un nombre compris entre 1 et 5.\nVotre choix : ");
                choiceMenu = Integer.parseInt(choiceMenu1);
            }while(choiceMenu < 1 || choiceMenu > 5);

            switch(choiceMenu){
                //TODO develop methods for these
                case 1 -> menuClient();
                case 2 -> menuTaxi();
                case 3 -> System.out.println("Menu Addresses");
                case 4 -> System.out.println("Menu Locations");
                case 5 -> System.out.println("Au revoir");
                default -> System.out.println("Erreur de saisie.\n");
            }
        }while(choiceMenu!=5);

    }

    public void menuClient(){
        String optionClient = "\n1 - Enregistrer un client\n2 - Voir les locations d'un client\n3 - Voir les informations d'un client\n4 - Retour au menu précédent";
        String choiceMenuCli1;
        int choiceMenuCli;

        System.out.println("-- Menu Client --");
        do{
            do{
                System.out.println(optionClient);
                System.out.println("Votre choix : ");
                choiceMenuCli1 = saisie("[1-4]","Veuillez saisir un nombre compris entre 1 et 4\nVotre choix : ");
                choiceMenuCli = Integer.parseInt(choiceMenuCli1);
            }while(choiceMenuCli < 1 || choiceMenuCli > 4);

            switch (choiceMenuCli){
                //TODO develop methods for these
                case 1 -> System.out.println("Enregistrer un client");
                case 2 -> System.out.println("Voir les locations d'un client");
                case 3 -> System.out.println("Voir les infos d'un client");
                default -> System.out.println("Erreur de saisie\n");
            }
        }while(choiceMenuCli!=4);
    }

    //TODO modify -> See teacher instructions -> most of the homework is about Taxis !! -> need more options > see clients of one taxi + nmbre TOT km of one taxi + all locations made by one Taxi
    public void menuTaxi(){
        List<String> optionTaxi = new ArrayList<>(Arrays.asList("1. Voir les taxi","2. Ajouter un taxi","3. Effacer un taxi","4. Modifier un taxi","5. Voir les clients d'un taxi choisi", "6. Total de km parcourus d'un taxi choisi","7. Voir les locations d'un taxi choisi","8. Retour au menu précédent\n"));
        String choiceMenTax1;
        int choiceMenTax = -1, i = 1;
        StringBuilder errMsg = new StringBuilder("Erreur veuillez saisir un nombre compris entre 1 et " + optionTaxi.size());
        String errMesg = errMsg.toString();

        System.out.println("-- Menu Taxi --");
        do{
            do{
                for(String o:optionTaxi){
                    System.out.println(o);
                }
                System.out.println("\nVotre choix : ");
                choiceMenTax1 =  saisie("[0-9]*",errMesg);
                choiceMenTax = Integer.parseInt(choiceMenTax1);
                if(choiceMenTax < 1 || choiceMenTax> optionTaxi.size()){
                    System.out.println(errMesg);
                }
            }while(choiceMenTax < 1 || choiceMenTax> optionTaxi.size());

            switch (choiceMenTax){
                //TODO develop methods for these
                case 1 -> seeAllTaxi();
                case 2 -> createTaxi();
                case 3 -> deleteTaxi();
                case 4 -> System.out.println("Modifier un taxi");
                case 5 -> System.out.println("Voirs client d'1 taxi");
                case 6 -> System.out.println("tot km d'un Taxi");
                case 7 -> System.out.println("All locations d'un taxi");
            }

        }while(choiceMenTax!=8);
    }

    public void seeAllTaxi(){
        SQLTaxiAll all = new SQLTaxiAll();
    }

    public void createTaxi(){
        SQLTaxiCreate n1 = new SQLTaxiCreate();
    }

    private void deleteTaxi(){
        SQLTaxiDelete d1 = new SQLTaxiDelete();
    }




    public static void main(String[] args) {
        Gestion g = new Gestion();
    }
}
