package mvp.model.taxi;

import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.util.List;
import java.util.Map;

public interface TaxiSpecial {

    List<Taxi> taxisUtilisés(Client client);
    List<Location> allLocTaxi(Taxi taxi);
    List<Adresse> allAdressTaxi(Taxi taxi);

    public Client getClientById(int id);

    public Adresse getAdresseByID(int id);

    public Taxi getTaxiByID(int id);
    public Map<Integer, String> getTaxisMap();
    public List<Client> getClientsOfTaxi(Taxi taxi);
}
