package mvp.presenter;

import two_three.Taxi;

import java.util.Map;

public interface SpecialTaxiPresenter {

    Map<Integer, String> getMapTaxis();

    Taxi readTaxiById(int idTaxi);

}
