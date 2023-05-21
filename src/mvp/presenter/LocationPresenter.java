package mvp.presenter;

import mvp.model.DAO;
import mvp.view.LocationViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.time.LocalDate;
import java.util.List;

import static utilitaires.Utilitaire.affListe;

public class LocationPresenter {
    private DAO<Location> model;
    private LocationViewInterface view;

    private ClientPresenter clientPresenter;
    private AdressePresenter adressePresenter;
    private TaxiPresenter taxiPresenter;

    private static final Logger logger = LogManager.getLogger(LocationPresenter.class);

    public void setClientPresenter(ClientPresenter clientPresenter) {
        this.clientPresenter = clientPresenter;
    }
    public void setTaxiPresenter(TaxiPresenter taxiPresenter) {
        this.taxiPresenter = taxiPresenter;
    }
    public void setAdressePresenter(AdressePresenter adressePresenter) {
        this.adressePresenter = adressePresenter;
    }


    public LocationPresenter(DAO<Location> model, LocationViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start(){
        List<Location> locations = model.getAll();
        view.setListDatas(locations);
    }

    public void add(LocalDate date, int nbrKm, int nbrPassagers){
       //copie du modèle de Mr Poriaux > comfoact > addLigne

        Taxi taxi;
        do{
            taxi = taxiPresenter.selectTaxi();
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

    public void remove(Location location){
        boolean check;
        check = model.remove(location);
        if(check) view.affMsg("Location effacée");
        else view.affMsg("Erreur, location non effacée");
    }

    public void update(Location location){
        Location modifiedLocation = model.update(location);
        if(modifiedLocation!=null) view.affMsg("Modification effectuée : " + modifiedLocation);
        else view.affMsg("Erreur, modification non effectuée");
    }


    public Location read(int idLoc){
        Location loc = model.readbyId(idLoc);
        if(loc==null){
            view.affMsg("Aucune location trouvée");
            return null;
        }
        else{
            view.affMsg(loc.toString());
            return loc;
        }
    }

    public List<Location> getAll(){
        List<Location> ldatas = model.getAll();

        return ldatas;
    }

    //TODO specials methods

    public List<Taxi> ListeTaxi(){
        List<Taxi> taxis = taxiPresenter.getListTaxis();
        return taxis;
    }

    public List<Client> ListeClients(){
        List<Client> clients = clientPresenter.getClients();
        return clients;
    }

    public List<Adresse> ListeAdresse(){
        List<Adresse> adresses = adressePresenter.getAll();
        return adresses;
    }




}
