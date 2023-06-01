package mvp.presenter;

import mvp.model.DAO;
import mvp.model.taxi.TaxiSpecial;
import mvp.view.ViewInterface;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxiPresenter extends Presenter<Taxi> implements SpecialTaxiPresenter {

    public TaxiPresenter(DAO<Taxi> model, ViewInterface<Taxi> view, Comparator<Taxi> cmp) {
        super(model, view, cmp);
        this.view.setPresenter(this);
    }

    @Override
    public Taxi readTaxiById(int idTaxi) {
        Taxi tx = model.readbyId(idTaxi);
        if (tx == null) {
            view.affMsg("Taxi non trouvé\n");
            return null;
        } else {
            view.affMsg(tx.toString());
            view.affMsg("\n");
            return tx;
        }
    }

    @Override
    public Map<Integer, String> getMapTaxis() {
        Map<Integer, String> mapTaxis = ((TaxiSpecial) model).getTaxisMap();

        return mapTaxis;
    }

    @Override
    public List<Client> getClientsOfTaxi(Taxi taxi) {
        List<Client> lClients = ((TaxiSpecial) model).getClientsOfTaxi(taxi);

        return lClients;
    }

    @Override
    public int getKmParcourus(Taxi taxi) {
        int km = ((TaxiSpecial) model).getKmParcourus(taxi);
        if (km <= 0) view.affMsg("Le taxi n'as pas encore effectué de location\n");
        else view.affMsg("Km parcourus par le taxi  " + taxi.getImmatriculation() + " : " + km + "km\n");
        return km;
    }

    @Override
    public HashMap<Integer, Double> getNbrLocAndTotalGain(Taxi taxi, LocalDate dateLoc) {
        HashMap<Integer, Double> map = ((TaxiSpecial) model).getNbrLocAndTotalGain(taxi, dateLoc);
        if (map.isEmpty()) view.affMsg("Le taxi n'as pas encore effectué de location\n");

        return map;
    }

    @Override
    public void getAllLocationsChosenTaxi(Taxi taxi) {
        List<Location> lLoc = taxi.getListTaxiLoc();
        if(lLoc.isEmpty()) {
            view.affMsg("Le taxi n'as pas encore effectué de location\n");
        }
        else {
            view.affMsg(lLoc.toString());
        }

    }

}
