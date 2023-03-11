package application.model;

import java.util.*;

public class Prisliste {
    private String prislistenavn;
    private final int prislisteNr;
    private static int naestePrislisteNr = 1;

    // Ved opretning af et prisliste objekt sættes prislistenavn til "Ikke sat"
    public Prisliste() {
        this.prislistenavn = "Ikke sat";
        this.prislisteNr = naestePrislisteNr;
        naestePrislisteNr++;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public String getPrislistenavn() {
        return prislistenavn;
    }

    public int getPrislisteNr() {
        return prislisteNr;
    }

    public void setPrislistenavn(String prislistenavn) {
        this.prislistenavn = prislistenavn;
    }

    //---------------------------- Link til PrislisteProdukt ------------------------------------------

    // composition -> 0..* PrislisteProdukt
    private final List<PrislisteProdukt> prislisteProdukter = new ArrayList<>();

    public List<PrislisteProdukt> getPrislisteProdukter() {
        return new ArrayList<>(prislisteProdukter);
    }

    public void removePrislisteProdukt(PrislisteProdukt prislisteProdukt) {
        prislisteProdukter.remove(prislisteProdukt);
    }

    public PrislisteProdukt createPrislisteProdukt(double nyPris, Produkt produkt) {
        if (produkt == null)
            throw new NullPointerException("Produkt må ikke være null!");
        else {
            PrislisteProdukt prislisteProdukt = new PrislisteProdukt(nyPris, produkt);
            prislisteProdukter.add(prislisteProdukt);
            return prislisteProdukt;
        }
    }

    public PrislisteProdukt createFredagsbarPrislisteProdukt(double nyPris, Produkt produkt, int antalKlipSomPris) {
        if (produkt == null)
            throw new NullPointerException("Produkt må ikke være null!");
        if (!(antalKlipSomPris > 0))
            throw new RuntimeException("Antal klip skal være mere end 0");
        else {
            PrislisteProdukt fredagsbarPrislisteProdukt =
                    new FredagsbarPrislisteProdukt(nyPris, produkt, antalKlipSomPris);
            prislisteProdukter.add(fredagsbarPrislisteProdukt);
            return fredagsbarPrislisteProdukt;
        }
    }

    /**
     * Laver et fredagsbarPrislisteProdukt uden at tilføje det til listen prislisteProdukter i prislisten.
     * OBS: fredagsbarPrislisteProduktet skal tilføjes til prislisteProdukter bagefter brug af denne metode.
     *
     * @param nyPris           Den nye pris for fredagsbarPrislisteProduktet
     * @param produkt          Produktet som skal bruges i fredagsbarPrislisteProduktet
     * @param antalKlipSomPris Antal klip man kan bruge som pris
     * @return fredagsbarPrislisteProdukt
     */
    public PrislisteProdukt createFredagsbarPrislisteProduktUdenAddTilPrislisteProdukter
    (double nyPris, Produkt produkt, int antalKlipSomPris) {
        if (produkt == null)
            throw new NullPointerException("Produkt må ikke være null!");
        if (!(antalKlipSomPris > 0))
            throw new RuntimeException("Antal klip skal være mere end 0");
        else {
            return new FredagsbarPrislisteProdukt(nyPris, produkt, antalKlipSomPris);
        }
    }

    //---------------------------------------- Metoder ------------------------------------------


    @Override
    public String toString() {
        return prislistenavn + "  |  " + "Nr. " + prislisteNr;
    }

    /**
     * Returnerer prislistens prislisteProdukt der har samme produkt som parameterværdien.
     * Hvis der ikke findes noget så returneres null.
     *
     * @param produkt produktet der skal bruges til at finde prislisteProduktet med samme produkt
     * @return prislisteProduktet med samme produkt som parameterværdiens produkt
     */
    public PrislisteProdukt findPrislisteProduktUdFraProdukt(Produkt produkt) {
        int i = 0;
        while (i < prislisteProdukter.size()) {
            if (prislisteProdukter.get(i).getProdukt().equals(produkt)) {
                return prislisteProdukter.get(i);
            }
            i++;
        }
        return null;
    }

    /**
     * Returnerer prislistens prislisteProdukt der har et produkt med samme produktnavn som parameterværdien.
     * Hvis der ikke findes noget så returneres null.
     *
     * @param produktnavn produktnavnet der skal bruges til at finde prislisteProduktet
     * @return prislisteProduktet der blev fundet
     */
    public PrislisteProdukt findPrislisteProduktUdFraProduktnavn(String produktnavn) {
        int i = 0;
        while (i < prislisteProdukter.size()) {
            if (prislisteProdukter.get(i).getProdukt().getProduktnavn().equals(produktnavn)) {
                return prislisteProdukter.get(i);
            }
            i++;
        }
        return null;
    }

    /**
     * Udskifter et prislisteProdukt med et fredagsbarPrislisteProdukt og dets antalKlipSomPris i listen
     * prislisteProdukter.
     *
     * @param prislisteProdukt prislisteProdukt der skal udskiftes med et fredagsbarPrislisteProdukt.
     * @param antalKlipSomPris antallet af klip som pris i det nye fredagsbarPrislisteProdukt.
     * @return Det fredagsbarPrislisteProdukt der blev lavet og sat ind i listen
     */
    public PrislisteProdukt udskiftPrislisteProduktMedFredagsbarPrislisteProdukt(
            PrislisteProdukt prislisteProdukt, int antalKlipSomPris) {
        if (!prislisteProdukter.contains(prislisteProdukt)) {
            throw new NoSuchElementException("Prislisteproduktet er ikke i prislisten");
        } else {
            int prislisteProduktIndeks = prislisteProdukter.indexOf(prislisteProdukt);
            PrislisteProdukt fredagsbarPrislisteProdukt =
                    createFredagsbarPrislisteProduktUdenAddTilPrislisteProdukter(prislisteProdukt.getNyPris(),
                            prislisteProdukt.getProdukt(), antalKlipSomPris);
            prislisteProdukter.set(prislisteProduktIndeks, fredagsbarPrislisteProdukt);
            return fredagsbarPrislisteProdukt;
        }
    }

    /**
     * Kigger listen af prislisteprodukter igennem, og tjekker om der er en FredagsbarPrislisteProdukt.
     *
     * @return True hvis der er et FredagsbarPrislisteProdukt og false hvis ikke.
     */
    public boolean erDerFredagsbarPrislisteProdukter() {
        for (PrislisteProdukt plp : prislisteProdukter) {
            if (plp instanceof FredagsbarPrislisteProdukt)
                return true;
        }
        return false;
    }
}
