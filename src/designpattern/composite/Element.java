package designpattern.composite;

public abstract class Element {
    //github example https://github.com/iluwatar/java-design-patterns
    int id;

    public Element(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
