package mvp.model;

import two_three.Location;

import java.util.List;

public interface DAOLocation {
    Location addLocation(Location location);

    boolean removeLocation(Location location);

    Location updateLocation(Location location);

    Location readLocation(int idLocation);

    List<Location> getAllLocation();
}
