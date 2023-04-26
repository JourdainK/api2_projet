package two_three;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    /**
     * Classe client
     *
     * @author Kevin Jourdain
     * @version 2.0
     * @see Location
     */

    /**
     * identifiant unique-numéro du client
     */
    protected int idClient;
    /**
     * email du client
     */
    protected String mail;
    /**
     * nom du client
     */
    protected String nom;
    /**
     * prénom du client
     */
    protected String prenom;
    /**
     * téléphone du client
     */
    protected String tel;
    /**
     * Liste des locations du client
     */
    private List<Location> listLocations = new ArrayList<>();

    private Client(ClientBuilder builder){
        this.idClient = builder.idClient;
        this.mail = builder.mail;
        this.nom = builder.nom;
        this.prenom = builder.nom;
        this.tel = builder.tel;
    }


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

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

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
        return  "\tN° d'identification : " + idClient +
                "\nEmail : " + mail +
                "\nNom : " + nom +
                "\t\tPrénom : " + prenom +
                "\nN° de téléphone : " + tel + "\n";
    }

    public static class ClientBuilder{
        protected int idClient;
        protected String mail;
        protected String nom;
        protected String prenom;
        protected String tel;

        public ClientBuilder setIdClient(int idClient) {
            this.idClient = idClient;
            return this;
        }

        public ClientBuilder setMail(String mail) {
            this.mail = mail;
            return this;
        }

        public ClientBuilder setNom(String nom) {
            this.nom = nom;
            return this;
        }

        public ClientBuilder setPrenom(String prenom) {
            this.prenom = prenom;
            return this;
        }

        public ClientBuilder setTel(String tel) {
            this.tel = tel;
            return this;
        }

        public Client build() throws Exception{
            if(idClient<=0 || nom.isBlank() || prenom.isBlank() || tel.isBlank()) throw new Exception("Erreur lors de la construction du client");
            return new Client(this);
        }
    }


}
