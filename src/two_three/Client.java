package two_three;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    /**
     * Classe client
     *
     * @author Kevin Jourdain
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

    /**
     * constructeur paramétré
     *
     * @param idClient identifiant unique du client
     * @param mail mail du client
     * @param nom nom du client
     * @param prenom prénom du client
     * @param tel téléphone du client
     */
    public Client(int idClient, String mail, String nom, String prenom, String tel) {
        this.idClient = idClient;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    /**
     * getter idclient
     *
     * @return identifiant du client
     */
    public int getIdclient() {
        return idClient;
    }

    /**
     * getter mail
     *
     * @return mail du client
     */
    public String getMail() {
        return mail;
    }

    /**
     * getter nom
     *
     * @return nom du client
     */
    public String getNom() {
        return nom;
    }

    /**
     * getter prenom
     *
     * @return prénom du client
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * getter téléphone
     *
     * @return téléphone du client
     */
    public String getTel() {
        return tel;
    }

    /**
     * getter listLocations
     *
     * @return liste des locations du clients
     */
    public List<Location> getListLocations() {
        return listLocations;
    }

    /**
     * setter mail
     *
     * @param mail mail du client
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * setter téléphone
     *
     * @param tel téléphone du client
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * setter liste des locations du client
     *
     * @param listLocations liste de locations du client
     */
    public void setListLocations(List<Location> listLocations) {
        this.listLocations = listLocations;
    }

    /**
     * éqalité de deux clients basée sur le mail du client
     * @param o autre élément
     * @return égalité ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(mail, client.mail);
    }

    /**
     * calcul du hascode du client basé sur le mail

     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    /**
     * méthode toString
     *
     * @return informations complètes excepté la liste des locations du client
     */
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
