package exercices1JDBC;

import java.util.HashMap;
import java.util.Map;

import static utilitaires.Utilitaire.printMapTaxis;
import static utilitaires.Utilitaire.saisie;

public class SQLTaxiDelete {

    public SQLTaxiDelete(){
        //TODO get hashmap TAXIS see SQLtaxiAllHAshmap > printhasmamp > utilities > propose > pick > delete in DB
        Map<Integer,String> allTaxis = new HashMap<>();
        SQLTaxiAllHashMap getMap = new SQLTaxiAllHashMap();
        allTaxis = getMap.getTaxis();
        int choixTaxi = -1;
        String choixTaxi1;
        int confirm = -1;
        String confirm1;

        System.out.println("-- Effacer un Taxi --");
        printMapTaxis(allTaxis);
        System.out.print("Saisir l'ID du taxi : ");
        do{
            choixTaxi1 = saisie("[0-9]*","Erreur, veuillez saisir un nombre.");
            choixTaxi = Integer.parseInt(choixTaxi1);
            if(!allTaxis.containsKey(choixTaxi)){
                System.out.println("Erreur, saisir un ID présent dans la liste !");
            }
        }while(!allTaxis.containsKey(choixTaxi));

        System.out.print("Taxi choisi : ");
        System.out.println("\tID : " + choixTaxi + "\t\tImmat : " + allTaxis.get(choixTaxi).toString());
        String chosenImmat = allTaxis.get(choixTaxi).toString();
        int idChosen = choixTaxi;

        do{
            System.out.println("Confirmer l'éffacement de " + chosenImmat + "\n1. Confirmer\n2. Annuler\nVotre choix : ");
            confirm1 = saisie("[1-2]{1}","Erreur, veuillez saisir 1 pour Confirmer\t2 pour annuler\nVotre choix : ");
            confirm = Integer.parseInt(confirm1);
        }while(confirm < 1 || confirm > 2);

        if(confirm==1){
            //TODO SQL Here pick up
            System.out.println("Effacer code -> sql");
        }
        else{
            System.out.println("\nEffacement annulé\n");
        }




    }
}
