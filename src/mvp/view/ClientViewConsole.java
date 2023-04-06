package mvp.view;

import mvp.presenter.ClientPresenter;
import two_three.Client;
import static utilitaires.Utilitaire.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientViewConsole implements ClientViewInterface{
    private ClientPresenter presenter;
    private List<Client> lClients;
    private Scanner sc = new Scanner(System.in);
    @Override
    public void setPresenter(ClientPresenter presenter) { this.presenter = presenter; }

    @Override
    public void setListDatas(List<Client> lClient) {
        this.lClients = lClient;
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : " + msg);
    }

    public void menu(){
        List<String> option = new ArrayList<>(Arrays.asList("Voir la liste des clients","Ajouter","Effacer","Modifier","Rechercher","Retour"));
        int choix;
        do{
            System.out.println("\n-- Menu Client -- ");
            affListe(option);
            choix = choixElt(option);
            switch (choix){
                case 1 -> affListe(lClients);
                case 2 -> addClient();
                case 3 -> deleteClient();
                case 4 -> System.out.println("Modifier");
                case 5 -> System.out.println("Rechercher");
            }

        }while(choix!=6);

    }

    public void addClient(){
        //REGEX by chatGPT
        System.out.println("\n--Ajout d'un client --");
        System.out.println("Saisir l'email du client : ");
        String email = saisie("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$","veuillez saisir un email valide");
        System.out.println("Saisir le nom du client : ");
        String nom = saisie("^[A-Z][a-zçéèêëîïôöûü']+([-\\s][A-ZÇÉÈ][a-zçéèêëîïôöûü']+)*$","Veuillez saisir un nom commençant par une majuscule");
        System.out.println("Saisir le prénom du client : ");
        String pren = saisie("^[A-Z][a-zçéèêëîïôöûü']+([-\\s][A-Z][a-zçéèêëîïôöûü']+)*$","Veuillez saisir un prénom commençant par une majuscule");
        System.out.println("Saisir le numéro de téléphone du client : ");
        //regex by me
        //String phone = saisie("^([04]+[0-9]{2}||[065])\\/[0-9]{2}\\.[0-9]{2}\\.[0-9]{2}$","Veuillez saisir un numéro de téléphone valide");
        String phone = sc.nextLine();
        Client cli = new Client(email,nom, pren, phone);
        presenter.addClient(cli);
        lClients = presenter.getClients();

    }


    public void deleteClient(){
        System.out.println("-- Suppression d'un client--\n");
        affListe(lClients);
        int choix = choixElt(lClients);
        Client clientDelete = lClients.get(choix-1);
        System.out.println("Client à supprimer : " + clientDelete);
        System.out.println("1.Confirmer l'effacement \n2.Annuler l'effacement" );
        String keepOn1 = saisie("[1-2]{1}","Veuillez saisir :  \n1 pour confirmer effacement\n2 pour annuler");
        int keepOn  = Integer.parseInt(keepOn1);
        if(keepOn==1){
            presenter.removeClient(clientDelete);
            lClients = presenter.getClients();
        }
        else System.out.println("effacement annulé");
    }



}
