package designpattern.composite;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Categorie extends Element{
    String nom;

    Set<Element> elements = new HashSet<>();

    public Categorie(int id,String nom) {
        super(id);
        this.nom = nom;
    }

    public void setElements(Set<Element> elements) {
        this.elements = elements;
    }

    public String getNom() {
        return nom;
    }

    public Set<Element> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Categorie :")
                .append("\nid : ").append(id)
                .append("nom :").append(nom);

        if (!elements.isEmpty()) {
            sb.append("\nelements :");
            Iterator<Element> iterator = elements.iterator();
            while (iterator.hasNext()) {
                Element element = iterator.next();
                sb.append(element.toString());
                if (iterator.hasNext()) {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }

}
