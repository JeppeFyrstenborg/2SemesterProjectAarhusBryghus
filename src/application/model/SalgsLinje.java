package application.model;

public class SalgsLinje {
    private int produktAntal;
    private final int salgslinjeNr;
    private static int naesteSalgslinjeNr = 1;
    //salgetsPrislistesPris er det tilhørende salgs prislistes pris for dette produkt ved oprettelsen af salgslinjen
    private double salgetsPrislistesPrisForProdukt;

    SalgsLinje(int produktAntal, Produkt produkt) // OBS: package visibility (må kun kaldes af et salg)
    {
        this.produktAntal = produktAntal;
        this.produkt = produkt; //forbindelsen til Produkt sættes i constructoren fordi forbindelsen er tvungen (--> 1)
        this.salgslinjeNr = naesteSalgslinjeNr;
        naesteSalgslinjeNr++;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public int getProduktAntal() {
        return produktAntal;
    }

    public int getSalgslinjeNr() {
        return salgslinjeNr;
    }

    public double getSalgetsPrislistesPrisForProdukt() {
        return salgetsPrislistesPrisForProdukt;
    }

    public void setSalgetsPrislistesPrisForProdukt(double salgetsPrislistesPrisForProdukt) {
        this.salgetsPrislistesPrisForProdukt = salgetsPrislistesPrisForProdukt;
    }

    public void setProduktAntal(int produktAntal) {
        this.produktAntal = produktAntal;
    }

    //------------------------------- Link til Produkt ----------------------------------------------

    // association --> 1 Produkt
    private final Produkt produkt;

    public Produkt getProdukt() {
        return produkt;
    }

    //------------------------------- Andre metoder ----------------------------------------------

    /**
     * Returnerer den samlede pris for salgslinjen ud fra det tilhørende salgs prisliste.
     *
     * @return samlet pris af slagslinjen ud fra salgets prisliste
     */
    public double samletPrislistePris() {
        return salgetsPrislistesPrisForProdukt * produktAntal;
    }

    @Override
    public String toString() {
        return produkt.getProduktnavn() + "  |  " + this.produktAntal + " Stk.";
    }
}
