package application.model;

public class Anlaeg extends Produkt {
    private int antalHaner;
    private boolean bar;

    public Anlaeg(String produktnavn, double normalpris, boolean tilbageleveres, int antalHaner, boolean bar) {
        super(produktnavn, normalpris, tilbageleveres);
        this.antalHaner = antalHaner;
        this.bar = bar;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public int getAntalHaner() {
        return antalHaner;
    }

    public boolean isBar() {
        return bar;
    }

    public void setAntalHaner(int antalHaner) {
        this.antalHaner = antalHaner;
    }

    public void setBar(boolean bar) {
        this.bar = bar;
    }
}
