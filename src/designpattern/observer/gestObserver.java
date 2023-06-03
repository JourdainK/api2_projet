package designpattern.observer;

public class gestObserver {
    public static void main(String[] args) {
        Client client1 = new Client(1, "mail1@gmail.com", "One", "Hun", "555-4562");
        Client client2 = new Client(2, "mail2@gmail.com", "Two", "Deux", "555-2654");

        Taxi taxi1 = new Taxi(1, 4, "1-ABC-123", 1.5);
        Taxi taxi2 = new Taxi(2, 6, "2-DEF-456", 2.0);

        taxi1.addObserver(client1);
        taxi1.addObserver(client2);

        taxi2.addObserver(client1);

        taxi1.setPrice(2.0);
        taxi2.setPrice(2.5);


    }
}
