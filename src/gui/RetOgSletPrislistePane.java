package gui;

import application.controller.Controller;
import application.model.FredagsbarPrislisteProdukt;
import application.model.Prisliste;
import application.model.PrislisteProdukt;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import storage.Storage;

public class RetOgSletPrislistePane extends GridPane {

    private final Button btnSletPrisliste = new Button("Slet Prisliste");
    private final Button btnRetPris = new Button("Ret pris");
    private final Button btnSletPrislisteProdukt = new Button("Slet prislisteprodukt");
    private final TextField txtNavnInfo = new TextField();
    private final TextField txtRetPris = new TextField();
    private final TextField txtAntalKlip = new TextField();
    private final ListView<Prisliste> listViewPrislister = new ListView<>();
    private final ListView<PrislisteProdukt> listViewPrislisteProdukter = new ListView<>();
    private Prisliste prislisteTemp;

    public RetOgSletPrislistePane() {
        new InitPaneFactory(this);

        Label lblPrislister = new Label("Prislister:");
        new StyleNodeFactory(lblPrislister);
        this.add(lblPrislister, 0, 0);

        new StyleNodeFactory(listViewPrislister);
        listViewPrislister.getItems().setAll(Storage.getStorage().getAllPrislister());
        this.add(listViewPrislister, 0, 1, 2, 6);
        listViewPrislister.setPrefWidth(400);
        listViewPrislister.setPrefHeight(550);
        this.setPrislisteListe();

        Label lblPrislisteProdukter = new Label("PrislisteProdukter:");
        new StyleNodeFactory(lblPrislisteProdukter);
        this.add(lblPrislisteProdukter, 2, 0);
        new StyleNodeFactory(listViewPrislisteProdukter);
        this.add(listViewPrislisteProdukter, 2, 1, 2, 6);
        listViewPrislisteProdukter.setPrefWidth(400);
        listViewPrislisteProdukter.setPrefHeight(550);

        ChangeListener<Prisliste> listenerPrisliste = (ov, o, n) -> opdaterPrislisteListe();
        listViewPrislister.getSelectionModel().selectedItemProperty().addListener(listenerPrisliste);

        ChangeListener<PrislisteProdukt> listenerPrislisteProdukter = (ov, o, n) -> opdaterPrislisteProdukter();
        listViewPrislisteProdukter.getSelectionModel().selectedItemProperty().addListener(listenerPrislisteProdukter);

        Label lblNavn = new Label("Navn:");
        new StyleNodeFactory(lblNavn);
        this.add(lblNavn, 4, 1);
        txtNavnInfo.setDisable(true);
        new StyleNodeFactory(txtNavnInfo);
        this.add(txtNavnInfo, 4, 2);

        Label lblPris = new Label("Pris:");
        new StyleNodeFactory(lblPris);
        this.add(lblPris, 4, 3);
        new StyleNodeFactory(txtRetPris);
        this.add(txtRetPris, 4, 4);

        Label lblKlip = new Label("Klip:");
        new StyleNodeFactory(lblKlip);
        this.add(lblKlip, 4, 5);
        new StyleNodeFactory(txtAntalKlip);
        this.add(txtAntalKlip, 4, 6);

        new StyleNodeFactory(btnRetPris);
        this.add(btnRetPris, 4, 7);
        btnRetPris.setDisable(true);
        btnRetPris.setOnAction(actionEvent -> this.opdaterPrislisteProdukt());

        btnSletPrislisteProdukt.setDisable(true);
        btnSletPrislisteProdukt.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: red;" +
                "-fx-opacity: 1.0");
        this.add(btnSletPrislisteProdukt, 4, 8);
        btnSletPrislisteProdukt.setOnAction(actionEvent -> this.sletPrislisteProdukt());

        btnSletPrisliste.setDisable(true);
        btnSletPrisliste.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-color: red;" +
                "-fx-opacity: 1.0");
        this.add(btnSletPrisliste, 4, 9);
        btnSletPrisliste.setOnAction(actionEvent -> this.sletPrisliste());


    }


    public void setPrislisteListe() {
        listViewPrislister.getItems().setAll(Storage.getStorage().getAllPrislister());
        listViewPrislister.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Prisliste item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = "Nr: " + item.getPrislisteNr() + " | " + item.getPrislistenavn();//get text from item
                    setText(text);
                }
            }
        });
    }

    private void setPrislisteProdukter() {
        listViewPrislisteProdukter.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(PrislisteProdukt item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text;
                    if (item instanceof FredagsbarPrislisteProdukt)
                        text = item.getProdukt().getProduktnavn() + " | " + item.getNyPris() + " | "
                                + ((FredagsbarPrislisteProdukt) item).getAntalKlipSomPris()
                                + " Klip";//get text from item
                    else
                        text = item.getProdukt().getProduktnavn() + " | " + item.getNyPris();//get text from item
                    setText(text);
                }
            }
        });
    }

    private void opdaterPrislisteListe() {
        Prisliste prisliste = listViewPrislister.getSelectionModel().getSelectedItem();
        if (prisliste == null)
            return;
        btnSletPrisliste.setDisable(false);
        listViewPrislisteProdukter.getItems().setAll(prisliste.getPrislisteProdukter());
        this.setPrislisteProdukter();
        clearAll();
    }

    private void opdaterPrislisteProdukter() {
        PrislisteProdukt plp = this.listViewPrislisteProdukter.getSelectionModel().getSelectedItem();
        if (plp == null)
            return;
        clearAll();
        this.btnRetPris.setDisable(false);
        this.btnSletPrislisteProdukt.setDisable(false);
        txtNavnInfo.setText(plp.getProdukt().getProduktnavn());
        txtRetPris.setText("" + plp.getNyPris());
        if (plp instanceof FredagsbarPrislisteProdukt)
            txtAntalKlip.setText(((FredagsbarPrislisteProdukt) plp).getAntalKlipSomPris() + "");
    }

    private void clearAll() {
        txtNavnInfo.clear();
        txtRetPris.clear();
        txtAntalKlip.clear();
        btnRetPris.setDisable(true);
        btnSletPrisliste.setDisable(true);
        btnSletPrislisteProdukt.setDisable(true);
    }

    private void sletPrislisteProdukt() {
        Prisliste prisliste = listViewPrislister.getSelectionModel().getSelectedItem();
        if (prisliste == null)
            return;
        PrislisteProdukt plp = listViewPrislisteProdukter.getSelectionModel().getSelectedItem();
        if (plp == null)
            return;
        prisliste.removePrislisteProdukt(plp);
        clearAll();
        opdaterPrislisteListe();
    }

    private void sletPrisliste() {
        Prisliste pl = listViewPrislister.getSelectionModel().getSelectedItem();
        if (pl == null)
            return;
        Storage.getStorage().deletePrisListe(pl);
        clearAll();
        this.setPrislisteListe();
    }

    private void opdaterPrislisteProdukt() {
        Prisliste pl = listViewPrislister.getSelectionModel().getSelectedItem();
        if (pl == null)
            return;
        PrislisteProdukt plp = listViewPrislisteProdukter.getSelectionModel().getSelectedItem();
        if (plp == null || pl == null)
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

            plp.setNyPris(pris);
            this.clearAll();
            opdaterPrislisteListe();
        } else if (!(txtAntalKlip.getText().trim().equals("")) && plp instanceof PrislisteProdukt) {
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

            plp.setNyPris(pris);

            int klip = -1;
            try {
                klip = Integer.parseInt(this.txtAntalKlip.getText().trim());
            } catch (NumberFormatException ex) {
                // do nothing
            }
            if (klip <= 0) {
                this.txtAntalKlip.requestFocus();
                return;
            }

            pl.udskiftPrislisteProduktMedFredagsbarPrislisteProdukt(plp, klip);
            clearAll();
            opdaterPrislisteListe();
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

            plp.setNyPris(pris);

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

            ((FredagsbarPrislisteProdukt) plp).setAntalKlipSomPris(klip);
            clearAll();
            opdaterPrislisteListe();
        }
    }

}
