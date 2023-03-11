package storage;

import application.model.Kunde;
import application.model.Prisliste;
import application.model.Produktkategori;
import application.model.Salg;

import java.util.ArrayList;
import java.util.List;

public class Storage implements StorageI {
    private final List<Produktkategori> produktkategorier;
    private final List<Prisliste> prislister;
    private final List<Salg> alleSalg;
    private final List<Kunde> kunder;

    //--------------------------------- Storage er nu lavet til en singleton ------------------------------------

    private static Storage storage;

    private Storage() {
        produktkategorier = new ArrayList<>();
        prislister = new ArrayList<>();
        alleSalg = new ArrayList<>();
        kunder = new ArrayList<>();
    }

    public static Storage getStorage() {
        if (storage == null)
            storage = new Storage();
        return storage;
    }

    public static Storage getTestStorage() {
        return new Storage();
    }

    //--------------------------------- Produktkategorier ---------------------------------------------

    /**
     * Returnerer en liste med alle gemte produktkategorier fra storage
     */
    @Override
    public List<Produktkategori> getAllProduktkategorier() {
        return new ArrayList<>(produktkategorier);
    }

    /**
     * Gemmer produktkategori i storage
     */
    @Override
    public void addProduktkategori(Produktkategori produktkategori) {
        if (!produktkategorier.contains(produktkategori)) {
            produktkategorier.add(produktkategori);
        }
    }

    /**
     * Sletter produktkategori fra storage og fortæller om produktkategorien var i storage.
     *
     * @param produktkategori af typen Produktkategori
     * @return true hvis produktkategorier i storage indeholder produktkategorien, false hvis ikke
     */
    @Override
    public boolean deleteProduktkategori(Produktkategori produktkategori) {
        boolean indeholderProduktkategori = produktkategorier.remove(produktkategori);
        return indeholderProduktkategori;
    }

    //------------------------------- Prislister --------------------------------------------

    /**
     * Returnerer en liste med alle gemte prislister i storage
     */
    @Override
    public List<Prisliste> getAllPrislister() {
        return new ArrayList<>(prislister);
    }

    /**
     * Gemmer prisliste i storage
     */
    @Override
    public void addPrisliste(Prisliste prisliste) {
        if (!prislister.contains(prisliste)) {
            prislister.add(prisliste);
        }
    }

    /**
     * Sletter prisliste fra storage og fortæller om prislisten var i storage.
     *
     * @param prisliste af typen Prisliste
     * @return true hvis prislister i storage indeholder prislisten, false hvis ikke
     */
    @Override
    public boolean deletePrisListe(Prisliste prisliste) {
        boolean indeholderPrisliste = prislister.remove(prisliste);
        return indeholderPrisliste;
    }

    //------------------------------- Salg --------------------------------------------

    /**
     * Returnerer en liste med alle gemte salg i storage
     */
    @Override
    public List<Salg> getAllSalg() {
        return new ArrayList<>(alleSalg);
    }

    /**
     * Gemmer salg i storage
     */
    @Override
    public void addSalg(Salg salg) {
        if (!alleSalg.contains(salg)) {
            alleSalg.add(salg);
        }
    }

    /**
     * Sletter salg fra storage og fortæller om salget var i storage.
     *
     * @param salg af typen Salg
     * @return true hvis alleSalg i storage indeholder salget, false hvis ikke
     */
    @Override
    public boolean deleteSalg(Salg salg) {
        boolean indeholderSalg = alleSalg.remove(salg);
        return indeholderSalg;
    }
    //------------------------------- Kunde --------------------------------------------

    /**
     * Returnerer en liste med alle gemte kunder i storage
     */
    @Override
    public List<Kunde> getAllKunder() {
        return new ArrayList<>(kunder);
    }

    /**
     * Gemmer kunde i storage
     */
    @Override
    public void addKunde(Kunde kunde) {
        if (!kunder.contains(kunde)) {
            kunder.add(kunde);
        }
    }

    /**
     * Sletter kunde fra storage og fortæller om kunden var i storage.
     *
     * @param kunde af typen Kunde
     * @return true hvis kunder i storage indeholder kunden, false hvis ikke
     */
    @Override
    public boolean deleteKunde(Kunde kunde) {
        boolean indeholderKunde = kunder.remove(kunde);
        return indeholderKunde;
    }
}
