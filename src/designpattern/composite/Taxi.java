package designpattern.composite;

public class Taxi extends Element{

    private String immatriculation;
    private String carburant;
    private double prixKm;
    public Taxi(int id) {
        super(id);
    }
}
