package gui;

import application.controller.Controller;
import application.model.Prisliste;
import application.model.PrislisteProdukt;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class OversigtDagssalgPane extends GridPane {
    private Stage stage;
    private final TextArea txaOversigt = new TextArea();
    private final TextField txfSamletIndkomst = new TextField();
    private Controller controller = new Controller();

    public OversigtDagssalgPane(Stage stage) {
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
        this.stage = stage;

        //Alt vedrørende textarea for oversigt.
        Label lblNavn = new Label("Oversigt over dagssalg for i dag (" + LocalDate.now() + ")");
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
        for (String u : controller.getOversigtDagssalg()) {
            sb.append(u).append("\n");
        }
        txaOversigt.setText(sb.toString());

        // txf samletIndkomst + label
        Label lblSamletIndkomst = new Label("Samlet indkomst for i dag (uden pant):");

        this.add(lblSamletIndkomst, 1, 1);
        this.add(this.txfSamletIndkomst, 1, 2);
        lblSamletIndkomst.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.txfSamletIndkomst.setPrefWidth(200);
        txfSamletIndkomst.setText(controller.samletIndkomstDagssalg() + " kr.");
        txfSamletIndkomst.setEditable(false);
        txfSamletIndkomst.setDisable(true);
        txfSamletIndkomst.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");

        this.stage = stage;
    }

    public void opdaterControls() {
        StringBuilder sb = new StringBuilder();
        for (String u : controller.getOversigtDagssalg()) {
            sb.append(u).append("\n");
        }
        txaOversigt.setText(sb.toString());
        txfSamletIndkomst.setText(controller.samletIndkomstDagssalg() + "");
    }
}
