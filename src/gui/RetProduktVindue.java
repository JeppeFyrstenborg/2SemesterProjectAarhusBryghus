package gui;

import application.model.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RetProduktVindue extends Stage {

    private final Produkt produkt; //Nullable

    /**
     * Note: Nullable param produkt.
     */
    public RetProduktVindue(String title, Produkt produkt) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);


        this.produkt = produkt;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private final TextField txfNavn = new TextField();
    private final TextField txtNormalpris = new TextField();
    private final Button btnOK = new Button("Ret produkt");
    private final Button btnAfbryd = new Button("Afbryd");
    private final Label lblError = new Label("Forkert input");
    private final TextField txtLiter = new TextField();
    private final TextField txtKilo = new TextField();
    private final TextField txtKlip = new TextField();
    private final ComboBox<Integer> haner = new ComboBox<>();
    private final CheckBox bar = new CheckBox("Bar?");
    private final CheckBox tilbageleveres = new CheckBox("Tilbageleveres, Ja/Nej");
    private final ComboBox<PantType> pantTyper = new ComboBox<>();


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
        pane.setPrefWidth(300);
        pane.setPrefHeight(350);
        //Baggrundsfarve
        pane.setStyle("-fx-background-color: #135a1f;");

        //Alt vedrørende navn.
        Label lblNavn = new Label("Navn");
        lblNavn.setPrefWidth(200);
        lblNavn.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lblNavn, 0, 0);
        this.txfNavn.setPrefWidth(200);
        this.txfNavn.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.txfNavn.setText(this.produkt.getProduktnavn());
        pane.add(this.txfNavn, 0, 1);

        //Alt vedrørende normal pris.
        Label lblPris = new Label("Normal pris:");
        lblPris.setPrefWidth(200);
        lblPris.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lblPris, 0, 2);
        this.txtNormalpris.setPrefWidth(200);
        this.txtNormalpris.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.txtNormalpris.setText(Double.toString(this.produkt.getNormalpris()));
        pane.add(this.txtNormalpris, 0, 3);
        this.tilbageleveres.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        this.tilbageleveres.setSelected(this.produkt.isTilbageleveres());
        pane.add(this.tilbageleveres, 0, 4);

        //Alt vedrørende mængde.

        if (this.produkt instanceof Drikkevare) {
            Label lblLiter = new Label("Liter:");
            lblLiter.setPrefWidth(200);
            lblLiter.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");
            pane.add(lblLiter, 0, 5);
            this.txtLiter.setPrefWidth(200);
            this.txtLiter.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;");
            this.txtLiter.setText(Double.toString(((Drikkevare) this.produkt).getLiter()));
            pane.add(txtLiter, 0, 6);
        }


        if (this.produkt instanceof Kilovare) {
            Label lblGram = new Label("Gram:");
            lblGram.setPrefWidth(200);
            lblGram.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");
            pane.add(lblGram, 0, 5);
            this.txtKilo.setPrefWidth(200);
            this.txtKilo.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;");
            this.txtKilo.setText(Double.toString(((Kilovare) this.produkt).getGram()));
            pane.add(txtKilo, 0, 6);
        }

        if (this.produkt instanceof Klippekort) {
            Label lblKlippekort = new Label("Antal klip:");
            lblKlippekort.setPrefWidth(200);
            lblKlippekort.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");
            pane.add(lblKlippekort, 0, 5);
            this.txtKlip.setPrefWidth(200);
            this.txtKlip.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;");
            this.txtKlip.setText(Integer.toString(((Klippekort) this.produkt).getAntalKlip()));
            pane.add(txtKlip, 0, 6);
        }

        if (this.produkt instanceof Anlaeg) {
            Label lblAntalHaner = new Label("Antal haner:");
            lblAntalHaner.setPrefWidth(200);
            lblAntalHaner.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");
            pane.add(lblAntalHaner, 0, 5);

            this.haner.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
            this.haner.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;");
            this.haner.setPrefWidth(90);
            pane.add(this.haner, 0, 6);
            this.haner.getSelectionModel().select(((Anlaeg) this.produkt).getAntalHaner());

            this.bar.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");
            pane.add(this.bar, 0, 7);
        }

        if (this.produkt instanceof Pant) {
            Label lblPantType = new Label("Gram:");
            lblPantType.setPrefWidth(200);
            lblPantType.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;" +
                    "-fx-text-fill: white;");
            pane.add(lblPantType, 0, 5);
            this.pantTyper.setPrefWidth(200);
            this.pantTyper.setStyle("-fx-font-size:14;" +
                    "-fx-font-weight: bold;");
            this.pantTyper.getItems().setAll(PantType.values());
            this.pantTyper.getSelectionModel().select(((Pant) this.produkt).getPantType());
            pane.add(pantTyper, 0, 6);
        }


        //Alt vedrørende knapper (OK og Afbryd).
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        this.btnOK.setPrefWidth(110);
        this.btnOK.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.btnAfbryd.setPrefWidth(110);
        this.btnAfbryd.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        hBox.getChildren().addAll(this.btnOK, this.btnAfbryd);
        this.lblError.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: lighter;" +
                "-fx-text-fill: red;");
        pane.add(hBox, 0, 10);
        pane.setAlignment(Pos.CENTER);
        this.btnAfbryd.setOnAction(actionEvent -> this.close());
        this.btnOK.setOnAction(actionEvent -> this.retProduktAction());

        //Error label
        pane.add(this.lblError, 0, 7);
        this.lblError.setVisible(false);

    }


    //Metode til at rette i produktet.
    public void retProduktAction() {
        String navn = this.txfNavn.getText().trim();

        Double normalpris = -1.0;
        try {
            normalpris = Double.parseDouble(this.txtNormalpris.getText().trim());
        } catch (NumberFormatException ex) {
            // do nothing
        }
        if (normalpris <= 0.0) {
            this.txtNormalpris.requestFocus();
            this.lblError.setVisible(true);
            return;
        }

        boolean tilbageLeveresTemp = this.tilbageleveres.selectedProperty().getValue();

        this.produkt.setProduktnavn(navn);
        this.produkt.setNormalpris(normalpris);
        this.produkt.setTilbageleveres(tilbageLeveresTemp);

        if (this.produkt instanceof Drikkevare) {
            double liter = -1.0;
            try {
                liter = Double.parseDouble(this.txtLiter.getText().trim());
            } catch (NumberFormatException ex) {
                // do nothing
            }
            if (liter <= 0.0) {
                this.txtLiter.requestFocus();
                this.lblError.setVisible(true);
                return;
            }
            ((Drikkevare) this.produkt).setLiter(liter);
        }

        if (this.produkt instanceof Kilovare) {
            double kilo = -1.0;
            try {
                kilo = Double.parseDouble(this.txtKilo.getText().trim());
            } catch (NumberFormatException ex) {
                // do nothing
            }
            if (kilo <= 0.0) {
                this.txtKilo.requestFocus();
                this.lblError.setVisible(true);
                return;
            }
            ((Kilovare) this.produkt).setGram(kilo);
        }

        if (this.produkt instanceof Klippekort) {
            int klip = -1;
            try {
                klip = Integer.parseInt(this.txtKlip.getText().trim());
            } catch (NumberFormatException ex) {
                // do nothing
            }
            if (klip <= 0) {
                this.txtKilo.requestFocus();
                this.lblError.setVisible(true);
                return;
            }
            ((Klippekort) this.produkt).setAntalKlip(klip);
        }

        if (this.produkt instanceof Anlaeg) {
            int antalHaner = this.haner.getValue();
            boolean barJaNej = this.bar.selectedProperty().get();
            ((Anlaeg) this.produkt).setAntalHaner(antalHaner);
            ((Anlaeg) this.produkt).setBar(barJaNej);
        }

        if (this.produkt instanceof Pant) {
            PantType pantType = this.pantTyper.getSelectionModel().getSelectedItem();

            ((Pant) this.produkt).setPantType(pantType);
        }

        this.close();
    }

}
