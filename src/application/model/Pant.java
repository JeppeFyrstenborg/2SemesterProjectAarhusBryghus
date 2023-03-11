package application.model;

public class Pant extends Produkt {
    private PantType pantType;

    public Pant(String produktnavn, double normalpris, boolean tilbageleveres, PantType pantType) {
        super(produktnavn, normalpris, tilbageleveres);
        this.pantType = pantType;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public PantType getPantType() {
        return pantType;
    }

    public void setPantType(PantType pantType) {
        this.pantType = pantType;
    }
}
