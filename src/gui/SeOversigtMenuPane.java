package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;

public class SeOversigtMenuPane extends GridPane {


    private final Button btnOversigtDagssalg = new Button("Oversigt over dagssalg");
    private final Button btnKlippekortStatus = new Button("Se klippekort status");
    private final Button btnSeAfleveringsprodukter = new Button("Se ikke afleverede anlæg");

    public SeOversigtMenuPane() {
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

        DropShadow shadow = new DropShadow();

        //Alt vedrørende knappen "Oversigt over dagssalg".
        this.btnOversigtDagssalg.setPrefSize(400, 200);
        this.btnOversigtDagssalg.setStyle("-fx-font-size:36;" +
                "-fx-font-weight: bold");
        this.btnOversigtDagssalg.setTextAlignment(TextAlignment.CENTER);
        this.btnOversigtDagssalg.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnOversigtDagssalg.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnOversigtDagssalg.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnOversigtDagssalg.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnOversigtDagssalg.setEffect(null));
        this.add(btnOversigtDagssalg, 0, 0);

        //Alt vedrørende knappen "Oversigt over dagssalg".
        this.btnKlippekortStatus.setPrefSize(400, 200);
        this.btnKlippekortStatus.setStyle("-fx-font-size:36;" +
                "-fx-font-weight: bold");
        this.btnKlippekortStatus.setTextAlignment(TextAlignment.CENTER);
        this.btnKlippekortStatus.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnKlippekortStatus.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnKlippekortStatus.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnKlippekortStatus.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnKlippekortStatus.setEffect(null));

        this.add(btnKlippekortStatus, 1, 0);

        //Alt vedrørende knappen "Oversigt over dagssalg".
        this.btnSeAfleveringsprodukter.setPrefSize(400, 200);
        this.btnSeAfleveringsprodukter.setStyle("-fx-font-size:36;" +
                "-fx-font-weight: bold");
        this.btnSeAfleveringsprodukter.setTextAlignment(TextAlignment.CENTER);
        this.btnSeAfleveringsprodukter.setWrapText(true);
//Adding the shadow when the mouse cursor is on
        this.btnSeAfleveringsprodukter.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnSeAfleveringsprodukter.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnSeAfleveringsprodukter.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnSeAfleveringsprodukter.setEffect(null));

        this.add(btnSeAfleveringsprodukter, 2, 0);


        this.setAlignment(Pos.TOP_CENTER);

    }

    public Button getBtnOversigtDagssalg() {
        return btnOversigtDagssalg;
    }

    public Button getBtnKlippekortStatus() {
        return btnKlippekortStatus;
    }

    public Button getBtnSeAfleveringsprodukter() {
        return btnSeAfleveringsprodukter;
    }
}
