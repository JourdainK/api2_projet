package mvp.model;

import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.util.List;

public interface TaxiSpecial {

    List<Taxi> taxisUtilisés(Client client);
    List<Location> allLocTaxi(Taxi taxi);
    List<Adresse> allAdressTaxi(Taxi taxi);
}
