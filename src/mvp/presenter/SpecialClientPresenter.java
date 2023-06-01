package mvp.presenter;

import two_three.Client;
import two_three.Taxi;

import java.util.List;

public interface SpecialClientPresenter {
    Client readClientById(int idClient);

    List<Taxi> getTaxisOfClient(Client client);

    int getIdAddClient(Client client);

    void getListLocations(Client client);
}
