package two_three;


import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe métier de gestion de location de service Taxi
 *
 * @author Kevin Jourdain
 * @version 2.0
 * @see Taxi
 * @see Client
 * @see Adresse
 */

public class Location {
    /**
     * identifiant unique du client
     */
    protected int idLoc;
    /**
     * nombre de kilomètres parcouru lors de la location
     */
    protected int kmTotal;
    /**
     * nombre de passagers véhiculés lors de la location
     */
    protected int nbrePassagers;
    /**
     * date de la location
     */
    protected LocalDate dateLoc;
    /**
     * coût total de la location
     */
    protected double total;
    /**
     * Taxi utilisé pour la location
     */
    protected Taxi vehicule;
    /**
     * Client de la Location
     */
    protected Client client;
    /**
     * Adresse de départ de la location
     */
    protected Adresse adrDebut;
    /**
     * Adresse d'arrivée de la location
     */
    protected Adresse adrFin;


    /**
     * Constructeur à l'aide du pattern Builder
     *
     * @param builder
     */
    private Location(LocationBuilder builder) {
        this.idLoc = builder.idLoc;
        this.kmTotal = builder.kmTot;
        this.nbrePassagers = builder.nbrePassagers;
        this.dateLoc = builder.dateLoc;
        this.total = builder.total;
        this.vehicule = builder.vehicule;
        this.client = builder.client;
        this.adrDebut = builder.adrDebut;
        this.adrFin = builder.adrFin;
    }

    /**
     * Setter idLoc (numéro d'identification de la location)
     * id donné par la base de données
     *
     * @param idLoc
     */
    public void setId(int idLoc) {
        this.idLoc = idLoc;
    }

    /**
     * Setter Véhicule (taxi utilisé pour la location)
     *
     * @param vehicule
     */
    public void setVehicule(Taxi vehicule) {
        this.vehicule = vehicule;
    }

    /**
     * Setter Client (Client de la location)
     *
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Setter adrDebut (Adresse de départ de la location)
     *
     * @param adrDebut
     */
    public void setAdrDebut(Adresse adrDebut) {
        this.adrDebut = adrDebut;
    }


    /**
     * Setter adrFin (Adresse d'arrivée de la location)
     *
     * @param adrFin
     */
    public void setAdrFin(Adresse adrFin) {
        this.adrFin = adrFin;
    }

    /**
     * getter idLoc
     *
     * @return identifiant de la location
     */
    public int getIdLoc() {
        return idLoc;
    }

    /**
     * getter kmTotal
     *
     * @return nombre de kilomètres parcourus
     */
    public int getKmTotal() {
        return kmTotal;
    }

    /**
     * getter nbrePassagers
     *
     * @return nombre de passagers véhiculés
     */
    public int getNbrePassagers() {
        return nbrePassagers;
    }

    /**
     * getter dateLoc
     *
     * @return date de la location
     */
    public LocalDate getDateLoc() {
        return dateLoc;
    }

    /**
     * getter total
     *
     * @return coût total de la livraison
     */
    public double getTotal() {
        return total;
    }

    /**
     * getter vehicule
     *
     * @return véhicule utilisé lors de la location
     */
    public Taxi getVehicule() {
        return vehicule;
    }

    /**
     * getter client
     *
     * @return client de la location
     */
    public Client getClient() {
        return client;
    }

    /**
     * getter adreDebut
     *
     * @return adresse de départ de la location
     */
    public Adresse getAdrDebut() {
        return adrDebut;
    }

    /**
     * getter adreFin
     *
     * @return adresse d'arrivée de la location
     */
    public Adresse getAdrFin() {
        return adrFin;
    }


    /**
     * égalité de deux locations basées sur le quadruplet id, adrDebut, adrFin, dateLoc
     *
     * @param o autre élément
     * @return égalité ou pas
     */

    //TODO check this equals => Compare to exercice instructions !
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return idLoc == location.idLoc &&
                adrDebut.equals(((Location) o).adrDebut) &&
                adrFin.equals(((Location) o).adrFin) &&
                dateLoc.equals(((Location) o).dateLoc);
    }

    /**
     * calcul du hashcode du client basé sur le quadruplet id, adrDebut, adrFin, dateLoc
     *
     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(idLoc, adrDebut, adrFin, dateLoc);
    }

    /**
     * methode toString
     *
     * @return informations complètes
     */
    @Override
    public String toString() {
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_RESET = "\u001B[0m";
        String ANSI_CYAN = "\u001B[36m";

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(ANSI_CYAN)
                .append("\t\tN° d'identification : ").append(ANSI_RESET)
                .append(idLoc)
                .append("\nKm Total : ").append(kmTotal)
                .append("\t\tNombre de passagers : ").append(nbrePassagers)
                .append("\nDate de la location : ").append(dateLoc)
                .append("\nTotal : ").append(total).append("€")
                .append(ANSI_GREEN).append("\nVéhicule : \n").append(ANSI_RESET)
                .append(vehicule.immatriculation).append("\t\tPrix/km : ").append(vehicule.prixKm).append(" €\t").append("Places : ").append(vehicule.getNbreMaxPassagers())
                .append(ANSI_GREEN).append("\nClient : \n").append(ANSI_RESET)
                .append(client.getMail()).append("\t\t").append(client.getNom()).append("\t\t").append(client.getPrenom())
                .append(ANSI_GREEN).append("\nTrajet :").append(ANSI_RESET)
                .append("\nAdresse de départ : ").append(adrDebut.getRue()).append("\t").append(adrDebut.getNum()).append("\t").append(adrDebut.getLocalite()).append("\t").append(adrDebut.getCp())
                .append("\nAdresse d'arrivée : ").append(adrFin.getRue()).append("\t").append(adrFin.getNum()).append("\t").append(adrFin.getLocalite()).append("\t").append(adrFin.getCp()).append("\n\n");

        String output = stringBuilder.toString();

        return output;
    }


    public static class LocationBuilder {
        protected int idLoc;
        protected int kmTot;
        protected int nbrePassagers;
        protected LocalDate dateLoc;
        protected Taxi vehicule;
        protected double total;
        protected Client client;
        protected Adresse adrDebut;
        protected Adresse adrFin;

        public LocationBuilder setIdLoc(int idLoc) {
            this.idLoc = idLoc;
            return this;
        }

        public LocationBuilder setKmTot(int kmTot) {
            this.kmTot = kmTot;
            return this;
        }

        public LocationBuilder setNbrePassagers(int nbrePassagers) {
            this.nbrePassagers = nbrePassagers;
            return this;
        }

        public LocationBuilder setDateLoc(LocalDate dateLoc) {
            this.dateLoc = dateLoc;
            return this;
        }

        public LocationBuilder setVehicule(Taxi vehicule) {
            this.vehicule = vehicule;
            return this;
        }

        public LocationBuilder setTotal(double total) {
            this.total = total;
            return this;
        }

        public LocationBuilder setClient(Client client) {
            this.client = client;
            return this;
        }

        public LocationBuilder setAdrDebut(Adresse adrDebut) {
            this.adrDebut = adrDebut;
            return this;
        }

        public LocationBuilder setAdrFin(Adresse adrFin) {
            this.adrFin = adrFin;
            return this;
        }

        //TODO check date -> fix
        public Location build() throws Exception {
            //problème pour l'exception concernant la date > condition la date ne doit précéder la date du jour -> dateLoc == null => exception.// passer par un trigger ou un check SQL
            //LocalDate tod = LocalDate.now();
            //dateLoc.isBefore(tod) -> passer par un trigger ou un check SQL

            if (kmTot <= 0 || nbrePassagers <= 0 ||  dateLoc == null  ||adrDebut == null || adrFin == null || client == null || vehicule == null) throw new Exception("Erreur lors de la construction de la location");

            return new Location(this);
        }

    }
}
