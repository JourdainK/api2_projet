package mvp.view;
import mvp.presenter.LocationPresenter;
import mvp.presenter.Presenter;
import mvp.presenter.SpecialClientPresenter;
import mvp.presenter.SpecialLocationPresenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

import static utilitaires.Utilitaire.*;

public class LocationViewConsole extends AbstractViewConsole<Location> implements SpecialLocationViewConsole{
    private Presenter<Location> presenter;
    private List<Location> locations;
    private Scanner sc = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(LocationViewConsole.class);
    @Override
    public void setPresenter(Presenter<Location> presenter) { this.presenter = (LocationPresenter) presenter; }

    @Override
    public void setListDatas(List<Location> locations,Comparator<Location> cmpt) {
        this.locations = locations;
        this.locations.sort(cmpt);
        //affListe(locations);
        menu();
    }

    @Override
    public void affMsg(String msg) { System.out.println("Information : " +  msg); }

    @Override
    public void add() {
        System.out.println("-- Encoder une nouvelle location --");
        //TODO today's or another day
        LocalDate today = LocalDate.now();
        System.out.println("Saisir le nombre de kilomètres total de la location : ");
        int nbrkm;
        do {
            String nbrKilo = saisie("[0-9]{1,3}", "Erreur de saisie ");
            nbrkm = Integer.parseInt(nbrKilo);
        } while (nbrkm < 1);

        System.out.println("Saisir le nombre de passagers : ");
        int nbrPass;
        do {
            String nbrPassagers = saisie("[0-9]{1,3}", "Erreur de saisie ");
            nbrPass = Integer.parseInt(nbrPassagers);
        } while (nbrPass < 1);
        ((SpecialLocationPresenter) presenter).add(today, nbrkm, nbrPass);
        locations = presenter.getAll();
    }

    @Override
    public void remove(){
        System.out.println("-- Supprimer une location --");
        affListe(locations);
        int choix = choixElt(locations);
        Location location = locations.get(choix-1);
        presenter.remove(location);

        locations = presenter.getAll();
    }

    @Override
    public void update(){
        int choixMod;
        System.out.println("-- Modifier une location --");
        affListe(locations);
        int choix = choixElt(locations);
        Location location = locations.get(choix-1);
        List<String> lmodif = new ArrayList<>(Arrays.asList("Date","Nombre de kilomètres","Nombre de passagers","Taxi","Client","Adresse de départ", "Adresse de retour","Retour"));
        do{
            System.out.println("Modifier : ");
            affListe(lmodif);
            choixMod = choixElt(lmodif);
            switch (choixMod){
                case 1 :
                    boolean isValid = false;
                    String newDate;
                    do{
                        System.out.println("Saisir la nouvelle date (format jj-mm-aaaa) : ");
                        newDate = saisie("[0-9]{2}-[0-9]{2}-[0-9]{4}", "Erreur de saisie (format jj-mm-aaaa)");
                        Predicate<String> dateValidator = dateString -> {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                            try {
                                LocalDate.parse(dateString, formatter);
                                return true;
                            } catch (Exception e) {
                                return false;
                            }
                        };
                        isValid = dateValidator.test(newDate);
                    }while (!isValid);
                    try{
                        location = new Location.LocationBuilder()
                                .setIdLoc(location.getIdLoc())
                                .setKmTot(location.getKmTotal())
                                .setNbrePassagers(location.getNbrePassagers())
                                .setVehicule(location.getVehicule())
                                .setClient(location.getClient())
                                .setAdrDebut(location.getAdrDebut())
                                .setAdrFin(location.getAdrFin())
                                .setDateLoc(LocalDate.parse(newDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                                .build();


                    }catch (Exception e){
                        logger.error(e.getMessage());
                    }
                    break;
                case 2 :
                    System.out.println("Saisir le nouveau nombre de kilomètres : ");
                    int nbrkm;
                    do {
                        String nbrKilo = saisie("[0-9]{1,3}", "Erreur de saisie ");
                        nbrkm = Integer.parseInt(nbrKilo);
                    } while (nbrkm < 1);
                    try{
                        location = new Location.LocationBuilder()
                                .setIdLoc(location.getIdLoc())
                                .setDateLoc(location.getDateLoc())
                                .setKmTot(nbrkm)
                                .setNbrePassagers(location.getNbrePassagers())
                                .setClient(location.getClient())
                                .setVehicule(location.getVehicule())
                                .setAdrDebut(location.getAdrDebut())
                                .setAdrFin(location.getAdrFin())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification de la location " + e.getMessage());
                    }
                    break;
                case 3 :
                    System.out.println("Saisir le nouveau nombre de passagers : ");
                    int nbrPass;
                    do {
                        String nbrPassagers = saisie("[0-9]{1,3}", "Erreur de saisie ");
                        nbrPass = Integer.parseInt(nbrPassagers);
                    } while (nbrPass < 1);
                    try{
                        location = new Location.LocationBuilder()
                                .setIdLoc(location.getIdLoc())
                                .setDateLoc(location.getDateLoc())
                                .setKmTot(location.getKmTotal())
                                .setNbrePassagers(nbrPass)
                                .setClient(location.getClient())
                                .setVehicule(location.getVehicule())
                                .setAdrDebut(location.getAdrDebut())
                                .setAdrFin(location.getAdrFin())
                                .build();


                    }catch (Exception e){
                        logger.error("Erreur lors de la modification de la location " + e.getMessage());
                    }
                    break;

                case 4 :
                    List<Taxi> lTaxis = ((LocationPresenter)presenter).ListeTaxi();
                    System.out.println("Choisir le nouveau taxi : ");
                    affListe(lTaxis);
                    int choixTaxi = choixElt(lTaxis);
                    Taxi taxi = lTaxis.get(choixTaxi-1);
                    try{
                        location = new Location.LocationBuilder()
                                .setIdLoc(location.getIdLoc())
                                .setDateLoc(location.getDateLoc())
                                .setKmTot(location.getKmTotal())
                                .setNbrePassagers(location.getNbrePassagers())
                                .setClient(location.getClient())
                                .setVehicule(taxi)
                                .setAdrDebut(location.getAdrDebut())
                                .setAdrFin(location.getAdrFin())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification de la location " + e.getMessage());
                    }
                    break;

                case 5 :
                    List<Client> lClients = ((LocationPresenter)presenter).ListeClients();
                    System.out.println("Saisir le nouveau client : ");
                    affListe(lClients);
                    int choixClient = choixElt(lClients);
                    Client client = lClients.get(choixClient-1);
                    try {
                        location = new Location.LocationBuilder()
                                .setIdLoc(location.getIdLoc())
                                .setDateLoc(location.getDateLoc())
                                .setKmTot(location.getKmTotal())
                                .setNbrePassagers(location.getNbrePassagers())
                                .setClient(client)
                                .setVehicule(location.getVehicule())
                                .setAdrDebut(location.getAdrDebut())
                                .setAdrFin(location.getAdrFin())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification de la location " + e.getMessage());
                    }
                    break;
                case 6 :
                    List<Adresse> lAdresses =  ((LocationPresenter)presenter).ListeAdresse();
                    System.out.println("Saisir la nouvelle adresse de départ : ");
                    affListe(lAdresses);
                    int choixAdr = choixElt(lAdresses);
                    Adresse adrDebut = lAdresses.get(choixAdr-1);
                    try {
                        location = new Location.LocationBuilder()
                                .setIdLoc(location.getIdLoc())
                                .setDateLoc(location.getDateLoc())
                                .setKmTot(location.getKmTotal())
                                .setNbrePassagers(location.getNbrePassagers())
                                .setClient(location.getClient())
                                .setVehicule(location.getVehicule())
                                .setAdrDebut(adrDebut)
                                .setAdrFin(location.getAdrFin())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification de la location " + e.getMessage());
                    }
                    break;
                case 7 :
                    List<Adresse> lAdresses2 =  ((LocationPresenter)presenter).ListeAdresse();
                    System.out.println("Saisir la nouvelle adresse de retour : ");
                    affListe(lAdresses2);
                    int choixAdr2 = choixElt(lAdresses2);
                    Adresse adrFin = lAdresses2.get(choixAdr2-1);
                    try {
                        location = new Location.LocationBuilder()
                                .setIdLoc(location.getIdLoc())
                                .setDateLoc(location.getDateLoc())
                                .setKmTot(location.getKmTotal())
                                .setNbrePassagers(location.getNbrePassagers())
                                .setClient(location.getClient())
                                .setVehicule(location.getVehicule())
                                .setAdrDebut(location.getAdrDebut())
                                .setAdrFin(adrFin)
                                .build();
                    } catch (Exception e) {
                        logger.error("Erreur lors de la modification de la location " + e.getMessage());
                    }
                    break;

            }
        }while (choixMod != 8);
        presenter.update(location);
        locations = presenter.getAll();
    }

    @Override
    public void seek(){
        System.out.println("Saisir le numéro de la location à rechercher : ");
        int id = Integer.parseInt(saisie("[0-9]{1,3}", "Erreur de saisie "));
        Location location = ((SpecialLocationPresenter)presenter).getLocById(id);
    }

    @Override
    public Location select(List<Location> locations){
        System.out.println("Saisir le numéro de la location à sélectionner : ");
        affListe(locations);
        int choix = choixElt(locations);
        Location location = locations.get(choix-1);

        return location;
    }
    @Override
    protected void special() {
        List<String> listOptions = new ArrayList<>(Arrays.asList("Voir toutes les locations" , "Retour"));

        int choix;

        do {
            System.out.println("");
            affListe(listOptions);
            choix = choixElt(listOptions);
            switch (choix) {
                case 1 -> affListe(locations);
            }
        } while (choix != listOptions.size());
    }

}