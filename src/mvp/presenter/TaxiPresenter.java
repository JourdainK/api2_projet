package mvp.presenter;

import mvp.model.DAOTaxi;
import mvp.view.TaxiViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Taxi;


import java.util.List;

public class TaxiPresenter {
    //TODO methods , see teach versions
    private DAOTaxi model;
    private TaxiViewInterface view;
    private static final Logger logger = LogManager.getLogger(TaxiPresenter.class);

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
        else  {
            view.affMsg("Erreur : échec de l'ajout");
            logger.error("Erreur lors de l'ajout du taxi " + taxi.getImmatriculation());
        }
    }

    public void removeTaxi(Taxi taxi){
        boolean check;
        check = model.removeTaxi(taxi);
        if(check) view.affMsg("Taxi éffacé");
        else view.affMsg("Erreur, taxi non effacé");
    }

    public void updateTaxi(Taxi taxi){
        Taxi modifiedTaxi = model.updateTaxi(taxi);
        if(modifiedTaxi!=null) view.affMsg("Modification effectuée " + modifiedTaxi);
        else view.affMsg("Erreur, modification non effectuée");
    }

    public Taxi readTaxi(int idTaxi){
        Taxi tx = model.readTaxi(idTaxi);
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

    public List<Taxi> getListTaxis(){
        List<Taxi> listTaxis = model.getTaxis();

        return listTaxis;
    }

    //TODO specials when all other cruds are done

}
