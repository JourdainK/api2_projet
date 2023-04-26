package mvp.view;

import mvp.presenter.AdressePresenter;
import two_three.Adresse;

import java.util.List;

public interface AdresseViewInterface {

    public void setPresenter(AdressePresenter presenter);

    public void setListDatas(List<Adresse> lAdresses);

    public void affMsg(String msg);

}
