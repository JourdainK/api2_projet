package mvp.model.taxi;

import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TaxiSpecial {

    List<Taxi> taxisUtilisés(Client client);
    List<Location> allLocTaxi(Taxi taxi);
    List<Adresse> allAdressTaxi(Taxi taxi);

    Client getClientById(int id);

    Adresse getAdresseByID(int id);

    Taxi getTaxiByID(int id);
    Map<Integer, String> getTaxisMap();
    List<Client> getClientsOfTaxi(Taxi taxi);
    int getKmParcourus(Taxi taxi);

    HashMap<Integer,Double> getNbrLocAndTotalGain(Taxi taxi, LocalDate dateLoc);


}
