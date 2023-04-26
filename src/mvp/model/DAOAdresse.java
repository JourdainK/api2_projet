package mvp.model;

import two_three.Adresse;

import java.util.List;

public interface DAOAdresse {
    Adresse addAdresse(Adresse adresse);
    boolean removeAdresse(Adresse adresse);
    Adresse updateAdresse(Adresse adresse);
    Adresse readAdresse(int idAdresse);
    List<Adresse> getAdresses();

}
