package application.model;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProduktkategoriTest
{
    //--------------------------------- createProdukt ------------------------------------
    @Order(1)
    @Test
    void createProdukt_normalpris0()
    {
        //Arrange
        Produktkategori produktkategori = new Produktkategori("Flaske");
        String expectedNavn = "Klosterbryg";
        double expectedNormalpris = 0.00;

        //Act
        Produkt produkt = produktkategori.createDrikkevareProdukt("Klosterbryg", 0.00,
                false, 0.6);

        //Assert
        assertEquals(expectedNavn, produkt.getProduktnavn());
        assertEquals(expectedNormalpris, produkt.getNormalpris());
    }

    @Order(2)
    @Test
    void createProdukt_normalpris1()
    {
        //Arrange
        Produktkategori produktkategori = new Produktkategori("Flaske");
        String expectedNavn = "Ekstra Pilsner";
        double expectedNormalpris = 1.00;

        //Act
        Produkt produkt = produktkategori.createDrikkevareProdukt("Ekstra Pilsner", 1.00,
                false, 0.6);

        //Assert
        assertEquals(expectedNavn, produkt.getProduktnavn());
        assertEquals(expectedNormalpris, produkt.getNormalpris());
    }

    @Order(3)
    @Test
    void createProdukt_forbindelseFraProduktkategoriTilProdukt()
    {
        //Arrange
        Produktkategori produktkategori = new Produktkategori("Flaske");

        //Act
        Produkt produkt = produktkategori.createDrikkevareProdukt("Ekstra Pilsner", 1.00,
                false, 0.6);

        //Assert
        assertTrue(produktkategori.getProdukter().contains(produkt));
    }
}