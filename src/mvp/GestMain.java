package mvp;

import mvp.model.ClientModelDB;
import mvp.model.DAOClient;
import mvp.model.DAOTaxi;
import mvp.model.TaxiModelDB;
import mvp.presenter.ClientPresenter;
import mvp.presenter.TaxiPresenter;
import mvp.view.ClientViewConsole;
import mvp.view.ClientViewInterface;
import mvp.view.TaxiViewConsole;
import mvp.view.TaxiViewInterface;

import java.util.Arrays;
import java.util.List;
import static utilitaires.Utilitaire.*;

public class GestMain {

    private DAOTaxi tm;
    private TaxiViewInterface tv;
    private TaxiPresenter tp;
    private DAOClient cm;
    private ClientViewInterface cv;
    private ClientPresenter cp;



    public void gestion(){

        tm = new TaxiModelDB();
        tv = new TaxiViewConsole();
        tp = new TaxiPresenter(tm,tv);
        cm = new ClientModelDB();
        cv = new ClientViewConsole();
        cp = new ClientPresenter(cm,cv);


        List<String> loptions = Arrays.asList("Menu Taxi", "Menu Client","fin");

        do{
            affListe(loptions);
            int choix = choixElt(loptions);
            switch (choix){
                case 1 :
                    tp.start();
                    break;
                case 2 :
                    cp.start();
                    break;
                case 3 :
                    System.exit(0);
            }
        }while(true);

    }

    public static void main(String[] args) {
        GestMain gest = new GestMain();
        gest.gestion();
    }
}
