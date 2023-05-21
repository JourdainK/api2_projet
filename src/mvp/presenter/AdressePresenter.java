package mvp.presenter;

import mvp.model.DAO;
import mvp.model.adresse.AdresseSpecial;
import mvp.view.ViewInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import two_three.Adresse;

import java.util.Comparator;
import java.util.List;

public class AdressePresenter extends Presenter<Adresse> implements SpecialAdressePresenter {

    private static final Logger logger = LogManager.getLogger(AdressePresenter.class);


    public AdressePresenter(DAO<Adresse> model, ViewInterface<Adresse> view, Comparator<Adresse> cmp){
        super(model, view,cmp);
        this.view.setPresenter(this);
    }

    @Override
    public Adresse getAdresseByid(int idAdresse){
        Adresse adre = model.readbyId(idAdresse);

        if(adre==null){
            view.affMsg("Adresse non trouvée");
            return null;
        }
        else {
            view.affMsg(adre.toString());
            return adre;
        }
    }

    @Override
    public List<Adresse> getAdressesByCP(int codePostal){
        List<Adresse> lAdressCp = ((AdresseSpecial)model).getAdressesByCP(codePostal);

        return lAdressCp;
    }

    @Override
    public List<Adresse> getAdressesByLocalite(String localite){
        List<Adresse> lAdresseLoc = ((AdresseSpecial)model).getAdressesByLocalite(localite);

        return lAdresseLoc;
    }

}
