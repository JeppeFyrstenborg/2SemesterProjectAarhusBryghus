//package application.controller;
//
//import application.model.*;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.jupiter.api.*;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import storage.Storage;
//import storage.StorageI;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)



/*
ControllerTest blev lavet uden mocking, og tester derfor ikke ordentligt, fordi andre metoder der bliver brugt inden i
selve metoden der skal testes, også bliver en del af testen. Desuden bliver testmetoder der bruger storage fyldt op
med objekter fra tidligere testmetoder, der også brugte storage.

Se ControllerTestMedMocking for mere brugbare tests, hvor der er brugt mocking.
 */





//class ControllerTest
//{
//
//    //--------------------------------- opretPrisliste ------------------------------------
//
//    // gammelt mockito kode:
////    //Arrange
////    mockedStorage = mock(Storage.class);
////    Controller testController = Controller.getTestController();
////    when(mockedcontroller.getStorage()).thenReturn(mockedStorage);
////    when(mockedcontroller.).thenReturn(mockedStorage);
////
////    Produktkategori produktkategori1 = new Produktkategori("Øl", "Flasker");
////
////    Produkt produkt1 = new Produkt("Stout", "Mørk øl", 55.00);
////    Produkt produkt2 = new Produkt("IPA", "Lys øl", 60.00);
////    Produkt produkt3 = new Produkt("Pilsner", "Let øl", 45.00);
////
////        produktkategori1.addProdukt(produkt1);
////        produktkategori1.addProdukt(produkt2);
////        produktkategori1.addProdukt(produkt3);
////
////        mockedStorage.addProduktkategori(produktkategori1);
////
////    //Act
////    Prisliste pl = mockedcontroller.opretPrisliste();
////
////    //Assert
////    assertTrue(mockedStorage.getAllPrislister().contains(pl));
//
//    private final Controller controller = new Controller();
//    private StorageI mockedStorage;
//
//    @Order(1)
//    @Test
//    void opretPrisliste_ingenProduktkategorier()
//    {
//        //Arrange
//        mockedStorage = mock(Storage.class);
//
//        int expectedSize = 0;
//        String expectedPrislistenavn = "Ikke sat";
//        int expectedPrislisteNr = 1;
//
//        //Act
//        /*testcontroller virker ikke hvis metoden vi tester bruger den statiske metode Controller.getController()*/
////        Controller controller = Controller.getTestController();
//        Prisliste pl = controller.opretPrisliste();
//
//        //Assert
//        assertEquals(expectedSize, pl.getPrislisteProdukter().size());
//        assertEquals(expectedPrislistenavn, pl.getPrislistenavn());
//        assertEquals(expectedPrislisteNr, pl.getPrislisteNr());
//    }
//
//    // virker ikke fordi den statiske metode getcontroller blev brugt i sidste testmetode
//    // (Controller.getController().opretPrisliste),
//    // så klassen Prisliste's statiske attribut naestePrislisteNr har talt op,
//    // da der blev lavet en prisliste i sidste testmetode.
//    @Order(2)
//    @Test
//    void opretPrisliste_2Produktkategorier5Produkter()
//    {
//        //Arrange
//        mockedStorage = mock(Storage.class);
//
//        Produktkategori produktkategori1 = new Produktkategori("Øl");
//        Produktkategori produktkategori2 = new Produktkategori("Spiritus");
//
//        Produkt produkt1 = new Produkt("Stout",55.00, false);
//        Produkt produkt2 = new Produkt("IPA", 60.00, false);
//        Produkt produkt3 = new Produkt("Pilsner", 45.00, false);
//        Produkt produkt4 = new Produkt("Spirit Of Aarhus", 200.00, false);
//        Produkt produkt5 = new Produkt("Spirit Of Aarhus2", 160.00, false);
//
//        produktkategori1.addProdukt(produkt1);
//        produktkategori1.addProdukt(produkt2);
//        produktkategori1.addProdukt(produkt3);
//        produktkategori2.addProdukt(produkt4);
//        produktkategori2.addProdukt(produkt5);
//
//        /*testcontroller virker ikke hvis metoden vi tester bruger den statiske metode Controller.getController()*/
////        Controller controller = Controller.getTestController();
//
////        Controller.getController().getStorage().addProduktkategori(produktkategori1);
////        Controller.getController().getStorage().addProduktkategori(produktkategori2);
//
//        int expectedSize = 0;
//        String expectedPrislistenavn = "Ikke sat";
//        int expectedPrislisteNr = 1;
//
//        //Act
//        Prisliste pl = controller.opretPrisliste();
//
//        //Assert
//        assertEquals(expectedSize, pl.getPrislisteProdukter().size());
//        assertEquals(expectedPrislistenavn, pl.getPrislistenavn());
//        assertEquals(expectedPrislisteNr, pl.getPrislisteNr());
//    }
//
//    @Order(3)
//    @Test
//    void opretPrisliste_prislisteTilfoejetTilStorage()
//    {
//        //Arrange
//
//        //Act
//        /*testcontroller virker ikke hvis metoden vi tester bruger den statiske metode Controller.getController()*/
////        Controller controller = Controller.getTestController();
//        Prisliste pl = controller.opretPrisliste();
//
//        //Assert
////        assertTrue(Controller.getController().getStorage().getAllPrislister().contains(pl));
//    }
//
//    //--------------------------------- udfyldPrislisteMedPrislisteProdukter ------------------------------------
//
//    // virker ikke fordi den statiske metode getcontroller blev brugt i testmetoden
//    // "opretPrisliste_2Produktkategorier5Produkter",
//    // så der er i forvejen objekter i storage.
//    @Order(4)
//    @Test
//    void udfyldPrislisteMedPrislisteProdukter_listsize0()
//    {
//        //Arrange
//        Prisliste pl = new Prisliste();
//
//        int expectedSize = 0;
//
//        //Act
//        controller.udfyldPrislisteMedPrislisteProdukter(pl);
//
//        //Assert
//        assertEquals(expectedSize, pl.getPrislisteProdukter().size());
//    }
//
//    // virker ikke fordi den statiske metode getcontroller blev brugt i testmetoden
//    // "opretPrisliste_2Produktkategorier5Produkter",
//    // så der er i forvejen objekter i storage.
//    @Order(5)
//    @Test
//    void udfyldPrislisteMedPrislisteProdukter_listsize5()
//    {
//        //Arrange
//        Produktkategori produktkategori1 = new Produktkategori("Øl");
//        Produktkategori produktkategori2 = new Produktkategori("Spiritus");
//
//        Produkt produkt1 = new Produkt("Stout",55.00, false);
//        Produkt produkt2 = new Produkt("IPA", 60.00, false);
//        Produkt produkt3 = new Produkt("Pilsner", 45.00, false);
//        Produkt produkt4 = new Produkt("Spirit Of Aarhus", 200.00, false);
//        Produkt produkt5 = new Produkt("Spirit Of Aarhus2", 160.00, false);
//
//        produktkategori1.addProdukt(produkt1);
//        produktkategori1.addProdukt(produkt2);
//        produktkategori1.addProdukt(produkt3);
//        produktkategori2.addProdukt(produkt4);
//        produktkategori2.addProdukt(produkt5);
//
//        /*testcontroller virker ikke hvis metoden vi tester bruger den statiske metode Controller.getController()*/
////        Controller controller = Controller.getTestController();
//
////        Controller.getController().getStorage().addProduktkategori(produktkategori1);
////        Controller.getController().getStorage().addProduktkategori(produktkategori2);
//
//        Prisliste pl = new Prisliste();
//
//        int expectedSize = 5;
//        double expectedNyPris = 55.00;
//
//        //Act
//        controller.udfyldPrislisteMedPrislisteProdukter(pl);
//
//        //Assert
//        assertEquals(expectedSize, pl.getPrislisteProdukter().size());
//        assertEquals(expectedNyPris, pl.getPrislisteProdukter().get(0).getNyPris());
//        assertEquals(produkt1, pl.getPrislisteProdukter().get(0).getProdukt());
//    }
//
//    // virker ikke fordi den statiske metode getcontroller blev brugt i sidste testmetode
//    // (Controller.getController().getStorage().addProduktkategori();),
//    // så der er i forvejen objekter i storage.
//    @Order(6)
//    @Test
//    void udfyldPrislisteMedPrislisteProdukter_listsize3()
//    {
//        //Arrange
//        Produktkategori produktkategori1 = new Produktkategori("Øl");
//
//        Produkt produkt1 = new Produkt("Stout",55.00, false);
//        Produkt produkt2 = new Produkt("IPA", 60.00, false);
//        Produkt produkt3 = new Produkt("Pilsner", 45.00, false);
//
//        produktkategori1.addProdukt(produkt1);
//        produktkategori1.addProdukt(produkt2);
//        produktkategori1.addProdukt(produkt3);
//
//        /*testcontroller virker ikke hvis metoden vi tester bruger den statiske metode Controller.getController()*/
////        Controller controller = Controller.getTestController();
//
////        Controller.getController().getStorage().addProduktkategori(produktkategori1);
//
//        Prisliste pl = new Prisliste();
//
//        int expectedSize = 3;
//        double expectedNyPris = 45.00;
//
//        //Act
//        controller.udfyldPrislisteMedPrislisteProdukter(pl);
//
//        //Assert
//        assertEquals(expectedSize, pl.getPrislisteProdukter().size());
//        assertEquals(expectedNyPris, pl.getPrislisteProdukter().get(2).getNyPris());
//        assertEquals(produkt3, pl.getPrislisteProdukter().get(2).getProdukt());
//    }
//
//    @Order(7)
//    @Test
//    void udfyldPrislisteMedPrislisteProdukter_alleredeUdfyldtException()
//    {
//        //Arrange
//        Prisliste pl = new Prisliste();
//        pl.createPrislisteProdukt(50.00, (new Produkt("Stout",55.00, false)));
//
//        //Act & Assert
//        Exception exception = assertThrows(RuntimeException.class, () ->
//                controller.udfyldPrislisteMedPrislisteProdukter(pl));
//        assertTrue(exception.getMessage().contains(
//                "Prislisten er allerede udfyldt"));
//    }
//
//
//    //------------------------------------- opretProduktkategori --------------------------------------------
//
//    @Order(10)
//    @Test
//    void opretProduktkategori_produktkategorinavnFlasker()
//    {
//        //Arrange
//        String expectedProduktkategorinavn = "Flasker";
//
//        //Act
//        Produktkategori produktkategori = controller.opretProduktkategori("Flasker");
//
//        //Assert
//        assertEquals(expectedProduktkategorinavn, produktkategori.getProduktkategorinavn());
//    }
//
//    @Order(11)
//    @Test
//    void opretProduktkategori_produktkategorinavnNull()
//    {
//        //Arrange
//        String expectedProduktkategorinavn = null;
//
//        //Act
//        Produktkategori produktkategori = controller.opretProduktkategori(null);
//
//        //Assert
//        assertEquals(expectedProduktkategorinavn, produktkategori.getProduktkategorinavn());
//    }
//
//    //------------------------------------- antalSolgteKlipPeriode --------------------------------------------
//
//    @Order(12)
//    @Test
//    void antalSolgteKlipPeriode_startdatoSammeSomSalgPaabegyndtDato()
//    {
//        //Arrange
//        Produktkategori klippekort = controller.opretProduktkategori("Klippekort");
//        Produkt klippekort4Klip = klippekort.createKlippekortProdukt("Klippekort, 4 Klip", 130,
//                false, 4);
//
//        SalgsLinje mockedSalgslinje = mock(SalgsLinje.class);
//        when(mockedSalgslinje.getProdukt()).thenReturn(klippekort4Klip);
//        when(mockedSalgslinje.getProduktAntal()).thenReturn(1, 3);
//        List<SalgsLinje> slagslinjer = new ArrayList<>();
//        slagslinjer.add(mockedSalgslinje);
//        slagslinjer.add(mockedSalgslinje);
//
//        Salg mockedSalg = mock(Salg.class);
//        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
//        when(mockedSalg.getSalgsLinjer()).thenReturn(slagslinjer);
//
////        controller.getStorage().addSalg(mockedSalg);
//
//        LocalDate startDato = LocalDate.of(2022, 4, 1);
//        LocalDate slutDato = LocalDate.of(2022, 4, 4);
//
//        int expectedSizeSalgslinjerISalg = 2;
//        int expectedAntalKlipSolgtIPeriode = 16;
//
//        //Act
//        int antal = controller.antalSolgteKlipPeriode(startDato, slutDato);
//
//        //Assert
//        assertEquals(expectedSizeSalgslinjerISalg, mockedSalg.getSalgsLinjer().size());
//        assertEquals(expectedAntalKlipSolgtIPeriode, antal);
//    }
//
//    @Order(13)
//    @Test
//    void antalSolgteKlipPeriode_startdato1DagEfterSalgPaabegyndtDato()
//    {
//        //Arrange
//        Produktkategori klippekort = controller.opretProduktkategori("Klippekort");
//        Produkt klippekort4Klip = klippekort.createKlippekortProdukt("Klippekort, 4 Klip", 130,
//                false, 4);
//
//        SalgsLinje mockedSalgslinje = mock(SalgsLinje.class);
//        when(mockedSalgslinje.getProdukt()).thenReturn(klippekort4Klip);
//        when(mockedSalgslinje.getProduktAntal()).thenReturn(2);
//        List<SalgsLinje> slagslinjer = new ArrayList<>();
//        slagslinjer.add(mockedSalgslinje);
//
//        Salg mockedSalg = mock(Salg.class);
//        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
//        when(mockedSalg.getSalgsLinjer()).thenReturn(slagslinjer);
//
//        LocalDate startDato = LocalDate.of(2022, 4, 2);
//        LocalDate slutDato = LocalDate.of(2022, 4, 4);
//
//        int expectedSizeSalgslinjerISalg = 1;
//        int expectedAntalKlipSolgtIPeriode = 0;
//
//        //Act
//        int antal = controller.antalSolgteKlipPeriode(startDato, slutDato);
//
//        //Assert
//        assertEquals(expectedSizeSalgslinjerISalg, mockedSalg.getSalgsLinjer().size());
//        assertEquals(expectedAntalKlipSolgtIPeriode, antal);
//    }
//
//    //------------------------------------- antalForbrugteKlipPeriode --------------------------------------------
//
//    @Order(14)
//    @Test
//    void antalForbrugteKlipPeriode_startdatoSammeSomSalgPaabegyndtDato_2Salgslinjer()
//    {
//        //Arrange
//        Produktkategori flaskeoel = controller.opretProduktkategori("Flasker");
//        Produkt flaskeKlosterbryg = flaskeoel.createDrikkevareProdukt("Klosterbryg", 70.0,
//                false, 0.6);
//        Produkt flaskeJulebryg = flaskeoel.createDrikkevareProdukt("Julebryg", 70.0,
//                false, 0.6);
//
//        SalgsLinje mockedSalgslinje = mock(SalgsLinje.class);
//        when(mockedSalgslinje.getProdukt()).thenReturn(flaskeKlosterbryg);
//        when(mockedSalgslinje.getProduktAntal()).thenReturn(1, 3);
//        List<SalgsLinje> slagslinjer = new ArrayList<>();
//        slagslinjer.add(mockedSalgslinje);
//        slagslinjer.add(mockedSalgslinje);
//
//        Salg mockedSalg = mock(Salg.class);
//        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
//        when(mockedSalg.getSalgsLinjer()).thenReturn(slagslinjer);
//        when(mockedSalg.getBetalingsform()).thenReturn(Betalingsform.KLIPPEKORT);
//
//        FredagsbarPrislisteProdukt mockedFredagsbarPrislisteProdukt = mock(FredagsbarPrislisteProdukt.class);
//        when(mockedFredagsbarPrislisteProdukt.getAntalKlipSomPris()).thenReturn(2);
//
//        Prisliste mockedPrisliste = mock(Prisliste.class);
//        mockedSalg.setPrisliste(mockedPrisliste);
//        when(mockedPrisliste.findPrislisteProduktUdFraProdukt(flaskeKlosterbryg)).
//                thenReturn(mockedFredagsbarPrislisteProdukt);
//        when(mockedPrisliste.findPrislisteProduktUdFraProdukt(flaskeJulebryg)).
//                thenReturn(mockedFredagsbarPrislisteProdukt);
//        when(mockedSalg.getPrisliste()).thenReturn(mockedPrisliste);
//
////        controller.getStorage().addSalg(mockedSalg);
//
//        LocalDate startDato = LocalDate.of(2022, 4, 1);
//        LocalDate slutDato = LocalDate.of(2022, 4, 4);
//
//        int expectedSizeSalgslinjerISalg = 2;
//        int expectedForbrugteKlip = 8;
//
//        //Act
//        int antal = controller.antalForbrugteKlipPeriode(startDato, slutDato);
//
//        //Assert
//        assertEquals(expectedSizeSalgslinjerISalg, mockedSalg.getSalgsLinjer().size());
//        assertEquals(expectedForbrugteKlip, antal);
//    }
//
//    @Order(15)
//    @Test
//    void antalForbrugteKlipPeriode_startdatoSammeSomSalgPaabegyndtDato_3Salgslinjer()
//    {
//        //Arrange
//        Produktkategori flaskeoel = controller.opretProduktkategori("Flasker");
//        Produkt flaskeKlosterbryg = flaskeoel.createDrikkevareProdukt("Klosterbryg", 70.0,
//                false, 0.6);
//        Produkt flaskeJulebryg = flaskeoel.createDrikkevareProdukt("Julebryg", 70.0,
//                false, 0.6);
//
//        SalgsLinje mockedSalgslinje = mock(SalgsLinje.class);
//        when(mockedSalgslinje.getProdukt()).thenReturn(flaskeKlosterbryg, flaskeJulebryg);
//        when(mockedSalgslinje.getProduktAntal()).thenReturn(1, 3, 2);
//        List<SalgsLinje> slagslinjer = new ArrayList<>();
//        slagslinjer.add(mockedSalgslinje);
//        slagslinjer.add(mockedSalgslinje);
//        slagslinjer.add(mockedSalgslinje);
//
//        Salg mockedSalg = mock(Salg.class);
//        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
//        when(mockedSalg.getSalgsLinjer()).thenReturn(slagslinjer);
//        when(mockedSalg.getBetalingsform()).thenReturn(Betalingsform.KLIPPEKORT);
//
//        FredagsbarPrislisteProdukt mockedFredagsbarPrislisteProdukt = mock(FredagsbarPrislisteProdukt.class);
//        when(mockedFredagsbarPrislisteProdukt.getAntalKlipSomPris()).thenReturn(2, 3, 2);
//
//        Prisliste mockedPrisliste = mock(Prisliste.class);
//        mockedSalg.setPrisliste(mockedPrisliste);
//        when(mockedPrisliste.findPrislisteProduktUdFraProdukt(flaskeKlosterbryg)).
//                thenReturn(mockedFredagsbarPrislisteProdukt);
//        when(mockedPrisliste.findPrislisteProduktUdFraProdukt(flaskeJulebryg)).
//                thenReturn(mockedFredagsbarPrislisteProdukt);
//        when(mockedSalg.getPrisliste()).thenReturn(mockedPrisliste);
//
////        controller.getStorage().addSalg(mockedSalg)
//
//        LocalDate startDato = LocalDate.of(2022, 4, 1);
//        LocalDate slutDato = LocalDate.of(2022, 4, 4);
//
//        int expectedSizeSalgslinjerISalg = 3;
//        int expectedForbrugteKlip = 15;
//
//        //Act
//        int antal = controller.antalForbrugteKlipPeriode(startDato, slutDato);
//
//        //Assert
//        assertEquals(expectedSizeSalgslinjerISalg, mockedSalg.getSalgsLinjer().size());
//        assertEquals(expectedForbrugteKlip, antal);
//    }
//}