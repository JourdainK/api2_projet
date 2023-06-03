package designpattern.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Taxi extends PriceObserver{
    //TODO docu
    private int id, nbreMaxPassagers;
    private String immatriculation;
    private double prixKm;

    private List<Observer> lObservers = new ArrayList<>();


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

    @Override
    public void setPrice(double prixKm) {

        this.prixKm = prixKm;
        setAlert(prixKm);
        notifyObservers("Le prix au kilomètre a été modifié" +
                "\nNouveau prix : " + prixKm + "€\n");
    }

    public List<Observer> getlObservers() {
        return lObservers;
    }

    public void setlObservers(List<Observer> lObservers) {
        this.lObservers = lObservers;
    }

    public void addObserver(Observer observer) {
        lObservers.add(observer);
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


    @Override
    public double getPrice() {
        return this.prixKm;
    }

    @Override
    public void setAlert(double alert) {
        update("Le prix du taxi " + immatriculation + " est passé à " + alert + "€");
    }

    @Override
    public void update(String alert) {
        System.out.println("Taxi : " + immatriculation + " : " + alert);
    }

    public void notifyObservers(String alert) {
        for (Observer observer : lObservers) {
            observer.update(alert);
        }
    }


}