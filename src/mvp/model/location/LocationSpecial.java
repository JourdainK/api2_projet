package mvp.model.location;

import two_three.Adresse;
import two_three.Client;
import two_three.Location;

public interface LocationSpecial {
    //TODO check comfact example magasin -> understand , implement
    public boolean addClient(Location loc, Client client);
    public boolean addAdresse(Adresse adresse);
}
