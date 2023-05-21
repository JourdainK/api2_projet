package mvp.presenter;

import mvp.model.DAO;

import mvp.view.ViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Client;


import java.util.Comparator;
import java.util.List;

public class ClientPresenter extends Presenter<Client> implements SpecialClientPresenter {
    private static final Logger logger = LogManager.getLogger(ClientPresenter.class);

    public ClientPresenter(DAO<Client> model, ViewInterface<Client> view, Comparator<Client> cmp){
        super(model, view,cmp);
        this.view.setPresenter(this);
    }

    @Override
    public Client readClientById(int idClient){
        Client cli = model.readbyId(idClient);
        if(cli == null) {
            view.affMsg("Client non trouvé");
            return null;
        }
        else{
            view.affMsg("Client trouvé : " + cli);
            return cli;
        }
    }

    //TODO client Specials
}
