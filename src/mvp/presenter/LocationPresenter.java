package mvp.presenter;

import mvp.model.DAO;
import mvp.model.location.LocationSpecial;
import mvp.view.ViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LocationPresenter extends Presenter<Location> implements SpecialLocationPresenter {
    private ClientPresenter clientPresenter;
    private AdressePresenter adressePresenter;
    private TaxiPresenter taxiPresenter;

    private static final Logger logger = LogManager.getLogger(LocationPresenter.class);

    public LocationPresenter(DAO<Location> model, ViewInterface<Location> view, Comparator<Location> cmp) {
        super(model, view, cmp);
        this.view.setPresenter(this);
    }

    @Override
    public void setClientPresenter(ClientPresenter clientPresenter) {
        this.clientPresenter = clientPresenter;
    }

    @Override
    public void setTaxiPresenter(TaxiPresenter taxiPresenter) {
        this.taxiPresenter = taxiPresenter;
    }

    @Override
    public void setAdressePresenter(AdressePresenter adressePresenter) {
        this.adressePresenter = adressePresenter;
    }

    @Override
    public void add(LocalDate date, int nbrKm, int nbrPassagers, Taxi taxi) {
        //copie du modèle de Mr Poriaux > comfact > addLigne

        view.affMsg("Client : ");
        Client client = clientPresenter.select();
        view.affMsg("Adresse de départ : ");
        Adresse adresseDepart = adressePresenter.select();
        view.affMsg("Adresse d'arrivée : ");
        Adresse adresseArrivee = adressePresenter.select();
        Location location = null;
        try {
            location = new Location.LocationBuilder()
                    .setDateLoc(date)
                    .setKmTot(nbrKm)
                    .setNbrePassagers(nbrPassagers)
                    .setClient(client)
                    .setVehicule(taxi)
                    .setAdrDebut(adresseDepart)
                    .setAdrFin(adresseArrivee)
                    .build();
            Location loc = model.add(location);
            //utilisation du travail API/SGBD , on récupère le prix total de la location via une fonction pl/sql
            double price = ((LocationSpecial) model).getTotalLocat(loc.getIdLoc());
            loc.setTotal(price);

            if (loc != null) {
                view.affMsg("Location ajoutée\nNuméro d'identification de la nouvelle location : " + loc.getIdLoc());
            } else {
                view.affMsg("Erreur : échec de l'ajout de la location");
            }
        } catch (Exception e) {
            view.affMsg("Erreur lors de la création de la location");
            logger.error("Erreur : création location: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Location getLocById(int idLoc) {
        Location loc = model.readbyId(idLoc);
        if (loc != null) {
            view.affMsg("Location : " + loc.toString());
        } else view.affMsg("Erreur : location introuvable");
        return loc;
    }

    @Override
    public List<Taxi> ListeTaxi() {
        List<Taxi> taxis = taxiPresenter.getAll();
        return taxis;
    }

    @Override
    public List<Client> ListeClients() {
        List<Client> clients = clientPresenter.getAll();
        return clients;
    }

    @Override
    public List<Adresse> ListeAdresse() {
        List<Adresse> adresses = adressePresenter.getAll();
        return adresses;
    }

    @Override
    public void getAllLocatSamePlace() {
        List<Location> locations = ((LocationSpecial) model).getAllLocatSamePlace();
        view.affMsg("Locations : \n" + locations.toString());
    }

    @Override
    public void getAllLocatSamePlaceWithPrice(LocalDate date) {
        HashMap<List<Location>, Double> locations = ((LocationSpecial) model).getAllLocatSamePlaceWithPrice(date);
        if (locations.isEmpty()) {
            view.affMsg("Aucune location n'a été trouvée pour cette date");
        } else {
            for (Map.Entry<List<Location>, Double> entry : locations.entrySet()) {
                if (entry.getKey().size() > 0) {
                    List<Location> loc = entry.getKey();
                    view.affMsg("Locations : ");
                    view.affMsg(loc.toString());
                    view.affMsg("Gain total de la journée ( " + date + ") : " + entry.getValue() + " €");
                } else {
                    view.affMsg("Aucune location pour cette date");
                }
            }
        }
        //throw to the garbage collector
        locations = null;
    }

    @Override
    public double getTotalLocat(int id) {
        double total = ((LocationSpecial) model).getTotalLocat(id);
        return total;
    }

    @Override
    public List<Taxi> getTaxiByNbrPass(int nbrPass) {
        List<Taxi> taxis = ((LocationSpecial) model).getTaxiByNbrPass(nbrPass);

        if (taxis.size() == 0) {
            view.affMsg("Aucun taxi n'a été trouvé pour ce nombre de passagers");
            return null;
        } else {
            return taxis;
        }

    }

}