package mvp.view;

import mvp.presenter.Presenter;
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
    private static final Logger logger = LogManager.getLogger(TaxiViewConsole.class);


    @Override
    public void setPresenter(Presenter<Taxi> presenter) {
        this.presenter = (TaxiPresenter) presenter;
    }

    @Override
    public void setListDatas(List<Taxi> ltaxis, Comparator<Taxi> cmp) {
        this.lTaxis = ltaxis;
        this.lTaxis.sort(cmp);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : " + msg);
    }

    @Override
    public void add() {
        System.out.println(" -- Encoder un nouveau taxi --\n ");
        System.out.println("Saisir l'immatriculation : ");
        String immat = saisie("^[T]{1}\\-([L]{1}||[X]{1})[A-Z]{2}\\-[0-9]{3}$", "Erreur de saisie, veuillez saisir une immatriculation de type 'T-XXX-000' ou 'T-LXX-000'\nSaisir l'immatriculation : ");
        int maxPass;
        do {
            System.out.println("Saisir le nombre de passagers maximum : ");
            String maxPass1 = saisie("[0-9]{1,3}", "Erreur de saisie ");
            maxPass = Integer.parseInt(maxPass1);
        } while (maxPass < 1);

        double prixkm;
        do {
            System.out.println("Saisir prix au km :");
            String prixkm1 = saisie("[0-9]{0,10}[.][0-9]{0,2}|[0-9]{0,10}", "Erreur de saisie, veuillez saisir un nombre réel (séparée d'un point) supérieur à 0\nSaisir le prix au km : ");
            prixkm = Double.parseDouble(prixkm1);
            if (prixkm <= 0) {
                System.out.println("Erreur, le prix au km doit étre supérieur à 0");
            }
        } while (prixkm <= 0);
        try {
            Taxi tmpTaxi = new Taxi.TaxiBuilder()
                    .setImmatriculation(immat).setNbreMaxPassagers(maxPass)
                    .setPrixKm(prixkm).build();
            presenter.add(tmpTaxi);
        } catch (Exception e) {
            logger.error("Erreur lors de l'ajout d'un taxi : " + e);
            e.printStackTrace();
        }
        lTaxis = presenter.getAll();
    }

    @Override
    public void remove() {

        System.out.println("-- Effacer un Taxi --");
        Taxi choixTaxi = getChoice(lTaxis);
        presenter.remove(choixTaxi);
        lTaxis = presenter.getAll();
    }

    @Override
    public void update() {
        int choixMod;
        Taxi chosenTaxi = getChoice(lTaxis);
        //System.out.println("chosen = "  + chosenTaxi);
        if (chosenTaxi != null) {
            String immat = chosenTaxi.getImmatriculation();
            int newnbrMax = chosenTaxi.getNbreMaxPassagers();
            double prixKm = chosenTaxi.getPrixKm();
            Taxi taxiModified = null;

            List<String> lmodif = new ArrayList<>(Arrays.asList("Immatriculation", "Nombre maximum de passager", "Prix au kilomètre", "retour"));
            do {
                System.out.println("Modifier : ");
                affListe(lmodif);
                choixMod = choixElt(lmodif);
                switch (choixMod) {

                    case 1:
                        System.out.print("\nSaisir la nouvelle immatriculation : ");
                        immat = saisie("^[T]{1}\\-([L]{1}||[X]{1})[A-Z]{2}\\-[0-9]{3}$", "Erreur de saisie, veuillez saisir une immatriculation de type 'T-XXX-000' ou 'T-LXX-000'\nSaisir l'immatriculation : ");
                        break;

                    case 2:
                        System.out.print("\nSaisir le nouveau nombre de passagers maximum : ");
                        do {
                            String newMax = saisie("[0-9]*", "Veuillez entrer un nombre entier");
                            newnbrMax = Integer.parseInt(newMax);
                        } while (newnbrMax < 0);

                        break;
                    case 3:
                        System.out.print("\nSaisir le nouveau prix au kilomètre : ");
                        do {
                            String newPrice = saisie("[0-9]{0,10}[.][0-9]{0,2}|[0-9]{0,10}", "Erreur de saisie, veuillez saisir un nombre réel (séparée d'un point) supérieur à 0\nSaisir le prix au km : ");
                            prixKm = Double.parseDouble(newPrice);
                        } while (prixKm < 0);

                        break;
                    case 4:

                        try {
                            taxiModified = new Taxi.TaxiBuilder()
                                    .setIdTaxi(chosenTaxi.getIdTaxi())
                                    .setImmatriculation(immat)
                                    .setPrixKm(prixKm)
                                    .setNbreMaxPassagers(newnbrMax)
                                    .build();
                        } catch (Exception e) {
                            logger.error("Erreur lors de la modification du taxi (prix au km) : " + e);
                            e.printStackTrace();
                        }
                        break;
                }
            } while (choixMod != 4);

            //equals based on id number and immatriculation
            if (!chosenTaxi.equals(taxiModified) || taxiModified.getNbreMaxPassagers()!= chosenTaxi.getNbreMaxPassagers() || taxiModified.getPrixKm()!= chosenTaxi.getPrixKm()) {
                presenter.update(taxiModified);
                lTaxis = presenter.getAll();
            } else System.out.println("Aucune modification effectuée");

        }

    }

    @Override
    public void seek() {
        System.out.println("\t--Recherche d'un taxi--");
        System.out.print("Saisir le numéro d'identification du taxi : ");
        String idTaxi = saisie("[0-9]*", "Veuillez saisir un nombre");
        int taxiID = Integer.parseInt(idTaxi);
        Taxi seekdTaxi = presenter.readTaxiById(taxiID);
    }


    @Override
    protected void special() {
        List<String> listOptions = new ArrayList<>(Arrays.asList("Voir tous les taxis","Voir les locations d'un taxi" ,"Voir les clients d'un taxi", "Voir les gains et kilomètres total d'un taxi", "Voir les locations entre deux dates d'un taxi", "Voir les kilomètres parcourus d'un taxi", "Voir le nombre de location et les gains d'un taxi, une date donnée", "Retour"));

        int choix;

        do {
            System.out.println("");
            affListe(listOptions);
            choix = choixElt(listOptions);
            switch (choix) {
                case 1 -> affListe(lTaxis);
                case 2 -> getAllLocationsChosenTaxi();
                case 3 -> affClient();
                case 4 -> taxiKmAndCashTotal();
                case 5 -> getLocationsBetween2Dates();
                case 6 -> getKmParcourus();
                case 7 -> getNbrLocAndTotalGain();
            }
        } while (choix != listOptions.size());
    }

    @Override
    public void affClient() {
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";
        System.out.println("\t-- Liste des clients d'un taxi --");
        System.out.println("Choisir un taxi : ");

        Taxi taxi = getChoice(lTaxis);
        /*
        Utilisation objet : liste de locations du taxi (via MVP) : utilisation de la méthode de classe getClientsOfTaxi()
        List<Client> lClients = taxi.getClientsOfTaxi();
         */

        //utilisation fonction sql :  client_by_taxi > exploitation du curseur
        List<Client> lClients = presenter.getClientsOfTaxi(taxi);

        System.out.println(ANSI_GREEN + "\n\tClients du taxi " + ANSI_RESET + taxi.getImmatriculation() + " : ");
        affListe(lClients);
    }

    @Override
    public void taxiKmAndCashTotal() {
        System.out.println("\t-- Liste des taxis avec le nombre de kilomètres parcourus et le montant total des locations --\n");
        Taxi taxi = getChoice(lTaxis);

        //utilisation de méthodes de classes de taxi : utilisation du modèle hybride
        System.out.println("Le taxi à parcouru un total de : " + taxi.getTotKm() + " km" + "\nGain total : " + taxi.getTotGain() + " €");
    }

    @Override
    public void getLocationsBetween2Dates() {
        Taxi taxi1 = getChoice(lTaxis);

        String dateDeb;
        do {
            System.out.println("Saisir la date de début (format : dd-mm-yyyy) : ");
            dateDeb = saisie("^[0-9]{2}\\-[0-9]{2}\\-[0-9]{4}$", "Erreur de saisie, veuillez saisir une date au format dd/mm/yyyy\nSaisir la date de début : ");
        } while (!isDateValid(dateDeb));
        LocalDate dateDebut = LocalDate.parse(dateDeb, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        String dateFin;
        do {
            System.out.println("Saisir la date de fin (format : dd-mm-yyyy) : ");
            dateFin = saisie("^[0-9]{2}\\-[0-9]{2}\\-[0-9]{4}$", "Erreur de saisie, veuillez saisir une date au format dd-mm-yyyy\nSaisir la date de fin : ");
        } while (!isDateValid(dateFin));
        LocalDate dateFinale = LocalDate.parse(dateFin, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Location> llocations = taxi1.getListLocationBetweenDates(dateDebut, dateFinale);
        Collections.sort(llocations, new Comparator<Location>() {
            @Override
            public int compare(Location o1, Location o2) {
                return o1.getIdLoc() - o2.getIdLoc();
            }
        });

        if(!llocations.isEmpty()){
            System.out.println("Liste des location entre le " + dateDebut + " et le " + dateFinale + " : ");
            affListe(llocations);
        } else System.out.println("Aucune location entre le " + dateDebut + " et le " + dateFinale + " pour le taxi " + taxi1.getImmatriculation());

    }

    @Override
    public void getKmParcourus() {
        Taxi chosentaxi = getChoice(lTaxis);
        int km = presenter.getKmParcourus(chosentaxi);
    }

    @Override
    public void getNbrLocAndTotalGain() {
        Taxi taxi = getChoice(lTaxis);
        String date;
        do {
            System.out.println("Saisir la date : ");
            date = saisie("^[0-9]{2}\\-[0-9]{2}\\-[0-9]{4}$", "Erreur de saisie, veuillez saisir une date au format dd/mm/yyyy\nSaisir la date : ");
        } while (!isDateValid(date));
        LocalDate dateLoc = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        HashMap<Integer, Double> map = presenter.getNbrLocAndTotalGain(taxi, dateLoc);

        if (map.isEmpty()) {
            System.out.println("Aucune location pour ce taxi à cette date");
        } else {
            for (Map.Entry<Integer, Double> entry : map.entrySet()) {
                System.out.println("Le taxi " + taxi.getImmatriculation() + " a effectué " + entry.getKey() + " locations pour un gain total de " + entry.getValue() + "€");
            }
        }

    }

    @Override
    public void getAllLocationsChosenTaxi() {
        Taxi taxi = getChoice(lTaxis);
        presenter.getAllLocationsChosenTaxi(taxi);
    }

}
