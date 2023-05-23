package mvp.model.client;

import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.util.List;

public interface SpecialClient {
    //TODO special client
    List<Location> getLocationsOfClient(Client client);
    Client getClientById(int id);
    Taxi getTaxiByID(int id);
    Adresse getAdresseByID(int id);

}
