package mvp.view;

import two_three.Location;
import two_three.Taxi;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public interface SpecialTaxiViewConsole {
    void affClient();
    void taxiKmAndCashTotal();

    void getLocationsBetween2Dates();

    void getKmParcourus();

    void getNbrLocAndTotalGain();

}
