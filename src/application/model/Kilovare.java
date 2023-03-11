package application.model;

public class Kilovare extends Produkt {
    private double gram;

    public Kilovare(String produktnavn, double normalpris, boolean tilbageleveres, double gram) {
        super(produktnavn, normalpris, tilbageleveres);
        this.gram = gram;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public double getGram() {
        return gram;
    }

    public void setGram(double gram) {
        this.gram = gram;
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
     * Pre: Denne kilovare er ikke forbundet til et Pant-objekt.
     */
    public void setPant(Pant pant) {
        this.pant = pant;
    }

    /**
     * Pre: Denne kilovare er forbundet til et Pant-objekt.
     */
    public void removePant() {
        this.pant = null;
    }
}
