package utilitaires;

import exercices1JDBC.DateValidator;
import exercices1JDBC.DateValidatorUsingDateTimeFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            choix1 = saisie("[1-9]*","Veuillez saisir un nombre");
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

    public static int getCp(){
        int cp;

        do{
            System.out.print("\nSaisir le code postal : ");
            String cp1 = saisie("[0-9]*","Veuillez saisir un numéro de code postal compris entre 1000 et 9992");
            cp = Integer.parseInt(cp1);
            if(cp < 1000 || cp > 9992) System.out.println("Erreur le numéro doit être compris entre 1000 et 9992 (Belgique)");
        }while (cp < 1000 || cp > 9992);

        return cp;
    }

    public static boolean isDateValid(String date, String format) {
        boolean check;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate.parse(date, formatter);
            //System.out.println("La date est valide");
            check = true;
        } catch (DateTimeParseException e) {
            System.out.println("La date n'est pas valide");
            check = false;
        }

        return check;
    }




}
