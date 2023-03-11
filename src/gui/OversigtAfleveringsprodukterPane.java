package gui;

import application.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class OversigtAfleveringsprodukterPane extends GridPane {

    private Stage stage;
    private final TextArea txaOversigt = new TextArea();
    private Controller controller = new Controller();

    public OversigtAfleveringsprodukterPane() {

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

        //Alt vedrørende textarea for oversigt.
        Label lblNavn = new Label("Oversigt over ikke afleverede anlæg");
        lblNavn.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblNavn, 0, 0);
        this.add(this.txaOversigt, 0, 1, 1, 9);
        this.txaOversigt.setPrefWidth(1000);
        this.txaOversigt.setPrefHeight(600);
        this.txaOversigt.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.txaOversigt.setEditable(false);

        StringBuilder sb = new StringBuilder();
        for (String u : controller.getOversigtIkkeAfleveredeProdukter()) {
            sb.append(u).append("\n");
        }
        txaOversigt.setText(sb.toString());
    }

    public void opdaterControls() {
        StringBuilder sb = new StringBuilder();
        for (String u : controller.getOversigtIkkeAfleveredeProdukter()) {
            sb.append(u).append("\n");
        }
        txaOversigt.setText(sb.toString());
    }
}
