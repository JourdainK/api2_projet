package mvp.view;

import mvp.presenter.ClientPresenter;
import two_three.Client;

import java.util.List;

public interface ClientViewInterface {

    public void setPresenter(ClientPresenter presenter);

    public void setListDatas(List<Client> lClient);

    public void affMsg(String msg);

    public Client select(List<Client> all);

}
