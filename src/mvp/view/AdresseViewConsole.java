package mvp.view;

import mvp.presenter.AdressePresenter;
import mvp.presenter.Presenter;
import mvp.presenter.SpecialAdressePresenter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;

import java.util.*;

import static utilitaires.Utilitaire.*;

public class AdresseViewConsole extends AbstractViewConsole<Adresse> implements SpecialAdresseViewConsole {
    private Presenter<Adresse> presenter;
    private List<Adresse> lAdresses;
    private Scanner sc = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(AdresseViewConsole.class);


    @Override
    public void setPresenter(Presenter<Adresse> presenter) {
        this.presenter = (AdressePresenter) presenter;
    }

    @Override
    public void setListDatas(List<Adresse> lAdresses, Comparator<Adresse> cmp) {
        this.lAdresses = lAdresses;
        this.lAdresses.sort(cmp);
        //affListe(lAdresses);
        menu();
    }

    @Override
    public void affMsg(String msg) {
        System.out.println("Information : " + msg);
    }

    public void add() {
        System.out.println(" -- Encoder une nouvelle adresse --\n");
        System.out.print("\nSaisir la rue : ");
        String rue = sc.nextLine();
        System.out.print("\nSaisir le numéro : ");
        //String num = sc.nextLine();
        String num = saisie("[0-9]{1,3}[a-zA-Z]{0,1}", "Veuillez un numéro d'adresse correcte ( 10 , 10A)");
        System.out.print("\nSaisir la localité : ");
        String localite = sc.nextLine();
        int cp = getCp();
        try {

            Adresse newAdresse = new Adresse.AdresseBuilder()
                    .setCp(cp)
                    .setLocalite(localite)
                    .setRue(rue)
                    .setNum(num)
                    .build();
            presenter.add(newAdresse);
        } catch (Exception e) {
            logger.error("Erreur lors de la création de l'adresse");
            e.printStackTrace();
        }

        lAdresses = presenter.getAll();
        //affListe(lAdresses);
    }

    @Override
    public void remove() {
        List<Adresse> lAdresse = null;
        System.out.println("-- Suppresion d'une adresse --\n");
        lAdresse = getListByChoice();

        if(lAdresse.size() > 0){
            affListe(lAdresse);
            int choice = choixElt(lAdresse);
            System.out.println("Adresse à effacer : " + lAdresse.get(choice - 1));
            presenter.remove(lAdresse.get(choice - 1));
            lAdresses = presenter.getAll();
        }
        else{
            System.out.println("Erreur, pas d'adresses trouvées");
        }
    }

    @Override
    public void seek() {
        System.out.println("Saisir le numéro d'identification de l'adresse : ");
        String idAdre = saisie("[0-9]*", "Veuillez saisir un numéro : ");
        int idAdr = Integer.parseInt(idAdre);
        Adresse seekedAdresse = ((SpecialAdressePresenter) presenter).getAdresseByid(idAdr);
    }


    @Override
    public void update() {
        List<String> loption = new ArrayList<>(Arrays.asList("Rue", "Numéro", "Localité et code postal", "Retour"));

        System.out.println("-- Modifier une adresse --\n");
        List<Adresse> lAdresse = getListByChoice();

        Adresse adress = getChoice(lAdresse);
        System.out.println("Adresse à modifier : " + adress);
        int choixMod;

        if(adress != null){
            int newCp = adress.getCp();
            String newLocat = adress.getLocalite();
            String newRue = adress.getRue();
            String newNum = adress.getNum();
            Adresse modifAdress = null;

            do {
                System.out.println("\t-- Modifier --");
                affListe(loption);
                choixMod = choixElt(loption);
                switch (choixMod) {

                    case 1:
                        System.out.print("\nSaisir la nouvelle rue : ");
                        newRue = sc.nextLine();
                        break;

                    case 2:
                        System.out.print("\nSaisir le nouveau numéro : ");
                        newNum = saisie("[0-9]{1,3}[a-zA-Z]{0,1}", "Veuillez un numéro d'adresse correcte ( 10 , 10A)");

                        break;
                    case 3:
                        System.out.print("\nSaisir la nouvelle localité : ");
                        newLocat = sc.nextLine();
                        newCp = getCp();

                        break;

                    case 4:
                        try {
                            modifAdress = new Adresse.AdresseBuilder()
                                    .setIdAdr(adress.getIdAdr())
                                    .setCp(newCp)
                                    .setLocalite(newLocat)
                                    .setRue(newRue)
                                    .setNum(newNum)
                                    .build();
                        } catch (Exception e) {
                            logger.error("Erreur lors de la modification de l'adresse (rue)");
                            e.printStackTrace();
                        }
                        break;

                }
            } while (choixMod != loption.size());

            if(!modifAdress.equals(adress)){
                presenter.update(modifAdress);
                lAdresses = presenter.getAll();
            } else System.out.println("Aucune modification n'a été effectuée");

        }

    }

    @Override
    protected void special() {
        List<String> listOptions = new ArrayList<>(Arrays.asList("Voir toutes les adresses", "Rechercher par localité", "Rechercher par Code postal", "Retour"));

        int choix;

        do {
            System.out.println("");
            affListe(listOptions);
            choix = choixElt(listOptions);
            switch (choix) {
                case 1 -> affListe(lAdresses);
                case 2 -> seekAdresseLoc();
                case 3 -> seekAdresseCP();
            }
        } while (choix != listOptions.size());

    }


    public void seekAdresseCP() {
        List<Adresse> lAdresse;

        int cp = getCp();
        lAdresse = ((SpecialAdressePresenter) presenter).getAdressesByCP(cp);
        affListe(lAdresse);
    }

    public void seekAdresseLoc() {
        System.out.println("Saisir la localité : ");
        String loc = sc.nextLine();
        List<Adresse> lAdresse = ((SpecialAdressePresenter) presenter).getAdressesByLocalite(loc);
        affListe(lAdresse);
    }


    public List<Adresse> getListByChoice() {
        List<Adresse> lAdresse = null;

        List<String> loptionRecherche = new ArrayList<>(Arrays.asList("Voir toutes les adresses", "Rechercher par code postal", "Rechercher par Localité"));
        affListe(loptionRecherche);
        int choix = choixElt(loptionRecherche);
        switch (choix) {
            case 1:
                lAdresse = presenter.getAll();
                break;
            case 2:
                int cp = getCp();
                lAdresse = ((SpecialAdressePresenter) presenter).getAdressesByCP(cp);
                break;
            case 3:
                do{
                    System.out.println("Saisir la localité : ");
                    String loc = sc.nextLine();
                    lAdresse = ((SpecialAdressePresenter) presenter).getAdressesByLocalite(loc);
                }while(lAdresse==null);
                break;
        }

        return lAdresse;
    }

}
