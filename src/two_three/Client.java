package two_three;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    /**
     * Classe client
     * @version 1.0
     * @see Location
     */

    /**
     * identifiant unique-numéro du client
     */
    private int idClient;
    /**
     * email du client
     */
    private String mail;
    /**
     * nom du client
     */
    private String nom;
    /**
     * prénom du client
     */
    private String prenom;
    /**
     * téléphone du client
     */
    private String tel;
    /**
     * Liste des locations du client
     */
    private List<Location> listLocations = new ArrayList<>();

    //TODO pick up here
    public Client(int idClient, String mail, String nom, String prenom, String tel) {
        this.idClient = idClient;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    public int getId() {
        return idClient;
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

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setListLocations(List<Location> listLocations) {
        this.listLocations = listLocations;
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
                "N° d'identification : " + idClient +
                "\nEmail : " + mail +
                "\nNom : " + nom +
                "\t\tPrénom : " + prenom +
                "\nN° de téléphone : " + tel;
    }


}
