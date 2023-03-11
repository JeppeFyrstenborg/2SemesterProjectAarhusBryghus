package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;

public class SePrislisteMenuPane extends GridPane {

    private final Button btnOpretPrisliste = new Button("Opret prisliste");
    private final Button btnSePrislister = new Button("Se prislister");
    private final Button btnRetOgSletPrislister = new Button("Slet og ret prislister");

    public SePrislisteMenuPane() {
        //Afstand fra kant og ind.
        this.setPadding(new Insets(100));
        //Afstand mellem noder horisontalt.
        this.setHgap(70);
        //Afstand mellem noder vertikalt.
        this.setVgap(10);
        //Synlighed for linjerne i gridpane.
        this.setGridLinesVisible(false);
        //Baggrundsfarve
        this.setStyle("-fx-background-color: #fff");

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);

        DropShadow shadow = new DropShadow();

        //Alt vedrørende knappen "Oversigt over dagssalg".
        this.btnOpretPrisliste.setPrefSize(400, 200);
        this.btnOpretPrisliste.setStyle("-fx-font-size:36;" +
                "-fx-font-weight: bold");
        this.btnOpretPrisliste.setTextAlignment(TextAlignment.CENTER);
        this.btnOpretPrisliste.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnOpretPrisliste.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnOpretPrisliste.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnOpretPrisliste.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnOpretPrisliste.setEffect(null));
        this.add(btnOpretPrisliste, 0, 0);

        //Alt vedrørende knappen "Oversigt over dagssalg".
        this.btnSePrislister.setPrefSize(400, 200);
        this.btnSePrislister.setStyle("-fx-font-size:36;" +
                "-fx-font-weight: bold");
        this.btnSePrislister.setTextAlignment(TextAlignment.CENTER);
        this.btnSePrislister.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnSePrislister.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnSePrislister.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnSePrislister.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnSePrislister.setEffect(null));

        this.add(btnSePrislister, 1, 0);

        //Alt vedrørende knappen "Oversigt over dagssalg".
        this.btnRetOgSletPrislister.setPrefSize(400, 200);
        this.btnRetOgSletPrislister.setStyle("-fx-font-size:36;" +
                "-fx-font-weight: bold");
        this.btnRetOgSletPrislister.setTextAlignment(TextAlignment.CENTER);
        this.btnRetOgSletPrislister.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnRetOgSletPrislister.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnRetOgSletPrislister.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnRetOgSletPrislister.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnRetOgSletPrislister.setEffect(null));


        hBox.getChildren().addAll(btnOpretPrisliste, btnSePrislister, btnRetOgSletPrislister);

        this.add(hBox, 0, 0);
        this.setAlignment(Pos.TOP_CENTER);

    }

    public Button getBtnOpretPrisliste() {
        return btnOpretPrisliste;
    }

    public Button getBtnSePrislister() {
        return btnSePrislister;
    }

    public Button getBtnRetOgSletPrislister() {
        return btnRetOgSletPrislister;
    }

    private void setPane() {
        //Afstand fra kant og ind.
        this.setPadding(new Insets(100));
        //Afstand mellem noder horisontalt.
        this.setHgap(70);
        //Afstand mellem noder vertikalt.
        this.setVgap(10);
        //Synlighed for linjerne i gridpane.
        this.setGridLinesVisible(false);
        //Baggrundsfarve
        this.setStyle("-fx-background-color: #fff");
    }
}
