package mvp.model;

import two_three.Client;

import java.util.List;

public interface DAOClient {

    Client addClient(Client client);

    boolean removeClient(Client client);

    Client updateClient(Client client);

    Client readClient(int idClient);
    //TODO readCLient EMAIL

    List<Client> getClients();
}
