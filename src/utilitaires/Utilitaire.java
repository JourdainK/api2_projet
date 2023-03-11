package utilitaires;

import java.util.Iterator;
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

    public static void printMapTaxis(Map<Integer,String> map){
        for(Map.Entry<Integer,String> set : map.entrySet()){
            System.out.println("ID : " + set.getKey() + "\tImmatriculation : " + set.getValue() + "\n");
        }
    }
}
