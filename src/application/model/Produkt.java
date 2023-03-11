package application.model;

public class Produkt {
    private String produktnavn;
    private final int produktNr;
    private static int naesteProduktNr = 1;
    private boolean tilbageleveres; //om produktet skal tilbageleveres

    /*Den basale pris på et produkt. Normalprisen bruges i en prisliste hvis prisen på produktet ikke ændres*/
    private double normalpris;

    public Produkt(String produktnavn, double normalpris, boolean tilbageleveres) {
        this.produktnavn = produktnavn;
        this.normalpris = normalpris;
        this.produktNr = naesteProduktNr;
        naesteProduktNr++;
        this.tilbageleveres = tilbageleveres;
    }

    @Override
    public String toString() {
        return produktnavn + " | >>>>>>" + normalpris + " Kr.<<<<<<";
    }

    //----------------------------- Getters og setters ----------------------------------------

    public String getProduktnavn() {
        return produktnavn;
    }

    public double getNormalpris() {
        return normalpris;
    }

    public int getProduktNr() {
        return produktNr;
    }

    public boolean skalTilbageleveres() {
        return tilbageleveres;
    }

    public void setTilbageleveres(boolean tilbageleveres) {
        this.tilbageleveres = tilbageleveres;
    }

    public void setProduktnavn(String produktnavn) {
        this.produktnavn = produktnavn;
    }

    public void setNormalpris(double normalpris) {
        this.normalpris = normalpris;
    }

    public boolean isTilbageleveres() {
        return tilbageleveres;
    }
}
