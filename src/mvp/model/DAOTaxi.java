package mvp.model;

import two_three.Taxi;

import java.util.List;

public interface DAOTaxi {

    Taxi addTaxi(Taxi taxi);

    boolean removeTaxi(Taxi taxi);

    Taxi updateTaxi(Taxi taxi);

    Taxi readTaxi(int idTaxi);
    //TODO READ -> IMMATRICULATION

    List<Taxi> getTaxis();
}
