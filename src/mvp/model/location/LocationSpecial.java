package mvp.model.location;

import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface LocationSpecial {
    Client getClientById(int id);

    Adresse getAdresseByID(int id);

    Taxi getTaxiByID(int id);

    List<Location> getAllLocatSamePlace();

    HashMap<List<Location>,Double> getAllLocatSamePlaceWithPrice(LocalDate date);

    double getTotalLocat(int id);

    List<Taxi> getTaxiByNbrPass(int nbrPass);



}
