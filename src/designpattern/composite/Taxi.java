package designpattern.composite;

import java.util.Objects;

public class Taxi extends Element{

    private String immatriculation;

    private int nbreMaxPassagers;

    private String carburant;

    private double prixKm;

    public Taxi(int id, String immatriculation, int nbreMaxPassagers, String carburant, double prixKm) {
        super(id);
        this.immatriculation = immatriculation;
        this.nbreMaxPassagers = nbreMaxPassagers;
        this.carburant = carburant;
        this.prixKm = prixKm;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public int getNbreMaxPassagers() {
        return nbreMaxPassagers;
    }

    public String getCarburant() {
        return carburant;
    }

    public double getPrixKm() {
        return prixKm;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public void setNbreMaxPassagers(int nbreMaxPassagers) {
        this.nbreMaxPassagers = nbreMaxPassagers;
    }

    public void setCarburant(String carburant) {
        this.carburant = carburant;
    }

    public void setPrixKm(double prixKm) {
        this.prixKm = prixKm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return Objects.equals(immatriculation, taxi.immatriculation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(immatriculation);
    }

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
