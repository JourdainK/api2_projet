package mvp.presenter;

import mvp.model.DAO;
import mvp.view.ViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;


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
    public void add(LocalDate date, int nbrKm, int nbrPassagers){
       //copie du modèle de Mr Poriaux > comfact > addLigne

        Taxi taxi;
        do{
            taxi = taxiPresenter.select();
            if (taxi.getNbreMaxPassagers() < nbrPassagers) {
                view.affMsg("Erreur : le taxi sélectionné ne peut pas accueillir autant de passagers");
            } else {
                view.affMsg("Taxi sélectionné : " + taxi);
            }
        }while (taxi == null || taxi.getNbreMaxPassagers()<nbrPassagers);

        Client client = clientPresenter.select();
        view.affMsg("Adresse de départ : ");
        Adresse adresseDepart = adressePresenter.select();
        view.affMsg("Adresse d'arrivée : ");
        Adresse adresseArrivee = adressePresenter.select();
        Location location = null;
        try{
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

            if(loc!=null){
                view.affMsg("Location ajoutée\nNuméro d'identification de la nouvelle location : " + loc.getIdLoc());
            }
            else{
                view.affMsg("Erreur : échec de l'ajout de la location");
            }
        }catch (Exception e){
            view.affMsg("Erreur lors de la création de la location");
            logger.error("Erreur : création location: " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public Location getLocById(int idLoc){
        Location loc = model.readbyId(idLoc);
        return loc;
    }

    @Override
    public List<Taxi> ListeTaxi(){
        List<Taxi> taxis = taxiPresenter.getAll();
        return taxis;
    }

    @Override
    public List<Client> ListeClients(){
        List<Client> clients = clientPresenter.getAll();
        return clients;
    }

    @Override
    public List<Adresse> ListeAdresse(){
        List<Adresse> adresses = adressePresenter.getAll();
        return adresses;
    }




}
