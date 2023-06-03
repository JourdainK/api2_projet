package designpattern.composite;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Categorie extends Element {
    /**
     * classe Categorie
     */

    /**
     * nom de la catégorie
     */
    String nom;

    /**
     * liste des éléments de la catégorie
     */
    Set<Element> elements = new HashSet<>();


    /**
     * Constructeur de la classe Categorie
     *
     * @param id  identifiant de la catégorie
     * @param nom nom de la catégorie
     */
    public Categorie(int id, String nom) {
        super(id);
        this.nom = nom;
    }

    /**
     * Ajoute un élément à la catégorie
     * @param elements
     */

    public void setElements(Set<Element> elements) {
        this.elements = elements;
    }

    /**
     * Getter nom de la catégorie
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter des éléments de la catégorie
     * @return
     */
    public Set<Element> getElements() {
        return elements;
    }

    /**
     * Redéfinition de la méthode toString
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Categorie :")
                .append("\nid : ").append(id)
                .append("\tnom : ").append(nom);

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
