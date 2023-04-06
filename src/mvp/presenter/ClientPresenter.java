package mvp.presenter;

import mvp.model.DAOClient;
import mvp.view.ClientViewInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Client;

import java.util.List;

public class ClientPresenter {
    private DAOClient model;

    private ClientViewInterface view;

    private static final Logger logger = LogManager.getLogger(ClientPresenter.class);

    public ClientPresenter(DAOClient model, ClientViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start(){
        List<Client> clients = model.getClients();
        view.setListDatas(clients);
    }

    public void addClient(Client client){
        Client newCli = model.addClient(client);
        if(newCli!=null) view.affMsg("Client ajouté (id : " + client.getIdclient() + " )");
        else{
            view.affMsg("Erreur : échec de l'ajout client");
            logger.error("Erreur : échec de l'ajout client " + client.getIdclient() + "\t" + client.getNom());
        }
    }

    public void removeClient(Client client){
        boolean check;
        check = model.removeClient(client);
        if(check) view.affMsg("Client effacé");
        else {
            view.affMsg("Erreur, client non effacé");
            logger.error("Erreur effacement client : " + client.getIdclient() + " " + client.getNom());
        }
    }

    public void updateClient(Client client){
        Client updateClient = model.updateClient(client);
        if(updateClient!=null) view.affMsg("Modification effectuée" + updateClient);
        else view.affMsg("Erreur, modification non effectuée");
    }

    public Client readClient(int numClient){
        Client retClient = model.readClient(numClient);

        if(retClient==null){
            view.affMsg("Client non trouvé");
            return null;
        }
        else {
            view.affMsg("Client trouvé :" + retClient);
            return retClient;
        }
    }

    public List<Client> getClients() {
        List<Client> listCli = model.getClients();

        return listCli;
    }

}
