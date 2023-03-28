package mvp.view;

import mvp.presenter.TaxiPresenter;
import two_three.Taxi;
import utilitaires.SQLTaxiAllHashMap;

import java.util.*;

import static utilitaires.Utilitaire.*;

public class TaxiViewConsole implements TaxiViewInterface {
    private TaxiPresenter presenter;
    private List<Taxi> lTaxis;
    private Scanner sc = new Scanner(System.in);


    @Override
    public void setPresenter(TaxiPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Taxi> ltaxis) {
        this.lTaxis = ltaxis;
        //affListe(lTaxis);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : " + msg);
    }


    public void menu(){
        List<String> loptions = new ArrayList<>(Arrays.asList("Voir la liste des taxis","Ajouter", "Effacer","Modifier","Rechercher","Voir les taxi d'un client donné","Voir les locations d'un taxi","Voir les adresses auquel s'est rendu un taxi","Retour"));
        int choix;
        do{
            affListe(loptions);
            choix = choixElt(loptions);
            switch (choix){
                case 1 -> affListe(lTaxis);
                case 2 -> ajouter();
                case 3 -> deleteTaxi();
                case 4 -> System.out.println("modif");
                case 5 -> System.out.println("rechercher");
                case 6 -> System.out.println("voir taxi client");
                case 7 -> System.out.println("voir locat taxi");
                case 8 -> System.out.println("Voir adre");
            }

        }while(choix != 9);

    }

    public void ajouter(){
        System.out.println(" -- Encoder un nouveau taxi --\n ");
        System.out.println("Saisir l'immatriculation : ");
        String immat = saisie("^[T]{1}\\-([L]{1}||[X]{1})[A-Z]{2}\\-[0-9]{3}$", "Erreur de saisie, veuillez saisir une immatriculation de type 'T-XXX-000' ou 'T-LXX-000'\nSaisir l'immatriculation : ");
        int maxPass = -1;
        do {
            System.out.println("Saisir le nombre de passagers maximum : ");
            String maxPass1 = saisie("[0-9]{1,3}", "Erreur de saisie ");
            maxPass = Integer.parseInt(maxPass1);
        } while (maxPass < 1);

        double prixkm = -1;
        do {
            System.out.println("Saisir prix au km :");
            String prixkm1 = saisie("[0-9]{0,10}[.][0-9]{0,2}|[0-9]{0,10}", "Erreur de saisie, veuillez saisir un nombre réel (séparée d'un point) supérieur à 0\nSaisir le prix au km : ");
            prixkm = Double.parseDouble(prixkm1);
            if (prixkm <= 0) {
                System.out.println("Erreur, le prix au km doit étre supérieur à 0");
            }
        } while (prixkm <= 0);

        Taxi tmpTaxi = new Taxi(maxPass,immat,prixkm);
        presenter.addTaxi(tmpTaxi);
        lTaxis = presenter.getListTaxis();
        //affListe(lTaxis);
    }

    public void deleteTaxi(){
        /*
        Map <Integer, String> allTaxis = new HashMap<>();
        SQLTaxiAllHashMap getMap = new SQLTaxiAllHashMap();
        allTaxis = getMap.getTaxis();
        int choixTaxi = -1;
        String choixTaxi1;
        int confirm = -1;
        String confirm1;

        System.out.println("-- Effacer un Taxi --");
        printMapTaxis(allTaxis);
        System.out.print("Saisir l'ID du taxi : ");
        do {
            choixTaxi1 = saisie("[0-9]*", "Erreur, veuillez saisir un nombre.");
            choixTaxi = Integer.parseInt(choixTaxi1);
            if (!allTaxis.containsKey(choixTaxi)) {
                System.out.println("Erreur, saisir un ID présent dans la liste !");
            }
        } while (!allTaxis.containsKey(choixTaxi));

        System.out.print("Taxi choisi : ");
        System.out.println("\tID : " + choixTaxi + "\t\tImmatriculation : " + allTaxis.get(choixTaxi).toString());
        String chosenImmat = allTaxis.get(choixTaxi).toString();
        Taxi tmpTaxi = presenter.readTaxi(choixTaxi);
        System.out.println(tmpTaxi);

         */
        System.out.println("Saisir id : ");
        int id = sc.nextInt();
        Taxi tmp = presenter.readTaxi(id);
        System.out.println("test"  + tmp);
        //presenter.removeTaxi(tmpTaxi);
    }

    //TODO all CRUD OPTION
    //leave special after CRUD client / adr are done

}
