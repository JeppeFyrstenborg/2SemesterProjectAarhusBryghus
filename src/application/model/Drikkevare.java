package application.model;

public class Drikkevare extends Produkt {
    private double liter;

    public Drikkevare(String produktnavn, double normalpris, boolean tilbageleveres, double liter) {
        super(produktnavn, normalpris, tilbageleveres);
        this.liter = liter;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public double getLiter() {
        return liter;
    }

    public void setLiter(double liter) {
        this.liter = liter;
    }

    //------------------------------- Link til Pant ----------------------------------------------

    // association --> 0..1 Pant
    private Pant pant; // nullable

    /**
     * Note: Nullable retur værdi.
     */
    public Pant getPant() {
        return pant;
    }

    /**
     * Pre: Denne drikkevare er ikke forbundet til et Pant-objekt.
     */
    public void setPant(Pant pant) {
        this.pant = pant;
    }

    /**
     * Pre: Denne drikkevare er forbundet til et Pant-objekt.
     */
    public void removePant() {
        this.pant = null;
    }
}
