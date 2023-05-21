package mvp.view;

import mvp.presenter.TaxiPresenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Taxi;

import java.util.*;

import static utilitaires.Utilitaire.*;

public class TaxiViewConsole implements TaxiViewInterface {
    private TaxiPresenter presenter;
    private List<Taxi> lTaxis;
    private Scanner sc = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(TaxiViewConsole.class);


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
            System.out.println("");
            affListe(loptions);
            choix = choixElt(loptions);
            switch (choix){
                case 1 -> affListe(lTaxis);
                case 2 -> ajouter();
                case 3 -> deleteTaxi();
                case 4 -> modifTaxi();
                case 5 -> seekTaxi();
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
        try{
            Taxi tmpTaxi =  new Taxi.TaxiBuilder()
                    .setImmatriculation(immat).setNbreMaxPassagers(maxPass)
                    .setPrixKm(prixkm).build();
            int idTaxi = presenter.addTaxi(tmpTaxi);
            tmpTaxi.setIdTaxi(idTaxi);
        }catch (Exception e){
            logger.error("Erreur lors de l'ajout d'un taxi : " + e );
            e.printStackTrace();
        }
        lTaxis = presenter.getListTaxis();
        //affListe(lTaxis);
    }

    public void deleteTaxi(){
        Map <Integer, String> allTaxis;
        allTaxis = presenter.getMapTaxis();
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
        presenter.removeTaxi(tmpTaxi);
        lTaxis = presenter.getListTaxis();
    }

    public void modifTaxi(){
        boolean check = false;
        affListe(lTaxis);
        int choix = choixElt(lTaxis);
        int choixMod = -1;
        Taxi chosenTaxi = lTaxis.get(choix-1);
        //System.out.println("chosen = "  + chosenTaxi);
        List<String> lmodif = new ArrayList<>(Arrays.asList("Immatriculation","Nombre maximum de passager","Prix au kilomètre","retour"));
        do{
            System.out.println("Modifier : ");
            affListe(lmodif);
            choixMod = choixElt(lmodif);
            switch (choixMod){
                case 1 :
                    System.out.print("\nSaisir la nouvelle immatriculation : ");
                    String immat = saisie("^[T]{1}\\-([L]{1}||[X]{1})[A-Z]{2}\\-[0-9]{3}$", "Erreur de saisie, veuillez saisir une immatriculation de type 'T-XXX-000' ou 'T-LXX-000'\nSaisir l'immatriculation : ");
                    try{
                        chosenTaxi = new Taxi.TaxiBuilder()
                                .setIdTaxi(chosenTaxi.getIdTaxi())
                                .setImmatriculation(immat)
                                .setPrixKm(chosenTaxi.getPrixKm())
                                .setNbreMaxPassagers(chosenTaxi.getNbreMaxPassagers())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification du taxi (immatriculation) : " + e);
                        e.printStackTrace();
                    }
                    break;
                case 2 :
                    System.out.print("\nSaisir le nouveau nombre de passagers maximum : ");
                    int newnbrMax;
                    do{
                        String newMax = saisie("[0-9]*","Veuillez entrer un nombre entier");
                        newnbrMax = Integer.parseInt(newMax);
                    }while(newnbrMax < 0);
                    try{
                        chosenTaxi = new Taxi.TaxiBuilder()
                                .setIdTaxi(chosenTaxi.getIdTaxi())
                                .setImmatriculation(chosenTaxi.getImmatriculation())
                                .setPrixKm(chosenTaxi.getPrixKm())
                                .setNbreMaxPassagers(newnbrMax)
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification du taxi (passagers max) : " + e);
                        e.printStackTrace();
                    }
                    break;
                case 3 :
                    System.out.print("\nSaisir le nouveau prix au kilomètre : ");
                    double newprix;
                    do{
                        String newPrice = saisie("[0-9]{0,10}[.][0-9]{0,2}|[0-9]{0,10}", "Erreur de saisie, veuillez saisir un nombre réel (séparée d'un point) supérieur à 0\nSaisir le prix au km : ");
                        newprix = Double.parseDouble(newPrice);
                    }while(newprix < 0);
                    try{
                        chosenTaxi = new Taxi.TaxiBuilder()
                                .setIdTaxi(chosenTaxi.getIdTaxi())
                                .setImmatriculation(chosenTaxi.getImmatriculation())
                                .setPrixKm(newprix)
                                .setNbreMaxPassagers(chosenTaxi.getNbreMaxPassagers())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification du taxi (prix au km) : " + e);
                        e.printStackTrace();
                    }
                    break;
            }
        }while(choixMod!=4);
        presenter.updateTaxi(chosenTaxi);
        lTaxis = presenter.getListTaxis();
    }

    public void seekTaxi(){
        System.out.println("\t--Recherche d'un taxi--");
        System.out.print("Saisir le numéro d'identification du taxi : ");
        String idTaxi = saisie("[0-9]*","Veuillez saisir un nombre");
        int taxiID = Integer.parseInt(idTaxi);
        Taxi seekdTaxi = presenter.readTaxi(taxiID);
    }

    //TODO special when all other classes' Crud are done
    @Override
    public Taxi selectTaxi(List<Taxi> lTaxis) {
        affListe(lTaxis);
        int choix = choixElt(lTaxis);
        Taxi taxi = lTaxis.get(choix-1);
        return taxi;
    }
}
