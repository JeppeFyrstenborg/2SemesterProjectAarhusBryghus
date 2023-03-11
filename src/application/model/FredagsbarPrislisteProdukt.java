package application.model;

public class FredagsbarPrislisteProdukt extends PrislisteProdukt {
    private int antalKlipSomPris;

    public FredagsbarPrislisteProdukt(double nyPris, Produkt produkt, int antalKlipSomPris) {
        super(nyPris, produkt);
        this.antalKlipSomPris = antalKlipSomPris;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public int getAntalKlipSomPris() {
        return antalKlipSomPris;
    }

    public void setAntalKlipSomPris(int antalKlipSomPris) {
        this.antalKlipSomPris = antalKlipSomPris;
    }

    @Override
    public String toString() {
        return super.toString() + "  |  " + this.antalKlipSomPris + " klip";
    }
}
