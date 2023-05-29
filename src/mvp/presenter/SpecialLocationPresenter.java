package mvp.presenter;

import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface SpecialLocationPresenter{
    List<Taxi> ListeTaxi();
    List<Client> ListeClients();
    List<Adresse> ListeAdresse();
    Location getLocById(int idLoc);
    void add(LocalDate date, int nbrKm, int nbrPassagers,Taxi taxi);

    void setClientPresenter(ClientPresenter clientPresenter);

    void setTaxiPresenter(TaxiPresenter taxiPresenter);
    void setAdressePresenter(AdressePresenter adressePresenter);

    List<Location> getAllLocatSamePlace();

    HashMap<List<Location>, Double> getAllLocatSamePlaceWithPrice(LocalDate date);

    double getTotalLocat(int id);

    List<Taxi> getTaxiByNbrPass(int nbrPass);

}
