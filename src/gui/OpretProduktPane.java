package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import storage.Storage;

import java.util.*;

public class OpretProduktPane extends GridPane {


    //---------------------------------------------------Constances---------------------------------------------------
    private Stage stage;
    private ToggleGroup kategoriGruppe = new ToggleGroup();
    private List<Produktkategori> produktkategorier;
    private List<RadioButton> rdbsKategorier = new ArrayList<>();
    private final TextField txtNavn = new TextField();
    private final TextField txtNormalpris = new TextField();
    private final ListView<Produkt> listViewProdukter = new ListView<>();
    private final Button btnRetProdukt = new Button("Ret produkt");
    private final Button btnSletProdukt = new Button("Slet produkt");
    private final Button btnOpretProdukt = new Button("Opret Produkt");
    private Label lblError = new Label("Forkert eller der mangler input!");
    private Label lblErrorKategori = new Label("Husk valg af kategori!");
    private Label lblTilbageleveres = new Label("Tilbageleveres:");
    private final CheckBox checkBoxTilbageleveres = new CheckBox("Ja/Nej");
    private final Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
    private boolean check;
    private final Controller controller = new Controller();
    private final Storage storage = Storage.getStorage();
    //Radioknapper til valg af type produkt der skal oprettes.
    private final ToggleGroup produktTypeToggle = new ToggleGroup();
    private final RadioButton rdbProdukt = new RadioButton("Produkt");
    private final RadioButton rdbDrikkevare = new RadioButton("Drikkevare");
    private final RadioButton rdbAnlaeg = new RadioButton("Anlæg");
    private final RadioButton rdbKilovare = new RadioButton("Kilovare");
    private final RadioButton rdbPant = new RadioButton("Pant");
    private final RadioButton rdbKlippekort = new RadioButton("Klippekort");
    //Til oprettelse af drikkevare.
    private final Label lblLiter = new Label("Liter:");
    private final TextField txtLiter = new TextField();
    //Til oprettelse af drikkevare.
    private final Label lblKilo = new Label("Gram:");
    private final TextField txtKilo = new TextField();
    //Til oprettelse af anlæg.
    private final Label lblAntalHaner = new Label("Antal Haner:");
    private final ComboBox<Integer> comboAntalHaner = new ComboBox<>();
    private final Label lblBar = new Label("Bar:");
    private final CheckBox checkBoxBar = new CheckBox("Ja/nej");
    //Til oprettelse af pant.
    private final Label lblPantType = new Label("Pant Type:");
    private final ComboBox<PantType> comboPantType = new ComboBox<>();
    //Til oprettelse af klippekort.
    private final Label lblKlippekort = new Label("Klippekort antal:");
    private final TextField txtAntalKlip = new TextField();


    //-----------------------------------------------------Content-----------------------------------------------------

    public OpretProduktPane(Stage stage) {
        //Afstand fra kant og ind.
        this.setPadding(new Insets(20));
        //Afstand mellem noder horisontalt.
        this.setHgap(30);
        //Afstand mellem noder vertikalt.
        this.setVgap(10);
        //Synlighed for linjerne i gridpane.
        this.setGridLinesVisible(false);
        //Baggrundsfarve
        this.setStyle("-fx-background-color: #fff");
        //Stage er bliver sat.
        this.stage = stage;

        //******Label og textField til navn.
        Label lblNavn = new Label("Navn:");
        lblNavn.setPrefWidth(130);
        lblNavn.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.txtNavn.setPrefWidth(300);
        this.txtNavn.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblNavn, 0, 1);
        this.add(this.txtNavn, 1, 1);

        //******Label og textField til normalPris.
        Label lblNormalpris = new Label("Normalpris:");
        lblNormalpris.setPrefWidth(130);
        lblNormalpris.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.txtNormalpris.setPrefWidth(300);
        this.txtNormalpris.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblNormalpris, 0, 2);
        this.add(txtNormalpris, 1, 2);

        //Label og checkbox til tilbageleveres.
        this.lblTilbageleveres.setPrefWidth(130);
        this.lblTilbageleveres.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.lblTilbageleveres, 0, 3);
        this.checkBoxTilbageleveres.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.checkBoxTilbageleveres, 1, 3);

        //******Listener på gruppen for Radiobuttons.
        ChangeListener<Toggle> listenerTogglegroup = (ov, o, n) -> this.valgtProduktkategoriAendret();
        this.kategoriGruppe.selectedToggleProperty().addListener(listenerTogglegroup);

        //******Listview for produkter.
        this.listViewProdukter.setPrefWidth(550);
        this.listViewProdukter.setPrefHeight(800);
        this.listViewProdukter.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.add(listViewProdukter, 3, 1, 2, 13);

        //******Knapper til redigering af eksisterende produkter
        this.btnRetProdukt.setPrefWidth(200);
        this.btnRetProdukt.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.btnRetProdukt, 5, 1);
        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        this.btnRetProdukt.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnRetProdukt.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnRetProdukt.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnRetProdukt.setEffect(null));
        this.btnRetProdukt.setOnAction(actionEvent -> this.retProduktAction());

        this.btnSletProdukt.setPrefWidth(200);
        this.btnSletProdukt.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.btnSletProdukt, 5, 2);
        //Adding the shadow when the mouse cursor is on
        this.btnSletProdukt.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnSletProdukt.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnSletProdukt.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnSletProdukt.setEffect(null));
        this.btnSletProdukt.setOnAction(actionEvent -> this.sletProdukt());


        //******Alt visuelt vedrørende opret nyt produkt knap.
        this.add(this.btnOpretProdukt, 0, 18, 2, 2);
        this.btnOpretProdukt.setPrefSize(300, 50);
//        this.btnOpretPrisliste.setStyle("-fx-font-size:22");
        this.btnOpretProdukt.setStyle("-fx-text-fill: white;" +
                "-fx-background-color: #135a1f;" +
                "-fx-font-weight: bolder;" +
                "-fx-font-size: 30;");
        //Adding the shadow when the mouse cursor is on
        this.btnOpretProdukt.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnOpretProdukt.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnOpretProdukt.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnOpretProdukt.setEffect(null));


        //******Error label til forkert input.
        this.lblError.setStyle("-fx-font-size:20;" +
                "-fx-font-weight: lighter;" +
                "-fx-text-fill: red;");
        this.add(this.lblError, 0, 17);
        this.lblError.setVisible(false);

        //******Error label til ikke valgt kategori.
        this.lblErrorKategori.setStyle("-fx-font-size:20;" +
                "-fx-font-weight: lighter;" +
                "-fx-text-fill: red;");
        this.add(this.lblErrorKategori, 0, 17);
        this.lblErrorKategori.setVisible(false);

        //Radioknapper til valg af type produkt der skal oprettes.
        this.rdbProdukt.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.rdbProdukt.setToggleGroup(produktTypeToggle);
        this.add(rdbProdukt, 0, 4);
        this.rdbDrikkevare.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.rdbDrikkevare.setToggleGroup(produktTypeToggle);
        this.add(rdbDrikkevare, 0, 5);
        this.rdbKilovare.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.rdbKilovare.setToggleGroup(produktTypeToggle);
        this.add(rdbKilovare, 0, 6);
        this.rdbAnlaeg.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.rdbAnlaeg.setToggleGroup(produktTypeToggle);
        this.add(rdbAnlaeg, 0, 7);
        this.rdbPant.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.rdbPant.setToggleGroup(produktTypeToggle);
        this.add(rdbPant, 0, 8);
        this.rdbKlippekort.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.rdbKlippekort.setToggleGroup(produktTypeToggle);
        this.add(rdbKlippekort, 0, 9);


        this.produktTypeToggle.selectToggle(rdbProdukt);

        //Labels, textbox, checkBox og cobobox til de forskellige produkttyper.
        this.lblLiter.setPrefWidth(300);
        this.lblLiter.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.lblLiter, 1, 4);
        this.lblLiter.setVisible(false);
        this.txtLiter.setPrefWidth(300);
        this.txtLiter.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.txtLiter, 1, 5);
        this.txtLiter.setVisible(false);

        this.lblKilo.setPrefWidth(300);
        this.lblKilo.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.lblKilo, 1, 4);
        this.lblKilo.setVisible(false);
        this.txtKilo.setPrefWidth(300);
        this.txtKilo.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.txtKilo, 1, 5);
        this.txtKilo.setVisible(false);

        this.lblKlippekort.setPrefWidth(300);
        this.lblKlippekort.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.lblKlippekort, 1, 4);
        this.lblKlippekort.setVisible(false);
        this.txtAntalKlip.setPrefWidth(300);
        this.txtAntalKlip.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.txtAntalKlip, 1, 5);
        this.txtAntalKlip.setVisible(false);

        this.lblAntalHaner.setPrefWidth(300);
        this.lblAntalHaner.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.lblAntalHaner, 1, 4);
        this.lblAntalHaner.setVisible(false);
        this.comboAntalHaner.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.comboAntalHaner, 1, 5);
        this.comboAntalHaner.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9);
        this.comboAntalHaner.setVisible(false);
        this.lblBar.setPrefWidth(300);
        this.lblBar.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.lblBar, 1, 6);
        this.lblBar.setVisible(false);
        this.checkBoxBar.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.checkBoxBar, 1, 7);
        this.checkBoxBar.setVisible(false);

        this.lblPantType.setPrefWidth(300);
        this.lblPantType.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.lblPantType, 1, 4);
        this.lblPantType.setVisible(false);
        this.comboPantType.setPrefWidth(300);
        this.comboPantType.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.comboPantType, 1, 5);
        this.comboPantType.setVisible(false);
        this.comboPantType.getItems().setAll(PantType.values());

        ChangeListener<Toggle> listenerToggleProdukttype = (ov, o, n) -> this.valgtProduktTypeAendret();
        this.produktTypeToggle.selectedToggleProperty().addListener(listenerToggleProdukttype);

        //Sørger for at the er "baggrunden" der har fokus.
        this.requestFocus();

        //Sætter produktkategorier fra start.
        this.produktkategorier = storage.getAllProduktkategorier();

        //Opretter radioknapper fra start ud fra produktkategorier.
        this.opdaterInit();
    }

    private void valgtProduktTypeAendret() {
        if (this.produktTypeToggle.getSelectedToggle() == this.rdbProdukt) {
            this.lblLiter.setVisible(false);
            this.lblKilo.setVisible(false);
            this.lblKlippekort.setVisible(false);
            this.txtAntalKlip.setVisible(false);
            this.txtAntalKlip.clear();
            this.lblAntalHaner.setVisible(false);
            this.lblBar.setVisible(false);
            this.lblPantType.setVisible(false);
            this.txtLiter.setVisible(false);
            this.txtLiter.clear();
            this.txtKilo.setVisible(false);
            this.txtKilo.clear();
            this.comboAntalHaner.setVisible(false);
            this.comboAntalHaner.getSelectionModel().clearSelection();
            this.comboPantType.setVisible(false);
            this.comboPantType.getSelectionModel().clearSelection();
            this.checkBoxBar.setVisible(false);
            this.checkBoxBar.setSelected(false);
            this.checkBoxTilbageleveres.setSelected(false);
            this.checkBoxTilbageleveres.setDisable(false);
        }
        if (this.produktTypeToggle.getSelectedToggle() == this.rdbDrikkevare) {
            this.lblLiter.setVisible(true);
            this.lblKilo.setVisible(false);
            this.lblAntalHaner.setVisible(false);
            this.lblBar.setVisible(false);
            this.lblPantType.setVisible(false);
            this.txtLiter.setVisible(true);
            this.txtKilo.setVisible(false);
            this.txtKilo.clear();
            this.lblKlippekort.setVisible(false);
            this.txtAntalKlip.setVisible(false);
            this.txtAntalKlip.clear();
            this.comboAntalHaner.setVisible(false);
            this.comboAntalHaner.getSelectionModel().clearSelection();
            this.comboPantType.setVisible(false);
            this.comboPantType.getSelectionModel().clearSelection();
            this.checkBoxBar.setVisible(false);
            this.checkBoxBar.setSelected(false);
            this.checkBoxTilbageleveres.setSelected(false);
            this.checkBoxTilbageleveres.setDisable(false);
        }
        if (this.produktTypeToggle.getSelectedToggle() == this.rdbKilovare) {
            this.lblLiter.setVisible(false);
            this.lblKilo.setVisible(true);
            this.lblAntalHaner.setVisible(false);
            this.lblBar.setVisible(false);
            this.lblPantType.setVisible(false);
            this.txtLiter.setVisible(false);
            this.txtLiter.clear();
            this.lblKlippekort.setVisible(false);
            this.txtAntalKlip.setVisible(false);
            this.txtAntalKlip.clear();
            this.txtKilo.setVisible(true);
            this.comboAntalHaner.setVisible(false);
            this.comboAntalHaner.getSelectionModel().clearSelection();
            this.comboPantType.setVisible(false);
            this.comboPantType.getSelectionModel().clearSelection();
            this.checkBoxBar.setVisible(false);
            this.checkBoxBar.setSelected(false);
            this.checkBoxTilbageleveres.setSelected(false);
            this.checkBoxTilbageleveres.setDisable(false);
        }
        if (this.produktTypeToggle.getSelectedToggle() == this.rdbAnlaeg) {
            this.lblLiter.setVisible(false);
            this.lblKilo.setVisible(false);
            this.lblAntalHaner.setVisible(true);
            this.lblBar.setVisible(true);
            this.lblPantType.setVisible(false);
            this.txtLiter.setVisible(false);
            this.txtLiter.clear();
            this.txtKilo.setVisible(false);
            this.txtKilo.clear();
            this.lblKlippekort.setVisible(false);
            this.txtAntalKlip.setVisible(false);
            this.txtAntalKlip.clear();
            this.comboAntalHaner.setVisible(true);
            this.comboPantType.setVisible(false);
            this.comboPantType.getSelectionModel().clearSelection();
            this.checkBoxBar.setVisible(true);
            this.checkBoxTilbageleveres.setSelected(false);
            this.checkBoxTilbageleveres.setDisable(false);
        }
        if (this.produktTypeToggle.getSelectedToggle() == this.rdbPant) {
            this.lblLiter.setVisible(false);
            this.lblKilo.setVisible(false);
            this.lblAntalHaner.setVisible(false);
            this.lblBar.setVisible(false);
            this.lblPantType.setVisible(true);
            this.txtLiter.setVisible(false);
            this.txtLiter.clear();
            this.txtKilo.setVisible(false);
            this.txtKilo.clear();
            this.lblKlippekort.setVisible(false);
            this.txtAntalKlip.setVisible(false);
            this.txtAntalKlip.clear();
            this.comboAntalHaner.setVisible(false);
            this.comboAntalHaner.getSelectionModel().clearSelection();
            this.comboPantType.setVisible(true);
            this.checkBoxBar.setVisible(false);
            this.checkBoxBar.setSelected(false);
            this.checkBoxTilbageleveres.setSelected(false);
            this.checkBoxTilbageleveres.setDisable(false);
        }
        if (this.produktTypeToggle.getSelectedToggle() == this.rdbKlippekort) {
            this.lblLiter.setVisible(false);
            this.lblKilo.setVisible(false);
            this.lblAntalHaner.setVisible(false);
            this.lblBar.setVisible(false);
            this.lblPantType.setVisible(false);
            this.txtLiter.setVisible(false);
            this.txtLiter.clear();
            this.txtKilo.setVisible(false);
            this.txtKilo.clear();
            this.lblKlippekort.setVisible(true);
            this.txtAntalKlip.setVisible(true);
            this.comboAntalHaner.setVisible(false);
            this.comboAntalHaner.getSelectionModel().clearSelection();
            this.comboPantType.setVisible(false);
            this.checkBoxBar.setVisible(false);
            this.checkBoxBar.setSelected(false);
            this.checkBoxTilbageleveres.setSelected(false);
            this.checkBoxTilbageleveres.setDisable(false);
        }
    }

    //Opretter radioknapper fra start ud fra produktkategorier.
    public void opdaterInit() {
        this.produktkategorier = Storage.getStorage().getAllProduktkategorier();
        rdbsKategorier.clear();
        for (int i = 0; i < this.storage.getAllProduktkategorier().size(); i++) {
            rdbsKategorier.add(new RadioButton(this.produktkategorier.get(i).getProduktkategorinavn()));
            rdbsKategorier.get(i).setPrefHeight(20);
            rdbsKategorier.get(i).setStyle("-fx-font-size:18;" +
                    "-fx-font-weight: bold;");
            this.add(rdbsKategorier.get(i), 2, i + 1, 1, 1);
            rdbsKategorier.get(i).setToggleGroup(this.kategoriGruppe);
        }
    }

    private void valgtProduktkategoriAendret() {
        this.opdaterControls();
    }

    //Opdaterer forskellige noder.
    public void opdaterControls() {
        RadioButton rdb = (RadioButton) this.kategoriGruppe.getSelectedToggle();
        if (rdb == null)
            return;

        int i = this.rdbsKategorier.indexOf(rdb);

        List<Produkt> list = this.produktkategorier.get(i).getProdukter();

        this.listViewProdukter.getItems().setAll(list);
        this.setProdukListe();
    }

    private void setProdukListe() {
        listViewProdukter.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Produkt item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = "Nr: " + item.getProduktNr() + " | " + item.getProduktnavn() + " | " +
                            item.getNormalpris() +
                            " Kr.";//get text from item
                    setText(text);
                }
            }
        });
    }

    //Det der skal ske når man trykker på knappen "Ret produkt".
    public void retProduktAction() {
        Produkt produkt = this.listViewProdukter.getSelectionModel().getSelectedItem();

        if (produkt == null)
            return;

        RetProduktVindue retProduktVindue = new RetProduktVindue("Ret " + '"' + produkt.getProduktnavn() +
                '"', produkt);
        retProduktVindue.initOwner(stage);
        retProduktVindue.showAndWait();
        this.opdaterControls();
    }


    //Hvad der skal ske når man trykker på knappen "Opret produkt".
    public void opretProduktAction() {
        this.lblError.setVisible(false);
        this.lblError.setVisible(false);
        this.check = true;
        String navn = this.txtNavn.getText().trim();
        if (navn.equalsIgnoreCase("")) {
            this.txtNavn.requestFocus();
            this.lblError.setVisible(true);
            return;
        }

        double normalpris = -1.0;
        try {
            normalpris = Double.parseDouble(this.txtNormalpris.getText().trim());
        } catch (NumberFormatException ex) {
            // do nothing
        }
        if (normalpris < 0) {
            this.txtNormalpris.requestFocus();
            this.lblError.setVisible(true);
            return;
        }

        Produktkategori produktkategori = getProduktKategori();
        if (produktkategori == null) {
            this.lblError.setVisible(false);
            this.lblErrorKategori.setVisible(true);
            return;
        }

        boolean tilbageLeveresVardi = this.checkBoxTilbageleveres.selectedProperty().getValue();

        Produkt produkt = null;

        if (this.produktTypeToggle.getSelectedToggle() == this.rdbProdukt) {
            produkt = produktkategori.createProdukt(navn, normalpris, tilbageLeveresVardi);
        } else if (this.produktTypeToggle.getSelectedToggle() == this.rdbDrikkevare) {
            double liter = -1.0;
            try {
                liter = Double.parseDouble(this.txtLiter.getText().trim());
            } catch (NumberFormatException ex) {
                // do nothing
            }

            if (liter < 0) {
                this.txtLiter.requestFocus();
                this.lblError.setVisible(true);
                return;
            }
            produkt = produktkategori.createDrikkevareProdukt(navn, normalpris, tilbageLeveresVardi, liter);
        } else if (this.produktTypeToggle.getSelectedToggle() == this.rdbKilovare) {
            double gram = -1.0;
            try {
                gram = Double.parseDouble(this.txtKilo.getText().trim());
            } catch (NumberFormatException ex) {
                // do nothing
            }
            if (gram <= 0.0) {
                this.txtKilo.requestFocus();
                this.lblError.setVisible(true);
                return;
            }
            produkt = produktkategori.createKilovareProdukt(navn, normalpris, tilbageLeveresVardi, gram);
        } else if (this.produktTypeToggle.getSelectedToggle() == this.rdbAnlaeg) {
            if (this.comboAntalHaner.getSelectionModel().isEmpty()) {
                this.comboAntalHaner.requestFocus();
                return;
            }
            int antalHaner = this.comboAntalHaner.getSelectionModel().getSelectedItem();

            boolean barCheck = this.checkBoxBar.selectedProperty().getValue();

            produkt = produktkategori.createAnlaegProdukt(navn, normalpris, tilbageLeveresVardi, antalHaner, barCheck);
        } else if (this.produktTypeToggle.getSelectedToggle() == this.rdbPant) {
            if (this.comboPantType.getSelectionModel().isEmpty()) {
                this.comboPantType.requestFocus();
                return;
            }
            PantType pantType = this.comboPantType.getSelectionModel().getSelectedItem();
            produkt = produktkategori.createPantProdukt(navn, normalpris, tilbageLeveresVardi, pantType);
        } else {
            int klip = -1;
            try {
                klip = Integer.parseInt(this.txtAntalKlip.getText().trim());
            } catch (NumberFormatException ex) {
                // do nothing
            }
            if (klip <= 0) {
                this.txtAntalKlip.requestFocus();
                this.lblError.setVisible(true);
                return;
            }
            produkt = produktkategori.createKlippekortProdukt(navn, normalpris, tilbageLeveresVardi, klip);
        }

        if (produkt != null) {
            alertConfirmation.setTitle("Information");
            alertConfirmation.setHeaderText("Du er ved at oprette et nyt produkt!");
            alertConfirmation.setContentText(this.tekstTilAlert(produkt));
            if (alertConfirmation.getOwner() != stage)
                alertConfirmation.initOwner(stage);
            Optional<ButtonType> result = alertConfirmation.showAndWait();
            if (result.isPresent()) {
                if (result.get() != ButtonType.OK) {
                    produktkategori.removeProdukt(produkt);
                    return;
                }
            }
        }

        alertConfirmation.setTitle("Information");
        alertConfirmation.setHeaderText("Du har nu oprettet et nyt produkt!");
        alertConfirmation.setContentText("Ønsker du at oprette nyt produkt mere?");
        ((Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK)).setText("Ja");
        ((Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Nej");
        if (alertConfirmation.getOwner() != stage)
            alertConfirmation.initOwner(stage);
        Optional<ButtonType> result2 = alertConfirmation.showAndWait();
        if (result2.isPresent()) {
            if (result2.get() != ButtonType.OK) {
                this.clearAll();
                this.check = false;
            } else {
                this.clearAll();
            }
        }

    }

    private void clearAll() {
        this.txtNavn.clear();
        this.txtNormalpris.clear();
        this.kategoriGruppe.selectedToggleProperty().isNull();
        this.txtLiter.clear();
        this.txtKilo.clear();
        this.comboAntalHaner.getSelectionModel().clearSelection();
        this.comboPantType.getSelectionModel().clearSelection();
        this.checkBoxBar.setSelected(false);
        this.checkBoxTilbageleveres.setSelected(false);
        this.rdbProdukt.setSelected(true);
        this.rdbsKategorier.get(0).setSelected(true);
        this.txtAntalKlip.clear();
        this.opdaterControls();
    }

    private String tekstTilAlert(Produkt produkt) {
        String tekst = "Ønsker du at oprette dette produkt? ";

        if (produkt instanceof Drikkevare) {
            tekst += "\n" + produkt.getProduktnavn() +
                    "\n" + produkt.getNormalpris() + " kr." +
                    "\n" + "Tilbageleveres: " + produkt.isTilbageleveres() +
                    "\n" + ((Drikkevare) produkt).getLiter() + " L.";
        } else if (produkt instanceof Kilovare) {
            tekst += "\n" + produkt.getProduktnavn() +
                    "\n" + produkt.getNormalpris() + " kr." +
                    "\n" + "Tilbageleveres: " + produkt.isTilbageleveres() +
                    "\n" + ((Kilovare) produkt).getGram() + " G.";
        } else if (produkt instanceof Anlaeg) {
            tekst += "\n" + produkt.getProduktnavn() +
                    "\n" + produkt.getNormalpris() + " kr." +
                    "\n" + "Tilbageleveres: " + produkt.isTilbageleveres() +
                    "\n" + ((Anlaeg) produkt).getAntalHaner() + " Antal haner" +
                    "\n" + ((Anlaeg) produkt).isBar() + " Bar";
        } else if (produkt instanceof Pant) {
            tekst += "\n" + produkt.getProduktnavn() +
                    "\n" + produkt.getNormalpris() + " kr." +
                    "\n" + "Tilbageleveres: " + produkt.isTilbageleveres() +
                    "\n" + ((Pant) produkt).getPantType() + " (PantType)";
        } else if (produkt instanceof Klippekort) {
            tekst += "\n" + produkt.getProduktnavn() +
                    "\n" + produkt.getNormalpris() + " kr." +
                    "\n" + "Tilbageleveres: " + produkt.isTilbageleveres() +
                    "\n" + ((Klippekort) produkt).getAntalKlip() + " Stk.";
        } else {
            tekst += "\n" + produkt.getProduktnavn() +
                    "\n" + produkt.getNormalpris() + " kr." +
                    "\n" + "Tilbageleveres: " + produkt.isTilbageleveres();
        }
        return tekst;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Button getBtnOpretProdukt() {
        return btnOpretProdukt;
    }

    private void sletProdukt() {
        Produkt produkt = this.listViewProdukter.getSelectionModel().getSelectedItem();
        if (produkt == null)
            return;
        Produktkategori produktkategori = getProduktKategori();
        if (produktkategori == null)
            return;
        produktkategori.removeProdukt(produkt);
        clearAll();
    }

    private Produktkategori getProduktKategori() {
        RadioButton rdb = (RadioButton) this.kategoriGruppe.getSelectedToggle();
        if (rdb == null) {
            return null;
        }
        int i = this.rdbsKategorier.indexOf(rdb);
        return this.produktkategorier.get(i);
    }
}
