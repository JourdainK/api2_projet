package designpattern.composite;

import java.util.Objects;

public class Taxi extends Element{
    /**
     * classe Taxi
     * @see Element
     */

    /**
     * immatriculation du taxi
     */
    private String immatriculation;

    /**
     * nombre maximum de passagers du taxi
     */
    private int nbreMaxPassagers;

    /**
     * carburant du taxi
     */
    private String carburant;

    /**
     * prix au kilomètre
     */
    private double prixKm;


    /**
     * Constructeur de la classe Taxi
     * @param id
     * @param immatriculation
     * @param nbreMaxPassagers
     * @param carburant
     * @param prixKm
     */
    public Taxi(int id, String immatriculation, int nbreMaxPassagers, String carburant, double prixKm) {
        super(id);
        this.immatriculation = immatriculation;
        this.nbreMaxPassagers = nbreMaxPassagers;
        this.carburant = carburant;
        this.prixKm = prixKm;
    }

    /**
     * Getter immatriculation
     * @return immatriculation
     */
    public String getImmatriculation() {
        return immatriculation;
    }

    /**
     * Getter nbreMaxPassagers
     * @return nbreMaxPassagers
     */
    public int getNbreMaxPassagers() {
        return nbreMaxPassagers;
    }

    /**
     * Getter carburant
     * @return carburant
     */
    public String getCarburant() {
        return carburant;
    }

    /**
     * Getter prixKm
     * @return prixKm
     */
    public double getPrixKm() {
        return prixKm;
    }

    /**
     * Setter immatriculation
     * @param immatriculation
     */
    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    /**
     * Setter nbreMaxPassagers
     * @param nbreMaxPassagers
     */
    public void setNbreMaxPassagers(int nbreMaxPassagers) {
        this.nbreMaxPassagers = nbreMaxPassagers;
    }

    /**
     * Setter carburant
     * @param carburant
     */
    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    /**
     * Setter prixKm
     * @param prixKm
     */
    public void setPrixKm(double prixKm) {
        this.prixKm = prixKm;
    }


    /**
     * Redéfinition de la méthode equals
     *  Basé sur l'immatriculation
     * @param o
     * @return
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
     *  Basé sur l'immatriculation
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(immatriculation);
    }


    /**
     * Redéfinition de la méthode toString
     *  Utilisation de StringBuilder
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Taxi : \n")
                .append("id : ").append(id)
                .append("\n immatriculation : ").append(immatriculation)
                .append("\tPassagers maximum : ").append(nbreMaxPassagers)
                .append("\tcarburant : ").append(carburant)
                .append("\nprix au kilomètre : ").append(prixKm);
        return sb.toString();
    }

}
