package application.model;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PrislisteTest {
    //--------------------------------- Prisliste(constructor) ------------------------------------
    @Order(1)
    @Test
    void Prisliste_naestePrislisteNrSatTil1() {
        //Arrange
        String expectedNavn = "Ikke sat";
        int expectedPrislisteNr = 1;

        //Act
        Prisliste pl = new Prisliste();

        //Assert
        assertEquals(expectedNavn, pl.getPrislistenavn());
        assertEquals(expectedPrislisteNr, pl.getPrislisteNr());
    }

    @Order(2)
    @Test
    void Prisliste_naestePrislisteNrSatTil4() {
        //Arrange
        new Prisliste();
        new Prisliste();
        new Prisliste();
        String expectedNavn = "Ikke sat";
        int expectedPrislisteNr = 5; //5, da den statiske attribut er blevet brugt 1 gang i sidste testmetode

        //Act
        Prisliste pl = new Prisliste();

        //Assert
        assertEquals(expectedNavn, pl.getPrislistenavn());
        assertEquals(expectedPrislisteNr, pl.getPrislisteNr());
    }


    //--------------------------------- Prisliste(getPrislisteProdukter) ------------------------------------
    @Order(3)
    @Test
    void getPrislisteProdukterINyListe() {
        //Arrange
        Prisliste pl = new Prisliste();
        //Act
        ArrayList<PrislisteProdukt> temp2 = new ArrayList<>(pl.getPrislisteProdukter());
        //Assert
        assertNotSame(temp2, pl.getPrislisteProdukter());
    }

    //--------------------------------- Prisliste(setPrislisteNavn) ------------------------------------

    @Order(4)
    @Test
    void saetterNavn() {
        //Arrange
        Prisliste pl = new Prisliste();

        String nytNavn = "Lørdagsbar";
        //Act
        pl.setPrislistenavn(nytNavn);
        //Assert
        assertEquals(nytNavn, pl.getPrislistenavn());
    }

    //--------------------------------- Prisliste(createPrislisteProdukt) ------------------------------------

    @Order(5)
    @Test
    void etPrislisteProduktOprettet() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);
        Produkt produkt2 = new Produkt("Klosterbryg", 36.0, false);
        Produkt produkt3 = new Produkt("Juleøl", 36.0, false);
        Produkt produkt4 = new Produkt("Forårsbryg", 36.0, false);
        Produkt produkt5 = new Produkt("Celebration", 36.0, false);

        //Act
        pl.createPrislisteProdukt(45.0, produkt1);
        //Assert
        assertEquals(1, pl.getPrislisteProdukter().size());
        assertEquals(produkt1, pl.getPrislisteProdukter().get(0).getProdukt());
        assertEquals(45.0, pl.getPrislisteProdukter().get(0).getNyPris());
    }

    @Order(6)
    @Test
    void femPrislisteProdukterOprettet() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);
        Produkt produkt2 = new Produkt("Klosterbryg", 36.0, false);
        Produkt produkt3 = new Produkt("Juleøl", 36.0, false);
        Produkt produkt4 = new Produkt("Forårsbryg", 36.0, false);
        Produkt produkt5 = new Produkt("Celebration", 36.0, false);

        //Act
        pl.createPrislisteProdukt(45.0, produkt1);
        pl.createPrislisteProdukt(45.0, produkt2);
        pl.createPrislisteProdukt(45.0, produkt3);
        pl.createPrislisteProdukt(45.0, produkt4);
        pl.createPrislisteProdukt(50.0, produkt5);
        //Assert
        assertEquals(5, pl.getPrislisteProdukter().size());
        assertEquals(produkt5, pl.getPrislisteProdukter().get(4).getProdukt());
        assertEquals(50.0, pl.getPrislisteProdukter().get(4).getNyPris());
    }

    @Order(7)
    @Test
    void nulPrislisteProduktOprettetMedProduktNull() {
        //Arrange
        Prisliste pl = new Prisliste();
        //Act
        Exception exception = assertThrows(NullPointerException.class, () ->
                pl.createPrislisteProdukt(45.0, null));
        // Assert
        assertTrue(exception.getMessage().contains("Produkt må ikke være null!"));
    }

    //------------------------ Prisliste(create FredagsbarPrislisteProdukt) --------------------------

    @Order(8)
    @Test
    void etFredagsbarPrislisteProduktOprettet() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);

        //Act
        pl.createFredagsbarPrislisteProdukt(70.0, produkt1, 1);

        //Assert
        assertEquals(1, pl.getPrislisteProdukter().size());
        assertEquals(produkt1, pl.getPrislisteProdukter().get(0).getProdukt());
        assertEquals(70.0, pl.getPrislisteProdukter().get(0).getNyPris());
        assertTrue(PrislisteProdukt.class.isAssignableFrom(pl.getPrislisteProdukter().get(0).getClass()));
    }

    @Order(9)
    @Test
    void femFredagsbarPrislisteProdukterOprettet() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);
        Produkt produkt2 = new Produkt("Klosterbryg", 36.0, false);
        Produkt produkt3 = new Produkt("Juleøl", 36.0, false);
        Produkt produkt4 = new Produkt("Forårsbryg", 36.0, false);
        Produkt produkt5 = new Produkt("Celebration", 36.0, false);

        var eks = new PrislisteProdukt(45.0, produkt1);

        //Act
        pl.createFredagsbarPrislisteProdukt(70.0, produkt1, 1);
        pl.createFredagsbarPrislisteProdukt(70.0, produkt2, 1);
        pl.createFredagsbarPrislisteProdukt(70.0, produkt3, 1);
        pl.createFredagsbarPrislisteProdukt(70.0, produkt4, 1);
        pl.createFredagsbarPrislisteProdukt(80.0, produkt5, 2);
        //Assert
        assertEquals(5, pl.getPrislisteProdukter().size());
        assertEquals(produkt5, pl.getPrislisteProdukter().get(4).getProdukt());
        assertEquals(80.0, pl.getPrislisteProdukter().get(4).getNyPris());
        assertTrue(PrislisteProdukt.class.isAssignableFrom(pl.getPrislisteProdukter().get(4).getClass()));
    }

    @Order(10)
    @Test
    void nulFredagsbarPrislisteProdukterOprettet() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);

        //Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            pl.createFredagsbarPrislisteProdukt(70.0, produkt1, 0);});
        //Assert
        assertEquals(0,pl.getPrislisteProdukter().size());
        assertTrue(exception.getMessage().contains("Antal klip skal være mere end 0"));

    }


    //--------------------------------- Prisliste(removePrislisteProdukt) ------------------------------------

    @Order(11)
    @Test
    void etPrislisteProduktFjernet() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);
        Produkt produkt2 = new Produkt("Klosterbryg", 36.0, false);
        Produkt produkt3 = new Produkt("Juleøl", 36.0, false);
        Produkt produkt4 = new Produkt("Forårsbryg", 36.0, false);
        Produkt produkt5 = new Produkt("Celebration", 36.0, false);

        PrislisteProdukt plp1 = pl.createPrislisteProdukt(45.0, produkt1);
        PrislisteProdukt plp2 = pl.createPrislisteProdukt(45.0, produkt2);
        PrislisteProdukt plp3 = pl.createPrislisteProdukt(45.0, produkt3);
        PrislisteProdukt plp4 = pl.createPrislisteProdukt(45.0, produkt4);
        PrislisteProdukt plp5 = pl.createPrislisteProdukt(50.0, produkt5);

        //Act
        pl.removePrislisteProdukt(plp2);
        //Assert
        assertEquals(4, pl.getPrislisteProdukter().size());
        assertFalse(pl.getPrislisteProdukter().contains(plp2));
    }


    @Order(12)
    @Test
    void nulPrislisteProduktFjernet() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);
        Produkt produkt2 = new Produkt("Klosterbryg", 36.0, false);
        Produkt produkt3 = new Produkt("Juleøl", 36.0, false);
        Produkt produkt4 = new Produkt("Forårsbryg", 36.0, false);
        Produkt produkt5 = new Produkt("Celebration", 36.0, false);

        PrislisteProdukt plp2 = new PrislisteProdukt(45.0, produkt2);

        PrislisteProdukt plp1 = pl.createPrislisteProdukt(45.0, produkt1);
        PrislisteProdukt plp3 = pl.createPrislisteProdukt(45.0, produkt3);
        PrislisteProdukt plp4 = pl.createPrislisteProdukt(45.0, produkt4);
        PrislisteProdukt plp5 = pl.createPrislisteProdukt(50.0, produkt5);

        //Act
        pl.removePrislisteProdukt(plp2);
        //Assert
        assertEquals(4, pl.getPrislisteProdukter().size());
        assertFalse(pl.getPrislisteProdukter().contains(plp2));
    }


    //------------------------ Prisliste(findPrislisteProduktUdFraProdukt) ------------------------

    @Order(13)
    @Test
    void findPrislisteProduktMedProdukt() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);
        Produkt produkt2 = new Produkt("Klosterbryg", 36.0, false);
        Produkt produkt3 = new Produkt("Juleøl", 36.0, false);
        Produkt produkt4 = new Produkt("Forårsbryg", 36.0, false);
        Produkt produkt5 = new Produkt("Celebration", 36.0, false);

        pl.createPrislisteProdukt(45.0, produkt1);
        pl.createPrislisteProdukt(45.0, produkt2);
        pl.createPrislisteProdukt(45.0, produkt3);
        pl.createPrislisteProdukt(45.0, produkt4);
        pl.createPrislisteProdukt(50.0, produkt5);

        //Act
        PrislisteProdukt plp = pl.findPrislisteProduktUdFraProdukt(produkt1);
        //Assert
        assertNotNull(plp);
        assertEquals(produkt1, plp.getProdukt());
    }

    @Order(14)
    @Test
    void findPrislisteProduktIListeUdenProdukt() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);
        Produkt produkt2 = new Produkt("Klosterbryg", 36.0, false);
        Produkt produkt3 = new Produkt("Juleøl", 36.0, false);
        Produkt produkt4 = new Produkt("Forårsbryg", 36.0, false);
        Produkt produkt5 = new Produkt("Celebration", 36.0, false);

        pl.createPrislisteProdukt(45.0, produkt2);
        pl.createPrislisteProdukt(45.0, produkt3);
        pl.createPrislisteProdukt(45.0, produkt4);
        pl.createPrislisteProdukt(50.0, produkt5);

        //Act
        PrislisteProdukt plp = pl.findPrislisteProduktUdFraProdukt(produkt1);
        //Assert
        assertNull(plp);
    }

    //-------------------------- Prisliste(findPrislisteProduktUdFraProduktnavn) ------------------------

    @Order(15)
    @Test
    void findPrislisteProduktMedProduktnavn() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);
        Produkt produkt2 = new Produkt("Klosterbryg", 36.0, false);
        Produkt produkt3 = new Produkt("Juleøl", 36.0, false);
        Produkt produkt4 = new Produkt("Forårsbryg", 36.0, false);
        Produkt produkt5 = new Produkt("Celebration", 36.0, false);

        pl.createPrislisteProdukt(45.0, produkt1);
        pl.createPrislisteProdukt(45.0, produkt2);
        pl.createPrislisteProdukt(45.0, produkt3);
        pl.createPrislisteProdukt(45.0, produkt4);
        pl.createPrislisteProdukt(50.0, produkt5);

        String navn = "Pilsner";

        //Act
        PrislisteProdukt plp = pl.findPrislisteProduktUdFraProduktnavn(navn);
        //Assert
        assertNotNull(plp);
        assertEquals(navn, plp.getProdukt().getProduktnavn());
    }

    @Order(16)
    @Test
    void findPrislisteProduktMedProduktnavnIngenProdukterMedNavn() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);
        Produkt produkt2 = new Produkt("Klosterbryg", 36.0, false);
        Produkt produkt3 = new Produkt("Juleøl", 36.0, false);
        Produkt produkt4 = new Produkt("Forårsbryg", 36.0, false);
        Produkt produkt5 = new Produkt("Celebration", 36.0, false);

        pl.createPrislisteProdukt(45.0, produkt2);
        pl.createPrislisteProdukt(45.0, produkt3);
        pl.createPrislisteProdukt(45.0, produkt4);
        pl.createPrislisteProdukt(50.0, produkt5);

        String navn = "Pilsner";

        //Act
        PrislisteProdukt plp = pl.findPrislisteProduktUdFraProduktnavn(navn);
        //Assert
        assertNull(plp);

    }


    //-------------------- Prisliste(udskiftPrislisteProduktMedFredagsbarPrislisteProdukt) ------------------------

    @Order(17)
    @Test
    void skiftPrislisteProduktTilFredagsbarPrislisteProdukt() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);


        PrislisteProdukt plp = pl.createPrislisteProdukt(45.0, produkt1);

        int antalKlip = 1;

        //Act
        pl.udskiftPrislisteProduktMedFredagsbarPrislisteProdukt(plp, antalKlip);
        FredagsbarPrislisteProdukt fredagPlp = (FredagsbarPrislisteProdukt) pl.getPrislisteProdukter().get(0);
        //Assert

        assertFalse(pl.getPrislisteProdukter().contains(plp));
        assertEquals(1, fredagPlp.getAntalKlipSomPris());
        assertTrue(PrislisteProdukt.class.isAssignableFrom(pl.getPrislisteProdukter().get(0).getClass()));
    }

    @Order(18)
    @Test
    void listeSomIkkeIndeholderDenAngivnePrislisteProdukt() {
        //Arrange
        Prisliste pl = new Prisliste();

        Produkt produkt1 = new Produkt("Pilsner", 36.0, false);
        Produkt produkt2 = new Produkt("Klosterbryg", 36.0, false);


        pl.createPrislisteProdukt(45.0, produkt1);
        PrislisteProdukt plp2 = new PrislisteProdukt(45.0, produkt2);

        //Act
        Exception exception = assertThrows(NoSuchElementException.class, () ->
                pl.udskiftPrislisteProduktMedFredagsbarPrislisteProdukt(plp2, 1));
        // Assert
        assertTrue(exception.getMessage().contains("Prislisteproduktet er ikke i prislisten"));

    }

    //----------------------- udskiftPrislisteProduktMedFredagsbarPrislisteProdukt(2) -------------------------

    @Order(19)
    @Test
    void udskiftPrislisteProduktMedFredagsbarPrislisteProdukt_antalKlip2()
    {
        //Arrange
        Produkt produkt1 = new Produkt("Stout",55.00, false);
        Produkt produkt2 = new Produkt("IPA", 60.00, false);
        Produkt produkt3 = new Produkt("Pilsner", 45.00, false);
        Produkt produkt4 = new Produkt("Spirit Of Aarhus", 200.00, false);
        Produkt produkt5 = new Produkt("Spirit Of Aarhus2", 160.00, false);

        Prisliste pl = new Prisliste();
        PrislisteProdukt prislisteProduktDerSkalUdskiftes = pl.createPrislisteProdukt(55.00, produkt1);
        pl.createPrislisteProdukt(60.00, produkt2);
        pl.createPrislisteProdukt(45.00, produkt3);
        pl.createPrislisteProdukt(200.00, produkt4);
        pl.createPrislisteProdukt(160.00, produkt5);

        int expectedAntalKlipSomPris = 2;
        int expectedSize = 5;
        int expectedIndeks = 0;

        //Act
        FredagsbarPrislisteProdukt fredagsbarPrislisteProduktDerBlevIndsat = (FredagsbarPrislisteProdukt) pl.
                udskiftPrislisteProduktMedFredagsbarPrislisteProdukt(prislisteProduktDerSkalUdskiftes, 2);

        //Assert
        assertEquals(expectedAntalKlipSomPris, fredagsbarPrislisteProduktDerBlevIndsat.getAntalKlipSomPris());
        assertEquals(expectedSize, pl.getPrislisteProdukter().size());
        assertEquals(expectedIndeks, pl.getPrislisteProdukter().indexOf(fredagsbarPrislisteProduktDerBlevIndsat));
    }

    @Order(20)
    @Test
    void udskiftPrislisteProduktMedFredagsbarPrislisteProdukt_prislisteProduktIkkeIPrislisteException()
    {
        //Arrange
        Produkt produkt1 = new Produkt("Stout",55.00, false);
        Produkt produkt2 = new Produkt("IPA", 60.00, false);
        Produkt produkt3 = new Produkt("Pilsner", 45.00, false);
        Produkt produkt4 = new Produkt("Spirit Of Aarhus", 200.00, false);
        Produkt produkt5 = new Produkt("Spirit Of Aarhus2", 160.00, false);

        Prisliste pl = new Prisliste();
        PrislisteProdukt prislisteProduktDerSkalUdskiftes = pl.createPrislisteProdukt(55.00, produkt1);
        pl.createPrislisteProdukt(60.00, produkt2);
        pl.createPrislisteProdukt(45.00, produkt3);
        pl.createPrislisteProdukt(200.00, produkt4);
        pl.createPrislisteProdukt(160.00, produkt5);

        Produkt produkt6 = new Produkt("En øl", 45.00, false);
        Prisliste pl2 = new Prisliste();
        PrislisteProdukt andetPrislisteProdukt = pl2.createPrislisteProdukt(99.00, produkt6);

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                pl.udskiftPrislisteProduktMedFredagsbarPrislisteProdukt(andetPrislisteProdukt, 2));
        assertTrue(exception.getMessage().contains(
                "Prislisteproduktet er ikke i prislisten"));
    }



    //----------------------------------- findPrislisteProduktUdFraProdukt(2) --------------------------------------

    @Order(21)
    @Test
    void findPrislisteProduktUdFraProdukt_()
    {
        //Arrange
        Produkt produkt1 = new Produkt("Stout",55.00, false);
        Produkt produkt2 = new Produkt("IPA", 60.00, false);
        Produkt produkt3 = new Produkt("Pilsner", 45.00, false);
        Produkt produkt4 = new Produkt("Spirit Of Aarhus", 200.00, false);
        Produkt produkt5 = new Produkt("Spirit Of Aarhus2", 160.00, false);

        Prisliste pl = new Prisliste();
        PrislisteProdukt prislisteProduktDerSkalFindes = pl.createPrislisteProdukt(55.00, produkt1);
        pl.createPrislisteProdukt(60.00, produkt2);
        pl.createPrislisteProdukt(45.00, produkt3);
        pl.createPrislisteProdukt(200.00, produkt4);
        pl.createPrislisteProdukt(160.00, produkt5);

        //Act
        PrislisteProdukt fundetPrislisteProdukt = pl.findPrislisteProduktUdFraProdukt(produkt1);

        //Assert
        assertEquals(prislisteProduktDerSkalFindes, fundetPrislisteProdukt);
    }

    //----------------------------------- erDerFredagsbarPrislisteProdukter --------------------------------------

    @Order(22)
    @Test
    void erDerFredagsbarPrislisteProdukter_prislisteHarToPrislisteProdukterIngenFredagsbarPirslisteProdukter()
    {
        //Arrange
        Produkt mockedProdukt = mock(Produkt.class);
        Prisliste prisliste = new Prisliste();
        prisliste.createPrislisteProdukt(36, mockedProdukt);
        prisliste.createPrislisteProdukt(36, mockedProdukt);
        boolean expectedResult = false;

        //Act
        boolean actualResult = prisliste.erDerFredagsbarPrislisteProdukter();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Order(23)
    @Test
    void erDerFredagsbarPrislisteProdukter_prislisteHar1PrislisteProdukter1FredagsbarPirslisteProdukter()
    {
        //Arrange
        Produkt mockedProdukt = mock(Produkt.class);
        Prisliste prisliste = new Prisliste();
        prisliste.createPrislisteProdukt(36, mockedProdukt);
        prisliste.createFredagsbarPrislisteProdukt(36, mockedProdukt, 1);
        boolean expectedResult = true;

        //Act
        boolean actualResult = prisliste.erDerFredagsbarPrislisteProdukter();

        //Assert
        assertEquals(expectedResult, actualResult);
    }

    @Order(24)
    @Test
    void erDerFredagsbarPrislisteProdukter_prislisteHarIngenPrislisteProdukter()
    {
        //Arrange
        Prisliste prisliste = new Prisliste();
        boolean expectedResult = false;

        //Act
        boolean actualResult = prisliste.erDerFredagsbarPrislisteProdukter();

        //Assert
        assertEquals(expectedResult, actualResult);
    }
}