package mvp.presenter;

import two_three.Client;
import two_three.Taxi;

import java.util.List;
import java.util.Map;

public interface SpecialTaxiPresenter {

    Map<Integer, String> getMapTaxis();

    Taxi readTaxiById(int idTaxi);

    List<Client> getClientsOfTaxi(Taxi taxi);

}
