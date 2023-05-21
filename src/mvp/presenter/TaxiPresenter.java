package mvp.presenter;

import mvp.model.DAO;
import mvp.model.taxi.TaxiSpecial;
import mvp.view.TaxiViewInterface;
import mvp.view.ViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Taxi;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TaxiPresenter extends Presenter<Taxi> implements SpecialTaxiPresenter {
    private static final Logger logger = LogManager.getLogger(TaxiPresenter.class);

    public TaxiPresenter(DAO<Taxi> model, ViewInterface<Taxi> view, Comparator<Taxi> cmp) {
        super(model, view, cmp);
        this.view.setPresenter(this);
    }

    @Override
    public Taxi readTaxiById(int idTaxi){
        Taxi tx = model.readbyId(idTaxi);
        if(tx==null) {
            view.affMsg("Taxi non trouvé\n");
            return null;
        }
        else {
            view.affMsg(tx.toString());
            view.affMsg("\n");
            return tx;
        }
    }

    //TODO specials when all other cruds are done

    @Override
    public Map<Integer, String> getMapTaxis(){
        Map<Integer, String> mapTaxis = ((TaxiSpecial) model).getTaxisMap();

        return mapTaxis;
    }

}
