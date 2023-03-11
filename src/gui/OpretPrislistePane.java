package gui;

import application.controller.Controller;
import application.model.FredagsbarPrislisteProdukt;
import application.model.Prisliste;
import application.model.PrislisteProdukt;
import application.model.Produkt;
import com.sun.javafx.collections.MappingChange;
import com.sun.jdi.Value;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import storage.Storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpretPrislistePane extends GridPane {
    private final TextField txtNavn = new TextField();
    private Prisliste prislisteTemp;
    private ListView<PrislisteProdukt> produkterOgPriser = new ListView<>();
    private final Button btnOpretPrisliste = new Button("Opret Prisliste");
    private final Button btnRetPris = new Button("Ret pris");
    private final Button btnSletPrislisteProdukt = new Button("Slet produkt fra liste");
    private final TextField txtNavnInfo = new TextField();
    private final TextField txtRetPris = new TextField();
    private final TextField txtAntalKlip = new TextField();
    private boolean check = false;
    private final Controller controller = new Controller();
    private final Storage storage = Storage.getStorage();


    public OpretPrislistePane() {
        //Afstand fra kant og ind.
        this.setPadding(new Insets(20));
        //Afstand mellem noder horisontalt.
        this.setHgap(70);
        //Afstand mellem noder vertikalt.
        this.setVgap(10);
        //Synlighed for linjerne i gridpane.
        this.setGridLinesVisible(false);
        //Baggrundsfarve
        this.setStyle("-fx-background-color: #fff");
        //Stage er bliver sat.


        //Alt vedrørende navn.
        Label lblNavn = new Label("Navn: ");
        lblNavn.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblNavn, 0, 3);
        this.add(this.txtNavn, 1, 3);
        this.txtNavn.setPrefWidth(200);
        this.txtNavn.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");


        //Alt vedrørende listview, som viser liste over produktpriser.
        this.add(this.produkterOgPriser, 2, 3, 5, 20);
        this.produkterOgPriser.setPrefHeight(600);
        this.produkterOgPriser.setPrefWidth(400);
        ChangeListener<PrislisteProdukt> listener = (ov, o, n) -> this.valgtProduktAendret();
        this.produkterOgPriser.getSelectionModel().selectedItemProperty().addListener(listener);
        this.produkterOgPriser.setStyle("-fx-font-size:18;" + "-fx-background-insets: 10");
//        +"-fx-control-inner-background: blue"


        //Alt vedrørende textbox med navn fra prislisteProdukt.
        Label lblNavnPaaPlp = new Label("Navn:");
        lblNavnPaaPlp.setPrefWidth(200);
        this.add(lblNavnPaaPlp, 7, 3);
        lblNavnPaaPlp.setStyle("-fx-font-size:18");
        this.txtNavnInfo.setEditable(false);
        this.txtNavnInfo.setPrefWidth(200);
        this.add(this.txtNavnInfo, 7, 4);
        this.txtNavnInfo.setStyle("-fx-font-size:18");

        //Alt vedrørende textbox med pris fra prislisteProdukt.
        Label lblPrisPaaPlp = new Label("Pris:");
        lblPrisPaaPlp.setPrefWidth(200);
        this.add(lblPrisPaaPlp, 7, 5);
        lblPrisPaaPlp.setStyle("-fx-font-size:18");
        this.txtRetPris.setPrefWidth(200);
        this.add(this.txtRetPris, 7, 6);
        this.txtRetPris.setStyle("-fx-font-size:18");

        //Alt vedrørende textbox med klip fra prislisteProdukt.
        Label lblKlipPaaPlp = new Label("Antal klip:");
        lblKlipPaaPlp.setPrefWidth(200);
        this.add(lblKlipPaaPlp, 7, 7);
        lblKlipPaaPlp.setStyle("-fx-font-size:18");
        this.txtAntalKlip.setPrefWidth(200);
        this.add(this.txtAntalKlip, 7, 8);
        this.txtAntalKlip.setStyle("-fx-font-size:18");

        //Alt vedrørende knappen "Ret pris".
        this.btnRetPris.setOnAction(actionEvent -> this.opdaterPrisBtn());
        this.btnRetPris.setPrefSize(200, 40);
        this.btnRetPris.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.add(this.btnRetPris, 7, 9);

        //Alt vedrørende knappen "Slet produkt fra liste".
        this.btnSletPrislisteProdukt.setOnAction(actionEvent -> this.sletPrislisteProduktFraListe());
        this.btnSletPrislisteProdukt.setPrefSize(200, 40);
        this.btnSletPrislisteProdukt.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;");
        this.add(this.btnSletPrislisteProdukt, 7, 10);

        //Alt vedrørende knappen "Opret Prisliste".
        this.add(this.btnOpretPrisliste, 0, 21, 2, 2);
        this.btnOpretPrisliste.setPrefSize(300, 50);
//        this.btnOpretPrisliste.setStyle("-fx-font-size:22");
        this.btnOpretPrisliste.setStyle("-fx-text-fill: white;" +
                "-fx-background-color: #135a1f;" +
                "-fx-font-weight: bolder;" +
                "-fx-font-size: 30;");
        DropShadow shadow = new DropShadow();
//Adding the shadow when the mouse cursor is on
        this.btnOpretPrisliste.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnOpretPrisliste.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnOpretPrisliste.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnOpretPrisliste.setEffect(null));
//        this.add(this.table, 2, 3, 5, 10);

    }

    //Laver en ny prisliste med standard priser og produkter.
    public void setPrisliste() {
        this.prislisteTemp = controller.opretPrisliste();
        this.controller.udfyldPrislisteMedPrislisteProdukter(this.prislisteTemp);
    }

    //Opdaterer navn pg liste af PrislisteProdukter.
    public void updaterInit() {
        this.txtNavn.setText(this.prislisteTemp.getPrislistenavn());
        this.produkterOgPriser.getItems().setAll(this.prislisteTemp.getPrislisteProdukter());
    }

    //Metode til at slette den midlerdige prisliste. Hvis man fortryder oprettelse af ny prisliste.
    public void tilbageOgSletTempPL() {
        if (this.prislisteTemp == null)
            return;
        storage.deletePrisListe(this.prislisteTemp);
        this.prislisteTemp = null;
    }

    //Ændrer navnet opå den allerede oprettede prisliste, og ændrer boolean værdien,
    // så man kan se der ikke er nogen midlertidig prisliste oprettet.
    public void opretPrisliste() {
        String navn = this.txtNavn.getText().trim();
        if (this.txtNavn.getText().trim().equalsIgnoreCase(this.prislisteTemp.getPrislistenavn())) {
            this.txtNavn.requestFocus();
            return;
        }

        this.prislisteTemp.setPrislistenavn(navn);
        this.prislisteTemp = null;
        this.setCheck(false);
    }


    private void valgtProduktAendret() {
        this.opdaterControls();
    }

    //Opdaterer de forskellige noder. Eksempelvis når man har ændret en pris.
    public void opdaterControls() {
        PrislisteProdukt prislisteProdukt = this.produkterOgPriser.getSelectionModel().getSelectedItem();
        if (prislisteProdukt != null) {
            this.txtNavnInfo.setText(prislisteProdukt.getProdukt().getProduktnavn());
            this.txtRetPris.setText(Double.toString(prislisteProdukt.getNyPris()));
            if (prislisteProdukt instanceof FredagsbarPrislisteProdukt)
                this.txtAntalKlip.setText(Integer.toString(((FredagsbarPrislisteProdukt) prislisteProdukt).
                        getAntalKlipSomPris()));
            else
                this.txtAntalKlip.clear();
        } else {
            this.txtNavnInfo.clear();
            this.txtRetPris.clear();
            this.txtAntalKlip.clear();
        }
    }

    //Opdaterer prisen på prislisteproduktet.
    public void opdaterPrisBtn() {
        PrislisteProdukt prislisteProdukt = this.produkterOgPriser.getSelectionModel().getSelectedItem();
        if (prislisteProdukt == null || this.prislisteTemp == null)
            return;

        if (this.txtAntalKlip.getText().trim().equals("")) {
            Double pris = -1.0;
            try {
                pris = Double.parseDouble(this.txtRetPris.getText().trim());
            } catch (NumberFormatException ex) {
                // do nothing
            }
            if (pris < 0) {
                this.txtRetPris.requestFocus();
                return;
            }

            prislisteProdukt.setNyPris(pris);
            this.txtNavnInfo.clear();
            this.txtRetPris.clear();
            this.produkterOgPriser.refresh();
            this.updaterInit();
        } else {

            Double pris = -1.0;
            try {
                pris = Double.parseDouble(this.txtRetPris.getText().trim());
            } catch (NumberFormatException ex) {
                // do nothing
            }
            if (pris < 0) {
                this.txtRetPris.requestFocus();
                return;
            }

            prislisteProdukt.setNyPris(pris);

            int klip = -1;
            try {
                klip = Integer.parseInt(this.txtAntalKlip.getText().trim());
            } catch (NumberFormatException ex) {
                // do nothing
            }
            if (klip < 0) {
                this.txtAntalKlip.requestFocus();
                return;
            }

            this.prislisteTemp.udskiftPrislisteProduktMedFredagsbarPrislisteProdukt(prislisteProdukt, klip);
            prislisteProdukt.setNyPris(pris);
            this.txtNavnInfo.clear();
            this.txtRetPris.clear();
            this.txtAntalKlip.clear();
            this.produkterOgPriser.refresh();
            this.updaterInit();
        }


    }

    //Fjerner prislisteProdukt fra prislisten.
    public void sletPrislisteProduktFraListe() {
        PrislisteProdukt prislisteProdukt = this.produkterOgPriser.getSelectionModel().getSelectedItem();
        if (prislisteProdukt == null)
            return;
        this.prislisteTemp.removePrislisteProdukt(prislisteProdukt);
        this.updaterInit();
    }

    public Button getBtnOpretPrisliste() {
        return btnOpretPrisliste;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}


