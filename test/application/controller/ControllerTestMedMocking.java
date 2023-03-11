package application.controller;

import application.model.*;
import org.junit.jupiter.api.*;
import storage.StorageI;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerTestMedMocking
{
    //--------------------------------- setup ------------------------------------

    private StorageI mockedStorage;
    private Controller controller;
    private Produktkategori mockedProduktkategori;
    private Produktkategori mockedProduktkategori2;
    private Produkt mockedProdukt;
    private Prisliste mockedPrisliste;
    private FredagsbarPrislisteProdukt mockedFredagsbarPrislisteProdukt;
    private Salg mockedSalg;
    private SalgsLinje mockedSalgsLinje;
    private Klippekort mockedKlippekort;

    private List<Produktkategori> mockedProduktkategoriListe;
    private List<Produkt> mockedProduktListe;
    private List<Produkt> mockedProduktListe2;
    private List<Salg> mockedSalgListe;
    private List<SalgsLinje> mockedSalgsLinjerListe;

    @BeforeEach
    void setup_mockTestController()
    {
        //Arrange
        mockedStorage = mock(StorageI.class);
        controller = new Controller(mockedStorage);
        mockedProduktkategori = mock(Produktkategori.class);
        mockedProduktkategori2 = mock(Produktkategori.class);
        mockedProdukt = mock(Produkt.class);
        mockedPrisliste = mock(Prisliste.class);
        mockedFredagsbarPrislisteProdukt = mock(FredagsbarPrislisteProdukt.class);
        mockedSalg = mock(Salg.class);
        mockedSalgsLinje = mock(SalgsLinje.class);
        mockedKlippekort = mock(Klippekort.class);

        mockedProduktkategoriListe = new ArrayList<>();
        mockedProduktListe = new ArrayList<>();
        mockedProduktListe2 = new ArrayList<>();
        mockedSalgListe = new ArrayList<>();
        mockedSalgsLinjerListe = new ArrayList<>();

        when(mockedStorage.getAllProduktkategorier()).thenReturn(mockedProduktkategoriListe);
        when(mockedStorage.getAllSalg()).thenReturn(mockedSalgListe);

    }

    //--------------------------------- udfyldPrislisteMedPrislisteProdukter ------------------------------------

    @Order(1)
    @Test
    void udfyldPrislisteMedPrislisteProdukter_listsize0()
    {
        //Arrange

        //Act
        controller.udfyldPrislisteMedPrislisteProdukter(mockedPrisliste);

        //Assert
        verify(mockedPrisliste, atMost(0)).createPrislisteProdukt(anyInt(), any(Produkt.class));
    }

    @Order(2)
    @Test
    void udfyldPrislisteMedPrislisteProdukter_listsize5()
    {
        //Arrange
        when(mockedProduktkategori.getProdukter()).thenReturn(mockedProduktListe);
        when(mockedProduktkategori2.getProdukter()).thenReturn(mockedProduktListe2);

        //mockedProduktkategoriListe får 2 produktkategorier
        mockedProduktkategoriListe.add(mockedProduktkategori);
        mockedProduktkategoriListe.add(mockedProduktkategori2);

        //mockedProduktListe får 3 produkter
        for (int i = 0; i < 3; i++)
        {
            mockedProduktListe.add(mockedProdukt);
        }

        //mockedProduktListe2 får 2 produkter
        for (int i = 0; i < 2; i++)
        {
            mockedProduktListe2.add(mockedProdukt);
        }

        //Act
        controller.udfyldPrislisteMedPrislisteProdukter(mockedPrisliste);

        //Assert
        verify(mockedStorage, atLeast(1)).getAllProduktkategorier();
        verify(mockedProduktkategori, atLeast(1)).getProdukter();
        verify(mockedProduktkategori2, atLeast(1)).getProdukter();
        verify(mockedPrisliste, atLeast(5)).createPrislisteProdukt(anyDouble(), any(Produkt.class));
    }

    @Order(3)
    @Test
    void udfyldPrislisteMedPrislisteProdukter_listsize3()
    {
        //Arrange
        //mockedProduktkategoriListe får 1 produktkategori
        mockedProduktkategoriListe.add(mockedProduktkategori);
        when(mockedProduktkategori.getProdukter()).thenReturn(mockedProduktListe);

        //mockedProduktListe får 3 produkter
        for (int i = 0; i < 3; i++)
        {
            mockedProduktListe.add(mockedProdukt);
        }

        //Act
        controller.udfyldPrislisteMedPrislisteProdukter(mockedPrisliste);

        //Assert
        verify(mockedStorage, atLeast(1)).getAllProduktkategorier();
        verify(mockedProduktkategori, atLeast(1)).getProdukter();
        verify(mockedPrisliste, atLeast(3)).createPrislisteProdukt(anyDouble(), any(Produkt.class));
        verify(mockedPrisliste, atMost(3)).createPrislisteProdukt(anyDouble(), any(Produkt.class));

    }

    //------------------------------------- antalSolgteKlipPeriode --------------------------------------------

    @Order(4)
    @Test
    void antalSolgteKlipPeriode_startdatoSammeSomSalgPaabegyndtDato()
    {
        //Arrange
        when(mockedKlippekort.getAntalKlip()).thenReturn(4);

        when(mockedSalgsLinje.getProdukt()).thenReturn(mockedKlippekort);
        when(mockedSalgsLinje.getProduktAntal()).thenReturn(1, 3);

        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);

        mockedSalgListe.add(mockedSalg);

        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
        when(mockedSalg.getSalgsLinjer()).thenReturn(mockedSalgsLinjerListe);

        LocalDate startDato = LocalDate.of(2022, 4, 1);
        LocalDate slutDato = LocalDate.of(2022, 4, 4);

        int expectedAntalKlipSolgtIPeriode = 16;

        //Act
        int antal = controller.antalSolgteKlipPeriode(startDato, slutDato);

        //Assert
        assertEquals(expectedAntalKlipSolgtIPeriode, antal);
        verify(mockedSalgsLinje, atLeast(2)).getProduktAntal();
        verify(mockedSalgsLinje, atMost(2)).getProduktAntal();
    }

    @Order(5)
    @Test
    void antalSolgteKlipPeriode_startdato1DagEfterSalgPaabegyndtDato()
    {

        //Arrange
        when(mockedKlippekort.getAntalKlip()).thenReturn(4);

        when(mockedSalgsLinje.getProdukt()).thenReturn(mockedKlippekort);
        when(mockedSalgsLinje.getProduktAntal()).thenReturn(1, 3);

        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);

        mockedSalgListe.add(mockedSalg);

        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
        when(mockedSalg.getSalgsLinjer()).thenReturn(mockedSalgsLinjerListe);

        LocalDate startDato = LocalDate.of(2022, 4, 2);
        LocalDate slutDato = LocalDate.of(2022, 4, 4);

        int expectedAntalKlipSolgtIPeriode = 0;

        //Act
        int antal = controller.antalSolgteKlipPeriode(startDato, slutDato);

        //Assert
        assertEquals(expectedAntalKlipSolgtIPeriode, antal);
        verify(mockedSalgsLinje, atLeast(0)).getProduktAntal();
        verify(mockedSalgsLinje, atMost(0)).getProduktAntal();
    }

    @Order(6)
    @Test
    void antalSolgteKlipPeriode_2SalgDatoerIndenforOgUdenforPeriode()
    {
        //Arrange
        Salg mockedSalg2 = mock(Salg.class);
        mockedSalgListe.add(mockedSalg2);
        when(mockedSalg2.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 3, 31));

        when(mockedKlippekort.getAntalKlip()).thenReturn(4);

        when(mockedSalgsLinje.getProdukt()).thenReturn(mockedKlippekort);
        when(mockedSalgsLinje.getProduktAntal()).thenReturn(1, 3);

        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);

        mockedSalgListe.add(mockedSalg);

        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
        when(mockedSalg.getSalgsLinjer()).thenReturn(mockedSalgsLinjerListe);

        LocalDate startDato = LocalDate.of(2022, 4, 2);
        LocalDate slutDato = LocalDate.of(2022, 4, 4);

        int expectedAntalKlipSolgtIPeriode = 0;

        //Act
        int antal = controller.antalSolgteKlipPeriode(startDato, slutDato);

        //Assert
        assertEquals(expectedAntalKlipSolgtIPeriode, antal);
        verify(mockedSalgsLinje, atLeast(0)).getProduktAntal();
        verify(mockedSalgsLinje, atMost(0)).getProduktAntal();
        verify(mockedSalg2, atMost(0)).getBetalingsform();
    }

    @Order(7)
    @Test
    void antalSolgteKlipPeriode_produktIkkeInstanceOfKlippekort()
    {
        //Arrange
        when(mockedKlippekort.getAntalKlip()).thenReturn(4);

        when(mockedSalgsLinje.getProdukt()).thenReturn(mockedKlippekort, mockedKlippekort);
        when(mockedSalgsLinje.getProduktAntal()).thenReturn(1, 3);

        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);

        SalgsLinje mockedSalgsLinje2 = mock(SalgsLinje.class);
        mockedSalgsLinjerListe.add(mockedSalgsLinje2);
        when(mockedSalgsLinje2.getProdukt()).thenReturn(mockedProdukt);

        mockedSalgListe.add(mockedSalg);

        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
        when(mockedSalg.getSalgsLinjer()).thenReturn(mockedSalgsLinjerListe);

        LocalDate startDato = LocalDate.of(2022, 4, 1);
        LocalDate slutDato = LocalDate.of(2022, 4, 4);

        int expectedAntalKlipSolgtIPeriode = 16;

        //Act
        int antal = controller.antalSolgteKlipPeriode(startDato, slutDato);

        //Assert
        assertEquals(expectedAntalKlipSolgtIPeriode, antal);
        verify(mockedSalgsLinje, atLeast(2)).getProduktAntal();
        verify(mockedSalgsLinje, atMost(2)).getProduktAntal();
        verify(mockedSalgsLinje2, atMost(1)).getProdukt();
    }

    //------------------------------------- antalForbrugteKlipPeriode --------------------------------------------

    @Order(8)
    @Test
    void antalForbrugteKlipPeriode_startdatoSammeSomSalgPaabegyndtDato_2Salgslinjer()
    {
        //Arrange
        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);

        mockedSalgListe.add(mockedSalg);

        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
        when(mockedSalg.getSalgsLinjer()).thenReturn(mockedSalgsLinjerListe);

        when(mockedSalg.getBetalingsform()).thenReturn(Betalingsform.KLIPPEKORT);
        when(mockedSalg.getPrisliste()).thenReturn(mockedPrisliste);
        when(mockedPrisliste.findPrislisteProduktUdFraProdukt(mockedProdukt)).
                thenReturn(mockedFredagsbarPrislisteProdukt);
        when(mockedFredagsbarPrislisteProdukt.getAntalKlipSomPris()).thenReturn(5, 2);
        when(mockedSalgsLinje.getProduktAntal()).thenReturn(2, 4);
        when(mockedSalgsLinje.getProdukt()).thenReturn(mockedProdukt);

        LocalDate startDato = LocalDate.of(2022, 4, 1);
        LocalDate slutDato = LocalDate.of(2022, 4, 4);

        int expectedForbrugteKlip = 18;

        //Act
        int antal = controller.antalForbrugteKlipPeriode(startDato, slutDato);

        //Assert
        assertEquals(expectedForbrugteKlip, antal);
        verify(mockedSalgsLinje, atLeast(2)).getProduktAntal();
        verify(mockedSalgsLinje, atMost(2)).getProduktAntal();
    }

    @Order(9)
    @Test
    void antalForbrugteKlipPeriode_startdatoSammeSomSalgPaabegyndtDato_3Salgslinjer()
    {
        //Arrange
        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);

        mockedSalgListe.add(mockedSalg);

        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
        when(mockedSalg.getSalgsLinjer()).thenReturn(mockedSalgsLinjerListe);

        when(mockedSalg.getBetalingsform()).thenReturn(Betalingsform.KLIPPEKORT);
        when(mockedSalg.getPrisliste()).thenReturn(mockedPrisliste);
        when(mockedPrisliste.findPrislisteProduktUdFraProdukt(mockedProdukt)).
                thenReturn(mockedFredagsbarPrislisteProdukt);
        when(mockedFredagsbarPrislisteProdukt.getAntalKlipSomPris()).thenReturn(5,3,1);
        when(mockedSalgsLinje.getProduktAntal()).thenReturn(2, 4, 3);
        when(mockedSalgsLinje.getProdukt()).thenReturn(mockedProdukt);

        LocalDate startDato = LocalDate.of(2022, 4, 1);
        LocalDate slutDato = LocalDate.of(2022, 4, 4);

        int expectedForbrugteKlip = 25;

        //Act
        int antal = controller.antalForbrugteKlipPeriode(startDato, slutDato);

        //Assert
        assertEquals(expectedForbrugteKlip, antal);
        verify(mockedSalgsLinje, atLeast(3)).getProduktAntal();
        verify(mockedSalgsLinje, atMost(3)).getProduktAntal();
    }

    @Order(10)
    @Test
    void antalForbrugteKlipPeriode_betalingsformIkkeKlippekort()
    {
        //Arrange
        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);

        mockedSalgListe.add(mockedSalg);

        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
        when(mockedSalg.getSalgsLinjer()).thenReturn(mockedSalgsLinjerListe);

        when(mockedSalg.getBetalingsform()).thenReturn(Betalingsform.KONTANT);
        when(mockedSalg.getPrisliste()).thenReturn(mockedPrisliste);
        when(mockedPrisliste.findPrislisteProduktUdFraProdukt(mockedProdukt)).
                thenReturn(mockedFredagsbarPrislisteProdukt);
        when(mockedFredagsbarPrislisteProdukt.getAntalKlipSomPris()).thenReturn(5,3,1);
        when(mockedSalgsLinje.getProduktAntal()).thenReturn(2, 4, 3);
        when(mockedSalgsLinje.getProdukt()).thenReturn(mockedProdukt);

        LocalDate startDato = LocalDate.of(2022, 4, 1);
        LocalDate slutDato = LocalDate.of(2022, 4, 4);

        int expectedForbrugteKlip = 0;

        //Act
        int antal = controller.antalForbrugteKlipPeriode(startDato, slutDato);

        //Assert
        assertEquals(expectedForbrugteKlip, antal);
        verify(mockedSalgsLinje, atLeast(0)).getProduktAntal();
        verify(mockedSalgsLinje, atMost(0)).getProduktAntal();
    }

    @Order(11)
    @Test
    void antalForbrugteKlipPeriode_2SalgBetalingsformKlippekortBegge()
    {
        //Arrange
        mockedSalgListe.add(mockedSalg);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);

        mockedSalgListe.add(mockedSalg);

        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
        when(mockedSalg.getSalgsLinjer()).thenReturn(mockedSalgsLinjerListe);

        when(mockedSalg.getBetalingsform()).thenReturn(Betalingsform.KLIPPEKORT);
        when(mockedSalg.getPrisliste()).thenReturn(mockedPrisliste);
        when(mockedPrisliste.findPrislisteProduktUdFraProdukt(mockedProdukt)).
                thenReturn(mockedFredagsbarPrislisteProdukt);
        when(mockedFredagsbarPrislisteProdukt.getAntalKlipSomPris()).thenReturn(5,3,1,2,3,1);
        when(mockedSalgsLinje.getProduktAntal()).thenReturn(2, 4, 3, 1, 2, 2);
        when(mockedSalgsLinje.getProdukt()).thenReturn(mockedProdukt);

        LocalDate startDato = LocalDate.of(2022, 4, 1);
        LocalDate slutDato = LocalDate.of(2022, 4, 4);

        int expectedForbrugteKlip = 35;

        //Act
        int antal = controller.antalForbrugteKlipPeriode(startDato, slutDato);

        //Assert
        assertEquals(expectedForbrugteKlip, antal);
        verify(mockedSalgsLinje, atLeast(6)).getProduktAntal();
        verify(mockedSalgsLinje, atMost(6)).getProduktAntal();
    }

    @Order(12)
    @Test
    void antalForbrugteKlipPeriode_2SalgBetalingsformKlippekortOgKontant()
    {
        //Arrange
        mockedSalgListe.add(mockedSalg);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);

        mockedSalgListe.add(mockedSalg);

        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
        when(mockedSalg.getSalgsLinjer()).thenReturn(mockedSalgsLinjerListe);

        when(mockedSalg.getBetalingsform()).thenReturn(Betalingsform.KLIPPEKORT, Betalingsform.KONTANT);
        when(mockedSalg.getPrisliste()).thenReturn(mockedPrisliste);
        when(mockedPrisliste.findPrislisteProduktUdFraProdukt(mockedProdukt)).
                thenReturn(mockedFredagsbarPrislisteProdukt);
        when(mockedFredagsbarPrislisteProdukt.getAntalKlipSomPris()).thenReturn(5,3,1,2,3,1);
        when(mockedSalgsLinje.getProduktAntal()).thenReturn(2, 4, 3, 1, 2, 2);
        when(mockedSalgsLinje.getProdukt()).thenReturn(mockedProdukt);

        LocalDate startDato = LocalDate.of(2022, 4, 1);
        LocalDate slutDato = LocalDate.of(2022, 4, 4);

        int expectedForbrugteKlip = 25;

        //Act
        int antal = controller.antalForbrugteKlipPeriode(startDato, slutDato);

        //Assert
        assertEquals(expectedForbrugteKlip, antal);
        verify(mockedSalgsLinje, atLeast(3)).getProduktAntal();
        verify(mockedSalgsLinje, atMost(3)).getProduktAntal();
    }

    @Order(13)
    @Test
    void antalForbrugteKlipPeriode_2SalgDatoerIndenforOgUdenforPeriode()
    {
        //Arrange
        Salg mockedSalg2 = mock(Salg.class);
        mockedSalgListe.add(mockedSalg2);
        when(mockedSalg2.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 3, 31));

        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);
        mockedSalgsLinjerListe.add(mockedSalgsLinje);

        mockedSalgListe.add(mockedSalg);

        when(mockedSalg.getSalgPaabegyndtDato()).thenReturn(LocalDate.of(2022, 4, 1));
        when(mockedSalg.getSalgsLinjer()).thenReturn(mockedSalgsLinjerListe);

        when(mockedSalg.getBetalingsform()).thenReturn(Betalingsform.KLIPPEKORT);
        when(mockedSalg.getPrisliste()).thenReturn(mockedPrisliste);
        when(mockedPrisliste.findPrislisteProduktUdFraProdukt(mockedProdukt)).
                thenReturn(mockedFredagsbarPrislisteProdukt);
        when(mockedFredagsbarPrislisteProdukt.getAntalKlipSomPris()).thenReturn(5,3,1,2,3,1);
        when(mockedSalgsLinje.getProduktAntal()).thenReturn(2, 4, 3, 1, 2, 2);
        when(mockedSalgsLinje.getProdukt()).thenReturn(mockedProdukt);

        LocalDate startDato = LocalDate.of(2022, 4, 1);
        LocalDate slutDato = LocalDate.of(2022, 4, 4);

        int expectedForbrugteKlip = 25;

        //Act
        int antal = controller.antalForbrugteKlipPeriode(startDato, slutDato);

        //Assert
        assertEquals(expectedForbrugteKlip, antal);
        verify(mockedSalgsLinje, atLeast(3)).getProduktAntal();
        verify(mockedSalgsLinje, atMost(3)).getProduktAntal();
        verify(mockedSalg2, atMost(0)).getBetalingsform();
    }
}
