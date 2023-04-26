package mvp.model;

import two_three.Adresse;

import java.util.List;

public interface AdresseSpecial {

    List<Adresse> getAdressesByCP(int cp);
    List<Adresse> getAdressesByLocalite(String localite);
}
