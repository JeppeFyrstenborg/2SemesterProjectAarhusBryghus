package gui;

import application.model.Betalingsform;
import application.model.Salg;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BetaltVindue extends Stage {

    public BetaltVindue(String title, Salg salg) {
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

    private final Salg salg;
    private final Button btnSetBetaling = new Button("Betalt");
    private final RadioButton rdbMobilePay = new RadioButton("MobilePay");
    private final RadioButton rdbBetalingskort = new RadioButton("Betalingskort");
    private final RadioButton rdbKontant = new RadioButton("Kontant");
    private final RadioButton rdbBankoverfoersel = new RadioButton("Bankoverførsel");
    private final RadioButton rdbAnden = new RadioButton("Anden betalingsform");
    private final RadioButton rdbKlippekort = new RadioButton("Klippekort");

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final Button btnAfbryd = new Button("Luk vindue");


    public void initContent(GridPane pane) {
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


        this.rdbMobilePay.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(this.rdbMobilePay, 0, 0);
        this.rdbMobilePay.setToggleGroup(toggleGroup);

        this.rdbBetalingskort.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(this.rdbBetalingskort, 0, 1);
        this.rdbBetalingskort.setToggleGroup(toggleGroup);

        this.rdbKontant.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(this.rdbKontant, 0, 2);
        this.rdbKontant.setToggleGroup(toggleGroup);

        this.rdbBankoverfoersel.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(this.rdbBankoverfoersel, 0, 3);
        this.rdbBankoverfoersel.setToggleGroup(toggleGroup);


        this.rdbKlippekort.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(this.rdbKlippekort, 0, 4);
        this.rdbKlippekort.setToggleGroup(toggleGroup);
        this.rdbKlippekort.setDisable(true);
        if (salg.getPrisliste().erDerFredagsbarPrislisteProdukter())
            this.rdbKlippekort.setDisable(false);

        this.rdbAnden.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white");
        pane.add(this.rdbAnden, 0, 5);
        this.rdbAnden.setToggleGroup(toggleGroup);


        DropShadow shadow = new DropShadow();

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        pane.add(hBox, 0, 7);
        this.btnSetBetaling.setPrefWidth(90);
        this.btnSetBetaling.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold");
        hBox.getChildren().add(this.btnSetBetaling);
        //Adding the shadow when the mouse cursor is on
        this.btnSetBetaling.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnSetBetaling.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnSetBetaling.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnSetBetaling.setEffect(null));
        this.btnSetBetaling.setOnAction(actionEvent -> this.betaltFuldAction());

        this.btnAfbryd.setPrefWidth(90);
        this.btnAfbryd.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold");
        hBox.getChildren().add(this.btnAfbryd);
        //Adding the shadow when the mouse cursor is on
        this.btnAfbryd.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnAfbryd.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnAfbryd.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnAfbryd.setEffect(null));
        this.btnAfbryd.setOnAction(actionEvent -> this.close());
    }


    public void betaltFuldAction() {

        if (this.salg.harPantTilbageleveringSalgslinje()) {
            if (this.toggleGroup.getSelectedToggle() == this.rdbMobilePay) {
                this.salg.betalPantSalgslinjer(Betalingsform.MOBILEPAY);
                this.close();
            }
            if (this.toggleGroup.getSelectedToggle() == this.rdbBetalingskort) {
                this.salg.betalPantSalgslinjer(Betalingsform.BETALINGSKORT);
                this.close();
            }
            if (this.toggleGroup.getSelectedToggle() == this.rdbKontant) {
                this.salg.betalPantSalgslinjer(Betalingsform.KONTANT);
                this.close();
            }
            if (this.toggleGroup.getSelectedToggle() == this.rdbBankoverfoersel) {
                this.salg.betalPantSalgslinjer(Betalingsform.BANKOVERFOERSEL);
                this.close();
            }
            if (this.toggleGroup.getSelectedToggle() == this.rdbKlippekort) {
                this.salg.betalPantSalgslinjer(Betalingsform.KLIPPEKORT);
                this.close();
            }
            if (this.toggleGroup.getSelectedToggle() == this.rdbAnden) {
                this.salg.betalPantSalgslinjer(Betalingsform.ANDEN_BETALINGSFORM);
                this.close();
            }

        } else if (!this.salg.harPantTilbageleveringSalgslinje()) {
            if (this.toggleGroup.getSelectedToggle() == this.rdbMobilePay) {
                this.salg.betalOgAfslutSalg(Betalingsform.MOBILEPAY);
                this.salg.setBetalt(true);
                this.close();
            }
            if (this.toggleGroup.getSelectedToggle() == this.rdbBetalingskort) {
                this.salg.betalOgAfslutSalg(Betalingsform.BETALINGSKORT);
                this.salg.setBetalt(true);
                this.close();
            }
            if (this.toggleGroup.getSelectedToggle() == this.rdbKontant) {
                this.salg.betalOgAfslutSalg(Betalingsform.KONTANT);
                this.salg.setBetalt(true);
                this.close();
            }
            if (this.toggleGroup.getSelectedToggle() == this.rdbBankoverfoersel) {
                this.salg.betalOgAfslutSalg(Betalingsform.BANKOVERFOERSEL);
                this.salg.setBetalt(true);
                this.close();
            }
            if (this.toggleGroup.getSelectedToggle() == this.rdbKlippekort) {
                this.salg.betalOgAfslutSalg(Betalingsform.KLIPPEKORT);
                this.salg.setBetalt(true);
                this.close();
            }
            if (this.toggleGroup.getSelectedToggle() == this.rdbAnden) {
                this.salg.betalOgAfslutSalg(Betalingsform.ANDEN_BETALINGSFORM);
                this.salg.setBetalt(true);
                this.close();
            }
        } else
            this.close();
    }
}

