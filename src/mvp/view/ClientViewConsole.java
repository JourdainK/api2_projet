package mvp.view;

import mvp.presenter.Presenter;
import mvp.presenter.SpecialClientPresenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Client;
import two_three.Taxi;

import static utilitaires.Utilitaire.*;

import java.util.*;

public class ClientViewConsole extends AbstractViewConsole<Client> implements SpecialClientViewConsole{
    private Presenter<Client> presenter;
    private List<Client> lClients;
    private Scanner sc = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(ClientViewConsole.class);
    @Override
    public void setPresenter(Presenter<Client> presenter) { this.presenter = presenter; }

    @Override
    public void setListDatas(List<Client> lClient, Comparator<Client> cmp) {
        this.lClients = lClient;
        this.lClients.sort(cmp);
        //affListe(lClients);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : " + msg);
    }
    @Override
    public void add(){

        System.out.println("\n--Ajout d'un client --");
        System.out.println("Saisir l'email du client : ");
        //REGEX by chatGPT
        String email = saisie("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$","veuillez saisir un email valide");
        System.out.println("Saisir le nom du client : ");
        //REGEX by chatGPT
        String nom = saisie("^[A-Z][a-zçéèêëîïôöûü']+([-\\s][A-ZÇÉÈ][a-zçéèêëîïôöûü']+)*$","Veuillez saisir un nom commençant par une majuscule");
        System.out.println("Saisir le prénom du client : ");
        //REGEX by chatGPT
        String pren = saisie("^[A-Z][a-zçéèêëîïôöûü']+([-\\s][A-Z][a-zçéèêëîïôöûü']+)*$","Veuillez saisir un prénom commençant par une majuscule");
        System.out.println("Saisir le numéro de téléphone du client : ");
        //regex by me
        //String phone = saisie("^([04]+[0-9]{2}||[065])\\/[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}$","Veuillez saisir un numéro de téléphone valide");
        String phone = sc.nextLine();
        try{
            Client cli = new Client.ClientBuilder()
                    .setMail(email)
                    .setNom(nom)
                    .setPrenom(pren)
                    .setTel(phone)
                    .build();
            //presenter.addClient -> return idClient
            int idcli = ((SpecialClientPresenter) presenter).getIdAddClient(cli);
            cli.setIdClient(idcli);

        }catch (Exception e){
            logger.error("Erreur lors de la création du client : " + e);
            e.printStackTrace();
        }
        lClients = presenter.getAll();
    }


    @Override
    public void remove(){
        System.out.println("-- Suppression d'un client--\n");

        Client clientDelete = getChoice(lClients);
        System.out.println("Client à supprimer : " + clientDelete);
        System.out.println("1.Confirmer l'effacement \n2.Annuler l'effacement" );
        String keepOn1 = saisie("[1-2]{1}","Veuillez saisir :  \n1 pour confirmer effacement\n2 pour annuler");
        int keepOn  = Integer.parseInt(keepOn1);
        if(keepOn==1){
            presenter.remove(clientDelete);
            lClients = presenter.getAll();
        }
        else System.out.println("effacement annulé");
    }

    @Override
    public void update(){

        int choixMod = -1;
        Client chosenClient = getChoice(lClients);
        List<String> lOptions = new ArrayList<>(Arrays.asList("Mail","Nom","Prénom","Téléphone","Retour"));

        if(chosenClient != null){
            System.out.println("Client à modifier choisi : " + chosenClient);

            String newName = chosenClient.getNom();
            String newPren = chosenClient.getPrenom();
            String newMail = chosenClient.getMail();
            String newPhone = chosenClient.getTel();
            Client modifClient = null;
            do{
                System.out.println("\t-- Modifier --");
                affListe(lOptions);
                choixMod = choixElt(lOptions);
                switch (choixMod){
                    case 1 :
                        System.out.print("\nSaisir le nouvel email : ");
                        newMail = saisie("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$","veuillez saisir un email valide");

                        break;
                    case 2 :
                        System.out.print("\nSaisir le nouveau nom : ");
                        newName = saisie("^[A-Z][a-zçéèêëîïôöûü']+([-\\s][A-ZÇÉÈ][a-zçéèêëîïôöûü']+)*$","Veuillez saisir un nom commençant par une majuscule");

                        break;
                    case 3 :
                        System.out.print("\nSaisir le nouveau prénom : ");
                        newPren = saisie("^[A-Z][a-zçéèêëîïôöûü']+([-\\s][A-Z][a-zçéèêëîïôöûü']+)*$","Veuillez saisir un prénom commençant par une majuscule");

                        break;
                    case 4 :
                        System.out.print("\nSaisir le nouveau numéro de téléphone :");
                        newPhone = sc.nextLine();
                        //String newPhone = saisie("^([04]+[0-9]{2}||[065])\\/[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}$","Veuillez saisir un numéro de téléphone valide");
                        break;
                    case 5 :
                        try{
                            modifClient = new Client.ClientBuilder()
                                    .setIdClient(chosenClient.getIdclient())
                                    .setMail(newMail)
                                    .setNom(newName)
                                    .setPrenom(newPren)
                                    .setTel(newPhone)
                                    .build();
                        }catch (Exception e){
                            logger.error("Erreur lors de la modification (téléphone) du client + " + e);
                            e.printStackTrace();
                        }

                        break;
                }
            }while(choixMod!=lOptions.size());
            if(!chosenClient.equals(modifClient) ||chosenClient.getNom().equals(newName) || chosenClient.getPrenom().equals(newPren) || !chosenClient.getTel().equals(newPhone) ){
                presenter.update(modifClient);
                lClients = presenter.getAll();
            }
            else System.out.println("Aucune modification effectuée");

        }

    }

    @Override
    protected void special() {
        List<String> listOptions = new ArrayList<>(Arrays.asList("Voir tous les clients" , "Voir la liste des taxis utilisés par un client","Voir les locations d'un client","Retour"));

        int choix;

        do {
            System.out.println("");
            affListe(listOptions);
            choix = choixElt(listOptions);
            switch (choix) {
                case 1 -> affListe(lClients);
                case 2 -> getTaxisOfClient();
                case 3 -> getListLocationsClient();
            }
        } while (choix != listOptions.size());

    }

    @Override
    public void seek(){
        System.out.println(" -- Rechercher un client --\n");
        System.out.print("Saisir le numéro du client : ");
        String idCli = saisie("[0-9]*","Veuillez saisir un numéro");
        int idClient = Integer.parseInt(idCli);
        Client cli = ((SpecialClientPresenter)presenter).readClientById(idClient);
    }

    @Override
    public void getTaxisOfClient() {
        Client cli = getChoice(lClients);
        List<Taxi> lTaxis = ((SpecialClientPresenter)presenter).getTaxisOfClient(cli);
    }

    public void getListLocationsClient() {
        Client cli = getChoice(lClients);
        ((SpecialClientPresenter) presenter).getListLocations(cli);
    }
}