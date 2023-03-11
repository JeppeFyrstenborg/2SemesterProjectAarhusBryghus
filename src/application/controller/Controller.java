package application.controller;

import application.model.*;
import storage.Storage;
import storage.StorageI;

import java.time.LocalDate;
import java.util.*;

public class Controller {
    private StorageI storage;

    public Controller(StorageI storage) {
        this.storage = storage;
    }

    public Controller() {
        this.storage = Storage.getStorage();
    }

    // Controller klassen er ikke længere en singleton
//
//    private static Controller controller;
//
//    public static Controller getController() {
//        if (controller == null) {
//            controller = new Controller();
//        }
//        return controller;
//    }
//
//    // getTestController skal bruges i testmetoder
//    public static Controller getTestController() {
//        return new Controller();
//    }
//
//    // Skal der være en getStorage() metode?
//    public Storage getStorage()
//    {
//        return storage;
//    }

    //------------------------------- Prisliste metoder -----------------------------------------

    /**
     * Opretter en prisliste hvor prislistenavn = "Ikke sat", prislisteNr = næste i rækken af numre
     * og arraylisten prislisteProdukter er tom. Prislisten tilføjes til storage.
     *
     * @return returnerer prislisten.
     */
    public Prisliste opretPrisliste() {
        Prisliste prisliste = new Prisliste();
        storage.addPrisliste(prisliste);
        return prisliste;
    }

    /**
     * Udfylder en prisliste med prislisteProdukter med deres nyPris sat til normalpriserne fra alle produkter i
     * storage.
     */
    public void udfyldPrislisteMedPrislisteProdukter(Prisliste prisliste) {
        if (prisliste.getPrislisteProdukter().size() > 0) {
            throw new IllegalArgumentException("Prislisten er allerede udfyldt");
        } else {
            for (Produktkategori produktkategori : storage.getAllProduktkategorier()) {
                for (Produkt produkt : produktkategori.getProdukter()) {
                    prisliste.createPrislisteProdukt(produkt.getNormalpris(), produkt);
                }
            }
        }
    }

    //------------------------------------- Produktkategori metoder ---------------------------------------

    /**
     * Opretter en produktkategori med en tom arrayliste af produkter og tilføjer den til storage.
     *
     * @return returnerer produktkategorien.
     */
    public Produktkategori opretProduktkategori(String produktkategorinavn) {
        Produktkategori produktkategori = new Produktkategori(produktkategorinavn);
        storage.addProduktkategori(produktkategori);
        return produktkategori;
    }

    /**
     * Finder en produktkategori ud fra et produkt.
     *
     * @return En produktkategori.
     */
    public Produktkategori findProduktKategori(Produkt produkt) {
        for (Produktkategori produktkategori : storage.getAllProduktkategorier()) {
            if (produktkategori.getProdukter().contains(produkt))
                return produktkategori;
        }
        return null;
    }

    //------------------------------------- Salg metoder ---------------------------------------

    /**
     * Opretter et salg og tilføjer det til storage hvor:
     * salgets salgPaabegyndtDato sættes til den nuværende dato
     * salgets salgAfsluttetDato sættes til null
     * salgets betalt sættes til false
     * salgets salgsNr sættes til det næste i rækken af salgsnumre
     *
     * @return returnerer salget.
     */
    public Salg opretSalg() {
        Salg salg = new Salg();
        storage.addSalg(salg);
        return salg;
    }

    /**
     * Henter fra storage alle salg der er under afvikling, dvs. salg hvor salgAfsluttetDato = null.
     *
     * @return arraylist med alle igangværende salg
     */
    public List<Salg> getIgangvaerendeSalg() {
        List<Salg> igangvaerendeListe = new ArrayList<>();
        for (Salg salg : storage.getAllSalg()) {
            if (salg.getSalgAfsluttetDato() == null) {
                igangvaerendeListe.add(salg);
            }
        }
        Collections.reverse(igangvaerendeListe);
        return igangvaerendeListe;
    }

    /**
     * Henter afsluttede salg.
     *
     * @return arraylist med alle afsluttede salg
     */
    public List<Salg> getAfsluttedeSalg() {
        List<Salg> afsluttedeListe = new ArrayList<>();
        for (Salg salg : storage.getAllSalg()) {
            if (salg.getSalgAfsluttetDato() != null) {
                afsluttedeListe.add(salg);
            }
        }
        Collections.reverse(afsluttedeListe);
        return afsluttedeListe;
    }
    //------------------------------------- Kunde metoder ---------------------------------------

    /**
     * Opretter en kunde hvor adresse, email og telefonNr sættes til "Ikke sat".
     * Kundens kundeNr sættes til det næste i rækken af kundenumre.
     * Kunden tilføjes til storage.
     *
     * @return returnerer kunden.
     */
    public Kunde opretKunde(String kundenavn) {
        Kunde kunde = new Kunde(kundenavn);
        storage.addKunde(kunde);
        return kunde;
    }

    /**
     * Finder salg som kunde har lavet.
     *
     * @return En liste med salg.
     */
    public List<Salg> findSalgPaaKunde(Kunde kunde) {
        List<Salg> salgPaaKunde = new ArrayList<>();

        for (Salg salg : storage.getAllSalg()) {
            if (salg.getKunde() == kunde)
                salgPaaKunde.add(salg);
        }
        return salgPaaKunde;
    }

    //----------------------------------- Metoder for oversigt med klippekortstatus ---------------------------

    public ArrayList<String> getOversigtKlippekortPeriode(LocalDate startdato, LocalDate slutDato) {
        ArrayList<String> oversigtKlippekortListe = new ArrayList<>();
        for (Salg salg : storage.getAllSalg()) {
            if (!(salg.getSalgAfsluttetDato() == null) &&
                    (salg.getSalgAfsluttetDato().isEqual(startdato) || salg.getSalgAfsluttetDato().isAfter(startdato))
                    &&
                    (salg.getSalgAfsluttetDato().isEqual(slutDato) || salg.getSalgAfsluttetDato().isBefore(slutDato))) {
                if (salg.getKunde() == null && salg.getBetalingsform() == Betalingsform.KLIPPEKORT) {
                    oversigtKlippekortListe.add("Salgsnr. " + salg.getSalgsNr() + " (fredagsbar)");
                    oversigtKlippekortListe.add(salg.samletPrisIAntalKlip()
                            + " klip (" + salg.getBetalingsform() + ")");

                    for (SalgsLinje salgsLinje : salg.getSalgsLinjer()) {
                        oversigtKlippekortListe.add("     " + findProduktKategori(salgsLinje.getProdukt()).
                                getProduktkategorinavn() + ", " + salgsLinje.getProdukt().getProduktnavn() + ", " +
                                salgsLinje.getProduktAntal() + " styk, " + salgsLinje.samletPrislistePris() + " kr.");
                    }
                    oversigtKlippekortListe.add("");
                }
            }
        }
        return oversigtKlippekortListe;
    }

    public int antalSolgteKlipPeriode(LocalDate startdato, LocalDate slutDato) {
        int antalSolgteKlip = 0;
        for (Salg salg : storage.getAllSalg()) {
            if ((salg.getSalgPaabegyndtDato().isAfter(startdato) || salg.getSalgPaabegyndtDato().isEqual(startdato))
                    && (salg.getSalgPaabegyndtDato().isBefore(slutDato) ||
                    salg.getSalgPaabegyndtDato().isEqual(slutDato))) {
                for (SalgsLinje salgsLinje : salg.getSalgsLinjer()) {
                    if (salgsLinje.getProdukt() instanceof Klippekort) {
                        antalSolgteKlip += salgsLinje.getProduktAntal() *
                                ((Klippekort) salgsLinje.getProdukt()).getAntalKlip();
                    }
                }
            }
        }
        return antalSolgteKlip;
    }

    /**
     * Returnerer antal forbrugte klip i en given periode. Dvs. for alle salg med betalingsformen KLIPPEKORT, udregnes
     * for alle salgets salgslinjer antal-klip-pris gange antal produkter.
     *
     * @param startdato Startdatoen for perioden
     * @param slutDato  Slutdatoen for perioden
     * @return Antal forbrugte klip
     */
    public int antalForbrugteKlipPeriode(LocalDate startdato, LocalDate slutDato) {
        int antalForbrugteKlip = 0;
        for (Salg salg : storage.getAllSalg()) {
            if ((salg.getSalgPaabegyndtDato().isAfter(startdato) || salg.getSalgPaabegyndtDato().isEqual(startdato))
                    && (salg.getSalgPaabegyndtDato().isBefore(slutDato) ||
                    salg.getSalgPaabegyndtDato().isEqual(slutDato))) {
                if (salg.getBetalingsform() == Betalingsform.KLIPPEKORT) {
                    for (SalgsLinje salgsLinje : salg.getSalgsLinjer()) {
                        int antalKlipPris = ((FredagsbarPrislisteProdukt) salg.getPrisliste().
                                findPrislisteProduktUdFraProdukt(salgsLinje.getProdukt())).getAntalKlipSomPris();
                        antalForbrugteKlip += salgsLinje.getProduktAntal() * antalKlipPris;
                    }
                }
            }
        }
        return antalForbrugteKlip;
    }

    //----------------------------------- Metoder for oversigt over dagssalg ----------------------------------

    public ArrayList<String> getOversigtDagssalg() {
        ArrayList<String> oversigtDagssalgListe = new ArrayList<>();
        for (Salg salg : storage.getAllSalg()) {
            if (!(salg.getSalgAfsluttetDato() == null) && salg.getSalgAfsluttetDato().isEqual(LocalDate.now())) {
                if (salg.getKunde() == null) {
                    if (salg.getBetalingsform() == Betalingsform.KLIPPEKORT) {
                        oversigtDagssalgListe.add("Salgsnr. " + salg.getSalgsNr() + " (fredagsbar)");
                        oversigtDagssalgListe.add(salg.samletPrisIAntalKlip()
                                + " klip (" + salg.getBetalingsform() + ")");
                    } else {
                        oversigtDagssalgListe.add("Salgsnr. " + salg.getSalgsNr() + " (fredagsbar)");
                        oversigtDagssalgListe.add(salg.samletPrisUdenPant()
                                + " kr. (" + salg.getBetalingsform() + ")");
                    }
                } else {
                    oversigtDagssalgListe.add("Salgsnr. " + salg.getSalgsNr() + " (andet salg)");
                    oversigtDagssalgListe.add(salg.samletPrisUdenPant() + " kr. uden pant ("
                            + salg.getBetalingsform() + ")");
                }

                for (SalgsLinje salgsLinje : salg.getSalgsLinjer()) {
                    oversigtDagssalgListe.add("     " + findProduktKategori(salgsLinje.getProdukt()).
                            getProduktkategorinavn() + ", " + salgsLinje.getProdukt().getProduktnavn() + ", " +
                            salgsLinje.getProduktAntal() + " styk, " + salgsLinje.samletPrislistePris() + " kr.");
                }
                oversigtDagssalgListe.add("");
            }
        }
        return oversigtDagssalgListe;
    }

    /**
     * Samlet indkomst for i dag (pant ikke medregnet)
     *
     * @return samlet indkomst for i dag
     */
    public double samletIndkomstDagssalg() {
        double samletIndkomstIDag = 0;
        for (Salg salg : storage.getAllSalg()) {
            if (!(salg.getSalgAfsluttetDato() == null) && salg.getSalgAfsluttetDato().isEqual(LocalDate.now())
                    && salg.getBetalingsform() != Betalingsform.KLIPPEKORT) {
                samletIndkomstIDag += salg.samletPrisUdenPant();
            }
        }
        return samletIndkomstIDag;
    }

    //---------------------- Metoder for oversigt over ikke afleverede produkter -----------------------

    public ArrayList<String> getOversigtIkkeAfleveredeProdukter() {
        ArrayList<String> oversigtIkkeAfleveredeProdukter = new ArrayList<>();
        for (Salg salg : storage.getAllSalg()) {
            if (salg.getSalgAfsluttetDato() == null && (!(salg.getKunde() == null))) {
                boolean tilbageleveresProduktUdenPant = false;
                for (SalgsLinje salgsLinje : salg.getSalgsLinjer()) {
                    if (salgsLinje.getProdukt() instanceof Anlaeg) {
                        tilbageleveresProduktUdenPant = true;
                        break;
                    }
                }
                if (tilbageleveresProduktUdenPant) {
                    oversigtIkkeAfleveredeProdukter.add("Salgsnr. " + salg.getSalgsNr() + ", "
                            + salg.getKunde().getKundenavn() + " (kundenr. " + salg.getKunde().getKundeNr() + "), "
                            + salg.getSalgPaabegyndtDato());
                    for (SalgsLinje salgsLinje : salg.getSalgsLinjer()) {
                        if (salgsLinje.getProdukt() instanceof Anlaeg
                                && (salgsLinje.getProduktAntal() -
                                ((TilbageleveresSalgsLinje) salgsLinje).getAntalTilbageleveret()) > 0) {
                            int antalIkkeAfleveret = salgsLinje.getProduktAntal() -
                                    ((TilbageleveresSalgsLinje) salgsLinje).getAntalTilbageleveret();

                            oversigtIkkeAfleveredeProdukter.add("     " + antalIkkeAfleveret + " stk. " +
                                    findProduktKategori(salgsLinje.getProdukt()).getProduktkategorinavn() + ", " +
                                    salgsLinje.getProdukt().getProduktnavn() + ", ikke afleveret endnu");
                        }
                    }
                    oversigtIkkeAfleveredeProdukter.add("");
                }
            }
        }
        return oversigtIkkeAfleveredeProdukter;
    }

    //----------------------------------------- Init storage -------------------------------------

    public void initStorage() {
        //Opretter produktgrupper

        Produktkategori flaskeoel = opretProduktkategori("Flasker");
        Produktkategori fadoel = opretProduktkategori("Fadøl");
        Produktkategori spiritus = opretProduktkategori("Spiritus");
        Produktkategori fustage = opretProduktkategori("Fustager");
        Produktkategori kulsyre = opretProduktkategori("Kulsyre");
        Produktkategori malt = opretProduktkategori("Malt");
        Produktkategori beklaedning = opretProduktkategori("Beklædning");
        Produktkategori anlaeg = opretProduktkategori("Anlæg");
        Produktkategori glas = opretProduktkategori("Glas");
        Produktkategori rundvisning = opretProduktkategori("Rundvisninger");
        Produktkategori sampakning = opretProduktkategori("Sampakninger");
        Produktkategori klippekort = opretProduktkategori("Klippekort");

        //Opretter klippekort
        Produkt klippekort4Klip = klippekort.createKlippekortProdukt("4 Klip", 130,
                false, 4);

//        //Opretter flaskeøl
        Produkt flaskeKlosterbryg = flaskeoel.createDrikkevareProdukt("Klosterbryg", 70.0,
                false, 0.6);
        Produkt flaskeSweetGeorgiaBrown = flaskeoel.createDrikkevareProdukt("Sweet Georgia Brown",
                70.0, false, 0.6);
        Produkt flaskeEkstraPilsner = flaskeoel.createDrikkevareProdukt("Ekstra Pilsner", 70.0,
                false, 0.6);
        Produkt flaskeCelebration = flaskeoel.createDrikkevareProdukt("Celebration", 70.0,
                false, 0.6);
        Produkt flaskeBlondie = flaskeoel.createDrikkevareProdukt("Blondie", 70.0,
                false, 0.6);
        Produkt flaskeForaarsbryg = flaskeoel.createDrikkevareProdukt("Forårsbryg", 70.0,
                false, 0.6);
        Produkt flaskeIndiaPaleAle = flaskeoel.createDrikkevareProdukt("India Pale Ale", 70.0,
                false, 0.6);
        Produkt flaskeJulebryg = flaskeoel.createDrikkevareProdukt("Julebryg", 70.0,
                false, 0.6);
        Produkt flaskeJuletoenden = flaskeoel.createDrikkevareProdukt("Jultønden", 70.0,
                false, 0.6);
        Produkt flaskeOldStrongAle = flaskeoel.createDrikkevareProdukt("Old Strong Ale", 70.0,
                false, 0.6);
        Produkt flaskeFregattenJylland = flaskeoel.createDrikkevareProdukt("Fregatten Jylland",
                70.0, false, 0.6);
        Produkt flaskeImperialStout = flaskeoel.createDrikkevareProdukt("Imperial Stout", 70.0,
                false, 0.6);
        Produkt flaskeTribute = flaskeoel.createDrikkevareProdukt("Tribute", 70.0,
                false, 0.6);
        Produkt flaskeBlackMonster = flaskeoel.createDrikkevareProdukt("Black Monster", 70.0,
                false, 0.6);

//        //Opretter fadøl
        Produkt fadKlosterbryg = fadoel.createDrikkevareProdukt("Klosterbryg", 38.0,
                false, 0.4);
        Produkt fadJazzClassic = fadoel.createDrikkevareProdukt("Jazz Classic", 38.0,
                false, 0.4);
        Produkt fadEkstraPilsner = fadoel.createDrikkevareProdukt("Ekstra Pilsner", 38.0,
                false, 0.4);
        Produkt fadCelebration = fadoel.createDrikkevareProdukt("Celebration", 38.0,
                false, 0.4);
        Produkt fadBlondie = fadoel.createDrikkevareProdukt("Blondie", 38.0, false,
                0.4);
        Produkt fadForaarsbryg = fadoel.createDrikkevareProdukt("Forårsbryg", 38.0,
                false, 0.4);
        Produkt fadIndiaPaleAle = fadoel.createDrikkevareProdukt("India Pale Ale", 38.0,
                false, 0.4);
        Produkt fadJulebryg = fadoel.createDrikkevareProdukt("Julebryg", 38.0, false,
                0.4);
        Produkt fadImperialStout = fadoel.createDrikkevareProdukt("Imperial Stout", 38.0,
                false, 0.4);
        Produkt fadSpecial = fadoel.createDrikkevareProdukt("Special", 38.0, false,
                0.4);
        Produkt aeblebrus = fadoel.createDrikkevareProdukt("Æblebrus", 15.0, false,
                0.4);
        Produkt chips = fadoel.createDrikkevareProdukt("Chips", 10.0, false,
                0.4);
        Produkt peanuts = fadoel.createDrikkevareProdukt("Peanuts", 15.0, false,
                0.4);
        Produkt cola = fadoel.createDrikkevareProdukt("Cola", 15.0, false,
                0.4);
        Produkt nikoline = fadoel.createDrikkevareProdukt("Nikoline", 15.0, false,
                0.4);
        Produkt sevenUp = fadoel.createDrikkevareProdukt("7-up", 15.0, false,
                0.4);
        Produkt vand = fadoel.createDrikkevareProdukt("Vand", 10.0, false, 0.4);
        Produkt oelpoelser = fadoel.createDrikkevareProdukt("Ølpølser", 30.0, false,
                0.4);

//        //Opretter spiritus
        Produkt whiskey50cl45Procent = spiritus.createDrikkevareProdukt("Whisky 45% 50cl rør",
                599.0, false, 0.50);
        Produkt whiskey4cl45Procent = spiritus.createDrikkevareProdukt("Whisky 4cl", 50.0,
                false, 0.04);
        Produkt whiskey50cl43Procent = spiritus.createDrikkevareProdukt("Whisky 43% 50cl rør",
                499.0, false, 0.50);
        Produkt udenEgesplint = spiritus.createDrikkevareProdukt("Whisky 40% 50cl u/ egesplint",
                300.0, false, 0.50);
        Produkt medEgesplint = spiritus.createDrikkevareProdukt("Whisky 40% 50cl m/ egesplint",
                350.0, false, 0.50);
        Produkt toWhiskyGlasMedBrikker = spiritus.createProdukt("2*whisky glas + brikker",
                80.0, false);
        Produkt liquorOfAarhus = spiritus.createDrikkevareProdukt("Liquor of Aarhus",
                175.0, false, 0.50);
        Produkt lyngGin50cl = spiritus.createDrikkevareProdukt("Lyng gin 50 cl", 350.0,
                false, 0.50);
        Produkt lyngGin4cl = spiritus.createDrikkevareProdukt("Lyng gin 4 cl", 40.0,
                false, 0.04);

        //Opretter fustage
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                true, 20.0);
        Produkt fustageJazzClassic = fustage.createDrikkevareProdukt("Jazz Classic", 625.0,
                true, 25.0);
        Produkt fustageEkstraPilsner = fustage.createDrikkevareProdukt("Ekstra Pilsner", 575.0,
                true, 25.0);
        Produkt fustageCelebration = fustage.createDrikkevareProdukt("Celebration", 775.0,
                true, 20.0);
        Produkt fustageBlondie = fustage.createDrikkevareProdukt("Blondie", 700.0,
                true, 25.0);
        Produkt fustageForaarsbryg = fustage.createDrikkevareProdukt("Forårsbryg", 775.0,
                true, 20.0);
        Produkt fustageIndiaPaleAle = fustage.createDrikkevareProdukt("India Pale Ale", 775.0,
                true, 20.0);
        Produkt fustageJulebryg = fustage.createDrikkevareProdukt("Julebryg", 775.0,
                true, 20.0);
        Produkt fustageImperialStout = fustage.createDrikkevareProdukt("Imperial Stout", 775.0,
                true, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Fustagepant", 200.0, true,
                PantType.FUSTAGEPANT);

        //Sætter pant for pantprodukter
        ((Drikkevare) fustageKlosterbryg).setPant((Pant) fustagePant);
        ((Drikkevare) fustageJazzClassic).setPant((Pant) fustagePant);
        ((Drikkevare) fustageEkstraPilsner).setPant((Pant) fustagePant);
        ((Drikkevare) fustageCelebration).setPant((Pant) fustagePant);
        ((Drikkevare) fustageBlondie).setPant((Pant) fustagePant);
        ((Drikkevare) fustageForaarsbryg).setPant((Pant) fustagePant);
        ((Drikkevare) fustageIndiaPaleAle).setPant((Pant) fustagePant);
        ((Drikkevare) fustageJulebryg).setPant((Pant) fustagePant);
        ((Drikkevare) fustageImperialStout).setPant((Pant) fustagePant);

        //Opretter kulsyre
        Produkt seksKg = kulsyre.createKilovareProdukt("Kulsyre", 400, true,
                6000);
        Produkt kulsyrePant = kulsyre.createPantProdukt("Kulsyrepant", 1000.0, true,
                PantType.KULSYREPANT);

        //Sætter pant for pantprodukter
        ((Kilovare) seksKg).setPant((Pant) kulsyrePant);

//        Produkt fireKg = kulsyre.createKilovareProdukt("Kulsyre",400,true,4000);
//        Produkt tiKg = kulsyre.createKilovareProdukt("Kulsyre",400,true,10000);

        //Opretter malt
        Produkt femOgTyveKg = malt.createKilovareProdukt("25 kg sæk", 300.0, false,
                25000);

        //Opretter beklædning
        Produkt tShirt = beklaedning.createProdukt("t-shirt", 70.0, false);
        Produkt polo = beklaedning.createProdukt("polo", 100.0, false);
        Produkt cap = beklaedning.createProdukt("cap", 30.0, false);

        //Opretter anlæg
        Produkt enHane = anlaeg.createAnlaegProdukt("Én hane", 250.0, true,
                1, true);
        Produkt toHaner = anlaeg.createAnlaegProdukt("To haner", 400.0, true,
                2, true);
        Produkt barMedFlereHaner = anlaeg.createAnlaegProdukt("Bar med flere haner", 400.0,
                true, 2, true);
        Produkt levering = anlaeg.createProdukt("Levering", 500.0, false);
        Produkt krus = anlaeg.createProdukt("Krus", 60.0, false);

        //Opretter glas
        Produkt glasAlleStoerrelser = glas.createProdukt("Glas (uanset størrelse)", 15.0,
                false);

        //Opretter rundvisninger
        Produkt rundvisningProdukt = rundvisning.createProdukt("Pr. person pr. dag", 100.0,
                false);

        //Opretter sampakninger
        Produkt gaveaeske1 = sampakning.createProdukt("Gaveæske (2 øl, 2 glas)", 110.0,
                false);
        Produkt gaveaeske2 = sampakning.createProdukt("Gaveæske (4 øl)", 140.0, false);
        Produkt traekasse1 = sampakning.createProdukt("Trækasse (6 øl)", 260.0, false);
        Produkt gavekurv = sampakning.createProdukt("Gavekurv (6 øl, 2 glas)", 260.0,
                false);
        Produkt traekasse2 = sampakning.createProdukt("Trækasse (6 øl, 6 glas)", 350.0,
                false);
        Produkt traekasse3 = sampakning.createProdukt("Trækasse (12 øl)", 410.0,
                false);
        Produkt papkasse = sampakning.createProdukt("Papkasse (12 øl)", 370.0, false);


        //Opretter kunder
        Kunde kunde1 = opretKunde("Hans");
        Kunde kunde2 = opretKunde("Klaus");
        Kunde kunde3 = opretKunde("Dorthe");
        kunde3.setAdresse("Æblevej 123");
        kunde3.setTelefonNr("12345678");
        kunde3.setEmail("eks@eks.dk");


        //Opretter prisliste for fredagsbar
        Prisliste fredagsbar = opretPrisliste();
        fredagsbar.setPrislistenavn("Fredagsbar");

        // Tildeler fredagsbarspris til klippekort
        fredagsbar.createPrislisteProdukt(130.0, klippekort4Klip);

        // Tildeler fredagsbarspris til flaskeøl
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeKlosterbryg, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeSweetGeorgiaBrown, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeEkstraPilsner, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeCelebration, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeBlondie, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeForaarsbryg, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeIndiaPaleAle, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeJulebryg, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeJuletoenden, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeOldStrongAle, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeFregattenJylland, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeImperialStout, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(70.0, flaskeTribute, 2);
        fredagsbar.createFredagsbarPrislisteProdukt(100.0, flaskeBlackMonster, 3);
        //Tildeler fredagsbarspris til fadøl
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, fadKlosterbryg, 1);
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, fadJazzClassic, 1);
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, fadEkstraPilsner, 1);
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, fadCelebration, 1);
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, fadBlondie, 1);
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, fadForaarsbryg, 1);
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, fadIndiaPaleAle, 1);
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, fadJulebryg, 1);
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, fadImperialStout, 1);
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, fadSpecial, 1);
        fredagsbar.createFredagsbarPrislisteProdukt(38.0, oelpoelser, 1);
        //Tildeler butikspris til Spiritus
        fredagsbar.createPrislisteProdukt(599.0, whiskey50cl45Procent);
        fredagsbar.createPrislisteProdukt(50.0, whiskey4cl45Procent);
        fredagsbar.createPrislisteProdukt(499.0, whiskey50cl43Procent);
        fredagsbar.createPrislisteProdukt(300.0, udenEgesplint);
        fredagsbar.createPrislisteProdukt(350.0, medEgesplint);
        fredagsbar.createPrislisteProdukt(80.0, toWhiskyGlasMedBrikker);
        fredagsbar.createPrislisteProdukt(175.0, liquorOfAarhus);
        fredagsbar.createPrislisteProdukt(350.0, lyngGin50cl);
        fredagsbar.createPrislisteProdukt(40.0, lyngGin4cl);
        //Tildeler fredagsbarspris til beklædning
        fredagsbar.createPrislisteProdukt(70.0, tShirt);
        fredagsbar.createPrislisteProdukt(100.0, polo);
        fredagsbar.createPrislisteProdukt(30.0, cap);
        //Tildeler butikspris til gaveæsker
        fredagsbar.createPrislisteProdukt(110.0, gaveaeske1);
        fredagsbar.createPrislisteProdukt(140.0, gaveaeske2);
        fredagsbar.createPrislisteProdukt(260.0, traekasse1);
        fredagsbar.createPrislisteProdukt(260.0, gavekurv);
        fredagsbar.createPrislisteProdukt(350.0, traekasse2);
        fredagsbar.createPrislisteProdukt(410.0, traekasse3);
        fredagsbar.createPrislisteProdukt(370.0, papkasse);

        //Opretter prisliste for butik
        Prisliste butik = opretPrisliste();
        butik.setPrislistenavn("Butik");

        butik.createPrislisteProdukt(130.0, klippekort4Klip);

        butik.createPrislisteProdukt(36.0, flaskeKlosterbryg);
        butik.createPrislisteProdukt(36.0, flaskeSweetGeorgiaBrown);
        butik.createPrislisteProdukt(36.0, flaskeEkstraPilsner);
        butik.createPrislisteProdukt(36.0, flaskeCelebration);
        butik.createPrislisteProdukt(36.0, flaskeBlondie);
        butik.createPrislisteProdukt(36.0, flaskeForaarsbryg);
        butik.createPrislisteProdukt(36.0, flaskeIndiaPaleAle);
        butik.createPrislisteProdukt(36.0, flaskeJulebryg);
        butik.createPrislisteProdukt(36.0, flaskeJuletoenden);
        butik.createPrislisteProdukt(36.0, flaskeOldStrongAle);
        butik.createPrislisteProdukt(36.0, flaskeFregattenJylland);
        butik.createPrislisteProdukt(36.0, flaskeImperialStout);
        butik.createPrislisteProdukt(36.0, flaskeTribute);
        butik.createPrislisteProdukt(60.0, flaskeBlackMonster);

        butik.createPrislisteProdukt(599.0, whiskey50cl45Procent);
        butik.createPrislisteProdukt(499.0, whiskey50cl43Procent);
        butik.createPrislisteProdukt(300.0, udenEgesplint);
        butik.createPrislisteProdukt(350.0, medEgesplint);
        butik.createPrislisteProdukt(80.0, toWhiskyGlasMedBrikker);
        butik.createPrislisteProdukt(175.0, liquorOfAarhus);
        butik.createPrislisteProdukt(350.0, lyngGin50cl);

        butik.createPrislisteProdukt(775.0, fustageKlosterbryg);
        butik.createPrislisteProdukt(625.0, fustageJazzClassic);
        butik.createPrislisteProdukt(575.0, fustageEkstraPilsner);
        butik.createPrislisteProdukt(775.0, fustageCelebration);
        butik.createPrislisteProdukt(700.0, fustageBlondie);
        butik.createPrislisteProdukt(775.0, fustageForaarsbryg);
        butik.createPrislisteProdukt(775.0, fustageIndiaPaleAle);
        butik.createPrislisteProdukt(775.0, fustageJulebryg);
        butik.createPrislisteProdukt(775.0, fustageImperialStout);
        butik.createPrislisteProdukt(200.0, fustagePant);

        butik.createPrislisteProdukt(400.0, seksKg);
        butik.createPrislisteProdukt(1000.0, kulsyrePant);

        butik.createPrislisteProdukt(300.0, femOgTyveKg);

        butik.createPrislisteProdukt(70.0, tShirt);
        butik.createPrislisteProdukt(100.0, polo);
        butik.createPrislisteProdukt(30.0, cap);

        butik.createPrislisteProdukt(250.0, enHane);
        butik.createPrislisteProdukt(400.0, toHaner);
        butik.createPrislisteProdukt(500.0, barMedFlereHaner);
        butik.createPrislisteProdukt(500.0, levering);
        butik.createPrislisteProdukt(60.0, krus);

        butik.createPrislisteProdukt(250.0, glasAlleStoerrelser);

        butik.createPrislisteProdukt(110.0, gaveaeske1);
        butik.createPrislisteProdukt(140.0, gaveaeske2);
        butik.createPrislisteProdukt(260.0, traekasse1);
        butik.createPrislisteProdukt(260.0, gavekurv);
        butik.createPrislisteProdukt(350.0, traekasse2);
        butik.createPrislisteProdukt(410.0, traekasse3);
        butik.createPrislisteProdukt(370.0, papkasse);

        butik.createPrislisteProdukt(100.0, rundvisningProdukt);

        //Oprettelse af salg

        Salg salg1 = opretSalg();
        salg1.setPrisliste(fredagsbar);
        salg1.opretEllerTilfoejTilSalgsLinje(5, fadKlosterbryg);
        salg1.betalOgAfslutSalg(Betalingsform.MOBILEPAY);

        Salg salg2 = opretSalg();
        salg2.setPrisliste(butik);
        salg2.opretEllerTilfoejTilSalgsLinje(5, fustageCelebration);
        salg2.setKunde(kunde3);
        salg2.betalPantSalgslinjer(Betalingsform.MOBILEPAY);

        Salg salg3 = opretSalg();
        salg3.setPrisliste(fredagsbar);
        salg3.opretEllerTilfoejTilSalgsLinje(1, flaskeIndiaPaleAle);
        salg3.opretEllerTilfoejTilSalgsLinje(1, fadBlondie);
        salg3.betalOgAfslutSalg(Betalingsform.KLIPPEKORT);

        Salg salg4 = opretSalg();
        salg4.setPrisliste(fredagsbar);
        salg4.opretEllerTilfoejTilSalgsLinje(1, fadCelebration);
        salg4.opretEllerTilfoejTilSalgsLinje(2, fadForaarsbryg);
        salg4.opretEllerTilfoejTilSalgsLinje(3, oelpoelser);
        salg4.opretEllerTilfoejTilSalgsLinje(2, klippekort4Klip);
        salg4.opretEllerTilfoejTilSalgsLinje(2, tShirt);
        salg4.betalOgAfslutSalg(Betalingsform.ANDEN_BETALINGSFORM);

        Salg salg5 = opretSalg();
        salg5.setPrisliste(butik);
        List<SalgsLinje> salgsLinjeListeSalg5 = salg5.opretEllerTilfoejTilSalgsLinje(3,
                fustageImperialStout);
        ((TilbageleveresSalgsLinje) salgsLinjeListeSalg5.get(1)).setAntalTilbageleveret(3);
        salg5.setKunde(kunde3);
        salg5.betalPantSalgslinjer(Betalingsform.MOBILEPAY);
        salg5.betalOgAfslutSalg(Betalingsform.BETALINGSKORT);

        Salg salg6 = opretSalg();
        salg6.setPrisliste(fredagsbar);
        salg6.opretEllerTilfoejTilSalgsLinje(2, fadBlondie);
        salg6.betalOgAfslutSalg(Betalingsform.KLIPPEKORT);

        Salg salg7 = opretSalg();
        salg7.setPrisliste(butik);
        List<SalgsLinje> salgsLinjeListeSalg7 = salg7.opretEllerTilfoejTilSalgsLinje(3,
                fustageImperialStout);
        ((TilbageleveresSalgsLinje) salgsLinjeListeSalg7.get(1)).setAntalTilbageleveret(3);
        salg7.setKunde(kunde3);
        salg7.betalPantSalgslinjer(Betalingsform.MOBILEPAY);

        Salg salg8 = opretSalg();
        salg8.setPrisliste(butik);
        List<SalgsLinje> salgsLinjeListeSalg8 = salg8.opretEllerTilfoejTilSalgsLinje(3,
                fustageImperialStout);
        ((TilbageleveresSalgsLinje) salgsLinjeListeSalg8.get(1)).setAntalTilbageleveret(0);
        salgsLinjeListeSalg8 = salg8.opretEllerTilfoejTilSalgsLinje(1, toHaner);
        ((TilbageleveresSalgsLinje) salgsLinjeListeSalg8.get(0)).setAntalTilbageleveret(1);
        salg8.setKunde(kunde3);
        salg8.betalPantSalgslinjer(Betalingsform.KONTANT);
        salg8.betalOgAfslutSalg(Betalingsform.BANKOVERFOERSEL);

        Salg salg9 = opretSalg();
        salg9.setPrisliste(butik);
        salg9.setKunde(kunde2);
        salg9.opretEllerTilfoejTilSalgsLinje(1, seksKg);
        salg9.opretEllerTilfoejTilSalgsLinje(1, fustageImperialStout);
        salg9.opretEllerTilfoejTilSalgsLinje(1, fustageImperialStout);
        salg9.opretEllerTilfoejTilSalgsLinje(1, fustageIndiaPaleAle);
        salg9.opretEllerTilfoejTilSalgsLinje(1, barMedFlereHaner);
        salg9.opretEllerTilfoejTilSalgsLinje(2, tShirt);
        salg9.betalPantSalgslinjer(Betalingsform.MOBILEPAY);
        salg9.betalOgAfslutSalg(Betalingsform.BANKOVERFOERSEL);

        Salg salg10 = opretSalg();
        salg10.setPrisliste(fredagsbar);
        salg10.opretEllerTilfoejTilSalgsLinje(1, fadCelebration);
        salg10.opretEllerTilfoejTilSalgsLinje(1, fadForaarsbryg);
        salg10.opretEllerTilfoejTilSalgsLinje(2, oelpoelser);
        salg10.opretEllerTilfoejTilSalgsLinje(1, klippekort4Klip);
        salg10.opretEllerTilfoejTilSalgsLinje(2, tShirt);
        salg10.betalOgAfslutSalg(Betalingsform.ANDEN_BETALINGSFORM);

        Salg salg11 = opretSalg();
        salg11.setPrisliste(butik);
        salg11.setKunde(kunde1);
        salg11.opretEllerTilfoejTilSalgsLinje(1, seksKg);
        salg11.opretEllerTilfoejTilSalgsLinje(1, fustageImperialStout);
        salg11.opretEllerTilfoejTilSalgsLinje(1, fustageImperialStout);
        salg11.opretEllerTilfoejTilSalgsLinje(1, fustageIndiaPaleAle);
        List<SalgsLinje> salgsLinjeListeSalg11 = salg11.opretEllerTilfoejTilSalgsLinje(2,
                barMedFlereHaner);
        ((TilbageleveresSalgsLinje) salgsLinjeListeSalg11.get(0)).setAntalTilbageleveret(0);
        salg11.betalPantSalgslinjer(Betalingsform.KONTANT);

        Salg salg12 = opretSalg();
        salg12.setPrisliste(butik);
        salg12.setKunde(kunde1);
        salg12.opretEllerTilfoejTilSalgsLinje(1, seksKg);
        salg12.opretEllerTilfoejTilSalgsLinje(1, fustageImperialStout);
        salg12.opretEllerTilfoejTilSalgsLinje(1, fustageImperialStout);
        salg12.opretEllerTilfoejTilSalgsLinje(1, fustageIndiaPaleAle);
        salg12.opretEllerTilfoejTilSalgsLinje(2, barMedFlereHaner);
        salg12.betalPantSalgslinjer(Betalingsform.MOBILEPAY);
    }

}
