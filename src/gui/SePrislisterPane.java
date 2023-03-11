package gui;

import application.controller.Controller;
import application.model.*;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import storage.Storage;

public class SePrislisterPane extends GridPane {

    private final ListView<Prisliste> listViewPrislister = new ListView<>();
    private final ListView<PrislisteProdukt> listViewPrislisteProdukter = new ListView<>();


    public SePrislisterPane() {
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


        ChangeListener<Prisliste> listenerPrisliste = (ov, o, n) -> opdaterControls();
        listViewPrislister.getSelectionModel().selectedItemProperty().addListener(listenerPrisliste);


        this.setAlignment(Pos.CENTER);
    }


    public void setPrislisteListe() {
        listViewPrislisteProdukter.getItems().clear();
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
                                + ((FredagsbarPrislisteProdukt) item).getAntalKlipSomPris() + " Klip";
                    else
                        text = item.getProdukt().getProduktnavn() + " | " + item.getNyPris();
                    setText(text);
                }
            }
        });
    }

    private void opdaterControls() {
        Prisliste prisliste = listViewPrislister.getSelectionModel().getSelectedItem();
        if (prisliste == null)
            return;
        listViewPrislisteProdukter.getItems().setAll(prisliste.getPrislisteProdukter());
        this.setPrislisteProdukter();

    }
}
