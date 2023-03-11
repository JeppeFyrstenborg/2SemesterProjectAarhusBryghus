//package storage;
//
//import application.controller.Controller;
//import application.model.Produkt;
//import application.model.Produktkategori;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)





/*
Se StorageTestMedMocking for mere brugbare tests, hvor der er brugt mocking.
 */






//class StorageTest
//{
//    private Controller controller = new Controller();
//    private StorageI mockedStorage;
//
//    //--------------------------------- addProduktkategori ------------------------------------
//    @Order(1)
//    @Test
//    void addProduktkategori_produktkategoriMed3Produkter()
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
////        Controller testController = Controller.getTestController();
//
//        int expectedProdukterSize = 3;
//
//        //Act
////        testController.getStorage().addProduktkategori(produktkategori1);
//
//        //Assert
////        assertEquals(produktkategori1, testController.getStorage().getAllProduktkategorier().get(0));
////        assertEquals(expectedProdukterSize,
////                testController.getStorage().getAllProduktkategorier().get(0).getProdukter().size());
//    }
//
//    @Order(2)
//    @Test
//    void addProduktkategori_produktkategoriUdenProdukter()
//    {
//        //Arrange
//        Produktkategori produktkategori2 = new Produktkategori("Spiritus");
//
//        int expectedProdukterSize = 0;
//
//        //Act
////        testController.getStorage().addProduktkategori(produktkategori2);
//
//        //Assert
////        assertEquals(produktkategori2, testController.getStorage().getAllProduktkategorier().get(0));
////        assertEquals(expectedProdukterSize,
////                testController.getStorage().getAllProduktkategorier().get(0).getProdukter().size());
//    }
//
//    @Order(3)
//    @Test
//    void addProduktkategori_produktkategoriAlleredeIStorage()
//    {
//        //Arrange
//        Produktkategori produktkategori2 = new Produktkategori("Spiritus");
//
////        Controller testController = Controller.getTestController();
////        testController.getStorage().addProduktkategori(produktkategori2);
//
//        int expectedSizeProduktkategorierStorage = 1;
//
//        //Act
////        testController.getStorage().addProduktkategori(produktkategori2);
//
//        //Assert
////        assertEquals(expectedSizeProduktkategorierStorage,
////                testController.getStorage().getAllProduktkategorier().size());
//    }
//
//    //--------------------------------- deleteProduktkategori ------------------------------------
//
//    @Order(4)
//    @Test
//    void deleteProduktkategori_produktkategoriErIStorage()
//    {
//        //Arrange
//        Produktkategori produktkategori1 = new Produktkategori("Øl");
//
////        Controller testController = Controller.getTestController();
////        testController.getStorage().addProduktkategori(produktkategori1);
//
//        //Act
////        testController.getStorage().deleteProduktkategori(produktkategori1);
//
//        //Assert
////        assertFalse(testController.getStorage().getAllProduktkategorier().contains(produktkategori1));
//    }
//
//    @Order(5)
//    @Test
//    void deleteProduktkategori_produktkategoriErIkkeIStorage()
//    {
//        //Arrange
//        Produktkategori produktkategori1 = new Produktkategori("Øl");
//
////        Controller testController = Controller.getTestController();
//
//        //Act
////        boolean indeholderProduktkategori1 = testController.getStorage().deleteProduktkategori(produktkategori1);
//
//        //Assert
////        assertFalse(indeholderProduktkategori1);
//    }
//}