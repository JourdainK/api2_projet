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
    private int idTaxi;
    /**
     * nombre maximum de passagers du taxi
     */
    private int nbreMaxPassagers;
    /**
     * immatriculation du taxi
     */
    private String immatriculation;
    /**
     * prix au kilomètre
     */
    private double prixKm;
    /**
     * liste des locations réalisées par le taxi
     */
    private List<Location> listTaxiLoc = new ArrayList<>();

    /**
     * constructeur paramétré
     *
     * @param idTaxi identifiant du taxi
     * @param nbreMaxPassagers nombre de passagers maximum que le taxi peut transporter
     * @param immatriculation immatriculation du taxi
     * @param prixKm prix au kilomètre
     */
    public Taxi(int idTaxi, int nbreMaxPassagers, String immatriculation, double prixKm) {
        this.idTaxi = idTaxi;
        this.nbreMaxPassagers = nbreMaxPassagers;
        this.immatriculation = immatriculation;
        this.prixKm = prixKm;
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
     * setter prixKm
     *
     * @param prixKm prix au kilomètre
     */
    public void setPrixKm(double prixKm) {
        this.prixKm = prixKm;
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
     * setter listTaxiLoc
     *
     * @param listTaxiLoc liste des locations du taxi
     */
    public void setLocation(List<Location> listTaxiLoc) {
        this.listTaxiLoc = listTaxiLoc;
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
        return "\n-- Taxi --\n" +
                "N° d'identification : " + idTaxi +
                "\nNombre de passagers maximum : " + nbreMaxPassagers +
                "\t\tImmatriculation : " + immatriculation +
                "\nTarif (au kilomètre ) : " + prixKm + "€";
    }

}
