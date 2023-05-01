package mvp.presenter;

import mvp.model.DAO;
import mvp.view.LocationViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Location;

import java.util.List;

public class LocationPresenter {
    //TODO all presenter Location
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
}
