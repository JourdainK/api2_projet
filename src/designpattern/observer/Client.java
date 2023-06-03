package designpattern.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client implements Observer{
    /**
     * Classe client
     *
     * @autor Kevin Jourdain
     * @version 1.0
     * @see Observer
     */

    /**
     * identifiant unique-numéro du client
     */
    private int id;

    /**
     * email du client
     */
    private String mail;

    /**
     * nom du client
     * prénom du client
     * téléphone du client
     */
    private String nom, prenom, tel;

    /**
     * Constructeur de la classe Client
     *
     * @param id
     * @param mail
     * @param nom
     * @param prenom
     * @param tel
     */
    public Client(int id, String mail, String nom, String prenom, String tel) {
        this.id = id;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
    }

    /**
     * getter id
     *
     * @return id du client
     */
    public int getId() {
        return id;
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
     * @return prenom du client
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * getter tel
     *
     * @return tel du client
     */
    public String getTel() {
        return tel;
    }


    /**
     * setter mail
     *
     * @param mail
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * setter tel
     *
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }


    /**
     * redéfinition de la méthode equals
     *  Basé sur l'adresse mail
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(mail, client.mail);
    }

    /**
     * redéfinition de la méthode hashCode
     * Basé sur l'adresse mail
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(mail);
    }

    /**
     * redéfinition de la méthode toString
     *
     * @return
     */
    @Override
    public String toString() {
        return "\n-- Client --\n" +
                "N° d'identification : " + id +
                "\nEmail : " + mail +
                "\nNom : " + nom +
                "\t\tPrénom : " + prenom +
                "\nN° de téléphone : " + tel;
    }


    /**
     * méthode update
     *  affiche l'alerte pour le desgin pattern observer
     *      alert : message d'alerte pour la modification du prix au kilomètre d'un taxi
     * @param alert
     */
    @Override
    public void update(String alert) {
        System.out.println(this.mail + "!\t\tAlerte : " + alert);
    }
}
