package designpattern.composite;

public abstract class Element {
    int id;

    public Element(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
