package two_three;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    private int id;
    private String mail;
    private String nom, prenom, tel;
    private List<Location> listLocations = new ArrayList<>();

    public Client(int id, String mail, String nom, String prenom, String tel) {
        this.id = id;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }

    public List<Location> getListLocations() {
        return listLocations;
    }

    public void setListLocations(Location loc) {
        this.listLocations.add(loc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(mail, client.mail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    @Override
    public String toString() {
        return "\n-- Client --\n" +
                "N° d'identification : " + id +
                "\tEmail : " + mail +
                "\nNom : " + nom +
                "\tPrénom : " + prenom + '\'' +
                "N° de téléphone : " + tel;
    }


}
