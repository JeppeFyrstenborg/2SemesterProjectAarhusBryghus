package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BetalSalgVindue extends Stage {
    public BetalSalgVindue(String title, Salg salg) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.salg = salg;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private Salg salg;

    private final Label lblId = new Label("Id:");
    private final TextField txtId = new TextField();
    private final Label lblNavn = new Label("Navn:");
    private final TextField txtNavn = new TextField();
    private final Label lblAdresse = new Label("Adresse:");
    private final TextField txtAdresse = new TextField();
    private final Label lblEmail = new Label("Email:");
    private final TextField txtEmail = new TextField();
    private final Label lbltelefonNr = new Label("TelefonNr.:");
    private final TextField txtTelefon = new TextField();

    private final Label lblRegDato = new Label("Reg. dato:");
    private final TextField txtRegDato = new TextField();
    private final Label lblSalgNr = new Label("Salgsnr.::");
    private final TextField txtSalgNr = new TextField();
    private final Label lblPrisliste = new Label("Prisliste:");
    private final TextField txtPrisliste = new TextField();
    private final Label lblPrisBetales = new Label("Skal betales:");
    private final TextField txtPrisBetales = new TextField();
    private final Label lblBetaltPant = new Label("Betalt pant:");
    private final TextField txtSamletBetaltPant = new TextField();
    private final Label lblPrisUdenPant = new Label("Pris uden Pant:");
    private final TextField txtPrisUdenPant = new TextField();
    private final ListView<SalgsLinje> salgslinjer = new ListView<>();
    private final ListView<SalgsLinje> salgslinjerTilbage = new ListView<>();
    private final ComboBox<Integer> comboTilbageleveret = new ComboBox<>();
    private final Button btnTilbageleveret = new Button("Tilbageleveret");

    private final Button btnBetalSalg = new Button("Betal");
    private final Button btnAfbryd = new Button("Afbryd");
    private final ComboBox<Betalingsform> comboBetalingsform = new ComboBox<>();

    private Controller controller = new Controller();


    private void initContent(GridPane pane) {
        //Afstand fra kant og ind.
        pane.setPadding(new Insets(10));
        //Afstand mellem noder horisontalt.
        pane.setHgap(10);
        //Afstand mellem noder vertikalt.
        pane.setVgap(10);
        //Synlighed for linjerne i gridpane.
        pane.setGridLinesVisible(false);
        //Størrelsen på vinduet.
//        pane.setPrefWidth(300);
//        pane.setPrefHeight(350);
        //Baggrundsfarve
        pane.setStyle("-fx-background-color: #135a1f;");


        //Alt vedrørende kunde.

        lblId.prefWidth(200);
        lblId.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lblId, 0, 1);
        lblId.setVisible(true);
        txtId.setPrefWidth(200);
        txtId.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtId, 1, 1);
        txtId.setVisible(true);
        txtId.setDisable(true);
        txtId.setText("" + salg.getKunde().getKundeNr());

        lblNavn.prefWidth(200);
        lblNavn.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lblNavn, 0, 2);
        lblNavn.setVisible(true);
        txtNavn.setPrefWidth(200);
        txtNavn.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtNavn, 1, 2);
        txtNavn.setVisible(true);
        txtNavn.setDisable(true);
        txtNavn.setText(salg.getKunde().getKundenavn());
        this.txtNavn.setPrefHeight(20);

        lblAdresse.prefWidth(200);
        lblAdresse.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lblAdresse, 0, 3);
        lblAdresse.setVisible(true);
        txtAdresse.setPrefWidth(200);
        txtAdresse.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtAdresse, 1, 3);
        txtAdresse.setVisible(true);
        txtAdresse.setDisable(true);
        txtAdresse.setText(salg.getKunde().getAdresse());
        this.txtAdresse.setPrefHeight(20);

        lblEmail.prefWidth(200);
        lblEmail.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lblEmail, 0, 4);
        lblEmail.setVisible(true);
        txtEmail.setPrefWidth(200);
        txtEmail.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtEmail, 1, 4);
        txtEmail.setVisible(true);
        txtEmail.setDisable(true);
        txtEmail.setText(salg.getKunde().getEmail());
        this.txtEmail.setPrefHeight(20);

        lbltelefonNr.prefWidth(200);
        lbltelefonNr.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lbltelefonNr, 0, 5);
        lbltelefonNr.setVisible(true);
        txtTelefon.setPrefWidth(200);
        txtTelefon.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtTelefon, 1, 5);
        txtTelefon.setVisible(true);
        txtTelefon.setDisable(true);
        txtTelefon.setText(salg.getKunde().getTelefonNr());
        this.txtTelefon.setPrefHeight(20);

        //Alt vedrørende salget.

        lblSalgNr.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(lblSalgNr, 2, 1);
        this.txtSalgNr.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtSalgNr, 3, 1, 1, 1);
        this.txtSalgNr.setDisable(true);
//        this.txtSalgNr.setPrefHeight(20);

        lblRegDato.setStyle("-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(lblRegDato, 2, 2);
        this.txtRegDato.setStyle("-fx-font-size: 14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtRegDato, 3, 2, 1, 1);
        this.txtRegDato.setDisable(true);
//        this.txtRegDato.setPrefHeight(20);

        lblPrisliste.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(lblPrisliste, 2, 3);
        this.txtPrisliste.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtPrisliste, 3, 3, 1, 1);
        this.txtPrisliste.setDisable(true);
//        this.txtPrisliste.setPrefHeight(20);

        lblPrisBetales.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(lblPrisBetales, 2, 4);
        this.txtPrisBetales.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtPrisBetales, 3, 4, 1, 1);
        this.txtPrisBetales.setDisable(true);
//        this.txtPrisBetales.setPrefHeight(20);


        lblBetaltPant.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(lblBetaltPant, 2, 5);
        this.txtSamletBetaltPant.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtSamletBetaltPant, 3, 5, 1, 1);
        this.txtSamletBetaltPant.setDisable(true);
//        this.txtSamletBetaltPant.setPrefHeight(20);

        lblPrisUdenPant.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(lblPrisUdenPant, 2, 6);
        this.txtPrisUdenPant.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        pane.add(txtPrisUdenPant, 3, 6, 1, 1);
        this.txtPrisUdenPant.setDisable(true);
//        this.txtPrisUdenPant.setPrefHeight(20);

//        this.txtUdlejet.setPrefHeight(20);
//        this.txtUdlejet.setText(""+this.txtId.getHeight());
        this.txtSalgNr.setText("" + this.salg.getSalgsNr());
        this.txtRegDato.setText(this.salg.getSalgPaabegyndtDato().toString());
        this.txtPrisliste.setText("" + this.salg.getPrisliste().getPrislistenavn());
        this.txtPrisBetales.setText("" + this.salg.samletPrisPantFratrukket());
        this.txtSamletBetaltPant.setText("" + this.salg.samletPrisPant());
        this.txtPrisUdenPant.setText("" + this.salg.samletPrisUdenPant());

        //Liste af salgslinjer

        this.salgslinjer.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        pane.add(this.salgslinjer, 4, 1, 3, 4);
        this.salgslinjer.setPrefWidth(400);
        this.salgslinjer.setPrefHeight(120);
//        salgslinjer.setMaxHeight(120);
        this.salgslinjer.getItems().setAll(this.salg.getSalgsLinjer());
        this.setSalgslinjer();

        this.salgslinjerTilbage.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        pane.add(this.salgslinjerTilbage, 4, 5, 3, 4);
        salgslinjerTilbage.setPrefHeight(120);
        salgslinjerTilbage.setPrefWidth(400);
        this.salgslinjerTilbage.getItems().setAll(this.salg.alleTilbageleveringslinjer());
        ChangeListener<SalgsLinje> listenerSalgslinje = (ov, o, n) -> this.aendreAntalICombo();
        this.salgslinjerTilbage.getSelectionModel().selectedItemProperty().addListener(listenerSalgslinje);
        this.setSalgslinjerTilbage();


        HBox hbox = new HBox();
        hbox.setSpacing(20);
        hbox.setAlignment(Pos.CENTER);

        this.comboTilbageleveret.setPrefWidth(65);
        this.comboTilbageleveret.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        hbox.getChildren().add(this.comboTilbageleveret);
        ChangeListener<Integer> listenerCombo = (ov, o, n) -> this.btnTilbageleveret.setDisable(false);
        this.comboTilbageleveret.getSelectionModel().selectedItemProperty().addListener(listenerCombo);
        this.btnTilbageleveret.setPrefWidth(115);
        this.btnTilbageleveret.setDisable(true);
        this.btnTilbageleveret.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        hbox.getChildren().add(this.btnTilbageleveret);

        pane.add(hbox, 3, 8);
        this.btnTilbageleveret.setOnAction(actionEvent -> this.tilfoejTilbageleveretTilSalgslinje());
        DropShadow shadow = new DropShadow();
//Adding the shadow when the mouse cursor is on
        this.btnTilbageleveret.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnTilbageleveret.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnTilbageleveret.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnTilbageleveret.setEffect(null));


        btnBetalSalg.setStyle("-fx-font-size:20;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        btnBetalSalg.setOnAction(actionEvent -> this.betalSalg());
        //Adding the shadow when the mouse cursor is on
        this.btnBetalSalg.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnBetalSalg.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnBetalSalg.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnBetalSalg.setEffect(null));
//        pane.add(btnBetalSalg,1,10);

        btnAfbryd.setStyle("-fx-font-size:20;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        btnAfbryd.setOnAction(actionEvent -> this.close());
        //Adding the shadow when the mouse cursor is on
        this.btnAfbryd.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnAfbryd.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnAfbryd.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnAfbryd.setEffect(null));
//        pane.add(btnAfbryd,2,10);
        HBox hboxBtn = new HBox();
        hboxBtn.setAlignment(Pos.CENTER);
        hboxBtn.setSpacing(20);

        hboxBtn.getChildren().addAll(btnBetalSalg, btnAfbryd);
        pane.add(hboxBtn, 1, 10);

        comboBetalingsform.setPromptText("Vælg betalingsform");
        comboBetalingsform.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        pane.add(comboBetalingsform, 1, 9);
        comboBetalingsform.getItems().setAll(Betalingsform.values());
        comboBetalingsform.getItems().remove(Betalingsform.KLIPPEKORT);


    }

    private void tilfoejTilbageleveretTilSalgslinje() {
        SalgsLinje salgsLinje = this.salgslinjerTilbage.getSelectionModel().getSelectedItem();
        if (salgsLinje == null)
            return;
        if (!(salgsLinje instanceof TilbageleveresSalgsLinje))
            return;
        if (this.comboTilbageleveret.valueProperty().getValue() == null)
            return;
        int tempValgt = (Integer) this.comboTilbageleveret.valueProperty().getValue();
        if (tempValgt == 0)
            return;
        ((TilbageleveresSalgsLinje) salgsLinje).setAntalTilbageleveret(tempValgt);
        this.opdaterControls();
    }

    private void betalSalg() {
        Betalingsform betalingsform = comboBetalingsform.valueProperty().getValue();
        if (betalingsform == null)
            return;
        this.salg.betalOgAfslutSalg(betalingsform);
        this.close();
    }

    private void opdaterControls() {
        this.comboTilbageleveret.getItems().clear();
        this.salgslinjerTilbage.getItems().setAll(this.salg.alleTilbageleveringslinjer());
        this.setSalgslinjerTilbage();
        this.txtPrisBetales.setText("" + this.salg.samletPrisPantFratrukket());
        this.txtSamletBetaltPant.setText("" + this.salg.samletPrisPant());
        this.txtPrisUdenPant.setText("" + this.salg.samletPrisUdenPant());
        this.txtSalgNr.setText("" + this.salg.getSalgsNr());
        this.txtRegDato.setText(this.salg.getSalgPaabegyndtDato().toString());
        this.txtPrisliste.setText("" + this.salg.getPrisliste().getPrislistenavn());
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

    private void setSalgslinjerTilbage() {
        this.salgslinjerTilbage.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(SalgsLinje item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    TilbageleveresSalgsLinje tsl = ((TilbageleveresSalgsLinje) item);
                    Produktkategori pg = controller.findProduktKategori(item.getProdukt());
                    String text = pg.getProduktkategorinavn() + " | " + item.getProdukt().getProduktnavn() + " | "
                            + item.getProduktAntal() + " stk." + " | Afleveret: "
                            + tsl.getAntalTilbageleveret() + " Stk."; // get text from item
                    setText(text);
                }
            }
        });
    }

    private void aendreAntalICombo() {
        this.comboTilbageleveret.getItems().clear();
        SalgsLinje salgsLinje = this.salgslinjerTilbage.getSelectionModel().getSelectedItem();
        if (salgsLinje == null)
            return;
        if (!(salgsLinje instanceof TilbageleveresSalgsLinje))
            return;
        int kanTilbageLeveres = salgsLinje.getProduktAntal();

        for (int i = 0; i <= kanTilbageLeveres; i++) {
            comboTilbageleveret.getItems().add(i);
        }
    }

}
