package mvp.view;

import mvp.presenter.Presenter;
import mvp.presenter.SpecialTaxiPresenter;
import mvp.presenter.TaxiPresenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static utilitaires.Utilitaire.*;

public class TaxiViewConsole extends AbstractViewConsole<Taxi> implements SpecialTaxiViewConsole {
    private TaxiPresenter presenter;
    private List<Taxi> lTaxis;
    private Scanner sc = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(TaxiViewConsole.class);


    @Override
    public void setPresenter(Presenter<Taxi> presenter) { this.presenter = (TaxiPresenter) presenter;
    }

    @Override
    public void setListDatas(List<Taxi> ltaxis,Comparator<Taxi> cmp) {
        this.lTaxis = ltaxis;
        //affListe(lTaxis);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : " + msg);
    }

    @Override
    public void add(){
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
            presenter.add(tmpTaxi);
        }catch (Exception e){
            logger.error("Erreur lors de l'ajout d'un taxi : " + e );
            e.printStackTrace();
        }
        lTaxis = presenter.getAll();
        //affListe(lTaxis);
    }

    @Override
    public void remove(){
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
        Taxi tmpTaxi = presenter.readTaxiById(choixTaxi);
        presenter.remove(tmpTaxi);
        lTaxis = presenter.getAll();
    }

    @Override
    public void update(){
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
        presenter.update(chosenTaxi);
        lTaxis = presenter.getAll();
    }

    @Override
    public void seek(){
        System.out.println("\t--Recherche d'un taxi--");
        System.out.print("Saisir le numéro d'identification du taxi : ");
        String idTaxi = saisie("[0-9]*","Veuillez saisir un nombre");
        int taxiID = Integer.parseInt(idTaxi);
        Taxi seekdTaxi = presenter.readTaxiById(taxiID);
    }

    @Override
    public Taxi select(List<Taxi> lTaxis) {
        affListe(lTaxis);
        int choix = choixElt(lTaxis);
        Taxi taxi = lTaxis.get(choix-1);
        return taxi;
    }

    @Override
    protected void special() {
        List<String> listOptions = new ArrayList<>(Arrays.asList("Voir tous les taxis" , "Voir les clients d'un taxi", "Voir les gains et kilomètres total d'un taxi","Voir les locations entre deux dates d'un taxi","Retour"));

        int choix;

        do {
            System.out.println("");
            affListe(listOptions);
            choix = choixElt(listOptions);
            switch (choix) {
                case 1 -> affListe(lTaxis);
                case 2 -> affClient();
                case 3 -> taxiKmAndCashTotal();
                case 4 -> getLocationsBetween2Dates();
            }
        } while (choix != listOptions.size());
    }

    @Override
    public void affClient() {
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";
        System.out.println("\t-- Liste des clients d'un taxi --");
        System.out.println("Choisir un taxi : ");
        affListe(lTaxis);
        int choix = choixElt(lTaxis);
        Taxi taxi = lTaxis.get(choix-1);
        /*
        //Utilisation objet : liste de locations du taxi (via MVP) -> utilisation de la méthode de classe getClientsOfTaxi()
        //List<Client> lClients = taxi.getClientsOfTaxi();
         */
        //utilisation fonction sql :  client_by_taxi > exploitation du curseur
        List<Client> lClients = presenter.getClientsOfTaxi(taxi);

        System.out.println(ANSI_GREEN + "\n\tClients du taxi " + ANSI_RESET + taxi.getImmatriculation() + " : " );
        affListe(lClients);
    }

    @Override
    public void taxiKmAndCashTotal(){
        System.out.println("\t-- Liste des taxis avec le nombre de kilomètres parcourus et le montant total des locations --\n");
        affListe(lTaxis);
        int choix = choixElt(lTaxis);
        Taxi taxi = lTaxis.get(choix-1);

        System.out.println("Le taxi à parcouru un total de : " + taxi.getTotKm() + " km" + "\nGain total : " + taxi.getTotGain() + " €");
    }

    @Override
    public void getLocationsBetween2Dates(){
        System.out.println("\t-- Liste des locations d'un taxi entre deux dates --\n");
        affListe(lTaxis);
        int choix = choixElt(lTaxis);
        Taxi taxi = lTaxis.get(choix-1);

        String dateDeb;
        do{
            System.out.println("Saisir la date de début (format : dd-mm-yyyy) : ");
            dateDeb = saisie("^[0-9]{2}\\-[0-9]{2}\\-[0-9]{4}$", "Erreur de saisie, veuillez saisir une date au format dd/mm/yyyy\nSaisir la date de début : ");
        }while(!isDateValid(dateDeb,"dd-MM-yyyy"));
        LocalDate dateDebut = LocalDate.parse(dateDeb,DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        String dateFin;
        do{
            System.out.println("Saisir la date de fin (format : dd/mm/yyyy) : ");
            dateFin = saisie("^[0-9]{2}\\-[0-9]{2}\\-[0-9]{4}$", "Erreur de saisie, veuillez saisir une date au format dd/mm/yyyy\nSaisir la date de fin : ");
        }while(!isDateValid(dateFin,"dd-MM-yyyy"));
        LocalDate dateFinale = LocalDate.parse(dateFin, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Location> llocations = taxi.getListLocationBetweenDates(dateDebut,dateFinale);
        Collections.sort(llocations, new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                return o1.getIdLoc()-o2.getIdLoc();
            }
        });

        System.out.println("Liste des location entre le " + dateDebut + " et le " + dateFinale + " : ");
        affListe(llocations);

    }
}
