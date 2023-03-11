package application.model;

import java.util.ArrayList;
import java.util.List;

public class Produktkategori {
    private String produktkategorinavn;

    public Produktkategori(String produktkategorinavn) {
        this.produktkategorinavn = produktkategorinavn;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public String getProduktkategorinavn() {
        return produktkategorinavn;
    }

    public void setProduktkategorinavn(String produktkategorinavn) {
        this.produktkategorinavn = produktkategorinavn;
    }

    //------------------------------- Link til Produkt ----------------------------------------------

    // aggregation --> 0..* Produkt
    private final List<Produkt> produkter = new ArrayList<>();

    public List<Produkt> getProdukter() {
        return new ArrayList<>(produkter);
    }

    /**
     * Pre: Produktet er ikke forbundet til en Produktkategori.
     */
    public void addProdukt(Produkt produkt) {
        produkter.add(produkt);
    }

    /**
     * Pre: Produktet er forbundet til denne Produktkategori.
     */
    public void removeProdukt(Produkt produkt) {
        produkter.remove(produkt);
    }

    public Produkt createProdukt(String produktnavn, double normalpris, boolean tilbageleveres) {
        Produkt produkt = new Produkt(produktnavn, normalpris, tilbageleveres);
        produkter.add(produkt);
        return produkt;
    }

    public Produkt createAnlaegProdukt(String produktnavn, double normalpris, boolean tilbageleveres, int antalHaner,
                                       boolean bar) {
        Produkt anlaeg = new Anlaeg(produktnavn, normalpris, tilbageleveres, antalHaner, bar);
        produkter.add(anlaeg);
        return anlaeg;
    }

    /**
     * Husk at sætte det tilhørende pant-objekt hvis drikkevaren har pant (fustager)
     */
    public Produkt createDrikkevareProdukt(
            String produktnavn, double normalpris, boolean tilbageleveres, double liter) {
        Produkt drikkevare = new Drikkevare(produktnavn, normalpris, tilbageleveres, liter);
        produkter.add(drikkevare);
        return drikkevare;
    }

    /**
     * Husk at sætte det tilhørende pant-objekt hvis drikkevaren har pant (kylsyre)
     */
    public Produkt createKilovareProdukt(String produktnavn, double normalpris, boolean tilbageleveres, double gram) {
        Produkt kilovare = new Kilovare(produktnavn, normalpris, tilbageleveres, gram);
        produkter.add(kilovare);
        return kilovare;
    }

    /**
     * Laver et Pant objekt.
     *
     * @param produktnavn    Pant-objektets navn
     * @param normalpris     Pant-objektets normalpris
     * @param tilbageleveres Om Pant-objektet skal tilbageleveres
     * @param pantType       Pant-objektets type
     * @return Pant-objektet
     */
    public Produkt createPantProdukt(String produktnavn, double normalpris, boolean tilbageleveres,
                                     PantType pantType) {
        Produkt pant = new Pant(produktnavn, normalpris, tilbageleveres, pantType);
        produkter.add(pant);
        return pant;
    }

    public Produkt createKlippekortProdukt(String produktnavn, double normalpris, boolean tilbageleveres,
                                           int antalKlip) {
        Produkt klippekort = new Klippekort(produktnavn, normalpris, tilbageleveres, antalKlip);
        produkter.add(klippekort);
        return klippekort;
    }
}
