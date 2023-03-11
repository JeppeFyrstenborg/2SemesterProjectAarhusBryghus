package storage;

import application.model.Kunde;
import application.model.Prisliste;
import application.model.Produktkategori;
import application.model.Salg;

import java.util.List;

public interface StorageI
{
    List<Produktkategori> getAllProduktkategorier();

    void addProduktkategori(Produktkategori produktkategori);

    boolean deleteProduktkategori(Produktkategori produktkategori);

    List<Prisliste> getAllPrislister();

    void addPrisliste(Prisliste prisliste);

    boolean deletePrisListe(Prisliste prisliste);

    List<Salg> getAllSalg();

    void addSalg(Salg salg);

    boolean deleteSalg(Salg salg);

    List<Kunde> getAllKunder();

    void addKunde(Kunde kunde);

    boolean deleteKunde(Kunde kunde);
}
