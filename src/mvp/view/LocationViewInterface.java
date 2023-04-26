package mvp.view;

import mvp.presenter.AdressePresenter;
import mvp.presenter.LocationPresenter;
import two_three.Location;

import java.util.List;

public interface LocationViewInterface {
    public void setPresenter(LocationPresenter presenter);
    public void setListDatas(List<Location> locations);
    public void affMsg(String msg);
}
