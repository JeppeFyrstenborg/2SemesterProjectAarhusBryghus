package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretSalgslinjeVindue extends Stage {

    private final PrislisteProdukt produkt; //Nullable
    private final Salg salg;

    /**
     * Note: Nullable param produkt.
     */
    public OpretSalgslinjeVindue(String title, PrislisteProdukt produkt, Salg salg) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.produkt = produkt;
        this.salg = salg;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }


    private final TextField txfProduktnavn = new TextField();
    private final TextField txtPris = new TextField();
    private final TextField txfPantNavn = new TextField();
    private final TextField txtPrisPant = new TextField();
    private final Button btnOK = new Button("Opret salgslinje");
    private final Button btnAfbryd = new Button("Afbryd");
    private final Label lblError = new Label("Forkert input");
    private final TextField txtAntalPant = new TextField();
    private final ComboBox<Integer> antal = new ComboBox<>();


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

        //De vboxe navn, pris, og antal skal ind i.
        VBox vBoxVenstre = new VBox();
        vBoxVenstre.setSpacing(5);
        vBoxVenstre.setAlignment(Pos.BASELINE_LEFT);
        VBox vBoxMidt = new VBox();
        vBoxMidt.setSpacing(5);
        vBoxMidt.setAlignment(Pos.BASELINE_LEFT);
        VBox vBoxHoejre = new VBox();
        vBoxHoejre.setSpacing(5);
        vBoxHoejre.setAlignment(Pos.BASELINE_LEFT);

        //Den hbox som holder styr på de forskellige vboxe.
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);
        hBox.getChildren().addAll(vBoxVenstre, vBoxMidt, vBoxHoejre);
        pane.add(hBox, 0, 0, 3, 4);

        //Alt vedrørende produktnavn.
        Label lblNavn = new Label("Produktnavn");
        lblNavn.setPrefWidth(200);
        lblNavn.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
//        pane.add(lblNavn, 0, 0);
        vBoxVenstre.getChildren().add(lblNavn);

        this.txfProduktnavn.setPrefWidth(200);
        this.txfProduktnavn.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.txfProduktnavn.setText(this.produkt.getProdukt().getProduktnavn());
        this.txfProduktnavn.setDisable(true);
//        pane.add(this.txfProduktnavn, 0, 1);
        vBoxVenstre.getChildren().add(txfProduktnavn);

        //Alt vedrørende produktpris
        Label lblPris = new Label("Produktpris");
        lblPris.setPrefWidth(100);
        lblPris.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
//        pane.add(lblPris, 1, 0);
        vBoxMidt.getChildren().add(lblPris);

        this.txtPris.setPrefWidth(100);
        this.txtPris.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.txtPris.setText("" + this.produkt.getNyPris());
        this.txtPris.setDisable(true);
//        pane.add(this.txtPris, 1, 1);
        vBoxMidt.getChildren().add(txtPris);

        //Alt vedrørende produktantal.
        Label lblAntal = new Label("Antal");
        lblAntal.setPrefWidth(100);
        lblAntal.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
//        pane.add(lblAntal, 2, 0);
        vBoxHoejre.getChildren().add(lblAntal);

        this.antal.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
        this.antal.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.antal.setPrefWidth(60);
        this.antal.getSelectionModel().selectFirst();
//        pane.add(this.antal,2,1);
        vBoxHoejre.getChildren().add(antal);
//        ChangeListener<Integer> listenerAntal = (ov, o, n) -> this.opdaterAntal();
//        this.antal.getSelectionModel().selectedItemProperty().addListener(listenerAntal);

//        //Alt vedrørende pantnavn.
//        if (this.pant != null) {
//            Label lblNavnPant = new Label("Pant");
//            lblNavnPant.setPrefWidth(200);
//            lblNavnPant.setStyle("-fx-font-size:14;" +
//                    "-fx-font-weight: bold;" +
//                    "-fx-text-fill: white;");
////        pane.add(lblNavnPant, 0, 2);
//            vBoxVenstre.getChildren().add(lblNavnPant);
//
//
//            this.txfPantNavn.setPrefWidth(200);
//            this.txfPantNavn.setStyle("-fx-font-size:14;" +
//                    "-fx-font-weight: bold;");
//            if (this.pant != null) {
//                this.txfPantNavn.setText(this.pant.getProdukt().getProduktnavn());
//            }
//            this.txfPantNavn.setDisable(true);
////        pane.add(this.txfPantNavn, 0, 3);
//            vBoxVenstre.getChildren().add(txfPantNavn);
//
//            //Alt vedrørende prispant.
//            Label lblPrisPant = new Label("Pant pris");
//            lblPrisPant.setPrefWidth(100);
//            lblPrisPant.setStyle("-fx-font-size:14;" +
//                    "-fx-font-weight: bold;" +
//                    "-fx-text-fill: white;");
////        pane.add(lblPrisPant, 1, 2);
//            vBoxMidt.getChildren().add(lblPrisPant);
//
//            this.txtPrisPant.setPrefWidth(100);
//            this.txtPrisPant.setStyle("-fx-font-size:14;" +
//                    "-fx-font-weight: bold;");
//            if (this.pant != null) {
//                this.txtPrisPant.setText("" + this.pant.getNyPris());
//            }
//            this.txtPrisPant.setDisable(true);
////        pane.add(this.txtPrisPant, 1, 3);
//            vBoxMidt.getChildren().add(txtPrisPant);
//
//            //Alt vedrørende antal pant.
//            Label lblAntalPant = new Label("Antal pant");
//            lblAntalPant.setPrefWidth(100);
//            lblAntalPant.setStyle("-fx-font-size:14;" +
//                    "-fx-font-weight: bold;" +
//                    "-fx-text-fill: white;");
////        pane.add(lblAntalPant, 2, 2);
//            vBoxHoejre.getChildren().add(lblAntalPant);
//
//            this.txtAntalPant.setPrefWidth(20);
//            this.txtAntalPant.setStyle("-fx-font-size:14;" +
//                    "-fx-font-weight: bold;");
//            if (this.pant != null)
//                this.txtAntalPant.setText("" + this.antal.getValue());
//            this.txtAntalPant.setDisable(true);
////        pane.add(this.txtAntalPant, 2, 3);
//            vBoxHoejre.getChildren().add(txtAntalPant);
//        }


        //Alt vedrørende knapper (OK og Afbryd).
        HBox hBoxBtn = new HBox();
        hBoxBtn.setSpacing(20);
        this.btnOK.setPrefWidth(110);
        this.btnOK.setStyle("-fx-font-size:12;" +
                "-fx-font-weight: bold;");
        this.btnAfbryd.setPrefWidth(110);
        this.btnAfbryd.setStyle("-fx-font-size:12;" +
                "-fx-font-weight: bold;");
        hBoxBtn.getChildren().addAll(this.btnOK, this.btnAfbryd);
        pane.add(hBoxBtn, 0, 5, 3, 1);

        pane.setAlignment(Pos.CENTER);
        this.btnAfbryd.setOnAction(actionEvent -> this.close());
        this.btnOK.setOnAction(actionEvent -> this.opretSalgslinje());

    }


    //Metode til at rette i produktet.
    public void opretSalgslinje() {
        int antalProdukter = this.antal.getSelectionModel().getSelectedItem();
        if (antalProdukter < 1)
            return;
        Produkt produkt = this.produkt.getProdukt();
        if (produkt == null)
            return;

        this.salg.opretEllerTilfoejTilSalgsLinje(antalProdukter, produkt);
        this.close();
    }


}
