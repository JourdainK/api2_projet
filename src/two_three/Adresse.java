package two_three;

import java.util.Objects;

public class Adresse {
    private int idAdr;
    private int cp;
    private String localite, rue, num;

    public Adresse(int id, int cp, String localite, String rue, String num) {
        this.idAdr = id;
        this.cp = cp;
        this.localite = localite;
        this.rue = rue;
        this.num = num;
    }

    public int getId() {
        return idAdr;
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

    //asked ChatGpt to give me examples
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return num == adresse.num &&
                idAdr == adresse.idAdr &&
                cp == adresse.cp &&
                localite.equals(adresse.localite) &&
                rue.equals(adresse.rue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdr);
    }

    @Override
    public String toString() {
        return "\nAdresse :\n" +
                "ID n° : " + idAdr +
                "\nCode postal :" + cp +
                "\tLocalité : " + localite +
                "\nRue :" + rue +
                "\t" + num + "\n\n";
    }


}
