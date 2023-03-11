package gui;

import application.controller.Controller;
import application.model.Salg;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegistrerTilbageleveringPane extends GridPane {

    private Stage stage;
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final RadioButton rdbIgangvaerendeSalg = new RadioButton("Igangværende salg");
    private final RadioButton rdbAfsluttedeSalg = new RadioButton("Afsluttede salg");
    private final ListView<Salg> listViewSalg = new ListView<>();
    private final TextField txtRegDato = new TextField();
    private final TextField txtSlutDato = new TextField();
    private final TextField txtKunde = new TextField();
    private final TextField txtSamletPris = new TextField();
    private final TextField txtSamletPant = new TextField();
    private final TextField txtPrisUdenPant = new TextField();
    private final TextField txtUdlejet = new TextField();
    private final Button btnSeOgBetal = new Button("Se og betal salg");

    public RegistrerTilbageleveringPane(Stage stage) {
        this.setPadding(new Insets(20));
        this.setHgap(70);
        this.setVgap(10);
        this.setGridLinesVisible(false);
//        this.setPrefSize(800, 500);
        this.setStyle("-fx-background-color: #fff");

        this.stage = stage;

        this.setAlignment(Pos.CENTER);

        this.rdbAfsluttedeSalg.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.rdbAfsluttedeSalg.setToggleGroup(toggleGroup);
        this.add(rdbAfsluttedeSalg, 1, 0);
        this.rdbIgangvaerendeSalg.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.rdbIgangvaerendeSalg.setToggleGroup(toggleGroup);
        this.add(rdbIgangvaerendeSalg, 0, 0);

        this.listViewSalg.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(this.listViewSalg, 0, 1, 2, 13);
        this.listViewSalg.setPrefSize(500, 500);

        Label lblRegDato = new Label("Reg. dato:");
        lblRegDato.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblRegDato, 2, 3);
        this.txtRegDato.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.add(txtRegDato, 3, 3);
        this.txtRegDato.setDisable(true);

        Label lblSlutDato = new Label("Slut dato:");
        lblSlutDato.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblSlutDato, 2, 4);
        this.txtSlutDato.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.add(txtSlutDato, 3, 4);
        this.txtSlutDato.setDisable(true);

        Label lblKunde = new Label("Kunde:");
        lblKunde.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblKunde, 2, 5);
        this.txtKunde.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.add(txtKunde, 3, 5);
        this.txtKunde.setDisable(true);

        Label lblSamletPris = new Label("Samlet pris:");
        lblSamletPris.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblSamletPris, 2, 6);
        this.txtSamletPris.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.add(txtSamletPris, 3, 6);
        this.txtSamletPris.setDisable(true);

        Label lblSamletPant = new Label("Samlet pant:");
        lblSamletPant.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblSamletPant, 2, 7);
        this.txtSamletPant.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.add(txtSamletPant, 3, 7);
        this.txtSamletPant.setDisable(true);

        Label lblPrisUdenPant = new Label("Pris u. pant:");
        lblPrisUdenPant.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblPrisUdenPant, 2, 8);
        this.txtPrisUdenPant.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
        this.add(txtPrisUdenPant, 3, 8);
        this.txtPrisUdenPant.setDisable(true);

        this.btnSeOgBetal.setDisable(true);
        this.btnSeOgBetal.setStyle("-fx-text-fill: white;" +
                "-fx-background-color: #135a1f;" +
                "-fx-font-weight: bolder;" +
                "-fx-font-size: 18;");
        this.btnSeOgBetal.wrapTextProperty();
        this.add(this.btnSeOgBetal, 2, 10);
        DropShadow shadow = new DropShadow();
//Adding the shadow when the mouse cursor is on
        this.btnSeOgBetal.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnSeOgBetal.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        this.btnSeOgBetal.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnSeOgBetal.setEffect(null));
        this.btnSeOgBetal.setOnAction(actionEvent -> this.seOgRetSalg());

        this.opdaterControls();


        ChangeListener<Toggle> listenerTogglegroup = (ov, o, n) -> this.opdaterListe();
        this.toggleGroup.selectedToggleProperty().addListener(listenerTogglegroup);

        ChangeListener<Salg> listenerSalg = (ov, o, n) -> this.valgtSalgAendret();
        this.listViewSalg.getSelectionModel().selectedItemProperty().addListener(listenerSalg);


    }

    private void valgtSalgAendret() {
        this.opdaterControls();
    }

    private void opdaterControls() {
        Controller controller = new Controller();

        this.clearAll();

        Salg salg = this.listViewSalg.getSelectionModel().getSelectedItem();
        if (salg == null)
            return;

        if (salg.getSalgAfsluttetDato() == null)
            this.btnSeOgBetal.setDisable(false);

        this.txtRegDato.setText(salg.getSalgPaabegyndtDato().toString());

        if (salg.getKunde() != null)
            this.txtKunde.setText(salg.getKunde().toString());
        if (salg.getSalgAfsluttetDato() != null)
            this.txtSlutDato.setText(salg.getSalgAfsluttetDato().toString());

        if (!salg.harPantTilbageleveringSalgslinje())
            this.txtSamletPris.setText("" + salg.samletPrisMedPant());
        else {
            this.txtSamletPris.setText("" + salg.samletPrisMedPant());
            this.txtSamletPant.setText("" + salg.samletPrisPant());
            this.txtPrisUdenPant.setText("" + salg.samletPrisUdenPant());
        }
    }

    private void opdaterListe() {
        Controller controller = new Controller();
        if (this.rdbAfsluttedeSalg.isSelected()) {
            this.listViewSalg.getItems().setAll(controller.getAfsluttedeSalg());
            this.btnSeOgBetal.setDisable(true);
        }
        if (this.rdbIgangvaerendeSalg.isSelected())
            this.listViewSalg.getItems().setAll(controller.getIgangvaerendeSalg());

        this.setSalg();
        this.clearAll();
    }

    private void clearAll() {
        this.txtRegDato.clear();
        this.txtSlutDato.clear();
        this.txtKunde.clear();
        this.txtSamletPris.clear();
        this.txtSamletPant.clear();
        this.txtPrisUdenPant.clear();
    }

    private void setSalg() {
        this.listViewSalg.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Salg item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text;
                    if (item.isBetalt())
                        text = "Nr. " + item.getSalgsNr() + " | " + item.getPrisliste().getPrislistenavn() +
                                " | " + "Afsluttet!"; // get text from item
                    else
                        text = "Nr. " + item.getSalgsNr() + " | " + item.getPrisliste().getPrislistenavn() +
                                " | " + "Ikke afsluttet!";
                    setText(text);
                }
            }
        });
    }

    private void seOgRetSalg() {
        Salg salg = this.listViewSalg.getSelectionModel().getSelectedItem();
        if (salg == null)
            return;
        BetalSalgVindue betalSalgVindue = new BetalSalgVindue("Betal salg", salg);
        betalSalgVindue.initOwner(stage);
        betalSalgVindue.showAndWait();
        this.opdaterControls();
        this.opdaterListe();

    }


}
