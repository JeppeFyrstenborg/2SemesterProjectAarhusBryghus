package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class StartsidePane extends GridPane {

    private final Button btnProdukt = new Button("Opret produkt");
    private final Button btnPrisliste = new Button("Se prislister");
    private final Button btnSeOversigter = new Button("Se oversigter");
    private final Button btnOpretKunde = new Button("Opret kunde");
    private final Button btnOpretProduktkategori = new Button("Opret produktkategori");
    private final Button btnRegistrerAndetSalg = new Button("Registrer andet salg");
    private final Button btnRegistrerSalgIFredagsbar = new Button("Salg i fredagsbar");
    private final Button btnBetalSalg = new Button("Betal salg");


    public StartsidePane() {
        //Afstand fra kant og ind.
        this.setPadding(new Insets(10));
        //Afstand mellem noder horisontalt.
        this.setHgap(10);
        //Afstand mellem noder vertikalt.
        this.setVgap(10);
        //Synlighed for linjerne i gridpane.
        this.setGridLinesVisible(false);
        //Baggrundsfarve
        this.setStyle("-fx-background-color: #fff");


        //Alt vedrørende knappen "Opret produkt".
        this.btnProdukt.setPrefSize(200, 100);
        this.btnProdukt.setStyle("-fx-font-size:20;" +
                "-fx-font-weight: bold");
        this.btnProdukt.setTextAlignment(TextAlignment.CENTER);
        this.btnProdukt.setWrapText(true);
        DropShadow shadow = new DropShadow();
//Adding the shadow when the mouse cursor is on
        this.btnProdukt.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnProdukt.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnProdukt.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnProdukt.setEffect(null));

        //Alt vedrørende knappen "Opret produktkategori".
        this.btnOpretProduktkategori.setPrefSize(200, 100);
        this.btnOpretProduktkategori.setStyle("-fx-font-size:20;" +
                "-fx-font-weight: bold");
        this.btnOpretProduktkategori.setTextAlignment(TextAlignment.CENTER);
        this.btnOpretProduktkategori.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnOpretProduktkategori.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnOpretProduktkategori.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnOpretProduktkategori.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnOpretProduktkategori.setEffect(null));

        //Alt vedrørende knappen "Opret kunde".
        this.btnOpretKunde.setPrefSize(200, 100);
        this.btnOpretKunde.setStyle("-fx-font-size:20;" +
                "-fx-font-weight: bold");
        this.btnOpretKunde.setTextAlignment(TextAlignment.CENTER);
        this.btnOpretKunde.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnOpretKunde.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnOpretKunde.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnOpretKunde.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnOpretKunde.setEffect(null));


        //Alt vedrørende knappen "Opret prisliste".
        this.btnPrisliste.setPrefSize(200, 100);
        this.btnPrisliste.setStyle("-fx-font-size:20;" +
                "-fx-font-weight: bold");
        this.btnPrisliste.setTextAlignment(TextAlignment.CENTER);
        this.btnPrisliste.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnPrisliste.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnPrisliste.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnPrisliste.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnPrisliste.setEffect(null));


        //Alt vedrørende knappen "Klippekort status".
        this.btnSeOversigter.setPrefSize(200, 100);
        this.btnSeOversigter.setStyle("-fx-font-size:20;" +
                "-fx-font-weight: bold");
//Adding the shadow when the mouse cursor is on
        this.btnSeOversigter.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnSeOversigter.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnSeOversigter.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnSeOversigter.setEffect(null));
        this.btnSeOversigter.setTextAlignment(TextAlignment.CENTER);
        this.btnSeOversigter.setWrapText(true);

        //Alt vedrørende knappen "Andet salg".
        this.btnRegistrerAndetSalg.setPrefSize(400, 200);
        this.btnRegistrerAndetSalg.setStyle("-fx-font-size:36;" +
                "-fx-font-weight: bold");
        this.btnRegistrerAndetSalg.setTextAlignment(TextAlignment.CENTER);
        this.btnRegistrerAndetSalg.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnRegistrerAndetSalg.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnRegistrerAndetSalg.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnRegistrerAndetSalg.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnRegistrerAndetSalg.setEffect(null));

        //Alt vedrørende knappen "Fredagsbarsalg".
        this.btnRegistrerSalgIFredagsbar.setPrefSize(400, 200);
        this.btnRegistrerSalgIFredagsbar.setStyle("-fx-font-size:36;" +
                "-fx-font-weight: bold");
        this.btnRegistrerSalgIFredagsbar.setTextAlignment(TextAlignment.CENTER);
        this.btnRegistrerSalgIFredagsbar.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnRegistrerSalgIFredagsbar.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnRegistrerSalgIFredagsbar.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnRegistrerSalgIFredagsbar.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnRegistrerSalgIFredagsbar.setEffect(null));


        //Alt vedrørende knappen "Registrer tilbagelevering".
        this.btnBetalSalg.setPrefSize(400, 200);
        this.btnBetalSalg.setStyle("-fx-font-size:36;" +
                "-fx-font-weight: bold");
        this.btnBetalSalg.setTextAlignment(TextAlignment.CENTER);
        this.btnBetalSalg.setWrapText(true);

//Adding the shadow when the mouse cursor is on
        this.btnBetalSalg.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnBetalSalg.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnBetalSalg.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnBetalSalg.setEffect(null));


        //HBox til salg.
        HBox hBox1Row = new HBox();
        hBox1Row.setSpacing(20);
        hBox1Row.setPadding(new Insets(5, 50, 50, 70));
        hBox1Row.getChildren().addAll(this.btnRegistrerAndetSalg, btnRegistrerSalgIFredagsbar, this.btnBetalSalg);
        this.add(hBox1Row, 0, 0, 4, 4);
        hBox1Row.setAlignment(Pos.CENTER);


        //HBox til opret og statistik.
        HBox hBox2Row = new HBox();
        hBox2Row.setSpacing(20);
        hBox2Row.setPadding(new Insets(5, 50, 50, 70));
        hBox2Row.getChildren().addAll(this.btnProdukt, btnOpretProduktkategori, this.btnOpretKunde,
                this.btnPrisliste, this.btnSeOversigter);
        this.add(hBox2Row, 0, 4, 4, 3);
        hBox2Row.setAlignment(Pos.CENTER);

        this.setAlignment(Pos.CENTER);

//        HBox.setHgrow(hBox1Row,Priority.ALWAYS);


    }


    public Button getBtnProdukt() {
        return this.btnProdukt;
    }

    public Button getBtnPrisliste() {
        return btnPrisliste;
    }

    public Button getBtnRegistrerSalg() {
        return btnRegistrerAndetSalg;
    }

    public Button getBtnRegistrerSalgIFredagsbar() {
        return btnRegistrerSalgIFredagsbar;
    }

    public Button getBtnSeOversigter() {
        return btnSeOversigter;
    }

    public Button getBtnRegistrerAndetSalg() {
        return btnRegistrerAndetSalg;
    }

    public Button getBtnBetalSalg() {
        return btnBetalSalg;
    }

    public Button getBtnOpretKunde() {
        return btnOpretKunde;
    }

    public Button getBtnOpretProduktkategori() {
        return btnOpretProduktkategori;
    }
}
