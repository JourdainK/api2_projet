package mvp.view;

import mvp.presenter.LocationPresenter;
import mvp.presenter.Presenter;
import mvp.presenter.SpecialLocationPresenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import static utilitaires.Utilitaire.*;

public class LocationViewConsole extends AbstractViewConsole<Location> implements SpecialLocationViewConsole {
    private Presenter<Location> presenter;
    private List<Location> locations;
    private static final Logger logger = LogManager.getLogger(LocationViewConsole.class);

    @Override
    public void setPresenter(Presenter<Location> presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Location> locations, Comparator<Location> cmpt) {
        this.locations = locations;
        this.locations.sort(cmpt);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : " + msg);
    }

    @Override
    public void add() {
        String date;
        boolean checkIsbefore = true;

        System.out.println("-- Encoder une nouvelle location --");
        do {
            System.out.println("Saisir la date de la location (format jj-mm-aaaa) : ");
            date = saisie("[0-9]{2}-[0-9]{2}-[0-9]{4}", "Erreur de saisie, veuillez saisir une date au format (jj-mm-aaaa)");
            try {
                checkIsbefore = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).isBefore(LocalDate.now());
                if (checkIsbefore)
                    System.out.println("Erreur de saisie, veuillez saisir une date supérieur à la date du jour.");
                else checkIsbefore = false;
            } catch (DateTimeParseException e) {
                logger.info("Erreur de saisie, veuillez saisir une date au format (jj-mm-aaaa) valide");
            }

        } while (!isDateValid(date) || checkIsbefore);
        LocalDate dateLoc = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        int nbrkm;
        do {
            System.out.println("Saisir le nombre de kilomètres total de la location : ");
            String nbrKilo = saisie("[0-9]{1,3}", "Erreur de saisie ");
            nbrkm = Integer.parseInt(nbrKilo);
        } while (nbrkm < 1);

        int nbrPass;
        do {
            System.out.println("Saisir le nombre de passagers : ");
            String nbrPassagers = saisie("[0-9]{1,3}", "Erreur de saisie ");
            nbrPass = Integer.parseInt(nbrPassagers);
        } while (nbrPass < 1);

        Taxi taxi;
        do {
            System.out.println("Choisir le taxi pour la location : ");
            taxi = getChoice(((SpecialLocationPresenter) presenter).getTaxiByNbrPass(nbrPass));
        } while (taxi == null);

        ((SpecialLocationPresenter) presenter).add(dateLoc, nbrkm, nbrPass, taxi);
    }

    @Override
    public void remove() {
        System.out.println("-- Supprimer une location --");
        Location location = getChoice(locations);
        presenter.remove(location);

        locations = presenter.getAll();
    }

    @Override
    public void update() {
        int choixMod;
        System.out.println("-- Modifier une location --");
        Location location = getChoice(locations);

        if (location != null) {
            LocalDate newDateLoc = location.getDateLoc();
            int newKm = location.getKmTotal();
            int newNbrPass = location.getNbrePassagers();
            Client client = location.getClient();
            Adresse adrDep = location.getAdrDebut();
            Adresse adrFin = location.getAdrFin();
            Taxi taxi = location.getVehicule();
            Location modifiedLoc = null;

            List<String> lmodif = new ArrayList<>(Arrays.asList("Date", "Nombre de kilomètres", "Nombre de passagers", "Taxi", "Client", "Adresse de départ", "Adresse de retour", "Retour"));
            do {
                System.out.println("Modifier : ");
                affListe(lmodif);
                choixMod = choixElt(lmodif);
                switch (choixMod) {
                    case 1:
                        String newDate;
                        do {
                            System.out.println("Saisir la nouvelle date (format jj-mm-aaaa) : ");
                            newDate = saisie("[0-9]{2}-[0-9]{2}-[0-9]{4}", "Erreur de saisie (format jj-mm-aaaa)");
                        } while (!isDateValid(newDate));
                        newDateLoc = LocalDate.parse(newDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                        break;

                    case 2:
                        do {
                            System.out.println("Saisir le nouveau nombre de kilomètres : ");
                            String nbrKilo = saisie("[0-9]{1,3}", "Erreur de saisie ");
                            newKm = Integer.parseInt(nbrKilo);
                        } while (newKm < 1);

                        break;

                    case 3:
                        do {
                            System.out.println("Saisir le nouveau nombre de passagers : ");
                            String nbrPassagers = saisie("[0-9]{1,3}", "Erreur de saisie ");
                            newNbrPass = Integer.parseInt(nbrPassagers);
                            if(newNbrPass > taxi.getNbreMaxPassagers()) System.out.println("Erreur de saisie, le nombre de passagers est supérieur au nombre de passagers maximum du taxi.");

                        } while (newNbrPass < 1 || newNbrPass > taxi.getNbreMaxPassagers());

                        break;

                    case 4:
                        List<Taxi> lTaxis = ((LocationPresenter) presenter).ListeTaxi();
                        do {
                            System.out.println("Choisir le nouveau taxi : ");
                            taxi = getChoice(lTaxis);
                        } while (taxi == null);

                        break;

                    case 5:
                        List<Client> lClients = ((LocationPresenter) presenter).ListeClients();

                        do {
                            System.out.println("Saisir le nouveau client : ");
                            client = getChoice(lClients);
                        } while (client == null);

                        break;
                    case 6:
                        List<Adresse> lAdresses = ((LocationPresenter) presenter).ListeAdresse();

                        do {
                            System.out.println("Saisir la nouvelle adresse de départ : ");
                            adrDep = getChoice(lAdresses);
                        } while (adrDep == null);

                        break;

                    case 7:
                        List<Adresse> lAdresses2 = ((LocationPresenter) presenter).ListeAdresse();

                        do {
                            System.out.println("Saisir la nouvelle adresse de retour : ");
                            adrFin = getChoice(lAdresses2);
                        } while (adrFin == null);

                        break;

                    case 8:
                        try {
                            modifiedLoc = new Location.LocationBuilder()
                                    .setIdLoc(location.getIdLoc())
                                    .setDateLoc(newDateLoc)
                                    .setKmTot(newKm)
                                    .setNbrePassagers(newNbrPass)
                                    .setClient(client)
                                    .setVehicule(taxi)
                                    .setAdrDebut(adrDep)
                                    .setAdrFin(adrFin)
                                    .build();
                        } catch (Exception e) {
                            logger.error("Erreur lors de la modification de la location " + e.getMessage());
                        }
                }

            } while (choixMod != 8);

            if (!location.equals(modifiedLoc) || !location.getVehicule().equals(modifiedLoc.getVehicule()) || location.getKmTotal() != modifiedLoc.getKmTotal() || location.getNbrePassagers() != modifiedLoc.getNbrePassagers() || !location.getClient().equals(modifiedLoc.getClient())) {
                presenter.update(modifiedLoc);
                locations = presenter.getAll();
            } else System.out.println("Aucune modification effectuée");

        }

    }

    @Override
    public void seek() {
        System.out.println("Saisir le numéro de la location à rechercher : ");
        int id = Integer.parseInt(saisie("[0-9]{1,3}", "Erreur de saisie "));
        Location location = ((SpecialLocationPresenter) presenter).getLocById(id);
    }

    @Override
    protected void special() {
        List<String> listOptions = new ArrayList<>(Arrays.asList("Voir toutes les locations", "Voir toutes les locations ayant la même adresse de départ et d'arrivée", "Voir les locations et le montant total d'une date", "Retour"));

        int choix;

        do {
            System.out.println("");
            affListe(listOptions);
            choix = choixElt(listOptions);
            switch (choix) {
                case 1 -> affListe(locations);
                case 2 -> ((SpecialLocationPresenter) presenter).getAllLocatSamePlace();
                case 3 -> getAllLocatSamePlaceWithPrice();
            }
        } while (choix != listOptions.size());
    }

    @Override
    public void getAllLocatSamePlaceWithPrice() {
        String date;
        do {
            System.out.println("Saisir la date de location : ");
            date = saisie("[0-9]{2}-[0-9]{2}-[0-9]{4}", "Erreur de saisie, la date doit être au format dd-MM-yyyy");
        } while (!isDateValid(date));

        ((SpecialLocationPresenter) presenter).getAllLocatSamePlaceWithPrice(LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }


}