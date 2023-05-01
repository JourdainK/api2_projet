package mvp.view;

import mvp.presenter.AdressePresenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static utilitaires.Utilitaire.*;

public class AdresseViewConsole implements AdresseViewInterface {
    private AdressePresenter presenter;
    private List<Adresse> lAdresses;
    private Scanner sc = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(AdresseViewConsole.class);


    @Override
    public void setPresenter(AdressePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setListDatas(List<Adresse> lAdresses) {
        this.lAdresses = lAdresses;
        //affListe(lAdresses);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : " + msg);
    }

    public void menu(){
        List<String> loptions = new ArrayList<>(Arrays.asList("Afficher toutes les adresses","Ajouter","effacer","Modifier","Rechercher par identifiant","Rechercher par code postal","Rechercher par localité","Retour"));

        int choix;

        do{
            System.out.println("");
            affListe(loptions);
            choix = choixElt(loptions);
            switch(choix){
                case 1 -> affListe(lAdresses);
                case 2 -> addAdresses();
                case 3 -> deleteAdresse();
                case 4 -> modifAdresse();
                case 5 -> seekAdresseID();
                case 6 -> seekAdresseCP();
                case 7 -> seekAdresseLoc();
            }
        }while(choix!=8);
    }

    public void addAdresses(){
        System.out.println(" -- Encoder une nouvelle adresse --\n");
        System.out.print("\nSaisir la rue : ");
        String rue = sc.nextLine();
        System.out.print("\nSaisir le numéro : ");
        //String num = sc.nextLine();
        String num = saisie("[0-9]{1,3}[a-zA-Z]{0,1}","Veuillez un numéro d'adresse correcte ( 10 , 10A)");
        System.out.print("\nSaisir la localité : ");
        String localite = sc.nextLine();
        int cp = getCp();
        try{

            Adresse newAdresse = new Adresse.AdresseBuilder()
                    .setCp(cp)
                    .setLocalite(localite)
                    .setRue(rue)
                    .setNum(num)
                    .build();
            int idAdre = presenter.addAdresse(newAdresse);
            newAdresse.setIdAdr(idAdre);
        }catch (Exception e){
            logger.error("Erreur lors de la création de l'adresse");
            e.printStackTrace();
        }

        lAdresses = presenter.getAll();
        //affListe(lAdresses);
    }

    public void deleteAdresse(){
        List<Adresse> lAdresse = null;
        System.out.println("-- Suppresion d'une adresse --\n");
        lAdresse = getListByChoice();

        if(lAdresse!=null){
            affListe(lAdresse);
            int choice = choixElt(lAdresse);
            System.out.println("Adresse à effacer : " + lAdresse.get(choice-1));
            presenter.removeAdresse(lAdresse.get(choice-1));
            lAdresses = presenter.getAll();
        }
        else System.out.println("Erreur, pas d'adresses trouvées");
    }

    public void seekAdresseID(){
        System.out.println("Saisir le numéro d'identification de l'adresse : ");
        String idAdre = saisie("[0-9]*","Veuillez saisir un numéro : ");
        int idAdr = Integer.parseInt(idAdre);

        Adresse seekedAdresse = presenter.readAdresse(idAdr);
    }

    public void seekAdresseCP(){
        List<Adresse> lAdresse;

        int cp = getCp();
        lAdresse = presenter.getAdressesByCP(cp);
        affListe(lAdresse);
    }

    public void seekAdresseLoc(){
        System.out.println("Saisir la localité : ");
        String loc = sc.nextLine();
        List<Adresse> lAdresse = presenter.getAdressesByLocalite(loc);
        affListe(lAdresse);
    }

    public void modifAdresse(){
        boolean check = false;
        List<String> loption = new ArrayList<>(Arrays.asList("Rue","Numéro","Localité et code postal","Retour"));

        System.out.println("-- Modifier une adresse --\n");
        List<Adresse> lAdresse = getListByChoice();
        affListe(lAdresse);
        int choice = choixElt(lAdresse);
        Adresse modifAdress = lAdresse.get(choice-1);
        int choixMod;
        String newLocat;
        int cp;

        do{
            System.out.println("\t-- Modifier --");
            affListe(loption);
            choixMod = choixElt(loption);
            switch (choixMod){
                case 1 :
                    System.out.print("\nSaisir la nouvelle rue : ");
                    String newRue = sc.nextLine();
                    try{
                        modifAdress = new Adresse.AdresseBuilder()
                                .setCp(modifAdress.getCp())
                                .setLocalite(modifAdress.getLocalite())
                                .setRue(newRue)
                                .setNum(modifAdress.getNum())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification de l'adresse (rue)");
                        e.printStackTrace();
                    }
                    break;
                case 2 :
                    System.out.print("\nSaisir le nouveau numéro : ");
                    String num = saisie("[0-9]{1,3}[a-zA-Z]{0,1}","Veuillez un numéro d'adresse correcte ( 10 , 10A)");
                    try{
                        modifAdress = new Adresse.AdresseBuilder()
                                .setCp(modifAdress.getCp())
                                .setLocalite(modifAdress.getLocalite())
                                .setRue(modifAdress.getRue())
                                .setNum(num)
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification de l'adresse (rue)");
                        e.printStackTrace();
                    }
                    break;
                case 3 :
                    System.out.print("\nSaisir la nouvelle localité : ");
                    newLocat = sc.nextLine();
                    cp = getCp();
                    try{
                        modifAdress = new Adresse.AdresseBuilder()
                                .setCp(cp)
                                .setLocalite(newLocat)
                                .setRue(modifAdress.getRue())
                                .setNum(modifAdress.getNum())
                                .build();
                    }catch (Exception e){
                        logger.error("Erreur lors de la modification de l'adresse (rue)");
                        e.printStackTrace();
                    }

                    break;
            }
        }while(choixMod!=loption.size());
        presenter.updateAdresse(modifAdress);
        lAdresses = presenter.getAll();
    }

    public List<Adresse> getListByChoice(){
        List<Adresse> lAdresse = null;

        List<String> loptionRecherche = new ArrayList<>(Arrays.asList("Voir toutes les adresses","Rechercher par code postal","Rechercher par Localité"));
        affListe(loptionRecherche);
        int choix = choixElt(loptionRecherche);
        switch(choix){
            case 1 :
                lAdresse = presenter.getAll();
                break;
            case 2 :
                int cp = getCp();
                lAdresse = presenter.getAdressesByCP(cp);
                break;
            case 3 :
                System.out.println("Saisir la localité : ");
                String loc = sc.nextLine();
                lAdresse = presenter.getAdressesByLocalite(loc);
                break;
        }

        return lAdresse;
    }


}
