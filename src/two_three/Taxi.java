package two_three;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Taxi {
    /**
     * classe Taxi
     * @author Kevin Jourdain
     * @version 1.0
     * @see Location
     */

    /**
     * Identifiant du Taxi
     */
    protected int idTaxi;
    /**
     * nombre maximum de passagers du taxi
     */
    protected int nbreMaxPassagers;
    /**
     * immatriculation du taxi
     */
    protected String immatriculation;
    /**
     * prix au kilomètre
     */
    protected double prixKm;
    /**
     * liste des locations réalisées par le taxi
     */
    protected List<Location> listTaxiLoc;

    private Taxi(TaxiBuilder builder){
        this.idTaxi = builder.idTaxi;
        this.nbreMaxPassagers = builder.nbreMaxPassagers;
        this.immatriculation = builder.immatriculation;
        this.prixKm = builder.prixKm;
        this.listTaxiLoc = new ArrayList<>();
    }


    /**
     * Setter IdTaxi -> numéro d'identification du taxi
     *
     * @param idTaxi
     */
    public void setIdTaxi(int idTaxi) {
        this.idTaxi = idTaxi;
    }

    /**
     * setter listTaxiLoc
     *
     * @param listTaxiLoc liste des locations du taxi
     */
    public void setLocation(List<Location> listTaxiLoc) {
        this.listTaxiLoc = listTaxiLoc;
    }

    /**
     * getter idTaxi
     *
     * @return identifiant unique du taxi
     */
    public int getIdTaxi() {
        return idTaxi;
    }

    /**
     * getter nbrMaxPassagers
     *
     * @return nombre de passagers maximum
     */
    public int getNbreMaxPassagers() {
        return nbreMaxPassagers;
    }

    /**
     * getter immatriculation
     *
     * @return immatriculation
     */
    public String getImmatriculation() {
        return immatriculation;
    }

    /**
     * getter prixKm
     *
     * @return prix au kilomètre
     */
    public double getPrixKm() {
        return prixKm;
    }

    /**
     * getter listTaxiLoc
     *
     * @return liste des locations du taxi
     */
    public List<Location> getListTaxiLoc() {
        return listTaxiLoc;
    }

    /**
     * setter immatriculation
     *
     * @param List de locations du taxi
     */
    public void setLocations(List<Location> locatTaxi) {
        this.listTaxiLoc = locatTaxi;
    }

    /**
     * égalité de deux taxis basée sur l'immatriculation
     *
     * @param o autre élément
     * @return égalité ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return Objects.equals(immatriculation, taxi.immatriculation);
    }

    /**
     * calcul du hashcode basé sur l'immatriculation

     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(immatriculation);
    }

    /**
     * méthode toString
     *
     * @return informations complètes excepté la liste des locations
     */
    @Override
    public String toString() {
        return "\t-- Taxi --\n" +
                "N° d'identification : " + idTaxi +
                "\nNombre de passagers maximum : " + nbreMaxPassagers +
                "\t\tImmatriculation : " + immatriculation +
                "\nTarif (au kilomètre ) : " + prixKm + "€";
    }

    public static class TaxiBuilder{
        protected int idTaxi;
        protected int nbreMaxPassagers;
        protected String immatriculation;
        protected double prixKm;
        protected List<Location> listTaxiLoc;

        public TaxiBuilder setIdTaxi(int idTaxi) {
            this.idTaxi = idTaxi;
            return this;
        }

        public TaxiBuilder setNbreMaxPassagers(int nbreMaxPassagers) {
            this.nbreMaxPassagers = nbreMaxPassagers;
            return this;
        }

        public TaxiBuilder setImmatriculation(String immatriculation) {
            this.immatriculation = immatriculation;
            return this;
        }

        public TaxiBuilder setPrixKm(double prixKm) {
            this.prixKm = prixKm;
            return this;
        }

        public TaxiBuilder setListTaxiLoc(List<Location> listTaxiLoc) {
            this.listTaxiLoc = listTaxiLoc;
            return this;
        }

        public Taxi build() throws Exception{
            if(idTaxi<0 || immatriculation.isBlank() || prixKm < 0 || nbreMaxPassagers < 1) throw new Exception("Erreur lors de la construction du Taxi");
            return new Taxi(this);
        }
    }
}
