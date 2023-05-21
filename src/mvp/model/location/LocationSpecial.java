package mvp.model.location;

import two_three.Adresse;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

public interface LocationSpecial {
    public Client getClientById(int id);

    public Adresse getAdresseByID(int id);

    public Taxi getTaxiByID(int id);


}
