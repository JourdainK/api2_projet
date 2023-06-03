package designpattern.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Taxi extends PriceObserver{
    /**
     * Classe Taxi
     *
     * @author Kevin Jourdain
     * @version 1.0
     * @see Observer
     *
     * Exemple d'implémentation du design Pattern Observer
     */

    /**
     * Identifiant du taxi
     */
    private int id;
    /**
     * Nombre maximum de passagers du taxi
     */
    private int nbreMaxPassagers;
    /**
     * Immatriculation du taxi
     */
    private String immatriculation;
    /**
     * Prix au kilomètre
     */
    private double prixKm;

    /**
     * Liste des observateurs du taxi
     */
    private List<Observer> lObservers = new ArrayList<>();

    /**
     * Constructeur de la classe Taxi
     * @param id
     * @param nbreMaxPassagers
     * @param immatriculation
     * @param prixKm
     */
    public Taxi(int id, int nbreMaxPassagers, String immatriculation, double prixKm) {
        this.id = id;
        this.nbreMaxPassagers = nbreMaxPassagers;
        this.immatriculation = immatriculation;
        this.prixKm = prixKm;
    }

    /**
     * Getter IdTaxi : numéro d'identification du taxi
     * @return  id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter nbreMaxPassagers : nombre de passagers maximum du taxi
     * @return  nbreMaxPassagers
     */
    public int getNbreMaxPassagers() {
        return nbreMaxPassagers;
    }

    /**
     * Getter immatriculation : immatriculation du taxi
     * @return  immatriculation
     */
    public String getImmatriculation() {
        return immatriculation;
    }

    /**
     * Getter prixKm : prix au kilomètre
     * @return  prixKm
     */
    public double getPrixKm() {
        return prixKm;
    }

    /**
     * Setter prixKm : prix au kilomètre
     * Utilisation de l'observer pour notifier les observateurs
     *
     * @param prixKm
     */
    @Override
    public void setPrice(double prixKm) {

        this.prixKm = prixKm;
        setAlert(prixKm);
        notifyObservers("Le prix au kilomètre a été modifié" +
                "\nNouveau prix : " + prixKm + "€\n");
    }

    /**
     * Getter lObservers : liste des observateurs du taxi
     * @return  lObservers
     */
    public List<Observer> getlObservers() {
        return lObservers;
    }

    /**
     * Ajout d'un observateur à la liste des observateurs du taxi
     * @param observer
     */
    public void addObserver(Observer observer) {
        lObservers.add(observer);
    }


    /**
     * Redéfinition de la méthode equals
     *  Basée sur l'immatriculation du taxi
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return Objects.equals(immatriculation, taxi.immatriculation);
    }

    /**
     * Redéfinition de la méthode hashCode
     *  Basée sur l'immatriculation du taxi
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(immatriculation);
    }

    /**
     * Redéfinition de la méthode toString
     * @return String
     */
    @Override
    public String toString() {
        return "\n-- Taxi --\n" +
                "N° d'identification : " + id +
                "\nNombre de passagers maximum : " + nbreMaxPassagers +
                "\t\tImmatriculation : " + immatriculation +
                "\nTarif (au kilomètre ) : " + prixKm + "€";
    }

    /**
     * Getter : prix au kilomètre
     * @return double
     */

    @Override
    public double getPrice() {
        return this.prixKm;
    }

    /**
     * Setter Alert : alerte de modification du prix au kilomètre
     *
     * @param alert, nouveau prix au kilomètre
     */
    @Override
    public void setAlert(double alert) {
        update("Le prix du taxi " + immatriculation + " est passé à " + alert + "€");
    }

    /**
     * Méthode update : affichage de l'alerte
     *
     * @param alert, alerte de modification du prix au kilomètre
     */
    @Override
    public void update(String alert) {
        System.out.println("Taxi : " + immatriculation + " : " + alert);
    }

    /**
     * Méthode notifyObservers : notification des observateurs
     *
     * @param alert, alerte de modification du prix au kilomètre
     */
    public void notifyObservers(String alert) {
        for (Observer observer : lObservers) {
            observer.update(alert);
        }
    }


}