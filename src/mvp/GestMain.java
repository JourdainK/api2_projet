package mvp;

import mvp.model.DAOTaxi;
import mvp.model.TaxiModelDB;
import mvp.presenter.TaxiPresenter;
import mvp.view.TaxiViewConsole;
import mvp.view.TaxiViewInterface;

import java.util.Arrays;
import java.util.List;
import static utilitaires.Utilitaire.*;

public class GestMain {

    private DAOTaxi tm;
    private TaxiViewInterface tv;
    private TaxiPresenter tp;


    public void gestion(){

        tm = new TaxiModelDB();
        tv = new TaxiViewConsole();
        tp = new TaxiPresenter(tm,tv);

        List<String> loptions = Arrays.asList("Menu Taxi", "fin");

        do{
            affListe(loptions);
            int choix = choixElt(loptions);
            switch (choix){
                case 1 :
                    tp.start();
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
