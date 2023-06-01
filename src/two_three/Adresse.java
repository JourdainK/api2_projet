package two_three;

import java.util.Objects;

public class Adresse {
    /**
     * Classe adresse
     * @author Kevin Jourdain
     * @version 2.0
     */

    /**
     * identifiant unique de l'adresse
     */
    protected int idAdr;
    /**
     * code postal de l'adresse
     */
    protected int cp;
    /**
     * localité de l'adresse
     */
    protected String localite;
    /**
     * rue de l'adresse
     */
    protected String rue;
    /**
     * numéro de l'adresse
     */
    protected String num;

    /**
     * Constructeur de la classe adresse utilisant le pattern builder
     *
     * @param builder
     */
    private Adresse(AdresseBuilder builder){
        this.idAdr = builder.idAdr;
        this.cp = builder.cp;
        this.localite = builder.localite;
        this.rue = builder.rue;
        this.num = builder.num;
    }

    /**
     * Setter idAdr
     *
     * @param idAdr
     */
    public void setIdAdr(int idAdr) {
        this.idAdr = idAdr;
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

    /**
     * classe builder de l'adresse
     *
     */
    public static class AdresseBuilder{
        protected int idAdr;
        protected int cp;
        protected String localite;
        protected String rue;
        protected String num;

        public AdresseBuilder setIdAdr(int idAdr) {
            this.idAdr = idAdr;
            return this;
        }

        public AdresseBuilder setCp(int cp) {
            this.cp = cp;
            return this;
        }

        public AdresseBuilder setLocalite(String localite) {
            this.localite = localite;
            return this;
        }

        public AdresseBuilder setRue(String rue) {
            this.rue = rue;
            return this;
        }

        public AdresseBuilder setNum(String num) {
            this.num = num;
            return this;
        }

        public Adresse build() throws Exception{
            if(idAdr<0 || localite.isBlank() || rue.isBlank() || num.isBlank()) throw new Exception("Erreur lors de la construction de l'adresse");
            return new Adresse(this);
        }
    }


}
