package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistrerAndetSalgPane extends GridPane {

    private ToggleGroup kategoriGruppe = new ToggleGroup();
    private List<Produktkategori> produktkategorier;
    private List<RadioButton> rdbsKategorier = new ArrayList<>();
    private final ListView<PrislisteProdukt> listViewProdukter = new ListView<>();
    private final ComboBox<Prisliste> comboBoxPrislister = new ComboBox<>();
    private final Button btnOpretSalgslinje = new Button("Opret salgslinje");
    private final Button btnTilfoejKunde = new Button("Tilføj kunde");
    private final Button btnRegistrerSalg = new Button("Registrer salg");
    private final ListView<SalgsLinje> salgslinjer = new ListView<>();
    private final TextField txtSamletPris = new TextField();
    private final TextField txtSamletPant = new TextField();
    private final TextField txtPrisUdenPant = new TextField();
    private final Button btnSletSalgslinje = new Button("Slet salgslinje");
    private final Button btnBetal = new Button("Betal");
    private final Button btnBetalPant = new Button("Betal pant");
    private final Controller controller = new Controller();
    private final Storage storage = Storage.getStorage();
    private Salg salg = null;
    private Prisliste prisliste;
    private final Stage stage;
    private boolean check = false;
    private final Label lblAlert = new Label();
    private final Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);


    public RegistrerAndetSalgPane(Stage stage) {
        //Afstand fra kant og ind.
        this.setPadding(new Insets(20));
        //Afstand mellem noder horisontalt.
        this.setHgap(40);
        //Afstand mellem noder vertikalt.
        this.setVgap(10);
        //Synlighed for linjerne i gridpane.
        this.setGridLinesVisible(false);
        //Baggrundsfarve
        this.setStyle("-fx-background-color: #fff");

        Label lblKategorier = new Label("Kategorier");
        lblKategorier.setPrefWidth(200);
        lblKategorier.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblKategorier, 1, 0);

        //Alt vedrørende comboBox med prislister.
        Label lblPrislister = new Label("Prislister");
        lblPrislister.setPrefWidth(200);
        lblPrislister.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblPrislister, 0, 0);
        this.comboBoxPrislister.setPrefWidth(200);
        this.comboBoxPrislister.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.comboBoxPrislister, 0, 1);
        this.comboBoxPrislister.getItems().setAll(storage.getAllPrislister());
        this.comboBoxPrislister.getSelectionModel().select(storage.getAllPrislister().get(1));
        ChangeListener<Prisliste> listenerComboBox = (ov, o, n) -> this.valgtProduktkategoriAendret();
        this.comboBoxPrislister.valueProperty().addListener(listenerComboBox);

        //Alt vedrørende listView med produkter og priser.
        Label lblProdukterOgPriser = new Label("Produkter og priser");
        lblProdukterOgPriser.setPrefWidth(200);
        lblProdukterOgPriser.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblProdukterOgPriser, 2, 0);
        this.listViewProdukter.setPrefHeight(500);
        this.listViewProdukter.setPrefWidth(350);
        this.listViewProdukter.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.listViewProdukter, 2, 1, 1, 12);

        //******Knapper til oprettelse af salgslinje.
        this.btnOpretSalgslinje.setPrefWidth(200);
        this.btnOpretSalgslinje.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.btnOpretSalgslinje, 3, 1);
        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        this.btnOpretSalgslinje.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnOpretSalgslinje.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnOpretSalgslinje.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnOpretSalgslinje.setEffect(null));
        this.btnOpretSalgslinje.setOnAction(actionEvent -> this.opretSalgslinjeAction());

        //******Knap til at tilføje kunde.
        this.btnTilfoejKunde.setPrefWidth(200);
        this.btnTilfoejKunde.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.btnTilfoejKunde, 3, 2);
        //Adding the shadow when the mouse cursor is on
        this.btnTilfoejKunde.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnTilfoejKunde.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnTilfoejKunde.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnTilfoejKunde.setEffect(null));
        this.btnTilfoejKunde.setOnAction(actionEvent -> this.tilfoejKundeBtnAction());

        //******Knap til at betale.
        this.btnBetal.setPrefWidth(200);
        this.btnBetal.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: red;" +
                "-fx-opacity: 1.0");
        this.add(this.btnBetal, 3, 3);
        this.btnBetal.setDisable(true);
        this.btnBetal.setVisible(false);
        //Adding the shadow when the mouse cursor is on
        this.btnBetal.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnBetal.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnBetal.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnBetal.setEffect(null));
        this.btnBetal.setOnAction(actionEvent -> this.betaltBtnAction());

        //******Knap til at betale pant.
        this.btnBetalPant.setPrefWidth(200);
        this.btnBetalPant.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: red;" +
                "-fx-opacity: 1.0");
        this.add(this.btnBetalPant, 3, 3);
        this.btnBetalPant.setDisable(true);
        this.btnBetalPant.setVisible(false);
        //Adding the shadow when the mouse cursor is on
        this.btnBetalPant.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnBetalPant.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnBetalPant.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnBetalPant.setEffect(null));
        this.btnBetalPant.setOnAction(actionEvent -> this.betaltBtnAction());

        //Alt vedrørende listView med salgslinjer.
        Label lblSalgslinjer = new Label("Salgslinjer");
        lblSalgslinjer.setPrefWidth(200);
        lblSalgslinjer.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblSalgslinjer, 4, 0);
        this.salgslinjer.setPrefHeight(180);
        this.salgslinjer.setPrefWidth(330);
        this.salgslinjer.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.salgslinjer, 4, 1, 1, 6);


        //Alt vedrørende textField til samlet pris og knap til sletning af salgslinje.
        Label lblSamletPris = new Label("Samlet pris:");
        lblSamletPris.setPrefWidth(105);
        lblSamletPris.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.txtSamletPris.setPrefWidth(105);
        this.txtSamletPris.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.txtSamletPris.setEditable(false);
        this.txtSamletPris.setDisable(true);

        Label lblSamletPant = new Label("Samlet Pant:");
        lblSamletPant.setPrefWidth(105);
        lblSamletPant.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.txtSamletPant.setPrefWidth(105);
        this.txtSamletPant.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.txtSamletPant.setEditable(false);
        this.txtSamletPant.setDisable(true);

        Label lblPrisUdenPant = new Label("Pris uden pant:");
        lblPrisUdenPant.setPrefWidth(105);
        lblPrisUdenPant.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.txtPrisUdenPant.setPrefWidth(105);
        this.txtPrisUdenPant.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.txtPrisUdenPant.setEditable(false);
        this.txtPrisUdenPant.setDisable(true);

        this.btnSletSalgslinje.setPrefWidth(120);
        this.btnSletSalgslinje.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.btnSletSalgslinje.setOnAction(actionEvent -> this.sletSalgslinjeBtnAction());

        HBox hBoxsalgslinjer = new HBox();
        hBoxsalgslinjer.setAlignment(Pos.CENTER);
        hBoxsalgslinjer.setSpacing(10);
        hBoxsalgslinjer.getChildren().addAll(lblSamletPris, this.txtSamletPris, this.btnSletSalgslinje);
        this.add(hBoxsalgslinjer, 4, 7);

        HBox hBoxSamletPant = new HBox();
        hBoxSamletPant.setAlignment(Pos.CENTER_LEFT);
        hBoxSamletPant.setSpacing(10);
        hBoxSamletPant.getChildren().addAll(lblSamletPant, this.txtSamletPant);
        this.add(hBoxSamletPant, 4, 8);

        HBox hBoxUdenPant = new HBox();
        hBoxUdenPant.setAlignment(Pos.CENTER_LEFT);
        hBoxUdenPant.setSpacing(10);
        hBoxUdenPant.getChildren().addAll(lblPrisUdenPant, this.txtPrisUdenPant);
        this.add(hBoxUdenPant, 4, 9);


        //******Listener på gruppen for Radiobuttons.
        ChangeListener<Toggle> listenerTogglegroup = (ov, o, n) -> this.valgtProduktkategoriAendret();
        this.kategoriGruppe.selectedToggleProperty().addListener(listenerTogglegroup);

        //Alt vedrørende Registrer salg knappen.
        this.add(this.btnRegistrerSalg, 4, 12, 1, 1);
        this.btnRegistrerSalg.setPrefSize(350, 50);
//        this.btnOpretPrisliste.setStyle("-fx-font-size:22");
        this.btnRegistrerSalg.setStyle("-fx-text-fill: white;" +
                "-fx-background-color: #135a1f;" +
                "-fx-font-weight: bolder;" +
                "-fx-font-size: 30;");
        //Adding the shadow when the mouse cursor is on
        this.btnRegistrerSalg.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnRegistrerSalg.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnRegistrerSalg.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnRegistrerSalg.setEffect(null));


        this.lblAlert.setStyle("-fx-text-fill: red;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20;");
        this.add(this.lblAlert, 3, 11);
        this.lblAlert.setVisible(false);


        this.stage = stage;
    }

    //Laver et nyt salg.
    public void setSalg() {
        this.salg = this.controller.opretSalg();
        this.setPrislisteOgProduktkategori(storage.getAllProduktkategorier());
        this.opdaterInit();
        this.kategoriGruppe.selectToggle(this.rdbsKategorier.get(0));

        //TODO tilføjet af Jesper (combobox med prislister skal opdateres efter man har lavet en ny prisliste)
        //TODO desuden skal nye produktkategoriers produkter også opdateres
        this.comboBoxPrislister.getItems().setAll(storage.getAllPrislister());
        this.comboBoxPrislister.getSelectionModel().select(storage.getAllPrislister().get(1));
    }

    public void setPrislisteOgProduktkategori(List<Produktkategori> produktkategorier) {
        this.produktkategorier = produktkategorier;
        this.prisliste = this.comboBoxPrislister.getSelectionModel().getSelectedItem();
        this.salg.setPrisliste(this.prisliste);
    }

    //Opretter radioknapper fra start ud fra produktkategorier.
    public void opdaterInit() {
        for (int i = 0; i < this.storage.getAllProduktkategorier().size(); i++) {
            rdbsKategorier.add(new RadioButton(this.produktkategorier.get(i).getProduktkategorinavn()));
            rdbsKategorier.get(i).setPrefHeight(20);
            rdbsKategorier.get(i).setStyle("-fx-font-size:18;" +
                    "-fx-font-weight: bold;");
            this.add(rdbsKategorier.get(i), 1, i + 1, 1, 1);
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

        Prisliste prisliste = this.comboBoxPrislister.getSelectionModel().getSelectedItem();
        if (prisliste == null)
            return;
        this.prisliste = prisliste;
        this.salg.setPrisliste(prisliste);

        int i = this.rdbsKategorier.indexOf(rdb);

        Produktkategori produktkategori = this.produktkategorier.get(i);
        if (produktkategori == null)
            return;

        List<Produkt> produkterIKategori = produktkategori.getProdukter();

        List<PrislisteProdukt> prislisteProdukterTemp = new ArrayList<>();

        for (PrislisteProdukt pp : prisliste.getPrislisteProdukter()) {
            if (produkterIKategori.contains(pp.getProdukt())) {
                prislisteProdukterTemp.add(pp);
            }
        }
        this.listViewProdukter.getItems().setAll(prislisteProdukterTemp);

        this.salgslinjer.getItems().setAll(this.salg.getSalgsLinjer());
        this.setSalgslinjer();
        this.txtSamletPris.setText(Double.toString(this.salg.samletPrisMedPant()));
        this.txtSamletPant.setText(Double.toString(this.salg.samletPrisPant()));
        this.txtPrisUdenPant.setText(Double.toString(this.salg.samletPrisUdenPant()));


        if (this.salg.checkOmDerErSalgslinjer() && !this.salg.isBetalt() &&
                !this.salg.harPantTilbageleveringSalgslinje()) {
            this.btnBetal.setDisable(false);
            this.btnBetal.setVisible(true);
            this.btnBetalPant.setDisable(true);
            this.btnBetalPant.setVisible(false);
        }

        if (this.salg.checkOmDerErSalgslinjer() && !this.salg.isBetalt() &&
                this.salg.harPantTilbageleveringSalgslinje()) {
            this.btnBetal.setDisable(true);
            this.btnBetal.setVisible(false);
            this.btnBetalPant.setDisable(false);
            this.btnBetalPant.setVisible(true);
        }
//        if (!this.salg.isBetalt())
//            this.btnBetal.setStyle("-fx-font-size:18;" +
//                    "-fx-font-weight: bold;");
//        if (this.salg.isBetalt())
//            this.btnBetal.setStyle("-fx-font-size:18;" +
//                    "-fx-font-weight: bold;" + "-fx-text-fill: white;" +
//                    "-fx-background-color: #008000");
    }

    //Opretter et object af salgslinje og til føjer den til det aktuelle salg.
    public void opretSalgslinjeAction() {
        PrislisteProdukt prislisteProdukt = this.listViewProdukter.getSelectionModel().getSelectedItem();
        if (prislisteProdukt == null)
            return;


        OpretSalgslinjeVindue opretSalgslinjeVindue =
                new OpretSalgslinjeVindue("Opret salgslinje", prislisteProdukt, this.salg);
        opretSalgslinjeVindue.initOwner(stage);
        opretSalgslinjeVindue.showAndWait();
        this.opdaterControls();
    }

    //Metode til at slette en salgslinje, og opdatere controls.
    public void sletSalgslinjeBtnAction() {
        SalgsLinje salgsLinje = this.salgslinjer.getSelectionModel().getSelectedItem();
        if (salgsLinje == null)
            return;
        this.salg.removeSalgsLinje(salgsLinje);
        this.opdaterControls();
    }

    public void registrerSalgBtnAction() {
        this.lblAlert.setVisible(false);
        if (this.salg.getSalgsLinjer().size() == 0) {
            this.btnOpretSalgslinje.requestFocus();
            this.lblAlert.setText("Salgslinjer mangler!");
            this.lblAlert.setVisible(true);
            return;
        }
        if (this.salg.getKunde() == null) {
            this.btnTilfoejKunde.requestFocus();
            this.lblAlert.setText("Kunde mangler!");
            this.lblAlert.setVisible(true);
            return;
        }
        if (!this.salg.erPantBetalt() && this.salg.harPantTilbageleveringSalgslinje()) {
            this.btnBetal.requestFocus();
            this.lblAlert.setText("Betaling mangler!");
            this.lblAlert.setVisible(true);
            return;
        }


        alertConfirmation.setTitle("Information");
        alertConfirmation.setHeaderText("Du har nu registreret et salg!");
        alertConfirmation.setContentText("Ønsker du at registrere et salg mere?");
        ((Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.OK)).setText("Ja");
        ((Button) alertConfirmation.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Nej");
        if (alertConfirmation.getOwner() != stage)
            alertConfirmation.initOwner(stage);
        Optional<ButtonType> result2 = alertConfirmation.showAndWait();
        if (result2.isPresent()) {
            if (result2.get() != ButtonType.OK) {
                this.salg = null;
                this.clearAll();
                this.check = false;
            } else {
                this.clearAll();
                this.setSalg();
                this.kategoriGruppe.selectToggle(this.rdbsKategorier.get(0));
                this.opdaterControls();
            }
        }


    }

    public void tilfoejKundeBtnAction() {

        TilfoejKundeTilSalgVindue tilfoejKundeTilSalgVindue =
                new TilfoejKundeTilSalgVindue("Tilføj kunde til salg", this.salg);
        tilfoejKundeTilSalgVindue.initOwner(stage);
        tilfoejKundeTilSalgVindue.showAndWait();
        this.opdaterControls();

    }

    public void betaltBtnAction() {

        BetaltVindue betaltVindue = new BetaltVindue("Betal vindue", this.salg);
        betaltVindue.initOwner(stage);
        betaltVindue.showAndWait();
        if (!this.salg.isBetalt())
            this.btnBetal.setStyle("-fx-font-size:18;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-color: red;" +
                    "-fx-opacity: 1.0");
        if (this.salg.isBetalt()) {
            this.btnBetal.setStyle("-fx-font-size:18;" +
                    "-fx-font-weight: bold;" + "-fx-text-fill: white;" +
                    "-fx-background-color: #008000");
            this.btnSletSalgslinje.setDisable(true);
            this.btnOpretSalgslinje.setDisable(true);
            this.btnBetal.setText("Betalt");
            this.btnBetal.setDisable(true);
        }
        if (!this.salg.erPantBetalt()) {
            this.btnBetalPant.setStyle("-fx-font-size:18;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-color: red;" +
                    "-fx-opacity: 1.0");
        }
        if (this.salg.erPantBetalt()) {
            this.btnBetalPant.setStyle("-fx-font-size:18;" +
                    "-fx-font-weight: bold;" + "-fx-text-fill: white;" +
                    "-fx-background-color: #008000");
            this.btnSletSalgslinje.setDisable(true);
            this.btnOpretSalgslinje.setDisable(true);
            this.btnBetalPant.setText("Pant betalt");
            this.btnBetalPant.setDisable(true);
        }
        this.opdaterControls();

    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public void tilbageOgSletTempSalg() {
        if (this.salg == null)
            return;
        storage.deleteSalg(this.salg);
        this.salg = null;
        this.rdbsKategorier.clear();
        this.clearAll();
    }

    private void clearAll() {
        this.rdbsKategorier.clear();
        this.salgslinjer.refresh();
        this.btnBetal.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.btnSletSalgslinje.setDisable(false);
        this.btnOpretSalgslinje.setDisable(false);
        this.btnBetal.setDisable(true);
        this.btnBetal.setVisible(false);
        this.btnBetal.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: red;" +
                "-fx-opacity: 1.0");
        this.btnBetal.setText("Betal");
        this.btnBetalPant.setDisable(true);
        this.btnBetalPant.setVisible(false);
        this.btnBetalPant.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: red;" +
                "-fx-opacity: 1.0");
        this.btnBetalPant.setText("Betal pant");
    }

    public Button getBtnRegistrerSalg() {
        return btnRegistrerSalg;
    }

    public boolean isCheck() {
        return check;
    }

    private void setSalgslinjer() {
        this.salgslinjer.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(SalgsLinje item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    Produktkategori pg = controller.findProduktKategori(item.getProdukt());
                    String text = pg.getProduktkategorinavn() + " | " + item.getProdukt().getProduktnavn() + " | "
                            + item.getProduktAntal() + " stk."; // get text from item
                    setText(text);
                }
            }
        });
    }
}
