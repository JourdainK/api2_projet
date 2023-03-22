package mvp.presenter;

import mvp.model.DAOTaxi;
import mvp.view.TaxiViewInterface;
import two_three.Taxi;

import java.util.List;

public class TaxiPresenter {
    //TODO methods , see teach versions
    private DAOTaxi model;
    private TaxiViewInterface view;

    public TaxiPresenter(DAOTaxi model, TaxiViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start(){
        List<Taxi> taxis = model.getTaxis();
        view.setListDatas(taxis);
    }

    public void addTaxi(Taxi taxi){

    }

}
