package gui;

import application.controller.Controller;
import application.model.Kunde;
import application.model.Salg;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import storage.Storage;

public class OpretKundePane extends GridPane {

    private final Label lblNyKunde = new Label("Ny kunde:");
    private final Label lblNavn = new Label("Navn");
    private final TextField txtNavn = new TextField();
    private final Label lblAdresse = new Label("Adresse");
    private final TextField txtAdresse = new TextField();
    private final Label lblEmail = new Label("Email");
    private final TextField txtEmail = new TextField();
    private final Label lbltelefonNr = new Label("TelefonNr.");
    private final TextField txtTelefon = new TextField();

    private final Label lblRetKunde = new Label("Ret kunde:");
    private final Label lblNavnRet = new Label("Navn");
    private final TextField txtNavnRet = new TextField();
    private final Label lblAdresseRet = new Label("Adresse");
    private final TextField txtAdresseRet = new TextField();
    private final Label lblEmailRet = new Label("Email");
    private final TextField txtEmailRet = new TextField();
    private final Label lbltelefonNrRet = new Label("TelefonNr.");
    private final TextField txtTelefonRet = new TextField();

    private final Button btnRetKunde = new Button("Ret kunde");
    private final Button btnOpretKunde = new Button("Opret kunde");
    private final Button btnSletKunde = new Button("Slet Kunde");
    private final Label lblListeKunder = new Label("Kunder:");
    private final ListView<Kunde> listViewKunder = new ListView<>();
    private final Label lblSalg = new Label("Salg på kunde:");
    private final ListView<Salg> listViewSalgPaaKunde = new ListView<>();

    private Controller controller = new Controller();

    public OpretKundePane() {
        new InitPaneFactory(this);

        //Alle noder til oprettelse af ny kunde.
        lblNyKunde.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bolder;" +
                "-fx-opacity: 1.0");
        lblNyKunde.setPrefWidth(200);
        this.add(lblNyKunde, 0, 0);

        new StyleNodeFactory(lblNavn);
        lblNavn.setPrefWidth(200);
        this.add(lblNavn, 0, 1);
        new StyleNodeFactory(txtNavn);
        txtNavn.setPrefWidth(200);
        this.add(txtNavn, 0, 2);

        new StyleNodeFactory(lblAdresse);
        lblAdresse.setPrefWidth(200);
        this.add(lblAdresse, 0, 3);
        new StyleNodeFactory(txtAdresse);
        txtAdresse.setPrefWidth(200);
        this.add(txtAdresse, 0, 4);

        new StyleNodeFactory(lblEmail);
        lblEmail.setPrefWidth(200);
        this.add(lblEmail, 0, 5);
        new StyleNodeFactory(txtEmail);
        txtEmail.setPrefWidth(200);
        this.add(txtEmail, 0, 6);

        new StyleNodeFactory(lbltelefonNr);
        lbltelefonNr.setPrefWidth(200);
        this.add(lbltelefonNr, 0, 7);
        new StyleNodeFactory(txtTelefon);
        txtTelefon.setPrefWidth(200);
        this.add(txtTelefon, 0, 8);


        //Alt til listview kunder.
        new StyleNodeFactory(lblListeKunder);
        lblListeKunder.setPrefWidth(250);
        this.add(lblListeKunder, 1, 0);

        new StyleNodeFactory(listViewKunder);
        listViewKunder.setPrefWidth(250);
        this.add(listViewKunder, 1, 1, 2, 11);

        //Alle noder til rettelse af kunde.
        lblRetKunde.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bolder;" +
                "-fx-opacity: 1.0");
        lblRetKunde.setPrefWidth(200);
        this.add(lblRetKunde, 3, 0);

        new StyleNodeFactory(lblNavnRet);
        lblNavnRet.setPrefWidth(200);
        this.add(lblNavnRet, 3, 1);
        new StyleNodeFactory(txtNavnRet);
        txtNavnRet.setPrefWidth(200);
        this.add(txtNavnRet, 3, 2);

        new StyleNodeFactory(lblAdresseRet);
        lblAdresseRet.setPrefWidth(200);
        this.add(lblAdresseRet, 3, 3);
        new StyleNodeFactory(txtAdresseRet);
        txtAdresseRet.setPrefWidth(200);
        this.add(txtAdresseRet, 3, 4);

        new StyleNodeFactory(lblEmailRet);
        lblEmailRet.setPrefWidth(200);
        this.add(lblEmailRet, 3, 5);
        new StyleNodeFactory(txtEmailRet);
        txtEmailRet.setPrefWidth(200);
        this.add(txtEmailRet, 3, 6);

        new StyleNodeFactory(lbltelefonNrRet);
        lbltelefonNrRet.setPrefWidth(200);
        this.add(lbltelefonNrRet, 3, 7);
        new StyleNodeFactory(txtTelefonRet);
        txtTelefonRet.setPrefWidth(200);
        this.add(txtTelefonRet, 3, 8);

        //Alt til listview salg.
        new StyleNodeFactory(lblSalg);
        lblSalg.setPrefWidth(250);
        this.add(lblSalg, 4, 0);

        new StyleNodeFactory(listViewSalgPaaKunde);
        listViewSalgPaaKunde.setPrefWidth(250);
        this.add(listViewSalgPaaKunde, 4, 1, 2, 11);
        ChangeListener<Kunde> listenerKunde = (ov, o, n) -> opdaterRetText();
        listViewKunder.getSelectionModel().selectedItemProperty().addListener(listenerKunde);

        //Knap til opret kunde.
        new StyleNodeFactory(btnOpretKunde);
//        btnOpretKunde.setDisable(true);
        this.add(btnOpretKunde, 0, 9);
        DropShadow shadow = new DropShadow();
        //Adding the shadow when the mouse cursor is on
        this.btnOpretKunde.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnOpretKunde.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnOpretKunde.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnOpretKunde.setEffect(null));
        this.btnOpretKunde.setOnAction(actionEvent -> this.opretKundeAction());

        //Knap til rettelse af kunde.
        new StyleNodeFactory(btnRetKunde);
//        btnRetKunde.setDisable(true);
        this.add(btnRetKunde, 3, 9);
        this.btnRetKunde.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnRetKunde.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnRetKunde.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnRetKunde.setEffect(null));
        this.btnRetKunde.setOnAction(actionEvent -> this.retKundeAction());

        //Knap til sletning af kunde.
        btnSletKunde.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0;" +
                "-fx-background-color: red");
//        btnSletKunde.setDisable(true);
        this.add(btnSletKunde, 1, 12);
        this.btnSletKunde.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> btnSletKunde.setEffect(shadow));
        //Removing the shadow when the mouse cursor is off
        this.btnSletKunde.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> btnSletKunde.setEffect(null));
        this.btnSletKunde.setOnAction(actionEvent -> this.sletKundeAction());

        this.setAlignment(Pos.TOP_CENTER);


    }

    private void sletKundeAction() {
        Kunde kunde = listViewKunder.getSelectionModel().getSelectedItem();
        if (kunde == null)
            return;
        Storage.getStorage().deleteKunde(kunde);
        this.setKundeListe();
        clearAll();
    }

    private void retKundeAction() {
        Kunde kunde = listViewKunder.getSelectionModel().getSelectedItem();
        if (kunde == null)
            return;
        String navn = txtNavnRet.getText().trim();
        if (navn.equalsIgnoreCase("")) {
            txtNavnRet.requestFocus();
            return;
        }
        String adresse = txtAdresseRet.getText().trim();
        if (adresse.equalsIgnoreCase("")) {
            txtAdresseRet.requestFocus();
            return;
        }
        String email = txtEmailRet.getText().trim();
        if (navn.equalsIgnoreCase("")) {
            txtEmailRet.requestFocus();
            return;
        }
        String tlf = txtTelefonRet.getText().trim();
        if (tlf.equalsIgnoreCase("")) {
            txtTelefonRet.requestFocus();
            return;
        }
        kunde.setKundenavn(navn);
        kunde.setAdresse(adresse);
        kunde.setEmail(email);
        kunde.setTelefonNr(tlf);
        clearAll();
        setKundeListe();
    }

    private void opdaterRetText() {
        clearAll();
        Kunde kunde = listViewKunder.getSelectionModel().getSelectedItem();
        if (kunde == null)
            return;
        txtNavnRet.setText(kunde.getKundenavn());
        txtAdresseRet.setText(kunde.getAdresse());
        txtEmailRet.setText(kunde.getEmail());
        txtTelefonRet.setText(kunde.getTelefonNr());
        setSalgListe();
    }

    private void opretKundeAction() {
        String navn = txtNavn.getText().trim();
        if (navn.equalsIgnoreCase("")) {
            txtNavn.requestFocus();
            return;
        }
        String adresse = txtAdresse.getText().trim();
        if (adresse.equalsIgnoreCase("")) {
            txtAdresse.requestFocus();
            return;
        }
        String email = txtEmail.getText().trim();
        if (navn.equalsIgnoreCase("")) {
            txtEmail.requestFocus();
            return;
        }
        String tlf = txtTelefon.getText().trim();
        if (tlf.equalsIgnoreCase("")) {
            txtTelefon.requestFocus();
            return;
        }
        Kunde kunde = controller.opretKunde(navn);
        kunde.setAdresse(adresse);
        kunde.setEmail(email);
        kunde.setTelefonNr(tlf);
        clearAll();
        setKundeListe();
    }

    public void setKundeListe() {
        listViewKunder.getItems().setAll(Storage.getStorage().getAllKunder());
        listViewKunder.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Kunde item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = "Nr: " + item.getKundeNr() + " | " + item.getKundenavn();//get text from item
                    setText(text);
                }
            }
        });
    }

    private void setSalgListe() {
        Kunde kunde = listViewKunder.getSelectionModel().getSelectedItem();
        if (kunde == null)
            return;
        listViewSalgPaaKunde.getItems().setAll(controller.findSalgPaaKunde(kunde));
        listViewSalgPaaKunde.setCellFactory(lv -> new ListCell<>() {
            @Override
            public void updateItem(Salg item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    String text = "Nr: " + item.getSalgsNr() + " | " + item.getPrisliste().getPrislistenavn() + " | " +
                            "Betalt: " + item.isBetalt();//get text from item
                    setText(text);
                }
            }
        });
    }

    private void clearAll() {
        txtNavn.clear();
        txtAdresse.clear();
        txtEmail.clear();
        txtTelefon.clear();
        txtNavnRet.clear();
        txtAdresseRet.clear();
        txtEmailRet.clear();
        txtTelefonRet.clear();
        listViewSalgPaaKunde.getItems().clear();

    }
}
