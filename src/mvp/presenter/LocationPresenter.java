package mvp.presenter;

import mvp.model.DAO;
import mvp.view.LocationViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Location;

import java.util.List;

public class LocationPresenter {
    private DAO<Location> model;
    private LocationViewInterface view;
    private static final Logger logger = LogManager.getLogger(LocationPresenter.class);

    public LocationPresenter(DAO<Location> model, LocationViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start(){
        List<Location> locations = model.getAll();
        view.setListDatas(locations);
    }

    public int add(Location location){
        Location newLoc = model.add(location);
        if(newLoc!=null) view.affMsg("Location ajoutée\nNuméro d'identification de la nouvelle location : " + location.getIdLoc());
        else{
            view.affMsg("Erreur : échec de l'ajout de la location");
        }

        return newLoc.getIdLoc();
    }

    public void remove(Location location){
        boolean check;
        check = model.remove(location);
        if(check) view.affMsg("Location effacée");
        else view.affMsg("Erreur, location non effacée");
    }

    public void update(Location location){
        Location modifiedLocation = model.update(location);
        if(modifiedLocation!=null) view.affMsg("Modification effectuée : " + modifiedLocation);
        else view.affMsg("Erreur, modification non effectuée");
    }


    public Location read(int idLoc){
        Location loc = model.readbyId(idLoc);
        if(loc==null){
            view.affMsg("Aucune location trouvée");
            return null;
        }
        else{
            view.affMsg(loc.toString());
            return loc;
        }
    }

    public List<Location> getAll(){
        List<Location> ldatas = model.getAll();

        return ldatas;
    }




    //TODO specials
}
