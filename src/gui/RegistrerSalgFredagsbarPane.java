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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistrerSalgFredagsbarPane extends GridPane {


    private final ToggleGroup kategoriGruppe = new ToggleGroup();
    private List<Produktkategori> produktkategorier;
    private final List<RadioButton> rdbsKategorier = new ArrayList<>();
    private final ListView<PrislisteProdukt> listViewProdukter = new ListView<>();
    private final Button btnOpretSalgslinje = new Button("Opret salgslinje");
    private final Button btnRegistrerSalg = new Button("Registrer salg");
    private final ListView<SalgsLinje> salgslinjer = new ListView<>();
    private final TextField txtSamletPris = new TextField();
    private final Button btnBetalt = new Button("Betal");
    private final ToggleGroup betaling = new ToggleGroup();
    private final RadioButton rdbAlmBetaling = new RadioButton("Betaling alm.");
    private final RadioButton rdbKlip = new RadioButton("Klippekort");
    private final Button btnBetaltKlip = new Button("Registrer Klip");
    private final Label lblAlert = new Label();
    private final Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
    private final Button btnSletSalgslinje = new Button("Slet salgslinje");


    private final Controller controller = new Controller();
    private final Storage storage = Storage.getStorage();
    private Salg salg = null;
    private boolean check = false;
    private Prisliste prisliste;
    private final Stage stage;


    public RegistrerSalgFredagsbarPane(Stage stage) {
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
        this.add(lblKategorier, 0, 0);

        //Alt vedrørende listView med produkter og priser.
        Label lblProdukterOgPriser = new Label("Produkter og priser");
        lblProdukterOgPriser.setPrefWidth(200);
        lblProdukterOgPriser.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblProdukterOgPriser, 1, 0);
        this.listViewProdukter.setPrefHeight(500);
        this.listViewProdukter.setPrefWidth(350);
        this.listViewProdukter.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.listViewProdukter, 1, 1, 1, 12);

        //******Knapper til oprettelse af salgslinje.
        this.btnOpretSalgslinje.setPrefWidth(200);
        this.btnOpretSalgslinje.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.btnOpretSalgslinje, 2, 1);
        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        this.btnOpretSalgslinje.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnOpretSalgslinje.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnOpretSalgslinje.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnOpretSalgslinje.setEffect(null));
        this.btnOpretSalgslinje.setOnAction(actionEvent -> this.opretSalgslinjeAction());

        //******Listener på gruppen for Radiobuttons.
        ChangeListener<Toggle> listenerTogglegroup = (ov, o, n) -> this.valgtProduktkategoriAendret();
        this.kategoriGruppe.selectedToggleProperty().addListener(listenerTogglegroup);

        //Alt vedrørende Registrer salg knappen.
        this.add(this.btnRegistrerSalg, 3, 12, 1, 1);
        this.btnRegistrerSalg.setPrefSize(350, 50);
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
        this.btnRegistrerSalg.setOnAction(actionEvent -> this.registrerSalgBtnAction());


        //Alt vedrørende listView med salgslinjer.
        Label lblSalgslinjer = new Label("Salgslinjer");
        lblSalgslinjer.setPrefWidth(200);
        lblSalgslinjer.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblSalgslinjer, 3, 0);
        this.salgslinjer.setPrefHeight(180);
        this.salgslinjer.setPrefWidth(330);
        this.salgslinjer.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.salgslinjer, 3, 1, 1, 6);

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
//        this.txtSamletPris.setFocusTraversable(true);
        this.txtSamletPris.setDisable(true);


        this.btnSletSalgslinje.setPrefWidth(120);
        this.btnSletSalgslinje.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.btnSletSalgslinje.setOnAction(actionEvent -> this.sletSalgslinjeBtnAction());
        HBox hBoxsalgslinjer = new HBox();
        hBoxsalgslinjer.setAlignment(Pos.CENTER);
        hBoxsalgslinjer.setSpacing(10);
        hBoxsalgslinjer.getChildren().addAll(lblSamletPris, this.txtSamletPris, this.btnSletSalgslinje);
        this.add(hBoxsalgslinjer, 3, 7);

        //******Knap til at betale.
        this.btnBetalt.setPrefWidth(200);
        this.btnBetalt.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: red;" +
                "-fx-opacity: 1.0");
        this.add(this.btnBetalt, 2, 5);
        this.btnBetalt.setDisable(true);
        //Adding the shadow when the mouse cursor is on
        this.btnBetalt.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnBetalt.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnBetalt.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnBetalt.setEffect(null));
        this.btnBetalt.setOnAction(actionEvent -> this.betaltBtnAction());

        this.lblAlert.setStyle("-fx-text-fill: red;" +
                "-fx-font-weight: bold;" +
                "-fx-font-size: 20;");
        this.add(this.lblAlert, 3, 11);
        this.lblAlert.setVisible(false);

        this.stage = stage;
    }

    public void registrerSalgBtnAction() {
        this.lblAlert.setVisible(false);
        if (this.salg.getSalgsLinjer().size() == 0) {
            this.btnOpretSalgslinje.requestFocus();
            this.lblAlert.setText("Salgslinjer mangler!");
            this.lblAlert.setVisible(true);
            return;
        }
        if (!this.salg.isBetalt()) {
            this.btnBetalt.requestFocus();
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

    private void sletSalgslinjeBtnAction() {
        SalgsLinje salgsLinje = this.salgslinjer.getSelectionModel().getSelectedItem();
        if (salgsLinje == null)
            return;
        this.salg.removeSalgsLinje(salgsLinje);
        this.opdaterControls();
    }

    private void opretSalgslinjeAction() {
        PrislisteProdukt prislisteProdukt = this.listViewProdukter.getSelectionModel().getSelectedItem();
        if (prislisteProdukt == null)
            return;

        OpretSalgslinjeVindue opretSalgslinjeVindue =
                new OpretSalgslinjeVindue("Opret salgslinje", prislisteProdukt, this.salg);
        opretSalgslinjeVindue.initOwner(stage);
        opretSalgslinjeVindue.showAndWait();
        this.opdaterControls();
    }

    private void betaltBtnAction() {
        BetaltVindue betaltVindue = new BetaltVindue("Betal vindue", this.salg);
        betaltVindue.initOwner(stage);
        betaltVindue.showAndWait();
        if (!this.salg.isBetalt())
            this.btnBetalt.setStyle("-fx-font-size:18;" +
                    "-fx-font-weight: bold;" +
                    "-fx-background-color: red;" +
                    "-fx-opacity: 1.0");
        if (this.salg.isBetalt()) {
            this.btnBetalt.setStyle("-fx-font-size:18;" +
                    "-fx-font-weight: bold;" + "-fx-text-fill: white;" +
                    "-fx-background-color: #008000;" +
                    "-fx-opacity: 1.0");
            this.btnBetalt.setText("Betalt");
            this.btnBetalt.setDisable(true);
            this.btnOpretSalgslinje.setDisable(true);
            this.btnSletSalgslinje.setDisable(true);
        }
        this.opdaterControls();
    }

    public void setSalg() {
        this.salg = this.controller.opretSalg();
        this.setPrislisteOgProduktkategori(storage.getAllProduktkategorier());
        this.opdaterKategorierKnapper();
        this.kategoriGruppe.selectToggle(this.rdbsKategorier.get(0));
    }

    public void setPrislisteOgProduktkategori(List<Produktkategori> produktkategorier) {
        this.produktkategorier = produktkategorier;
        this.prisliste = storage.getAllPrislister().get(0);
        this.salg.setPrisliste(this.prisliste);
    }

    public void opdaterKategorierKnapper() {
        for (int i = 0; i < this.storage.getAllProduktkategorier().size(); i++) {
            rdbsKategorier.add(new RadioButton(this.produktkategorier.get(i).getProduktkategorinavn()));
            rdbsKategorier.get(i).setPrefHeight(20);
            rdbsKategorier.get(i).setStyle("-fx-font-size:18;" +
                    "-fx-font-weight: bold;");
            this.add(rdbsKategorier.get(i), 0, i + 1, 1, 1);
            rdbsKategorier.get(i).setToggleGroup(this.kategoriGruppe);
        }
    }

    private void valgtProduktkategoriAendret() {
        this.opdaterControls();
    }

    //Opdaterer forskellige noder.
    public void opdaterControls() {
        if (this.prisliste == null)
            return;

        RadioButton rdb = (RadioButton) this.kategoriGruppe.getSelectedToggle();
        if (rdb == null)
            return;

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

        if (this.salg.checkOmDerErSalgslinjer() && !this.salg.isBetalt())
            this.btnBetalt.setDisable(false);
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
    }

    private void clearAll() {
        this.rdbsKategorier.clear();
        this.salgslinjer.refresh();
        this.btnBetalt.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: red;" +
                "-fx-opacity: 1.0");
        this.btnSletSalgslinje.setDisable(false);
        this.btnOpretSalgslinje.setDisable(false);
        btnBetalt.setDisable(true);
        this.btnBetalt.setText("Betal");
    }

    public boolean isCheck() {
        return check;
    }

    public Button getBtnRegistrerSalg() {
        return btnRegistrerSalg;
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
