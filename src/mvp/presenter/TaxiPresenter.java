package mvp.presenter;

import mvp.model.DAO;
import mvp.view.TaxiViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Taxi;

import java.util.List;

public class TaxiPresenter {
    private DAO<Taxi> model;
    private TaxiViewInterface view;
    private static final Logger logger = LogManager.getLogger(TaxiPresenter.class);

    public TaxiPresenter(DAO<Taxi> model, TaxiViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start(){
        List<Taxi> taxis = model.getAll();
        view.setListDatas(taxis);
    }

    public int addTaxi(Taxi taxi){
        Taxi newTaxi = model.add(taxi);
        if(newTaxi!=null) view.affMsg("Taxi ajouté \nID : " + newTaxi.getIdTaxi() + "\t\timmatriculation : " + newTaxi.getImmatriculation());
        else  {
            view.affMsg("Erreur : échec de l'ajout");
            logger.error("Erreur lors de l'ajout du taxi " + taxi.getImmatriculation());
        }

        return newTaxi.getIdTaxi();
    }

    public void removeTaxi(Taxi taxi){
        boolean check;
        check = model.remove(taxi);
        if(check) view.affMsg("Taxi effacé");
        else view.affMsg("Erreur, taxi non effacé");
    }

    public void updateTaxi(Taxi taxi){
        Taxi modifiedTaxi = model.update(taxi);
        if(modifiedTaxi!=null) view.affMsg("Modification effectuée " + modifiedTaxi);
        else view.affMsg("Erreur, modification non effectuée");
    }

    public Taxi readTaxi(int idTaxi){
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

    public List<Taxi> getListTaxis(){
        List<Taxi> listTaxis = model.getAll();

        return listTaxis;
    }

    //TODO specials when all other cruds are done
    //select a taxi From listTaxis and return it
    public Taxi selectTaxi(){
        List<Taxi> listTaxis = getListTaxis();
        Taxi taxi = view.selectTaxi(listTaxis);
        return taxi;
    }

}
