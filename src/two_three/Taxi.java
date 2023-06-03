package two_three;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Taxi {
    /**
     * classe Taxi
     * @author Kevin Jourdain
     * @version 2.0
     * @see Location
     */

    /**
     * Identifiant du Taxi
     */
    protected int idTaxi;
    /**
     * nombre maximum de passagers du taxi
     */
    protected int nbreMaxPassagers;
    /**
     * immatriculation du taxi
     */
    protected String immatriculation;
    /**
     * prix au kilomètre
     */
    protected double prixKm;
    /**
     * liste des locations réalisées par le taxi
     */
    protected List<Location> listTaxiLoc;

    /**
     * Constructeur de la classe Taxi
     *
     * @param builder
     */
    private Taxi(TaxiBuilder builder) {
        this.idTaxi = builder.idTaxi;
        this.nbreMaxPassagers = builder.nbreMaxPassagers;
        this.immatriculation = builder.immatriculation;
        this.prixKm = builder.prixKm;
        this.listTaxiLoc = new ArrayList<>();
    }


    /**
     * Setter IdTaxi : numéro d'identification du taxi
     *
     * @param idTaxi
     */
    public void setIdTaxi(int idTaxi) {
        this.idTaxi = idTaxi;
    }

    /**
     * setter listTaxiLoc
     *
     * @param listTaxiLoc liste des locations du taxi
     */
    public void setLocation(List<Location> listTaxiLoc) {
        this.listTaxiLoc = listTaxiLoc;
    }

    /**
     * getter idTaxi
     *
     * @return identifiant unique du taxi
     */
    public int getIdTaxi() {
        return idTaxi;
    }

    /**
     * getter nbrMaxPassagers
     *
     * @return nombre de passagers maximum
     */
    public int getNbreMaxPassagers() {
        return nbreMaxPassagers;
    }

    /**
     * getter immatriculation
     *
     * @return immatriculation
     */
    public String getImmatriculation() {
        return immatriculation;
    }

    /**
     * getter prixKm
     *
     * @return prix au kilomètre
     */
    public double getPrixKm() {
        return prixKm;
    }

    /**
     * getter listTaxiLoc
     *
     * @return liste des locations du taxi
     */
    public List<Location> getListTaxiLoc() {
        return listTaxiLoc;
    }

    /**
     * setter immatriculation
     *
     * @param
     */
    public void setLocations(List<Location> locatTaxi) {
        this.listTaxiLoc = locatTaxi;
    }

    /**
     * égalité de deux taxis basée sur l'immatriculation
     *
     * @param o autre élément
     * @return égalité ou pas
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taxi taxi = (Taxi) o;
        return Objects.equals(immatriculation, taxi.immatriculation);
    }

    /**
     * calcul du hashcode basé sur l'immatriculation
     *
     * @return valeur du hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(immatriculation);
    }

    /**
     * méthode toString
     *
     * @return informations complètes excepté la liste des locations
     */
    @Override
    public String toString() {
        return "\t-- Taxi --\n" +
                "N° d'identification : " + idTaxi +
                "\nNombre de passagers maximum : " + nbreMaxPassagers +
                "\t\tImmatriculation : " + immatriculation +
                "\nTarif (au kilomètre ) : " + prixKm + "€";
    }

    /**
     * méthode getClientsOfTaxi
     *
     * @return liste des clients (sans doublons) ayant utilisé le taxi
     */

    public List<Client> getClientsOfTaxi() {
        Set<Client> sClient = new HashSet<>();

        for (Location loc : listTaxiLoc) {
            System.out.println(loc);
            sClient.add(loc.getClient());
        }
        List<Client> listClient = new ArrayList<>(sClient);
        return listClient;
    }

    /**
     * méthode getTotKm
     *
     * @return total des kilomètres parcourus par le taxi lors de ses locations
     */
    public int getTotKm() {
        int totKm = 0;
        for (Location loc : listTaxiLoc) {
            totKm += loc.getKmTotal();
        }
        return totKm;
    }

    /**
     * méthode getTotGain
     *
     * @return total des gains réalisés par le taxi lors de ses locations
     */
    public double getTotGain() {
        BigDecimal totGain = new BigDecimal(0);
        for (Location loc : listTaxiLoc) {
            totGain = totGain.add(BigDecimal.valueOf(loc.getTotal()));
        }

        double tot = totGain.doubleValue();
        return tot;
    }

    /**
     * méthode getListLocationBetweenDates
     *
     * @param dateStart date de début de la période
     * @param dateEnd   date de fin de la période
     * @return la liste des locations du taxi entre les deux dates (incluses)
     */

    public List<Location> getListLocationBetweenDates(LocalDate dateStart, LocalDate dateEnd) {
        Set<Location> sLocat = new HashSet<>();

        for (Location l : listTaxiLoc) {
            if ((l.getDateLoc().isAfter(dateStart) && l.getDateLoc().isBefore(dateEnd)) || l.getDateLoc().isEqual(dateStart) || l.getDateLoc().isEqual(dateEnd) || l.getDateLoc().isEqual(dateStart) && l.getDateLoc().isEqual(dateEnd)) {
                sLocat.add(l);
            }
        }

        List<Location> listLocat = new ArrayList<>(sLocat);
        return listLocat;
    }


    /**
     * Design Pattern : Builder
     *
     *
     * Builder pour la classe Taxi
     */

    public static class TaxiBuilder {
        protected int idTaxi;
        protected int nbreMaxPassagers;
        protected String immatriculation;
        protected double prixKm;
        protected List<Location> listTaxiLoc;

        public TaxiBuilder setIdTaxi(int idTaxi) {
            this.idTaxi = idTaxi;
            return this;
        }

        public TaxiBuilder setNbreMaxPassagers(int nbreMaxPassagers) {
            this.nbreMaxPassagers = nbreMaxPassagers;
            return this;
        }

        public TaxiBuilder setImmatriculation(String immatriculation) {
            this.immatriculation = immatriculation;
            return this;
        }

        public TaxiBuilder setPrixKm(double prixKm) {
            this.prixKm = prixKm;
            return this;
        }

        public TaxiBuilder setListTaxiLoc(List<Location> listTaxiLoc) {
            this.listTaxiLoc = listTaxiLoc;
            return this;
        }

        public Taxi build() throws Exception {
            if (idTaxi < 0 || immatriculation.isBlank() || prixKm < 0 || nbreMaxPassagers < 1)
                throw new Exception("Erreur lors de la construction du Taxi");
            return new Taxi(this);
        }
    }
}
