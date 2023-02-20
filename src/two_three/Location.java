package two_three;

import java.util.Objects;

/**
 * Classe métier de gestion de location de service Taxi
 *
 *  @author Kevin Jourdain
 *  @version 1.0
 *  @see Taxi
 *  @see Client
 *  @see Adresse
 */

public class Location {
    /**
     * identifiant unique du client
     */
    private int idLoc;
    /**
     * nombre de kilomètres parcouru lors de la location
     */
    private int kmTotal;
    /**
     * nombre de passagers véhiculés lors de la location
     */
    private int nbrePassagers;
    /**
     * date de la location
     */
    private String dateLoc;
    /**
     * coût total de la location
     */
    private double total;
    /**
     * Taxi utilisé pour la location
     */
    private Taxi vehicule;
    /**
     * Client de la Location
     */
    private Client client;
    /**
     * Adresse de départ de la location
     */
    private Adresse adrDebut;
    /**
     * Adresse d'arrivée de la location
     */
    private Adresse adrFin;


    /**
     * constructeur paramétré
     *
     * @param idLoc Identifiant unique de la location
     * @param kmTotal Nombre de kilomètres parcourus lors de la location
     * @param nbrePassagers Nombre de passagers véhiculés lors de la location
     * @param dateLoc Date de la location
     * @param vehicule Véhicule utilisé lors de la location
     * @param client Client de la location
     * @param adrDebut Adresse de départ de la location
     * @param adrFin Adresse d'arrivée de la location
     */
    public Location(int idLoc, int kmTotal, int nbrePassagers, String dateLoc, Taxi vehicule, Client client, Adresse adrDebut, Adresse adrFin) {
        this.idLoc = idLoc;
        this.kmTotal = kmTotal;
        this.nbrePassagers = nbrePassagers;
        this.dateLoc = dateLoc;
        this.vehicule = vehicule;
        this.client = client;
        this.adrDebut = adrDebut;
        this.adrFin = adrFin;
        setTotal();
        this.vehicule.getListTaxiLoc().add(this);
        this.client.getListLocations().add(this);
    }

    /**
     * getter idLoc
     *
     * @return identifiant de la location
     */
    public int getIdLoc() {
        return idLoc;
    }

    /**
     * getter kmTotal
     *
     * @return nombre de kilomètres parcourus
     */
    public int getKmTotal() {
        return kmTotal;
    }

    /**
     * getter nbrePassagers
     *
     * @return nombre de passagers véhiculés
     */
    public int getNbrePassagers() {
        return nbrePassagers;
    }

    /**
     * getter dateLoc
     *
     * @return date de la location
     */
    public String getDateLoc() {
        return dateLoc;
    }

    /**
     * getter total
     *
     * @return coût total de la livraison
     */
    public double getTotal() {
        return total;
    }

    /**
     * getter vehicule
     *
     * @return véhicule utilisé lors de la location
     */
    public Taxi getVehicule() {
        return vehicule;
    }

    /**
     * getter client
     *
     * @return client de la location
     */
    public Client getClient() {
        return client;
    }

    /**
     * getter adreDebut
     *
     * @return adresse de départ de la location
     */
    public Adresse getAdrDebut() {
        return adrDebut;
    }

    /**
     * getter adreFin
     *
     * @return adresse d'arrivée de la location
     */
    public Adresse getAdrFin() {
        return adrFin;
    }

    /**
     * setter véhicule
     *
     * @param vehicule véhicule utilisé lors de la location
     */
    public void setVehicule(Taxi vehicule) {

        this.vehicule = vehicule;
        this.vehicule.getListTaxiLoc().add(this);
    }

    /**
     * setter client
     *
     * @param client client de la location
     */
    public void setClient(Client client) {

        this.client = client;
    }

    /**
     * setter adresse de départ
     *
     * @param adrDebut adresse de départ
     */
    public void setAdrDebut(Adresse adrDebut){
        this.adrDebut = adrDebut;
    }

    /**
     * setter adresse d'arrivée
     *
     * @param adrFin adresse d'arrivée
     */
    public void setAdrFin(Adresse adrFin){
        this.adrFin = adrFin;
    }

    /**
     * setter total
     *
     * this.total =
     *  kmTotal kilomètres totaux parcourus
     *      Multipliés
     *  par le prix kilométrique du véhicule utilisé
     */
    public void setTotal() { this.total = vehicule.getPrixKm() * kmTotal; }

    /**
     * égalité de deux locations basées sur le quadruplet id, adrDebut, adrFin, dateLoc
     * @param o autre élément
     * @return égalité ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return idLoc == location.idLoc &&
                adrDebut.equals(((Location) o).adrDebut) &&
                adrFin.equals(((Location) o).adrFin) &&
                dateLoc.equals(((Location) o).dateLoc);
    }

    /**
     * calcul du hashcode du client basé sur le quadruplet id, adrDebut, adrFin, dateLoc
     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(idLoc,adrDebut,adrFin,dateLoc);
    }

    /**
     * methode toString
     *
     * @return informations complètes
     */
    @Override
    public String toString() {
        return "\n-- Location --\n" +
                "\nN° d'identification : " + idLoc +
                "\nKm Total : " + kmTotal +
                "\t\tNombre de passagers : " + nbrePassagers +
                "\nDate de la location : " + dateLoc +
                "\nTotal : " + total + "€" +
                "\nVéhicule : \n" + vehicule +
                "\nClient : \n" + client +
                "\nTrajet :\n\nAdresse de départ : " + adrDebut +
                "\nAdresse d'arrivée : " + adrFin;
    }
}
