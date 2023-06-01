package mvp.presenter;

import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SpecialTaxiPresenter {

    Map<Integer, String> getMapTaxis();

    Taxi readTaxiById(int idTaxi);

    List<Client> getClientsOfTaxi(Taxi taxi);

    int getKmParcourus(Taxi taxi);

    HashMap<Integer, Double> getNbrLocAndTotalGain(Taxi taxi, LocalDate dateLoc);

    void getAllLocationsChosenTaxi(Taxi taxi);

}
