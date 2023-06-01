package mvp.presenter;

import two_three.Adresse;

import java.util.List;

public interface SpecialAdressePresenter {
    List<Adresse> getAdressesByCP(int codePostal);

    List<Adresse> getAdressesByLocalite(String localite);

    Adresse getAdresseByid(int idAdresse);

}
