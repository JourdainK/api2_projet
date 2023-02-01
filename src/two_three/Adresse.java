package two_three;

import java.util.Objects;

public class Adresse {
    private int id, cp;
    private String localite, rue, num;

    public Adresse(int id, int cp, String localite, String rue, String num) {
        this.id = id;
        this.cp = cp;
        this.localite = localite;
        this.rue = rue;
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public int getCp() {
        return cp;
    }

    public String getLocalite() {
        return localite;
    }

    public String getRue() {
        return rue;
    }

    public String getNum() {
        return num;
    }

    //TODO check-test enter diff. addresses (same road,zip,ect)
    //TODO check-test enter same addresses + slightly diff. addresses
    //TODO check a way not to enter twice the same address (equals based on id)
    //asked ChatGpt to give me examples
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return num == adresse.num &&
                id == adresse.id &&
                cp == adresse.cp &&
                localite.equals(adresse.localite) &&
                rue.equals(adresse.rue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "\nAdresse :\n" +
                "ID n° : \n" + id +
                "Code postal :" + cp +
                "\tLocalité : " + localite +
                "\n Rue :" + rue + '\'' +
                "\t" + num + "\n\n";
    }


}
