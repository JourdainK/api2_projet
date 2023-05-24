package mvp.view;

import mvp.presenter.ClientPresenter;
import mvp.presenter.Presenter;
import mvp.presenter.SpecialClientPresenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Client;
import static utilitaires.Utilitaire.*;

import java.util.*;

public class ClientViewConsole extends AbstractViewConsole<Client> implements SpecialClientViewConsole{
    private Presenter<Client> presenter;
    private List<Client> lClients;
    private Scanner sc = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(ClientViewConsole.class);
    @Override
    public void setPresenter(Presenter<Client> presenter) { this.presenter = (ClientPresenter) presenter; }

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
            presenter.add(cli);
            Client client = presenter.read(cli);
            cli.setIdClient(client.getIdclient());

        }catch (Exception e){
            logger.error("Erreur lors de la création du client : " + e);
            e.printStackTrace();
        }
        lClients = presenter.getAll();
    }


    @Override
    public void remove(){
        System.out.println("-- Suppression d'un client--\n");
        affListe(lClients);
        int choix = choixElt(lClients);
        Client clientDelete = lClients.get(choix-1);
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
        boolean check = false;
        affListe(lClients);
        int choix = choixElt(lClients);
        int choixMod = -1;
        Client chosenClient = lClients.get(choix-1);
        List<String> lOptions = new ArrayList<>(Arrays.asList("Mail","Nom","Prénom","Téléphone","Retour"));

        do{
            System.out.println("\t-- Modifier --");
            System.out.println("Client à modifier choisi : " + chosenClient);
            affListe(lOptions);
            choixMod = choixElt(lOptions);
            switch (choixMod){
                case 1 :
                    System.out.print("\nSaisir le nouvel email : ");
                    String newMail = sc.nextLine();
                    try{
                        chosenClient = new Client.ClientBuilder()
                                .setMail(newMail)
                                .setIdClient(chosenClient.getIdclient())
                                .setNom(chosenClient.getNom())
                                .setPrenom(chosenClient.getPrenom())
                                .setTel(chosenClient.getTel())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification (mail) du client + " + e);
                        e.printStackTrace();
                    }
                    break;
                case 2 :
                    System.out.print("\nSaisir le nouveau nom : ");
                    String newName = saisie("^[A-Z][a-zçéèêëîïôöûü']+([-\\s][A-ZÇÉÈ][a-zçéèêëîïôöûü']+)*$","Veuillez saisir un nom commençant par une majuscule");
                    try{
                        chosenClient = new Client.ClientBuilder()
                                .setMail(chosenClient.getMail())
                                .setIdClient(chosenClient.getIdclient())
                                .setNom(newName)
                                .setPrenom(chosenClient.getPrenom())
                                .setTel(chosenClient.getTel())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification (nom) du client : " + e);
                        e.printStackTrace();
                    }
                    break;
                case 3 :
                    System.out.print("\nSaisir le nouveau prénom : ");
                    String newPren = saisie("^[A-Z][a-zçéèêëîïôöûü']+([-\\s][A-Z][a-zçéèêëîïôöûü']+)*$","Veuillez saisir un prénom commençant par une majuscule");
                    try{
                        chosenClient = new Client.ClientBuilder()
                                .setMail(chosenClient.getMail())
                                .setIdClient(chosenClient.getIdclient())
                                .setNom(chosenClient.getNom())
                                .setPrenom(newPren)
                                .setTel(chosenClient.getTel())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification du client (prénom) : " + e);
                        e.printStackTrace();
                    }
                    break;
                case 4 :
                    System.out.print("\nSaisir le nouveau numéro de téléphone :");
                    String newPhone = sc.nextLine();
                    //String newPhone = saisie("^([04]+[0-9]{2}||[065])\\/[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}$","Veuillez saisir un numéro de téléphone valide");
                    try{
                        chosenClient = new Client.ClientBuilder()
                                .setMail(chosenClient.getMail())
                                .setIdClient(chosenClient.getIdclient())
                                .setNom(chosenClient.getNom())
                                .setPrenom(chosenClient.getPrenom())
                                .setTel(newPhone)
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification (téléphone) du client + " + e);
                        e.printStackTrace();
                    }
                    break;
            }
        }while(choixMod!=lOptions.size());
        presenter.update(chosenClient);
        lClients = presenter.getAll();
    }

    @Override
    protected void special() {
        List<String> listOptions = new ArrayList<>(Arrays.asList("Voir tous les clients" , "Retour"));

        int choix;

        do {
            System.out.println("");
            affListe(listOptions);
            choix = choixElt(listOptions);
            switch (choix) {
                case 1 -> affListe(lClients);
            }
        } while (choix != listOptions.size());

        //TODO special client Menu
    }

    @Override
    public void seek(){
        System.out.println(" -- Rechercher un client --\n");
        System.out.print("Saisir le numéro du client : ");
        String idCli = saisie("[0-9]*","Veuillez saisir un numéro");
        int idClient = Integer.parseInt(idCli);
        Client cli = ((SpecialClientPresenter)presenter).readClientById(idClient);
    }

    //TODO client specials view

}