package designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class PriceObserver implements Observer{

    List<Observer> lObservers = new ArrayList<>();

    public void add(Observer observer) {
        lObservers.add(observer);
    }

    public void remove(Observer observer) {
        lObservers.remove(observer);
    }

    public void notifyObservers(String alert) {
        for (Observer observer : lObservers) {
            observer.update(alert);
        }
    }

    public abstract void setPrice(double price);

    public abstract double getPrice();

    public abstract void setAlert(double alert);

}
