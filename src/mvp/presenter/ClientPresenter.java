package mvp.presenter;

import mvp.model.DAO;

import mvp.model.client.SpecialClient;
import mvp.view.ViewInterface;
import two_three.Client;
import two_three.Location;
import two_three.Taxi;


import java.util.Comparator;
import java.util.List;

public class ClientPresenter extends Presenter<Client> implements SpecialClientPresenter {
    public ClientPresenter(DAO<Client> model, ViewInterface<Client> view, Comparator<Client> cmp) {
        super(model, view, cmp);
        this.view.setPresenter(this);
    }

    @Override
    public Client readClientById(int idClient) {
        Client cli = model.readbyId(idClient);
        if (cli == null) {
            view.affMsg("Client non trouvé");
            return null;
        } else {
            view.affMsg("Client trouvé : " + cli);
            return cli;
        }
    }

    @Override
    public List<Taxi> getTaxisOfClient(Client client) {
        List<Taxi> lTaxisOfClient = ((SpecialClient) model).getTaxisOfClient(client);
        if (!lTaxisOfClient.isEmpty()) {
            view.affMsg("Taxis du client : ");
            view.affMsg(lTaxisOfClient.toString());
            return lTaxisOfClient;
        } else {
            view.affMsg("Aucun taxi trouvé pour ce client");
            return null;
        }
    }

    @Override
    public int getIdAddClient(Client client) {
        int id_cli = ((SpecialClient) model).getIdAddClient(client);
        if (id_cli > 0) {
            view.affMsg("Client ajouté avec le numéro d'identification : " + id_cli);
            return id_cli;
        } else {
            view.affMsg("Erreur lors de l'ajout du client");
            return -1;
        }
    }

    public void getListLocations(Client client){
        List<Location> llocs = client.getListLocations();
        if(!llocs.isEmpty()){
            view.affMsg("Liste des locations du client : ");
            view.affMsg(llocs.toString());
        } else view.affMsg("Aucune location trouvée pour ce client");
    }

}






















