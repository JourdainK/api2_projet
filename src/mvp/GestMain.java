package mvp;

import mvp.model.*;
import mvp.model.adresse.AdresseModelDB;
import mvp.model.client.ClientModelDB;
import mvp.model.location.LocationModelDB;
import mvp.model.taxi.TaxiModelDB;
import mvp.presenter.*;
import mvp.view.*;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import static utilitaires.Utilitaire.*;

public class GestMain {

    private DAO<Taxi> tm;
    private ViewInterface<Taxi> tv;
    private Presenter<Taxi> tp;
    private DAO<Client> cm;
    private ViewInterface<Client> cv;
    private Presenter<Client> cp;
    private DAO<Adresse> am;
    private ViewInterface<Adresse> av;
    private Presenter<Adresse> ap;
    private DAO<Location> lm;
    private ViewInterface<Location> lv;
    private Presenter<Location> lp;

    public void gestion(){
        //compare postal code > ascending order (a1.getCP() - a2.getCP())
        Comparator<Adresse> cmpAdr = (a1,a2)->a1.getCp()-(a2.getCp());

        //compare name of client > ascending order (c1.getNom().compareTo(c2.getNom()))
        Comparator<Client> cmpClient = (c1,c2)->c1.getNom().compareTo(c2.getNom());
        cmpClient = cmpClient.thenComparing((c1,c2)->c1.getPrenom().compareTo(c2.getPrenom()));

        //compare date of location > ascending order (l1.getDateLoc().compareTo(l2.getDateLoc()))
        Comparator<Location> cmpLocation = (l1,l2)->l1.getDateLoc().compareTo(l2.getDateLoc());

        //compare taxi number > ascending order (t1.getIdTaxi() - t2.getIdTaxi())
        Comparator<Taxi> cmpTaxi = (t1,t2)->t1.getIdTaxi() - t2.getIdTaxi();

        tm = new TaxiModelDB();
        tv = new TaxiViewConsole();
        tp = new TaxiPresenter(tm,tv,cmpTaxi);
        cm = new ClientModelDB();
        cv = new ClientViewConsole();
        cp = new ClientPresenter(cm,cv,cmpClient);
        am = new AdresseModelDB();
        av = new AdresseViewConsole();
        ap = new AdressePresenter(am,av,cmpAdr);
        lm = new LocationModelDB();
        lv = new LocationViewConsole();
        lp = new LocationPresenter(lm,lv,cmpLocation);
        ((SpecialLocationPresenter)lp).setAdressePresenter((AdressePresenter) ap);
        ((SpecialLocationPresenter)lp).setClientPresenter((ClientPresenter) cp);
        ((SpecialLocationPresenter)lp).setTaxiPresenter((TaxiPresenter) tp);



        List<String> loptions = Arrays.asList("Menu Taxi", "Menu Client", "Menu Adresse","Menu Location" ,"fin");

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
                    lp.start();
                    break;
                case 5 :
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
