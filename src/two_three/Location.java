package two_three;

import java.util.Objects;

public class Location {
    private int id, kmTotal, nbrePassagers;
    private String dateLoc;
    private double total;
    private Taxi vehicule;
    private Client client;
    private Adresse adrDebut, adrFin;

    public Location(int id, int kmTotal, int nbrePassagers, String dateLoc, double total) {
        this.id = id;
        this.kmTotal = kmTotal;
        this.nbrePassagers = nbrePassagers;
        this.dateLoc = dateLoc;
        this.total = total;
    }

    public Location(int id, int kmTotal, int nbrePassagers, String dateLoc, double total, Taxi vehicule, Client client, Adresse adrDebut, Adresse adrFin) {
        this.id = id;
        this.kmTotal = kmTotal;
        this.nbrePassagers = nbrePassagers;
        this.dateLoc = dateLoc;
        this.total = total;
        this.vehicule = vehicule;
        this.client = client;
        this.adrDebut = adrDebut;
        this.adrFin = adrFin;
    }

    public int getId() {
        return id;
    }

    public int getKmTotal() {
        return kmTotal;
    }

    public int getNbrePassagers() {
        return nbrePassagers;
    }

    public String getDateLoc() {
        return dateLoc;
    }

    public double getTotal() {
        return total;
    }

    public Taxi getVehicule() {
        return vehicule;
    }

    public Client getClient() {
        return client;
    }

    public Adresse getAdrDebut() {
        return adrDebut;
    }

    public Adresse getAdrFin() {
        return adrFin;
    }

    public void setVehicule(Taxi vehicule) {
        this.vehicule = vehicule;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setAdrDebut(Adresse adrDebut){
        this.adrDebut = adrDebut;
    }

    public void setAdrFin(Adresse adrFin){
        this.adrFin = adrFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id &&
                adrDebut.equals(((Location) o).adrDebut) &&
                adrFin.equals(((Location) o).adrFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "\n-- Location --\n" +
                "\nN° d'identification : " + id +
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
