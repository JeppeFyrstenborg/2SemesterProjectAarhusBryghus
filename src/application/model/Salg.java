package application.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Salg {
    private final LocalDate salgPaabegyndtDato; //sættes automatisk i constructoren til datoen hvor objektet oprettes
    private LocalDate salgAfsluttetDato; //sættes til null når slaget oprettes
    private boolean betalt; //sættes automatisk i constructoren til false når salget oprettes
    private final int salgsNr;
    private static int naesteSalgsNr = 1;
    private Betalingsform betalingsform; //enum attribut

    public Salg() {
        this.salgPaabegyndtDato = LocalDate.now();
        this.betalt = false;
        this.salgsNr = naesteSalgsNr;
        naesteSalgsNr++;
    }

    //----------------------------- Getters og setters ----------------------------------------

    public LocalDate getSalgPaabegyndtDato() {
        return salgPaabegyndtDato;
    }

    public LocalDate getSalgAfsluttetDato() {
        return salgAfsluttetDato;
    }

    public boolean isBetalt() {
        return betalt;
    }

    public int getSalgsNr() {
        return salgsNr;
    }

    public void setBetalt(boolean betalt) {
        this.betalt = betalt;
    }

    public Betalingsform getBetalingsform() {
        return betalingsform;
    }

    //------------------------------- Link til Prisliste ----------------------------------------------

    // association --> 0..1 Prisliste
    private Prisliste prisliste; // nullable

    /**
     * Note: Nullable retur værdi.
     */
    public Prisliste getPrisliste() {
        return prisliste;
    }

    /**
     * Pre: Dette salg er ikke forbundet til en prisliste.
     */
    public void setPrisliste(Prisliste prisliste) {
        this.prisliste = prisliste;
    }

    /**
     * Pre: Dette salg er forbundet til en prisliste.
     */
    public void removePrisliste() {
        this.prisliste = null;
    }

    //------------------------------- Link til Kunde ----------------------------------------------

    // association --> 0..1 Kunde
    private Kunde kunde; // nullable

    /**
     * Note: Nullable retur værdi.
     */
    public Kunde getKunde() {
        return kunde;
    }

    /**
     * Pre: Dette salg er ikke forbundet til en kunde.
     */
    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    /**
     * Pre: Dette salg er forbundet til en kunde.
     */
    public void removeKunde() {
        this.kunde = null;
    }

    //------------------------------- Link til SalgsLinje ----------------------------------------------

    // composition -> 0..* SalgsLinje
    private final List<SalgsLinje> salgsLinjer = new ArrayList<>();

    public List<SalgsLinje> getSalgsLinjer() {
        return new ArrayList<>(salgsLinjer);
    }

    public void removeSalgsLinje(SalgsLinje salgsLinje) {
        salgsLinjer.remove(salgsLinje);
    }

    private SalgsLinje createSalgsLinje(int produktAntal, Produkt produkt) {
        SalgsLinje salgsLinje = new SalgsLinje(produktAntal, produkt);
        salgsLinje.setSalgetsPrislistesPrisForProdukt(prisliste.findPrislisteProduktUdFraProdukt(produkt).getNyPris());
        salgsLinjer.add(salgsLinje);
        return salgsLinje;
    }

    /**
     * Laver en tilbageleveresSalgsLinje, antalTilbageleveret sættes til 0, pantBetalt sættes til false.
     * salgetsPrislistesPrisForProdukt attribut sættes her.
     *
     * @param produktAntal antal af produktet i TilbageleveresSalgsLinjen
     * @param produkt      produktet der skal laves en TilbageleveresSalgsLinje for
     * @return TilbageleveresSalgsLinjen der er blevet lavet
     */
    private SalgsLinje createTilbageleveresSalgsLinje(int produktAntal, Produkt produkt) {
        SalgsLinje tilbageleveresSalgsLinje = new TilbageleveresSalgsLinje(produktAntal, produkt);
        tilbageleveresSalgsLinje.setSalgetsPrislistesPrisForProdukt(
                prisliste.findPrislisteProduktUdFraProdukt(produkt).getNyPris());
        salgsLinjer.add(tilbageleveresSalgsLinje);
        return tilbageleveresSalgsLinje;
    }

    /**
     * Laver en TilbageleveresSalgsLinje hvis produktet skal tilbageleveres, ellers laves en SalgsLinje.
     * Hvis der laves en TilbageleveresSalgsLinje sættes antalTilbageleveret til 0.
     * Salgslinjens salgetsPrislistesPrisForProdukt attribut sættes her.
     * Pre: Salget skal have en udfyldt prisliste.
     *
     * @param produktAntal antal af produktet i SalgsLinjen
     * @param produkt      produktet der skal laves en SalgsLinje for
     * @return SalgsLinjen der er blevet lavet
     */
    private SalgsLinje createSalgsLinjeEllerTilbageleveresSalgsLinje(int produktAntal, Produkt produkt) {
        if (prisliste == null) {
            throw new RuntimeException("Salget har ingen prisliste (prisliste er null)");
        } else if (!(prisliste.getPrislisteProdukter().size() > 0)) {
            throw new RuntimeException("Salgets prisliste er ikke udfyldt");
        } else {
            SalgsLinje salgsLinje;
            if (produkt.skalTilbageleveres()) {
                salgsLinje = createTilbageleveresSalgsLinje(produktAntal, produkt);
            } else {
                salgsLinje = createSalgsLinje(produktAntal, produkt);
            }
            return salgsLinje;
        }
    }

    /**
     * Laver nye salgslinjer eller tilføjer til eksisterende alt efter hvad der i forvejen er i salgets salgslinjer.
     * Pant salgslinjer laves eller tilføjes til ligeledes automatisk.
     * Returnerer 2 salgslinjer hvis produktet har pant, ellers kun 1.
     * Salgslinjen for selve produktet returneres altid på indeks 0.
     * <p>
     * Hvis en salgslinje med produktet allerede er i salget lægges produktantal til denne salgslinje.
     * Hvis en salgslinje ikke er i salget laves en ny salgslinje.
     * Opretter automatisk en salgslinje med pant-objekt, hvis produktet har et pant objekt og skal tilbageleveres.
     * Hvis produktet fra parameteren har pant, og der ikke allerede er en salgslinje for det, men der er andre
     * salgslinjer med produkter med samme panttype (dvs. f.eks. andre fustager), så laves en ny salgslinje for
     * produktet, men produktantal lægges til den eksisterende salgslinje for panttypen.
     *
     * @param produktAntal antal produkter der skal tilføjes til eksisterende salgslinje eller bruges i ny.
     * @param produkt      produktet man tjekker for
     * @return den eksisterende salgslinje eller den nyoprettede samt salgslinje med pant, hvis produktet har pant.
     */
    public ArrayList<SalgsLinje> opretEllerTilfoejTilSalgsLinje(int produktAntal, Produkt produkt) {
        //der er to salgslinjer i listen hvis produktet har pant, ellers er der kun en.
        //Salgslinjen for selve produktet returneres altid på indeks 0, pant-salgslinjen er altid på 1.
        ArrayList<SalgsLinje> listeMedEnEllerToSalgsLinjer = new ArrayList<>();

        //Gennemløber listen af salgslinjer.
        for (SalgsLinje salgsLinje : salgsLinjer) {
            Produkt salgsLinjensProdukt = salgsLinje.getProdukt();
            //Hvis der er en salgslinje med et matchene produkt.
            if (salgsLinjensProdukt.equals(produkt)) {
                salgsLinje.setProduktAntal(salgsLinje.getProduktAntal() + produktAntal);
                listeMedEnEllerToSalgsLinjer.add(salgsLinje);

                //Tjekker om salgslinjen har et produkt som skal leveres tilbage.
                if (salgsLinjensProdukt.skalTilbageleveres()) {
                    //Hivs salgslinjeproduktet fra første gennemløb, er en instans af klassen Kilovare.
                    if (salgsLinjensProdukt instanceof Kilovare) {
                        //Gennemløber listen igen.
                        for (SalgsLinje salgsLinje2 : salgsLinjer) {
                            //Tjekker salgslinjen fra andet gennemløb, har et produkt, som har samme pant, som i det
                            // første gennemløb.
                            //salgsLinje2.getProdukt() instanceof Kilovare &&
                            //((Kilovare) salgsLinje2.getProdukt()).getPant()==((Kilovare)salgsLinje.getProdukt())
                            // .getPant()
                            if (salgsLinje2.getProdukt().equals(((Kilovare) salgsLinje.getProdukt()).getPant())) {
                                salgsLinje2.setProduktAntal(salgsLinje2.getProduktAntal() + produktAntal);
                                listeMedEnEllerToSalgsLinjer.add(salgsLinje2);
                                return listeMedEnEllerToSalgsLinjer;
                            }
                        }
                    } else if (salgsLinjensProdukt instanceof Drikkevare) {
                        //Gennemløber listen igen.
                        for (SalgsLinje salgsLinje2 : salgsLinjer) {
                            //Tjekker salgslinjen fra andet gennemløb, har et produkt, som har samme pant, som i det
                            // første gennemløb.
                            //salgsLinje2.getProdukt().equals(((Drikkevare)salgsLinje.getProdukt()).getPant())
                            if (salgsLinje2.getProdukt().equals(((Drikkevare) salgsLinje.getProdukt()).getPant())) {
                                salgsLinje2.setProduktAntal(salgsLinje2.getProduktAntal() + produktAntal);
                                listeMedEnEllerToSalgsLinjer.add(salgsLinje2);
                                return listeMedEnEllerToSalgsLinjer;
                            }
                        }
                    } else
                        return listeMedEnEllerToSalgsLinjer;
                }
                return listeMedEnEllerToSalgsLinjer;
            }
        }
        //Hvis produktet skal leveres tilbage.
        if (produkt.skalTilbageleveres()) {
            //Tjekker om det er af typen kilovare.
            if (produkt instanceof Kilovare) {
                listeMedEnEllerToSalgsLinjer.add(createSalgsLinjeEllerTilbageleveresSalgsLinje(produktAntal, produkt));
                for (SalgsLinje salgsLinje : salgsLinjer) {
                    if (salgsLinje.getProdukt() instanceof Pant &&
                            ((Pant) salgsLinje.getProdukt()).getPantType().equals(PantType.KULSYREPANT)) {
                        salgsLinje.setProduktAntal(salgsLinje.getProduktAntal() + produktAntal);
                        listeMedEnEllerToSalgsLinjer.add(salgsLinje);
                        return listeMedEnEllerToSalgsLinjer;
                    }
                }
                listeMedEnEllerToSalgsLinjer.add(createSalgsLinjeEllerTilbageleveresSalgsLinje(produktAntal,
                        ((Kilovare) produkt).getPant()));
                return listeMedEnEllerToSalgsLinjer;
            } else if (produkt instanceof Drikkevare) {
                listeMedEnEllerToSalgsLinjer.add(createSalgsLinjeEllerTilbageleveresSalgsLinje(produktAntal, produkt));
                for (SalgsLinje salgsLinje : salgsLinjer) {
                    if (salgsLinje.getProdukt() instanceof Pant &&
                            ((Pant) salgsLinje.getProdukt()).getPantType().equals(PantType.FUSTAGEPANT)) {
                        salgsLinje.setProduktAntal(salgsLinje.getProduktAntal() + produktAntal);
                        listeMedEnEllerToSalgsLinjer.add(salgsLinje);
                        return listeMedEnEllerToSalgsLinjer;
                    }
                }
                listeMedEnEllerToSalgsLinjer.add(createSalgsLinjeEllerTilbageleveresSalgsLinje(produktAntal,
                        ((Drikkevare) produkt).getPant()));
                return listeMedEnEllerToSalgsLinjer;
            }
            listeMedEnEllerToSalgsLinjer.add(createSalgsLinjeEllerTilbageleveresSalgsLinje(produktAntal, produkt));
            return listeMedEnEllerToSalgsLinjer;
        }
        listeMedEnEllerToSalgsLinjer.add(createSalgsLinjeEllerTilbageleveresSalgsLinje(produktAntal, produkt));
        return listeMedEnEllerToSalgsLinjer;
    }

    //------------------------------- Andre metoder ----------------------------------------------

    /**
     * Udregner samlet pris for salget beregnet ud fra priser fra prislisten tilknyttet til salget.
     *
     * @return samlet pris
     */
    public double samletPrisMedPant() {
        double samletPrisMedPant = 0;
        for (SalgsLinje salgsLinje : salgsLinjer) {
            samletPrisMedPant += salgsLinje.samletPrislistePris();
        }
        return samletPrisMedPant;
    }

    /**
     * Udregner samlet pris for alle salgslinjer i salget bortset fra dem med produkter af typen Pant.
     *
     * @return samlet pris uden pant
     */
    public double samletPrisUdenPant() {
        return samletPrisMedPant() - samletPrisPant();
    }

    /**
     * Udregner samlet pris for alle salgslinjer med produkter af typen Pant.
     *
     * @return samlet pant pris
     */
    public double samletPrisPant() {
        double samletPrisPant = 0.0;
        for (SalgsLinje salgsLinje : salgsLinjer) {
            if (salgsLinje.getProdukt() instanceof Pant) {
                samletPrisPant += salgsLinje.samletPrislistePris();
            }
        }
        return samletPrisPant;
    }

    /**
     * Udregner samlet pris der skal tilbagebetales for den pant der er betalt og tilbageleveret.
     *
     * @return samlet pris der skal tilbagebetales
     */
    public double samletPantPrisRetur() {
        double samletPantPrisRetur = 0;
        for (SalgsLinje salgsLinje : salgsLinjer) {
            if (salgsLinje instanceof TilbageleveresSalgsLinje && !(salgsLinje.getProdukt() instanceof Anlaeg)) {
                TilbageleveresSalgsLinje tilbageleveresSalgsLinje = (TilbageleveresSalgsLinje) salgsLinje;
                int antalTilbageleveret = tilbageleveresSalgsLinje.getAntalTilbageleveret();
                int produktAntal = tilbageleveresSalgsLinje.getProduktAntal();

                double betaltPantSalgslinje = tilbageleveresSalgsLinje.samletPrislistePris();
                double ikkeTilbageleveretPant = (produktAntal - antalTilbageleveret) *
                        tilbageleveresSalgsLinje.getSalgetsPrislistesPrisForProdukt();

                samletPantPrisRetur += betaltPantSalgslinje - ikkeTilbageleveretPant;
            }
        }
        return samletPantPrisRetur;
    }

    /**
     * Udregner den samlede pris der skal betales efter tilbageleveret pant er fratrukket.
     * Dvs. samlet pris uden pant minus samlet pris for tilbageleveret pant.
     *
     * @return Samlet pris uden pant minus tilbageleveret pant
     */
    public double samletPrisPantFratrukket() {
        return samletPrisUdenPant() - samletPantPrisRetur();
    }

    /**
     * Afslutter salget ved at sætte det som betalt, sætte salgAfsluttetDato til nuværende dato,
     * og sætte betalingform til parameterværdien.
     *
     * @param betalingsform Betalingsformen der bruges til at betale for salget
     */
    public void betalOgAfslutSalg(Betalingsform betalingsform) {
        this.betalt = true;
        this.betalingsform = betalingsform;
        this.salgAfsluttetDato = LocalDate.now();
    }

    /**
     * Tjekker om salget har en salglinje med tilbageleveringsprodukt (Eks. fustage eller pant).
     *
     * @return Returnerer boolean true eller false.
     */
    public boolean harPantTilbageleveringSalgslinje() {
        for (SalgsLinje salgsLinje : salgsLinjer) {
            if (salgsLinje instanceof TilbageleveresSalgsLinje && !(salgsLinje.getProdukt() instanceof Anlaeg))
                return true;
        }
        return false;
    }

    /**
     * Bruger metoden "harEnTilbageleveringSalgslinje" og betaler salgslinjer som skal tilbageleveres (Eks.pant).
     *
     * @param betalingsform betalingsformen der bruges til at betale for pant
     */
    public void betalPantSalgslinjer(Betalingsform betalingsform) {
        if (harPantTilbageleveringSalgslinje()) {
            for (SalgsLinje salgsLinje : salgsLinjer) {
                if (salgsLinje instanceof TilbageleveresSalgsLinje)
                    ((TilbageleveresSalgsLinje) salgsLinje).betalPant(betalingsform);
            }
        }
    }

    /**
     * Tjekker om der er salgslinjer på salget.
     *
     * @return boolean true eller false.
     */
    public boolean checkOmDerErSalgslinjer() {
        return !this.salgsLinjer.isEmpty();
    }

    /**
     * Tjekker om panten er betalt.
     *
     * @return true eller false.
     */
    public boolean erPantBetalt() {
        for (SalgsLinje sl : salgsLinjer) {
            if (sl instanceof TilbageleveresSalgsLinje) {
                if (((TilbageleveresSalgsLinje) sl).pantErBetalt())
                    return true;
            }
        }
        return false;
    }

    /**
     * Finder alle tilbageleveringslinjer.
     *
     * @return En liste med salgslinjer.
     */
    public List<SalgsLinje> alleTilbageleveringslinjer() {
        List<SalgsLinje> salgslinjerTilbage = new ArrayList<>();
        for (SalgsLinje sl : salgsLinjer) {
            if (sl instanceof TilbageleveresSalgsLinje) {
                salgslinjerTilbage.add(sl);
            }
        }
        return salgslinjerTilbage;
    }

    /**
     * Finder samlet pris i klip.
     *
     * @return Samlede antalt klip i int.
     */
    public int samletPrisIAntalKlip() {
        int samletAntalKlip = 0;
        if (betalingsform != Betalingsform.KLIPPEKORT) {
            throw new IllegalArgumentException("Der er ikke brugt klippekort til at betale for salget");
        } else {
            for (SalgsLinje salgsLinje : salgsLinjer) {
                FredagsbarPrislisteProdukt fredagsbarPrislisteProdukt = (FredagsbarPrislisteProdukt)
                        prisliste.findPrislisteProduktUdFraProdukt(salgsLinje.getProdukt());
                samletAntalKlip += fredagsbarPrislisteProdukt.getAntalKlipSomPris() * salgsLinje.getProduktAntal();
            }
        }
        return samletAntalKlip;
    }

    @Override
    public String toString() {
        return salgsNr + " | " + prisliste.getPrislistenavn() + " | " + salgPaabegyndtDato;
    }


}