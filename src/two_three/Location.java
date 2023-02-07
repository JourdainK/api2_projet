package two_three;

import java.util.Objects;

public class Location {
    private int id, kmTotal, nbrePassagers;
    private String dateLoc;
    private double total;
    private Taxi vehicule;
    private Client client;
    private Adresse adrDebut, adrFin;
    //idAtm => static used as counter/serial to generate the Location's id automatically
    private static int idAtm = 1;

    public Location(int id, int kmTotal, int nbrePassagers, String dateLoc, Taxi vehicule, Client client, Adresse adrDebut, Adresse adrFin) {
        this.id = idAtm;
        idAtm++;
        this.kmTotal = kmTotal;
        this.nbrePassagers = nbrePassagers;
        this.dateLoc = dateLoc;
        this.vehicule = vehicule;
        this.client = client;
        this.adrDebut = adrDebut;
        this.adrFin = adrFin;
        setTotal();
        //once location confirmed > add this location to the list of location of the vehicule
        this.vehicule.getLocations().add(this);
        //same as above but for the client
        this.client.getListLocations().add(this);
    }

    public Location(int id, int kmTotal, int nbrePassagers, String dateLoc, Taxi vehicule, Client client) {
        this.id = idAtm;
        idAtm++;
        this.kmTotal = kmTotal;
        this.nbrePassagers = nbrePassagers;
        this.dateLoc = dateLoc;
        this.vehicule = vehicule;
        this.client = client;
        setTotal();
        //once location confirmed > add this location to the list of location of the vehicule
        this.vehicule.getLocations().add(this);
        //same as above but for the client
        this.client.getListLocations().add(this);
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
        this.vehicule.getLocations().add(this);
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

    public void setTotal() { this.total = vehicule.getPrixKm() * kmTotal; }

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
