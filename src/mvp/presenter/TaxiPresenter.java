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
        Taxi newTaxi = model.addTaxi(taxi);
        if(newTaxi!=null) view.affMsg("Taxi ajouté \nID : " + newTaxi.getIdTaxi() + "\t\timmatriculation : " + newTaxi.getImmatriculation());
        else view.affMsg("Erreur : échec de l'ajout");
    }

    public void removeTaxi(Taxi taxi){
        boolean check;
        check = model.removeTaxi(taxi);
        if(check) view.affMsg("Taxi éffacé");
        else view.affMsg("Erreur, taxi non effacé");
    }

    public void updateTaxi(Taxi taxi){
        Taxi modifiedTaxi = model.updateTaxi(taxi);
        if(modifiedTaxi!=null) view.affMsg("Modification éffectuée " + modifiedTaxi);
        else view.affMsg("Erreur, modification non éffectuée");
    }

    //TODO Presenter link readTaxi
    public void readTaxi(Taxi taxi){

    }


    public void getListTaxis(){
        List<Taxi> listTaxis = model.getTaxis();
        //TODO view.setListTaxis(listTaxis) -->>> TODO IN VIEW !

    }

}
