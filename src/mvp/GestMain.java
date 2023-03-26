package mvp;

import java.util.Arrays;
import java.util.List;
import static utilitaires.Utilitaire.*;

public class GestMain {

    //TODO MAIN MENU
    public void gestion(){
        List<String> loptions = Arrays.asList("Menu Taxi", "fin");

        do{
            affListe(loptions);
            int choix = choixElt(loptions);
            switch (choix){
                case 1 :
                    System.out.println("taxis");
                    break;
                case 2 :
                    System.exit(0);
            }
        }while(true);

    }

    public static void main(String[] args) {
        GestMain gest = new GestMain();
        gest.gestion();
    }
}
