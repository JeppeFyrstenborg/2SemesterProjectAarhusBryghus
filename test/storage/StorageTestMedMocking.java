package storage;

import application.model.*;
import org.junit.jupiter.api.*;
import storage.StorageI;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StorageTestMedMocking
{
    //--------------------------------- setup ------------------------------------

    private Storage testStorage;
    private Produktkategori mockedProduktkategori;
    private Produktkategori mockedProduktkategori2;

    @BeforeEach
    void setup_mockTestStorage()
    {
        testStorage = Storage.getTestStorage();
        mockedProduktkategori = mock(Produktkategori.class);
        mockedProduktkategori2 = mock(Produktkategori.class);
    }

    //--------------------------------- addProduktkategori ------------------------------------

    @Order(1)
    @Test
    void addProduktkategori_1Produktkategori()
    {
        //Arrange

        //Act
        testStorage.addProduktkategori(mockedProduktkategori);

        //Arrange
        assertTrue(testStorage.getAllProduktkategorier().contains(mockedProduktkategori));
    }

    @Order(2)
    @Test
    void addProduktkategori_produktkategoriAlleredeIStorage()
    {
        //Arrange
        testStorage.addProduktkategori(mockedProduktkategori);
        int sizeBeforeAdd = testStorage.getAllProduktkategorier().size();

        //Act
        testStorage.addProduktkategori(mockedProduktkategori);
        int sizeAfterAdd = testStorage.getAllProduktkategorier().size();

                //Arrange
        assertEquals(sizeBeforeAdd, sizeAfterAdd);
    }

    @Order(3)
    @Test
    void addProduktkategori_2ForskelligeProduktkategorier()
    {
        //Arrange
        int expectedSize = 2;

        //Act
        testStorage.addProduktkategori(mockedProduktkategori);
        testStorage.addProduktkategori(mockedProduktkategori2);

        //Arrange
        assertEquals(expectedSize, testStorage.getAllProduktkategorier().size());
    }

    //--------------------------------- deleteProduktkategori ------------------------------------

    @Order(4)
    @Test
    void deleteProduktkategori_produktkategoriErIStorage()
    {
        //Arrange
        testStorage.addProduktkategori(mockedProduktkategori);
        int expectedsize = 0;

        //Act
        boolean varMockedProduktIStorage = testStorage.deleteProduktkategori(mockedProduktkategori);

        //Assert
        assertFalse(testStorage.getAllProduktkategorier().contains(mockedProduktkategori));
        assertTrue(varMockedProduktIStorage);
        assertEquals(expectedsize, testStorage.getAllProduktkategorier().size());
    }

    @Order(5)
    @Test
    void deleteProduktkategori_produktkategoriErIkkeIStorage()
    {
        //Arrange
        int expectedsize = 0;

        //Act
        boolean varMockedProduktIStorage = testStorage.deleteProduktkategori(mockedProduktkategori);

        //Assert
        assertFalse(testStorage.getAllProduktkategorier().contains(mockedProduktkategori));
        assertFalse(varMockedProduktIStorage);
        assertEquals(expectedsize, testStorage.getAllProduktkategorier().size());
    }

    //--------------------------------- getAllProduktkategorier ------------------------------------

    @Order(6)
    @Test
    void getAllProduktkategorier_ingenProduktkategorier()
    {
        //Arrange
        int expectedsize = 0;

        //Act
        int actualSize = testStorage.getAllProduktkategorier().size();

        //Assert
        assertEquals(expectedsize, actualSize);
    }

    @Order(7)
    @Test
    void getAllProduktkategorier_2Produktkategorier()
    {
        //Arrange
        testStorage.addProduktkategori(mockedProduktkategori);
        testStorage.addProduktkategori(mockedProduktkategori2);
        int expectedsize = 2;

        //Act
        int actualSize = testStorage.getAllProduktkategorier().size();

        //Assert
        assertEquals(expectedsize, actualSize);
    }
}
