package two_three;

import java.util.Objects;

public class Adresse {
    /**
     * Classe adresse
     * @author Kevin Jourdain
     * @version 1.0
     */

    /**
     * identifiant unique de l'adresse
     */
    private int idAdr;
    /**
     * code postal de l'adresse
     */
    private int cp;
    /**
     * localité de l'adresse
     */
    private String localite;
    /**
     * rue de l'adresse
     */
    private String rue;
    /**
     * numéro de l'adresse
     */
    private String num;

    /**
     * constructeur paramétré
     *
     * @param idAdr identifiant unique de l'adresse
     * @param cp code postal de l'adresse
     * @param localite localité de l'adresse
     * @param rue rue de l'adresse
     * @param num numéro de l'adresse
     */
    public Adresse(int idAdr, int cp, String localite, String rue, String num) {
        this.idAdr = idAdr;
        this.cp = cp;
        this.localite = localite;
        this.rue = rue;
        this.num = num;
    }

    /**
     * getter idAdre
     *
     * @return identifiant de l'adresse
     */
    public int getIdAdr() {
        return idAdr;
    }

    /**
     * getter cp
     *
     * @return code postal
     */
    public int getCp() {
        return cp;
    }

    /**
     * getter localite
     *
     * @return localité
     */
    public String getLocalite() {
        return localite;
    }

    /**
     * getter rue
     *
     * @return rue
     */
    public String getRue() {
        return rue;
    }

    /**
     * getter num
     *
     * @return numéro de l'adresse
     */
    public String getNum() {
        return num;
    }

    /**
     * égalité de deux adresses basée sur l'ensemble des paramètres
     * @param o autre élément
     * @return égalité ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return num == adresse.num &&
                idAdr == adresse.idAdr &&
                cp == adresse.cp &&
                localite.equals(adresse.localite) &&
                rue.equals(adresse.rue);
    }

    /**
     * caclcul du hashcode basé sur l'identifiant unique de l'adresse
     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(idAdr);
    }

    /**
     * méthode toString
     *
     * @return informations complètes
     */
    @Override
    public String toString() {
        return "\nAdresse :\n" +
                "ID n° : " + idAdr +
                "\nCode postal :" + cp +
                "\tLocalité : " + localite +
                "\nRue :" + rue +
                "\t" + num + "\n\n";
    }


}
