package two_three;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Taxi {
    private int id, nbreMaxPassagers;
    private String immatriculation;
    private double prixKm;
    private List<Location> location= new ArrayList<>();

    public Taxi(int id, int nbreMaxPassagers, String immatriculation, double prixKm) {
        this.id = id;
        this.nbreMaxPassagers = nbreMaxPassagers;
        this.immatriculation = immatriculation;
        this.prixKm = prixKm;
    }

    public int getId() {
        return id;
    }

    public int getNbreMaxPassagers() {
        return nbreMaxPassagers;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public double getPrixKm() {
        return prixKm;
    }

    public void setPrixKm(double prixKm) {
        this.prixKm = prixKm;
    }

    public List<Location> getLocations() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
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
        return "\n-- Taxi --\n" +
                "N° d'identification : " + id +
                "\nNombre de passagers maximum : " + nbreMaxPassagers +
                "\t\tImmatriculation : " + immatriculation +
                "\nTarif (au kilomètre ) : " + prixKm + "€";
    }

}
