package application.model;

public class Klippekort extends Produkt {
    private int antalKlip;

    public Klippekort(String produktnavn, double normalpris, boolean tilbageleveres, int antalKlip) {
        super(produktnavn, normalpris, tilbageleveres);
        this.antalKlip = antalKlip;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public int getAntalKlip() {
        return antalKlip;
    }

    public void setAntalKlip(int antalKlip) {
        this.antalKlip = antalKlip;
    }
}
