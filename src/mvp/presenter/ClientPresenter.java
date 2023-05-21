package mvp.presenter;

import mvp.model.DAO;
import mvp.view.ClientViewInterface;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Client;


import java.util.List;

public class ClientPresenter {
    private DAO<Client> model;

    private ClientViewInterface view;

    private static final Logger logger = LogManager.getLogger(ClientPresenter.class);

    public ClientPresenter(DAO<Client> model, ClientViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start(){
        List<Client> clients = model.getAll();
        view.setListDatas(clients);
    }

    public int addClient(Client client){
        Client newCli = model.add(client);
        if(newCli!=null) view.affMsg("Client ajouté (id : " + client.getIdclient() + " )");
        else{
            view.affMsg("Erreur : échec de l'ajout client");
            logger.error("Erreur : échec de l'ajout client " + client.getIdclient() + "\t" + client.getNom());
        }
        return client.getIdclient();
    }

    public void removeClient(Client client){
        boolean check;
        check = model.remove(client);
        if(check) view.affMsg("Client effacé");
        else {
            view.affMsg("Erreur, client non effacé");
            logger.error("Erreur effacement client : " + client.getIdclient() + " " + client.getNom());
        }
    }

    public void updateClient(Client client){
        Client updateClient = model.update(client);
        if(updateClient!=null) view.affMsg("Modification effectuée" + updateClient);
        else view.affMsg("Erreur, modification non effectuée");
    }

    public Client readClient(Client client){
        Client retClient = model.read(client);

        if(retClient==null){
            view.affMsg("Client non trouvé");
            return null;
        }
        else {
            view.affMsg("Client trouvé :" + retClient);
            return retClient;
        }
    }

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

    public List<Client> getClients() {
        List<Client> listCli = model.getAll();

        return listCli;
    }

    //TODO client Specials

    public Client select(){
        List<Client> listCli = model.getAll();
        Client cl = view.select(listCli);
        return cl;
    }
}
