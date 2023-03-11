package gui;

import application.controller.Controller;
import application.model.Kunde;
import application.model.Salg;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import storage.Storage;

public class TilfoejKundeTilSalgVindue extends Stage {

    public TilfoejKundeTilSalgVindue(String title, Salg salg) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);

        this.salg = salg;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private final Salg salg;
    private final Button btnTilfoejKunde = new Button("Tilføj kunde");
    private final Button btnAfbryd = new Button("Afbryd");
    private final Label lblKunder = new Label("Kunder");
    private final ComboBox<Kunde> comboBoxKunder = new ComboBox<>();
    private final Storage storage = Storage.getStorage();
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final RadioButton rdbVaelgKunde = new RadioButton("Vælg kunde");
    private final RadioButton rdbOpretNyKunde = new RadioButton("Opret kunde");

    private final Label lblNavn = new Label("Navn");
    private final TextField txtNavn = new TextField();
    private final Label lblAdresse = new Label("Adresse");
    private final TextField txtAdresse = new TextField();
    private final Label lblEmail = new Label("Email");
    private final TextField txtEmail = new TextField();
    private final Label lbltelefonNr = new Label("TelefonNr.");
    private final TextField txtTelefon = new TextField();

    private final Controller controller = new Controller();

    public void initContent(GridPane pane) {
        //Afstand fra kant og ind.
        pane.setPadding(new Insets(10));
        //Afstand mellem noder horisontalt.
        pane.setHgap(10);
        //Afstand mellem noder vertikalt.
        pane.setVgap(10);
        //Synlighed for linjerne i gridpane.
        pane.setGridLinesVisible(false);
        //Størrelsen på vinduet.
//        pane.setPrefWidth(300);
//        pane.setPrefHeight(350);
        //Baggrundsfarve
        pane.setStyle("-fx-background-color: #135a1f;");


        //Alt vedrørende radiobuttons og toggleGroup.
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        rdbVaelgKunde.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        rdbVaelgKunde.setPrefWidth(100);
        rdbVaelgKunde.setToggleGroup(this.toggleGroup);
        rdbOpretNyKunde.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        rdbOpretNyKunde.setPrefWidth(100);
        rdbOpretNyKunde.setToggleGroup(this.toggleGroup);
        hBox.getChildren().addAll(this.rdbVaelgKunde, this.rdbOpretNyKunde);
        pane.add(hBox, 0, 0);

        this.toggleGroup.selectToggle(rdbVaelgKunde);
        ChangeListener<Toggle> listenerTogglegroup = (ov, o, n) -> this.selectedRdbChanged();
        this.toggleGroup.selectedToggleProperty().addListener(listenerTogglegroup);


        //Alt vedrørende valg af kunder fra combobox.
        lblKunder.prefWidth(200);
        lblKunder.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lblKunder, 0, 1);
        this.comboBoxKunder.getItems().setAll(storage.getAllKunder());
        this.comboBoxKunder.setPrefWidth(240);
        this.comboBoxKunder.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold");
        pane.add(this.comboBoxKunder, 0, 2);

        //Alt vedrørende oprettelse af ny kunde.

        lblNavn.prefWidth(200);
        lblNavn.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lblNavn, 0, 1);
        lblNavn.setVisible(false);
        txtNavn.setPrefWidth(200);
        txtNavn.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold");
        pane.add(txtNavn, 0, 2);
        txtNavn.setVisible(false);

        lblAdresse.prefWidth(200);
        lblAdresse.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lblAdresse, 0, 3);
        lblAdresse.setVisible(false);
        txtAdresse.setPrefWidth(200);
        txtAdresse.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold");
        pane.add(txtAdresse, 0, 4);
        txtAdresse.setVisible(false);

        lblEmail.prefWidth(200);
        lblEmail.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lblEmail, 0, 5);
        lblEmail.setVisible(false);
        txtEmail.setPrefWidth(200);
        txtEmail.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold");
        pane.add(txtEmail, 0, 6);
        txtEmail.setVisible(false);

        lbltelefonNr.prefWidth(200);
        lbltelefonNr.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold;" +
                "-fx-text-fill: white;");
        pane.add(lbltelefonNr, 0, 7);
        lbltelefonNr.setVisible(false);
        txtTelefon.setPrefWidth(200);
        txtTelefon.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold");
        pane.add(txtTelefon, 0, 8);
        txtTelefon.setVisible(false);


        this.btnTilfoejKunde.setPrefWidth(110);
        this.btnTilfoejKunde.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold");
        this.btnAfbryd.setPrefWidth(110);
        this.btnAfbryd.setStyle("-fx-font-size:14;" +
                "-fx-font-weight: bold");
        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(20);
        hBox2.getChildren().addAll(this.btnTilfoejKunde, this.btnAfbryd);

        this.btnAfbryd.setOnAction(actionEvent -> this.close());
        this.btnTilfoejKunde.setOnAction(actionEvent -> this.tilfoejKundeBtnAction());

        pane.add(hBox2, 0, 10);

    }

    public void selectedRdbChanged() {
        opdaterControls();
    }

    public void opdaterControls() {
        RadioButton rdbTemp = (RadioButton) this.toggleGroup.getSelectedToggle();

        if (rdbTemp == null)
            return;

        if (rdbTemp == rdbVaelgKunde) {
            this.comboBoxKunder.getSelectionModel().clearSelection();
            this.comboBoxKunder.setVisible(true);
            lblKunder.setVisible(true);
            lblNavn.setVisible(false);
            lblAdresse.setVisible(false);
            lblEmail.setVisible(false);
            lbltelefonNr.setVisible(false);
            txtNavn.clear();
            txtNavn.setVisible(false);
            txtAdresse.clear();
            txtAdresse.setVisible(false);
            txtEmail.clear();
            txtEmail.setVisible(false);
            txtTelefon.clear();
            txtTelefon.setVisible(false);
        }
        if (rdbTemp == rdbOpretNyKunde) {
            this.comboBoxKunder.getSelectionModel().clearSelection();
            this.comboBoxKunder.setVisible(false);
            lblKunder.setVisible(false);
            lblNavn.setVisible(true);
            lblAdresse.setVisible(true);
            lblEmail.setVisible(true);
            lbltelefonNr.setVisible(true);
            txtNavn.clear();
            txtNavn.setVisible(true);
            txtAdresse.clear();
            txtAdresse.setVisible(true);
            txtEmail.clear();
            txtEmail.setVisible(true);
            txtTelefon.clear();
            txtTelefon.setVisible(true);
        }

    }


    public void tilfoejKundeBtnAction() {
        if (toggleGroup.getSelectedToggle() == rdbVaelgKunde) {
            Kunde kunde = this.comboBoxKunder.getValue();
            if (kunde == null)
                return;

            this.salg.setKunde(kunde);
        }
        if (toggleGroup.getSelectedToggle() == rdbOpretNyKunde) {
            if (txtNavn.getText().isEmpty()) {
                txtNavn.requestFocus();
                return;
            }
            if (txtAdresse.getText().isEmpty()) {
                txtAdresse.requestFocus();
                return;
            }
            if (txtEmail.getText().isEmpty()) {
                txtEmail.requestFocus();
                return;
            }
            if (txtTelefon.getText().isEmpty()) {
                txtTelefon.requestFocus();
                return;
            }

            Kunde kundeTemp = controller.opretKunde(txtNavn.getText().trim());
            kundeTemp.setAdresse(txtAdresse.getText().trim());
            kundeTemp.setEmail(txtEmail.getText().trim());
            kundeTemp.setTelefonNr(txtTelefon.getText().trim());
            this.salg.setKunde(kundeTemp);
        }
        this.close();
    }
}
