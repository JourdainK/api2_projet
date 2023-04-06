package utilitaires;

import two_three.Taxi;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Utilitaire {
    private static Scanner sca = new Scanner(System.in);

    public static String saisie(String regex, String message) {
        boolean check = false;

        String phrase;
        do {
            phrase = sca.nextLine();
            if (phrase.matches(regex)) {
                check = true;
            } else {
                System.out.println(message);
            }
        } while (!check);
        return phrase;
    }

    public static void affListe(List l){
        int i =1;
        for(Object o :l) {
            System.out.println((i++)+"."+o);
        }
    }

    public static void affNoNumberList(List l){
        for(Object o : l){
            System.out.println(o);
        }
    }


    public static int choixElt(List l){
        int choix;
        String choix1;
        do {
            System.out.println("choix :");
            choix1 = saisie("[0-9]*","Veuillez saisir un nombre");
            choix = Integer.parseInt(choix1);
            if(choix <1 || choix > l.size()){
                System.out.println("Erreur le nombre doit être compris entre 1 et " + l.size());
            }
        } while(choix <1 || choix > l.size());
        return choix;
    }

    public static void printMapTaxis(Map<Integer, String> map) {
        System.out.println("");
        for (Map.Entry<Integer, String> set : map.entrySet()) {
            System.out.println("ID : " + set.getKey() + "\tImmatriculation : " + set.getValue());
        }
    }






}
