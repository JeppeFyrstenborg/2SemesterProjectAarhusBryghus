package application.model;

public class PrislisteProdukt {
    private double nyPris;

    PrislisteProdukt(double nyPris, Produkt produkt) // OBS: package visibility (må kun kaldes af en prisliste)
    {
        this.nyPris = nyPris;
        this.produkt = produkt; //forbindelsen til Produkt sættes i constructoren fordi forbindelsen er tvungen (--> 1)
    }

    //----------------------------- Getters og setters ----------------------------------------

    public double getNyPris() {
        return nyPris;
    }

    public void setNyPris(double nyPris) {
        this.nyPris = nyPris;
    }

    //------------------------------- Link til Produkt ----------------------------------------------

    // association --> 1 Produkt
    private final Produkt produkt;

    public Produkt getProdukt() {
        return produkt;
    }

    //------------------------------------ Metoder ----------------------------------------------

    @Override
    public String toString() {
        return produkt.getProduktnavn() + "  |  " + nyPris + " Kr.";
    }
}
