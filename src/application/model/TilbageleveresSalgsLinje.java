package application.model;

public class TilbageleveresSalgsLinje extends SalgsLinje {
    private int antalTilbageleveret; //sættes til 0 når objektet oprettes
    private boolean pantBetalt; //sættes til false når objektet oprettes
    private Betalingsform betalingsform; //enum attribut

    public TilbageleveresSalgsLinje(int produktAntal, Produkt produkt) {
        super(produktAntal, produkt);
        this.antalTilbageleveret = 0;
        this.pantBetalt = false;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public int getAntalTilbageleveret() {
        return antalTilbageleveret;
    }

    public void setAntalTilbageleveret(int antalTilbageleveret) {
        this.antalTilbageleveret = antalTilbageleveret;
    }

    public boolean pantErBetalt() {
        return pantBetalt;
    }

    public Betalingsform getBetalingsform() {
        return betalingsform;
    }

    public void betalPant(Betalingsform betalingsform) {
        this.betalingsform = betalingsform;
        this.pantBetalt = true;
    }
}
