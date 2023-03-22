package mvp.view;

import mvp.presenter.TaxiPresenter;
import two_three.Taxi;

import java.util.List;

public interface TaxiViewInterface {
    public void setPresenter(TaxiPresenter presenter);

    public void setListDatas(List<Taxi> ltaxis);

    public void affMsg(String msg);
}
