package mvp.presenter;

import mvp.model.AdresseSpecial;
import mvp.model.DAOAdresse;
import mvp.view.AdresseViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;

import java.util.List;

public class AdressePresenter {
    private DAOAdresse model;
    private AdresseViewInterface view;
    private static final Logger logger = LogManager.getLogger(AdressePresenter.class);

    public AdressePresenter(DAOAdresse model,AdresseViewInterface view){
        this.model = model;
        this.view = view;
        this.view.setPresenter(this);
    }

    public void start(){
        view.setListDatas(getAll());
    }

    public List<Adresse> getAll(){
        List<Adresse> lAdre = model.getAdresses();
        return lAdre;
    }

    public void addAdresse(Adresse adresse){
        Adresse newAdresse = model.addAdresse(adresse);

        if(newAdresse!=null) view.affMsg("Adresse ajoutée");
        else view.affMsg("Erreur lors de l'ajout de l'adresse");
    }

    public void removeAdresse(Adresse adresse) {
        boolean check;
        check = model.removeAdresse(adresse);
        if(check) view.affMsg("Adresse effacée ");
        else view.affMsg("Erreur, Adresse non effacée");
    }

    public void updateAdresse(Adresse adresse){
        Adresse modifiedAdresse = model.updateAdresse(adresse);
        if(modifiedAdresse!=null) view.affMsg("Modification effectuée : " + modifiedAdresse);
        else view.affMsg("Erreur lors de la modification");
    }

    public Adresse readAdresse(int idAdresse){
        Adresse adre = model.readAdresse(idAdresse);

        if(adre==null){
            view.affMsg("Adresse non trouvée");
            return null;
        }
        else {
            view.affMsg(adre.toString());
            return adre;
        }
    }

    public List<Adresse> getAdressesByCP(int codePostal){
        List<Adresse> lAdressCp = ((AdresseSpecial)model).getAdressesByCP(codePostal);

        return lAdressCp;
    }

    public List<Adresse> getAdressesByLocalite(String localite){
        List<Adresse> lAdresseLoc = ((AdresseSpecial)model).getAdressesByLocalite(localite);

        return lAdresseLoc;
    }


}
