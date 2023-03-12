package utilitaires;

import two_three.Taxi;

import java.util.List;
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

    public static void printListTaxis(List<Taxi> taxis){
        int i = 1;
        for(Taxi t:taxis){
            System.out.println(i + " - " + t);
        }
    }

}
