package application.model;

import application.controller.Controller;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import storage.StorageI;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SalgTest
{
    private Controller controller = new Controller();

    //--------------------------------- samletPris ------------------------------------

    @Order(1)
    @Test
    void samletPrisMedPant_()
    {
        //Arrange
        Produktkategori flaskeoel = controller.opretProduktkategori("Flasker");
        Produkt flaskeKlosterbryg = flaskeoel.createDrikkevareProdukt("Klosterbryg", 70.0
                , false, 0.6);
        Produkt flaskeSweetGeorgiaBrown = flaskeoel.createDrikkevareProdukt("Sweet Georgia Brown"
                , 70.0, false,0.6);

        Produktkategori fustage = controller.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                false,20.0);
        Produkt fustageJazzClassic = fustage.createDrikkevareProdukt("Jazz Classic", 625.0,
                false,25.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0,
                true, PantType.FUSTAGEPANT);

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        salg.opretEllerTilfoejTilSalgsLinje(3, fustageKlosterbryg);
        salg.opretEllerTilfoejTilSalgsLinje(3, fustagePant);
        salg.opretEllerTilfoejTilSalgsLinje(2, flaskeKlosterbryg);
        salg.opretEllerTilfoejTilSalgsLinje(4, flaskeSweetGeorgiaBrown);

        double expectedSamletPris = 3345;

        //Act
        double samletPris = salg.samletPrisMedPant();

        //Assert
        assertEquals(expectedSamletPris, samletPris);
    }

    //--------------------------------- samletPrisUdenPant ------------------------------------

    @Order(2)
    @Test
    void samletPrisUdenPant_()
    {
        //Arrange
        Produktkategori flaskeoel = controller.opretProduktkategori("Flasker");
        Produkt flaskeKlosterbryg = flaskeoel.createDrikkevareProdukt("Klosterbryg", 70.0
                , false, 0.6);
        Produkt flaskeSweetGeorgiaBrown = flaskeoel.createDrikkevareProdukt("Sweet Georgia Brown"
                , 70.0, false,0.6);

        Produktkategori fustage = controller.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                false,20.0);
        Produkt fustageJazzClassic = fustage.createDrikkevareProdukt("Jazz Classic", 625.0,
                false,25.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0,
                true, PantType.FUSTAGEPANT);

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        salg.opretEllerTilfoejTilSalgsLinje(3, fustageKlosterbryg);
        salg.opretEllerTilfoejTilSalgsLinje(3, fustagePant);
        salg.opretEllerTilfoejTilSalgsLinje(2, flaskeKlosterbryg);
        salg.opretEllerTilfoejTilSalgsLinje(4, flaskeSweetGeorgiaBrown);

        double expectedSamletPrisUdenPant = 2745;

        //Act
        double samletPrisUdenPant = salg.samletPrisUdenPant();

        //Assert
        assertEquals(expectedSamletPrisUdenPant, samletPrisUdenPant);
    }

    //--------------------------------- samletPrisPant ------------------------------------

    @Order(3)
    @Test
    void samletPrisPant_()
    {
        //Arrange
        Produktkategori flaskeoel = controller.opretProduktkategori("Flasker");
        Produkt flaskeKlosterbryg = flaskeoel.createDrikkevareProdukt("Klosterbryg", 70.0
                , false, 0.6);
        Produkt flaskeSweetGeorgiaBrown = flaskeoel.createDrikkevareProdukt("Sweet Georgia Brown"
                , 70.0, false,0.6);

        Produktkategori fustage = controller.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                false,20.0);
        Produkt fustageJazzClassic = fustage.createDrikkevareProdukt("Jazz Classic", 625.0,
                false,25.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0,
                true, PantType.FUSTAGEPANT);

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        salg.opretEllerTilfoejTilSalgsLinje(3, fustageKlosterbryg);
        salg.opretEllerTilfoejTilSalgsLinje(3, fustagePant);
        salg.opretEllerTilfoejTilSalgsLinje(2, flaskeKlosterbryg);
        salg.opretEllerTilfoejTilSalgsLinje(4, flaskeSweetGeorgiaBrown);

        double expectedSamletPrisPant = 600;

        //Act
        double samletPrisPant = salg.samletPrisPant();

        //Assert
        assertEquals(expectedSamletPrisPant, samletPrisPant);
    }

    //--------------------------------- samletPantPrisRetur ------------------------------------

    @Order(4)
    @Test
    void samletPantPrisRetur_()
    {
        //Arrange
        Produktkategori fustage = controller.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                false, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0, true,
                PantType.FUSTAGEPANT);

        Produktkategori kulsyre = controller.opretProduktkategori("Kulsyre");
        Produkt seksKg = kulsyre.createKilovareProdukt("Kulsyre", 400, false,
                6000);
        Produkt kulsyrePant = kulsyre.createPantProdukt("Pant, kulsyre", 1000.0, true,
                PantType.KULSYREPANT);

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        ArrayList<SalgsLinje> salgsLinjeFustageKlosterbryg = salg.opretEllerTilfoejTilSalgsLinje(
                3, fustageKlosterbryg);
        ArrayList<SalgsLinje> salgsLinjeFustagePantListe = salg.opretEllerTilfoejTilSalgsLinje(
                3, fustagePant);
        ArrayList<SalgsLinje> salgsLinjeSeksKgKylsyre = salg.opretEllerTilfoejTilSalgsLinje(
                4, seksKg);
        ArrayList<SalgsLinje> salgsLinjeKulsyrePantListe = salg.opretEllerTilfoejTilSalgsLinje(
                4, kulsyrePant);

        ((TilbageleveresSalgsLinje) salgsLinjeKulsyrePantListe.get(0)).setAntalTilbageleveret(2);
        ((TilbageleveresSalgsLinje) salgsLinjeFustagePantListe.get(0)).setAntalTilbageleveret(3);

        double expectedSamletPantPrisRetur = 600+2000;

        //Act
        double samletPantPrisRetur = salg.samletPantPrisRetur();

        //Assert
        assertEquals(expectedSamletPantPrisRetur, samletPantPrisRetur);
    }

    //--------------------------- createSalgsLinjeEllerTilbageleveresSalgsLinje ------------------------------
    //metoden lavet private (kan ikke testes selvstændigt)

    /*@Order(5)
    @Test
    void createSalgsLinjeEllerTilbageleveresSalgsLinje_laverRigtigeTyper()
    {
        //Arrange
        Controller testController = Controller.getTestController();

        Produktkategori fustage = testController.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                false, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0, true,
                Pant.PantType.FUSTAGEPANT);

        Produktkategori kulsyre = testController.opretProduktkategori("Kulsyre");
        Produkt seksKg = kulsyre.createKilovareProdukt("Kulsyre", 400, false,
                6000);
        Produkt kulsyrePant = kulsyre.createPantProdukt("Pant, kulsyre", 1000.0, true,
                Pant.PantType.KULSYREPANT);

        Salg salg = testController.opretSalg();

        Prisliste prisliste = testController.opretPrisliste();
        salg.setPrisliste(prisliste);
        testController.udfyldPrislisteMedPrislisteProdukter(prisliste);

        //Act
        SalgsLinje salgsLinjeFustageKlosterbryg = salg.createSalgsLinjeEllerTilbageleveresSalgsLinje(
                3, fustageKlosterbryg);
        SalgsLinje salgsLinjeFustagePant = salg.createSalgsLinjeEllerTilbageleveresSalgsLinje(
                3, fustagePant);

        //Assert
        assertTrue(salgsLinjeFustagePant instanceof TilbageleveresSalgsLinje);
        assertFalse(salgsLinjeFustageKlosterbryg instanceof TilbageleveresSalgsLinje);
    }

    @Order(6)
    @Test
    void createSalgsLinjeEllerTilbageleveresSalgsLinje_prislisteNullException()
    {
        //Arrange
        Controller testController = Controller.getTestController();

        Produktkategori fustage = testController.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                false, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0, true,
                Pant.PantType.FUSTAGEPANT);

        Produktkategori kulsyre = testController.opretProduktkategori("Kulsyre");
        Produkt seksKg = kulsyre.createKilovareProdukt("Kulsyre", 400, false,
                6000);
        Produkt kulsyrePant = kulsyre.createPantProdukt("Pant, kulsyre", 1000.0, true,
                Pant.PantType.KULSYREPANT);

        Salg salg = testController.opretSalg();

        Prisliste prisliste = testController.opretPrisliste();
//        salg.setPrisliste(prisliste); //salgets prisliste ikke sat

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                salg.createSalgsLinjeEllerTilbageleveresSalgsLinje(3, fustageKlosterbryg));
        assertTrue(exception.getMessage().contains("Salget har ingen prisliste (prisliste er null)"));
    }

    @Order(7)
    @Test
    void createSalgsLinjeEllerTilbageleveresSalgsLinje_prislisteIkkeUdfyldtException()
    {
        //Arrange
        Controller testController = Controller.getTestController();

        Produktkategori fustage = testController.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                false, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0, true,
                Pant.PantType.FUSTAGEPANT);

        Produktkategori kulsyre = testController.opretProduktkategori("Kulsyre");
        Produkt seksKg = kulsyre.createKilovareProdukt("Kulsyre", 400, false,
                6000);
        Produkt kulsyrePant = kulsyre.createPantProdukt("Pant, kulsyre", 1000.0, true,
                Pant.PantType.KULSYREPANT);

        Salg salg = testController.opretSalg();

        Prisliste prisliste = testController.opretPrisliste();
        salg.setPrisliste(prisliste);
//        testController.udfyldPrislisteMedPrislisteProdukter(prisliste); //prisliste ikke blevet udfyldt

        //Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () ->
                salg.createSalgsLinjeEllerTilbageleveresSalgsLinje(3, fustageKlosterbryg));
        assertTrue(exception.getMessage().contains("Salgets prisliste er ikke udfyldt"));
    }*/

    //--------------------------- opretEllerTilfoejTilSalgsLinje ------------------------------

    @Order(8)
    @Test
    void opretEllerTilfoejTilSalgsLinje_opretNyeSalgsLinjerMedPantProdukter()
    {
        //Arrange
        Produktkategori fustage = controller.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                true, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0, true,
                PantType.FUSTAGEPANT);
        ((Drikkevare) fustageKlosterbryg).setPant(((Pant) fustagePant));

        Produktkategori kulsyre = controller.opretProduktkategori("Kulsyre");
        Produkt seksKg = kulsyre.createKilovareProdukt("Kulsyre", 400, true,
                6000);
        Produkt kulsyrePant = kulsyre.createPantProdukt("Pant, kulsyre", 1000.0, true,
                PantType.KULSYREPANT);
        ((Kilovare) seksKg).setPant(((Pant) kulsyrePant));

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        //der laves 2 pant salgslinjer ud over de to for fustageKlosterbryg og seksKg
        int expectedSizeSalgslinjerISalg = 4;

        //Act
        ArrayList<SalgsLinje> salgLinerReturFustageKlosterbryg =
                salg.opretEllerTilfoejTilSalgsLinje(2, fustageKlosterbryg);
        ArrayList<SalgsLinje> salgLinerReturSeksKg = salg.opretEllerTilfoejTilSalgsLinje(4, seksKg);

        //Assert
        //salgslinjen for fustagepant, der blev oprettet, ligger på indeks 1 i listen der returneres
        SalgsLinje salgsLinjePantFustage = salgLinerReturFustageKlosterbryg.get(1);
        //salgslinjen for kylsyrepant, der blev oprettet, ligger på indeks 1 i listen der returneres
        SalgsLinje salgsLinjePantKylsyre = salgLinerReturSeksKg.get(1);



        assertEquals(expectedSizeSalgslinjerISalg, salg.getSalgsLinjer().size());
        assertTrue(salg.getSalgsLinjer().contains(salgsLinjePantFustage));
        assertTrue(salg.getSalgsLinjer().contains(salgsLinjePantKylsyre));
    }

    @Order(9)
    @Test
    void opretEllerTilfoejTilSalgsLinje_opretNyeSalgsLinjerMedPantProdukter_derErIForvejenAndreSalgsLinjerMedPant()
    {
        //Arrange
//        Controller testController = Controller.getTestController();

        Produktkategori fustage = controller.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                true, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0, true,
                PantType.FUSTAGEPANT);
        ((Drikkevare) fustageKlosterbryg).setPant(((Pant) fustagePant));

        Produktkategori kulsyre = controller.opretProduktkategori("Kulsyre");
        Produkt seksKg = kulsyre.createKilovareProdukt("Kulsyre", 400, true,
                6000);
        Produkt kulsyrePant = kulsyre.createPantProdukt("Pant, kulsyre", 1000.0, true,
                PantType.KULSYREPANT);
        ((Kilovare) seksKg).setPant(((Pant) kulsyrePant));

        Produkt fustageJazzClassic = fustage.createDrikkevareProdukt("Jazz Classic", 625.0,
                true, 25.0);
        ((Drikkevare) fustageJazzClassic).setPant(((Pant) fustagePant));

        Produkt fireKg = kulsyre.createKilovareProdukt("Kulsyre",400,true,4000);
        ((Kilovare) fireKg).setPant(((Pant) kulsyrePant));

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        salg.opretEllerTilfoejTilSalgsLinje(2, fustageKlosterbryg);
        salg.opretEllerTilfoejTilSalgsLinje(4, seksKg);

        //der laves 2 salgslinjer i alt for fustageKlosterbryg og seksKg, der er 4 i forvejen
        int expectedSizeSalgslinjerISalg = 6;

        int expectedProduktAntalFustagePant = 7;
        int expectedProduktAntalKylsyrePant = 11;

        //Act
        ArrayList<SalgsLinje> salgLinerReturFustageJazzClassic =
                salg.opretEllerTilfoejTilSalgsLinje(5, fustageJazzClassic);
        ArrayList<SalgsLinje> salgLinerReturFireKg = salg.opretEllerTilfoejTilSalgsLinje(7, fireKg);

        //Assert
        //salgslinjen for fustagepant, der blev tilføjet til, ligger på indeks 1 i listen der returneres
        SalgsLinje salgsLinjePantFustage = salgLinerReturFustageJazzClassic.get(1);
        //salgslinjen for kylsyrepant, der blev tilføjet til, ligger på indeks 1 i listen der returneres
        SalgsLinje salgsLinjePantKylsyre = salgLinerReturFireKg.get(1);

        assertEquals(expectedSizeSalgslinjerISalg, salg.getSalgsLinjer().size());
        assertTrue(salg.getSalgsLinjer().contains(salgsLinjePantFustage));
        assertTrue(salg.getSalgsLinjer().contains(salgsLinjePantKylsyre));
        assertEquals(expectedProduktAntalFustagePant, salgsLinjePantFustage.getProduktAntal());
        assertEquals(expectedProduktAntalKylsyrePant, salgsLinjePantKylsyre.getProduktAntal());
    }

    @Order(10)
    @Test
    void opretEllerTilfoejTilSalgsLinje_opretNyeSalgsLinjerUdenPant()
    {
        //Arrange
//        Controller testController = Controller.getTestController();

        Produktkategori fustage = controller.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                true, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0, true,
                PantType.FUSTAGEPANT);
        ((Drikkevare) fustageKlosterbryg).setPant(((Pant) fustagePant));

        Produktkategori kulsyre = controller.opretProduktkategori("Kulsyre");
        Produkt seksKg = kulsyre.createKilovareProdukt("Kulsyre", 400, true,
                6000);
        Produkt kulsyrePant = kulsyre.createPantProdukt("Pant, kulsyre", 1000.0, true,
                PantType.KULSYREPANT);
        ((Kilovare) seksKg).setPant(((Pant) kulsyrePant));

        Produkt fustageJazzClassic = fustage.createDrikkevareProdukt("Jazz Classic", 625.0,
                true, 25.0);
        ((Drikkevare) fustageJazzClassic).setPant(((Pant) fustagePant));

        Produkt fireKg = kulsyre.createKilovareProdukt("Kulsyre",400,true,4000);
        ((Kilovare) fireKg).setPant(((Pant) kulsyrePant));

        Produktkategori spiritus = controller.opretProduktkategori("Spiritus");
        Produkt whiskey50cl45Procent = spiritus.createDrikkevareProdukt("Whisky 45% 50cl rør",
                599.0, false, 0.50);
        Produkt lyngGin4cl = spiritus.createDrikkevareProdukt("Lyng gin 4 cl", 40.0,
                false, 0.04);

        Produktkategori anlaeg = controller.opretProduktkategori("Anlæg");
        Produkt enHane = anlaeg.createAnlaegProdukt("Én hane", 250.0, true,
                1, true);

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        salg.opretEllerTilfoejTilSalgsLinje(2, fustageKlosterbryg);
        salg.opretEllerTilfoejTilSalgsLinje(4, seksKg);
        salg.opretEllerTilfoejTilSalgsLinje(5, fustageJazzClassic);
        salg.opretEllerTilfoejTilSalgsLinje(7, fireKg);

        //der laves 3 salgslinjer, og der er 6 i forvejen
        int expectedSizeSalgslinjerISalg = 9;

        int expectedSizeListRetur = 1;

        //Act
        ArrayList<SalgsLinje> salgLinerReturWhiskey50cl45Procent =
                salg.opretEllerTilfoejTilSalgsLinje(3, whiskey50cl45Procent);
        salg.opretEllerTilfoejTilSalgsLinje(4, lyngGin4cl);
        ArrayList<SalgsLinje> salgLinerReturEnHane =
                salg.opretEllerTilfoejTilSalgsLinje(2, enHane);

        //Assert
        //salgslinjen for whiskey50cl45Procent ligger på indeks 0 i listen der returneres
        SalgsLinje salgsLinjeWhiskey50cl45Procent = salgLinerReturWhiskey50cl45Procent.get(0);

        assertEquals(expectedSizeSalgslinjerISalg, salg.getSalgsLinjer().size());
        assertEquals(expectedSizeListRetur, salgLinerReturWhiskey50cl45Procent.size());
        assertEquals(whiskey50cl45Procent, salgsLinjeWhiskey50cl45Procent.getProdukt());
        assertEquals(enHane, salgLinerReturEnHane.get(0).getProdukt());
    }

    @Order(11)
    @Test
    void opretEllerTilfoejTilSalgsLinje_nytProduktMedPant()
    {
        //Arrange
//        Controller testController = Controller.controller();

        Produktkategori fustage = controller.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                true, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0, true,
                PantType.FUSTAGEPANT);
        ((Drikkevare) fustageKlosterbryg).setPant(((Pant) fustagePant));

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        //der laves 2 nye salgslinjer, og der er ingen i forvejen
        int expectedSizeSalgslinjerISalg = 2;

        int expectedSizeListRetur = 2;
        int expectedProduktAntal = 3;

        //Act
        ArrayList<SalgsLinje> salgLinerReturFustageKlosterbryg =
                salg.opretEllerTilfoejTilSalgsLinje(3, fustageKlosterbryg);

        //Assert
        //salgslinjen for fustageKlosterbryg ligger på indeks 0 i listen der returneres
        SalgsLinje salgsLinjeFustageKlosterbryg = salgLinerReturFustageKlosterbryg.get(0);

        //salgslinjen for fustagePant ligger på indeks 1 i listen der returneres
        SalgsLinje salgsLinjeFustagePant = salgLinerReturFustageKlosterbryg.get(1);

        assertEquals(expectedSizeSalgslinjerISalg, salg.getSalgsLinjer().size());
        assertEquals(expectedSizeListRetur, salgLinerReturFustageKlosterbryg.size());
        assertEquals(expectedProduktAntal, salgsLinjeFustageKlosterbryg.getProduktAntal());
        assertTrue(salgsLinjeFustagePant.getProdukt() instanceof Pant);
        assertEquals(expectedProduktAntal, salgsLinjeFustagePant.getProduktAntal());
    }

    @Order(12)
    @Test
    void opretEllerTilfoejTilSalgsLinje_tilfoejSammeProdukt2Gange()
    {
        //Arrange
//        Controller testController = Controller.getTestController();

        Produktkategori fustage = controller.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                true, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0, true,
                PantType.FUSTAGEPANT);
        ((Drikkevare) fustageKlosterbryg).setPant(((Pant) fustagePant));

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        salg.opretEllerTilfoejTilSalgsLinje(2, fustageKlosterbryg);

        //der laves ingen nye salgslinjer, og der er 2 i forvejen
        int expectedSizeSalgslinjerISalg = 2;

        int expectedSizeListRetur = 2;
        int expectedProduktAntal = 5;

        //Act
        ArrayList<SalgsLinje> salgLinerReturFustageKlosterbryg =
                salg.opretEllerTilfoejTilSalgsLinje(3, fustageKlosterbryg);

        //Assert
        //salgslinjen for fustageKlosterbryg ligger på indeks 0 i listen der returneres
        SalgsLinje salgsLinjeFustageKlosterbryg = salgLinerReturFustageKlosterbryg.get(0);

        //salgslinjen for fustagePant ligger på indeks 1 i listen der returneres
        SalgsLinje salgsLinjeFustagePant = salgLinerReturFustageKlosterbryg.get(1);

        assertEquals(expectedSizeSalgslinjerISalg, salg.getSalgsLinjer().size());
        assertEquals(expectedSizeListRetur, salgLinerReturFustageKlosterbryg.size());
        assertEquals(expectedProduktAntal, salgsLinjeFustageKlosterbryg.getProduktAntal());
        assertTrue(salgsLinjeFustagePant.getProdukt() instanceof Pant);
        assertEquals(expectedProduktAntal, salgsLinjeFustagePant.getProduktAntal());
    }

    @Order(13)
    @Test
    void opretEllerTilfoejTilSalgsLinje_tilfoejSammeProdukt3Gange()
    {
        //Arrange
//        Controller testController = Controller.getTestController();

        Produktkategori fustage = controller.opretProduktkategori("Fustager");
        Produkt fustageKlosterbryg = fustage.createDrikkevareProdukt("Klosterbryg", 775.0,
                true, 20.0);
        Produkt fustagePant = fustage.createPantProdukt("Pant, fustage", 200.0, true,
                PantType.FUSTAGEPANT);
        ((Drikkevare) fustageKlosterbryg).setPant(((Pant) fustagePant));

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        salg.opretEllerTilfoejTilSalgsLinje(2, fustageKlosterbryg);
        salg.opretEllerTilfoejTilSalgsLinje(1, fustageKlosterbryg);

        //der laves ingen nye salgslinjer, og der er 2 i forvejen
        int expectedSizeSalgslinjerISalg = 2;

        int expectedSizeListRetur = 2;
        int expectedProduktAntal = 8;

        //Act
        ArrayList<SalgsLinje> salgLinerReturFustageKlosterbryg =
                salg.opretEllerTilfoejTilSalgsLinje(5, fustageKlosterbryg);

        //Assert
        //salgslinjen for fustageKlosterbryg ligger på indeks 0 i listen der returneres
        SalgsLinje salgsLinjeFustageKlosterbryg = salgLinerReturFustageKlosterbryg.get(0);

        //salgslinjen for fustagePant ligger på indeks 1 i listen der returneres
        SalgsLinje salgsLinjeFustagePant = salgLinerReturFustageKlosterbryg.get(1);

        assertEquals(expectedSizeSalgslinjerISalg, salg.getSalgsLinjer().size());
        assertEquals(expectedSizeListRetur, salgLinerReturFustageKlosterbryg.size());
        assertEquals(expectedProduktAntal, salgsLinjeFustageKlosterbryg.getProduktAntal());
        assertTrue(salgsLinjeFustagePant.getProdukt() instanceof Pant);
        assertEquals(expectedProduktAntal, salgsLinjeFustagePant.getProduktAntal());
    }

    @Order(14)
    @Test
    void opretEllerTilfoejTilSalgsLinje_opret1NySalgsLinjeSpiritus()
    {
        //Arrange
//        Controller testController = Controller.getTestController();

        Produktkategori spiritus = controller.opretProduktkategori("Spiritus");
        Produkt whiskey50cl45Procent = spiritus.createDrikkevareProdukt("Whisky 45% 50cl rør",
                599.0, false, 0.50);

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        //der laves 2 pant salgslinjer ud over de to for fustageKlosterbryg og seksKg
        int expectedSizeSalgslinjerISalg = 1;

        //Act
        ArrayList<SalgsLinje> salgLinerReturWhiskey50cl45Procent =
                salg.opretEllerTilfoejTilSalgsLinje(2, whiskey50cl45Procent);

        //Assert
        SalgsLinje salgsLinjeWhiskey50cl45Procent = salgLinerReturWhiskey50cl45Procent.get(0);

        assertEquals(expectedSizeSalgslinjerISalg, salg.getSalgsLinjer().size());
        assertTrue(salg.getSalgsLinjer().contains(salgsLinjeWhiskey50cl45Procent));
    }

    @Order(15)
    @Test
    void opretEllerTilfoejTilSalgsLinje_opret1NySalgsLinjeDrikkevareUdenPant()
    {
        //Arrange
//        Controller testController = Controller.getTestController();

        Produktkategori flaskeoel = controller.opretProduktkategori("Flasker");
        Produkt flaskeKlosterbryg = flaskeoel.createDrikkevareProdukt("Klosterbryg", 70.0,
                false, 0.6);

        Salg salg = controller.opretSalg();

        Prisliste prisliste = controller.opretPrisliste();
        salg.setPrisliste(prisliste);
        controller.udfyldPrislisteMedPrislisteProdukter(prisliste);

        salg.opretEllerTilfoejTilSalgsLinje(1, flaskeKlosterbryg);
        int expectedSizeSalgslinjerISalg = 1;

        //Act
        ArrayList<SalgsLinje> salgLinerReturFlaskeKlosterbryg =
                salg.opretEllerTilfoejTilSalgsLinje(1, flaskeKlosterbryg);

        //Assert
        SalgsLinje salgsLinjeFlaskeKlosterbryg = salgLinerReturFlaskeKlosterbryg.get(0);

        assertEquals(expectedSizeSalgslinjerISalg, salg.getSalgsLinjer().size());
        assertTrue(salg.getSalgsLinjer().contains(salgsLinjeFlaskeKlosterbryg));
    }

}