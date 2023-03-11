package gui;

import application.controller.Controller;
import application.model.Produktkategori;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import storage.Storage;

public class OpretProduktkategoriPane extends GridPane {

    private final ListView<Produktkategori> listViewProKategorier = new ListView<>();
    private final TextField txtNavn = new TextField();
    private final TextField txtNavnRet = new TextField();
    private final Button btnOpret = new Button("Opret");
    private final Button btnRet = new Button("Ret");
    private final Button btnSlet = new Button("Slet");

    private Controller controller = new Controller();

    public OpretProduktkategoriPane() {
        new InitPaneFactory(this);

        Label lblProduktKategorier = new Label("Produktkategorier:");
        new StyleNodeFactory(lblProduktKategorier);
        this.add(lblProduktKategorier, 1, 0);

        Label lblNavn = new Label("Navn:");
        new StyleNodeFactory(lblNavn);
        this.add(lblNavn, 0, 0);

        new StyleNodeFactory(txtNavn);
        this.add(txtNavn, 0, 1);

        new StyleNodeFactory(txtNavnRet);
        this.add(txtNavnRet, 2, 1);

        Label lblRetNavn = new Label("Ret Navn:");
        new StyleNodeFactory(lblRetNavn);
        this.add(lblRetNavn, 2, 0);

        new StyleNodeFactory(listViewProKategorier);
        listViewProKategorier.setPrefWidth(300);
        this.add(listViewProKategorier, 1, 1, 1, 8);

        ChangeListener<Produktkategori> listenerProKategori = (ov, o, n) -> this.opdaterTextFelter();
        listViewProKategorier.getSelectionModel().selectedItemProperty().addListener(listenerProKategori);

        new StyleNodeFactory(btnOpret);
        this.add(btnOpret, 0, 2);
        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        this.btnOpret.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnOpret.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnOpret.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnOpret.setEffect(null));
        this.btnOpret.setOnAction(actionEvent -> this.opretProduktkategori());

        new StyleNodeFactory(btnRet);
        this.add(btnRet, 2, 2);
        //Adding the shadow when the mouse cursor is on
        this.btnRet.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnRet.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnRet.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnRet.setEffect(null));
        this.btnRet.setOnAction(actionEvent -> this.retProduktkategori());

        btnSlet.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0;" +
                "-fx-background-color: red");
        this.add(btnSlet, 2, 4);
        //Adding the shadow when the mouse cursor is on
        this.btnSlet.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnSlet.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnSlet.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnSlet.setEffect(null));
        this.btnSlet.setOnAction(actionEvent -> this.sletProduktKategori());


        this.setAlignment(Pos.TOP_CENTER);

    }

    public void setProguktKategorier() {
        listViewProKategorier.getItems().setAll(Storage.getStorage().getAllProduktkategorier());

        listViewProKategorier.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Produktkategori item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = item.getProduktkategorinavn(); // get text from item
                    setText(text);
                }
            }
        });
    }

    private void opdaterTextFelter() {
        clearAll();
        Produktkategori produktkategori = listViewProKategorier.getSelectionModel().getSelectedItem();
        if (produktkategori == null)
            return;
        txtNavnRet.setText(produktkategori.getProduktkategorinavn());
    }

    private void clearAll() {
        txtNavn.clear();
        txtNavnRet.clear();
    }

    private void opretProduktkategori() {
        String navn = txtNavn.getText().trim();
        if (navn.equalsIgnoreCase(""))
            return;
        controller.opretProduktkategori(navn);
        clearAll();
        setProguktKategorier();
    }

    private void retProduktkategori() {
        Produktkategori produktkategori = listViewProKategorier.getSelectionModel().getSelectedItem();
        if (produktkategori == null)
            return;
        String navnRet = txtNavnRet.getText().trim();
        if (navnRet.equalsIgnoreCase(""))
            return;
        produktkategori.setProduktkategorinavn(navnRet);

        clearAll();
        setProguktKategorier();
    }

    private void sletProduktKategori() {
        Produktkategori produktkategori = listViewProKategorier.getSelectionModel().getSelectedItem();
        if (produktkategori == null)
            return;
        Storage.getStorage().deleteProduktkategori(produktkategori);
        clearAll();
        setProguktKategorier();
    }
}
