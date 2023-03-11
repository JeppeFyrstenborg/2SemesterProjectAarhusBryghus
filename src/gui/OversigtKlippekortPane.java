package gui;

import application.controller.Controller;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class OversigtKlippekortPane extends GridPane {

    private Stage stage;
    private final TextArea txaOversigt = new TextArea();
    private final TextField txfAntalSolgteKlipPeriode = new TextField();
    private final TextField txfAntalForbrugteKlipPeriode = new TextField();
    private Controller controller = new Controller();
    private final DatePicker datePickerFraDato = new DatePicker();
    private final DatePicker datePickerTilDato = new DatePicker();

    //TODO: testdatoer skal laves om til at blive hentet fra to kalendere
    private LocalDate startDatoTest = LocalDate.of(2022, 4, 1);
    private LocalDate slutDatoTest = LocalDate.of(2022, 4, 10);

    public OversigtKlippekortPane(Stage stage) {
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
        Label lblNavn = new Label("Oversigt over salg betalt med klip for den valgte periode");
        lblNavn.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblNavn, 0, 0);
        this.add(this.txaOversigt, 0, 1, 1, 11);
        this.txaOversigt.setPrefWidth(1000);
        this.txaOversigt.setPrefHeight(500);
        this.txaOversigt.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.txaOversigt.setEditable(false);

//        StringBuilder sb = new StringBuilder();
//        for (String u : controller.getOversigtKlippekortPeriode(startDatoTest, slutDatoTest)) {
//            sb.append(u).append("\n");
//        }
//        txaOversigt.setText(sb.toString());

        // txf samletIndkomst + label
        Label lblAntalSolgteKlipPeriode = new Label("Antal solgte klip i perioden");
        lblAntalSolgteKlipPeriode.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblAntalSolgteKlipPeriode, 1, 1);
        this.add(this.txfAntalSolgteKlipPeriode, 1, 2);
//        txfAntalSolgteKlipPeriode.setText(controller.antalSolgteKlipPeriode(startDatoTest, slutDatoTest) + " klip");
        txfAntalSolgteKlipPeriode.setEditable(false);
        txfAntalSolgteKlipPeriode.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");

        // txf antalForbrugteKlipPeriode + label
        Label lblAntalForbrugteKlipPeriode = new Label("Antal forbrugte klip i perioden");
        lblAntalForbrugteKlipPeriode.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblAntalForbrugteKlipPeriode, 1, 3);
        this.add(this.txfAntalForbrugteKlipPeriode, 1, 4);
//        txfAntalForbrugteKlipPeriode.setText(controller.antalForbrugteKlipPeriode(startDatoTest, slutDatoTest)
//                + " klip");
        txfAntalForbrugteKlipPeriode.setEditable(false);
        txfAntalForbrugteKlipPeriode.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");

        //Datepicker fra info.
        Label lblFraDato = new Label("Vælg startdato:");
        lblFraDato.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblFraDato, 1, 6);
        datePickerFraDato.setStyle("-fx-font-size:16;");

        datePickerFraDato.setDayCellFactory(d ->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(LocalDate.now()));
                    }
                });
        this.add(datePickerFraDato, 1, 7);

        ChangeListener<LocalDate> listenerFraDato = (ov, o, n) -> this.minMaxDateUpdate(datePickerTilDato);
        datePickerFraDato.valueProperty().addListener(listenerFraDato);

        //Datepicker til info.
        Label lblTilDato = new Label("Vælg slutdato:");
        lblTilDato.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(lblTilDato, 1, 8);
        datePickerTilDato.setStyle("-fx-font-size:16;");

        datePickerTilDato.setDayCellFactory(d ->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        setDisable(item.isAfter(LocalDate.now()));
                    }
                });
        this.add(datePickerTilDato, 1, 9);
//        ChangeListener<LocalDate> listenerTilDato = (ov, o, n) -> this.opdaterData();
//        datePickerTilDato.valueProperty().addListener(listenerFraDato);
        Button btn = new Button("Vis");
        btn.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;");
        this.add(btn, 1, 10);
        btn.setOnAction(actionEvent -> this.opdaterData());
        this.stage = stage;
    }

//    public void opdaterControls(){
//        StringBuilder sb = new StringBuilder();
//        for (String u : controller.getOversigtDagssalg()) {
//            sb.append(u).append("\n");
//        }
//        txaOversigt.setText(sb.toString());
//        txfAntalSolgteKlipPeriode.setText(controller.samletIndkomstDagssalg() + "");
//    }

    private void minMaxDateUpdate(DatePicker datePicker) {
        LocalDate maxDate = LocalDate.now();

        LocalDate minDato = this.datePickerFraDato.getValue();
        if (minDato != null) {

            datePicker.setDayCellFactory(d ->
                    new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            setDisable(item.isAfter(maxDate) || item.isBefore(minDato));
                        }
                    });
        }
    }

    private void opdaterData() {
        LocalDate startDato = datePickerFraDato.getValue();
        LocalDate slutDato = datePickerTilDato.getValue();
        if (startDato == null | slutDato == null)
            return;
        StringBuilder sb = new StringBuilder();
        for (String u : controller.getOversigtKlippekortPeriode(startDato, slutDato)) {
            sb.append(u).append("\n");
        }
        txaOversigt.setText(sb.toString());


        txfAntalForbrugteKlipPeriode.setText(controller.antalForbrugteKlipPeriode(startDato, slutDato)
                + " klip");

        txfAntalSolgteKlipPeriode.setText(controller.antalSolgteKlipPeriode(startDato, slutDato) + " klip");

    }


}
