package mvp.presenter;

import mvp.model.DAOLocation;
import mvp.view.LocationViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Location;

import java.util.List;

public class LocationPresenter {
    //TODO all presenter Location
    private DAOLocation model;
    private LocationViewInterface view;
    private static final Logger logger = LogManager.getLogger(LocationPresenter.class);

    public LocationPresenter(DAOLocation model, LocationViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start(){
        List<Location> locations = model.getAllLocation();
        view.setListDatas(locations);
    }
}
