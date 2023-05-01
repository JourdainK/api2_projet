package mvp;

import mvp.model.*;
import mvp.model.adresse.AdresseModelDB;
import mvp.model.client.ClientModelDB;
import mvp.model.taxi.TaxiModelDB;
import mvp.presenter.AdressePresenter;
import mvp.presenter.ClientPresenter;
import mvp.presenter.TaxiPresenter;
import mvp.view.*;
import two_three.Adresse;
import two_three.Client;
import two_three.Taxi;

import java.util.Arrays;
import java.util.List;
import static utilitaires.Utilitaire.*;

public class GestMain {

    private DAO<Taxi> tm;
    private TaxiViewInterface tv;
    private TaxiPresenter tp;
    private DAO<Client> cm;
    private ClientViewInterface cv;
    private ClientPresenter cp;
    private DAO<Adresse> am;
    private AdresseViewInterface av;
    private AdressePresenter ap;


    public void gestion(){

        tm = new TaxiModelDB();
        tv = new TaxiViewConsole();
        tp = new TaxiPresenter(tm,tv);
        cm = new ClientModelDB();
        cv = new ClientViewConsole();
        cp = new ClientPresenter(cm,cv);
        am = new AdresseModelDB();
        av = new AdresseViewConsole();
        ap = new AdressePresenter(am,av);



        List<String> loptions = Arrays.asList("Menu Taxi", "Menu Client", "Menu Adresse","fin");

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
                    ap.start();
                    break;
                case 4 :
                    System.exit(0);
                    break;
            }
        }while(true);

    }

    public static void main(String[] args) {
        GestMain gest = new GestMain();
        gest.gestion();
    }
}
