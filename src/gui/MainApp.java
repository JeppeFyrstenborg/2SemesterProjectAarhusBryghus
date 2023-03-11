package gui;

import application.controller.Controller;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Optional;


public class MainApp extends Application {

    private Controller controller = new Controller();

    @Override
    public void init() {
        controller.initStorage();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Kasseapparatet");

        BorderPane pane = new BorderPane();

        this.initContent(pane);
        this.btnPutOnAction();

        Scene scene = new Scene(pane);
        pane.requestFocus();
        stage.setScene(scene);
//        stage.setMaximized(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        stage.show();
    }

    //---------------------------------------------------Constances---------------------------------------------------
    private Stage stage;
    private final Alert alertConfirmation = new Alert(Alert.AlertType.CONFIRMATION);
    private final StackPane stackPane = new StackPane();
    private final StartsidePane startsidePane = new StartsidePane();
    private OpretProduktPane opretProduktPane;
    private RegistrerTilbageleveringPane registrerTilbageleveringPane;
    private final OpretPrislistePane opretPrislistePane = new OpretPrislistePane();
    private OversigtKlippekortPane oversigtKlippekortPane;
    private RegistrerAndetSalgPane registrerAndetSalgPane;
    private RegistrerSalgFredagsbarPane registrerSalgFredagsbarPane;
    private final SeOversigtMenuPane seOversigtMenuPane = new SeOversigtMenuPane();
    private OversigtAfleveringsprodukterPane oversigtAfleveringsprodukterPane;
    private final SePrislisteMenuPane sePrislisteMenuPane = new SePrislisteMenuPane();
    private int tilbageTaeller = 0;
    private SePrislisterPane sePrislisterPane;
    private RetOgSletPrislistePane retOgSletPrislistePane;
    private OpretKundePane opretKundePane;
    private OpretProduktkategoriPane opretProduktkategoriPane;

    private OversigtDagssalgPane oversigtDagssalgPane;

    //---------------------------------------------------Content------------------------------------------------------
    private void initContent(BorderPane pane) {
        oversigtDagssalgPane = new OversigtDagssalgPane(this.stage);
        oversigtAfleveringsprodukterPane = new OversigtAfleveringsprodukterPane();
        sePrislisterPane = new SePrislisterPane();
        retOgSletPrislistePane = new RetOgSletPrislistePane();
        opretKundePane = new OpretKundePane();
        opretProduktkategoriPane = new OpretProduktkategoriPane();
        stackPane.getChildren().add(oversigtDagssalgPane);


        this.registrerAndetSalgPane = new RegistrerAndetSalgPane(stage);
        registrerSalgFredagsbarPane = new RegistrerSalgFredagsbarPane(stage);
        registrerTilbageleveringPane = new RegistrerTilbageleveringPane(this.stage);
        oversigtKlippekortPane = new OversigtKlippekortPane(this.stage);
        stackPane.getChildren().add(opretProduktkategoriPane);
        stackPane.getChildren().add(opretKundePane);
        stackPane.getChildren().add(retOgSletPrislistePane);
        stackPane.getChildren().add(sePrislisterPane);
        stackPane.getChildren().add(sePrislisteMenuPane);
        stackPane.getChildren().add(oversigtAfleveringsprodukterPane);
        stackPane.getChildren().add(seOversigtMenuPane);
        stackPane.getChildren().add(registrerSalgFredagsbarPane);
        stackPane.getChildren().add(registrerAndetSalgPane);
        stackPane.getChildren().add(registrerTilbageleveringPane);
        opretProduktPane = new OpretProduktPane(this.stage);
        stackPane.getChildren().add(opretProduktPane);
        stackPane.getChildren().add(oversigtKlippekortPane);
        stackPane.getChildren().add(opretPrislistePane);
        stackPane.getChildren().add(startsidePane);

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 5));
        hbox.setStyle("-fx-background-color: #135a1f;");
        hbox.setAlignment(Pos.CENTER);
        Pane spacer = new Pane();


        Image image = new Image("![](../../code/media/logo.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(140);
        this.updateGridImgLogo(imageView, this.startsidePane);
        hbox.getChildren().add(imageView);

        DropShadow shadow = new DropShadow();
//Adding the shadow when the mouse cursor is on
        imageView.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> imageView.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        imageView.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> imageView.setEffect(null));

        hbox.getChildren().add(spacer);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Image image2 = new Image("![](../../code/media/doorWhite.png");
        ImageView imageView2 = new ImageView(image2);
        imageView2.setFitWidth(60);
        imageView2.setFitHeight(80);
//Adding the shadow when the mouse cursor is on
        imageView2.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> imageView2.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        imageView2.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> imageView2.setEffect(null));
        imageView2.setPickOnBounds(true);
        imageView2.setOnMouseClicked(actionEvent -> this.closeProgram(this.stage));
        hbox.getChildren().add(imageView2);

        Image image3 = new Image("![](../../code/media/piltilbagehvid.png");
        ImageView imageView3 = new ImageView(image3);
        imageView3.setFitWidth(60);
        imageView3.setFitHeight(80);
        hbox.getChildren().add(imageView3);

//Adding the shadow when the mouse cursor is on
        imageView3.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> imageView3.setEffect(shadow));
//Removing the shadow when the mouse cursor is off
        imageView3.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> imageView3.setEffect(null));

        this.updateGridImgTilbage(imageView3);

        pane.setTop(hbox);
        pane.setCenter(stackPane);

    }

    //---------------------------------------------------Constances---------------------------------------------------

    public void updateGridImg(ImageView imageView, Pane gridPane) {
        imageView.setPickOnBounds(true);
        imageView.setOnMouseClicked(event -> {
            this.skiftTop(gridPane);
            gridPane.requestFocus();
        });
    }

    public void updateGridImgLogo(ImageView imageView, Pane gridPane) {
        imageView.setPickOnBounds(true);
        imageView.setOnMouseClicked(event -> {
            this.skiftTop(gridPane);
            gridPane.requestFocus();
            this.tilbageTaeller = 0;
        });
    }

    public void updateGridImgTilbage(ImageView imageView) {
        imageView.setPickOnBounds(true);
        imageView.setOnMouseClicked(event -> {
            this.aendreTopTilbage();
//            gridPane.requestFocus();
        });
    }

    public void updateGrid(Button btn, GridPane gridPane) {
        if (gridPane == this.opretProduktPane)
            this.opretProduktPane.setCheck(true);
        btn.setOnAction(event -> {
            this.skiftTop(gridPane);
            gridPane.requestFocus();
        });
    }

    private void gaaTilOversigtDagssalg(Button btn) {
        btn.setOnAction(event -> {
            this.skiftTop(this.oversigtDagssalgPane);
            this.oversigtDagssalgPane.opdaterControls();
            this.oversigtDagssalgPane.requestFocus();
        });
    }

    private void gaaTilTilbageLevering(Button btn) {
        btn.setOnAction(event -> {
            this.skiftTop(this.registrerTilbageleveringPane);
            this.registrerTilbageleveringPane.requestFocus();
        });

    }

    private void gaaTilKlippekortOversigt(Button btn) {
        btn.setOnAction(event -> {
            this.skiftTop(this.oversigtKlippekortPane);
            this.oversigtKlippekortPane.requestFocus();
        });

    }

    public void gaaTilOpretPrislistePane(Button btn, GridPane gridPane) {
        btn.setOnAction(event -> {
            this.skiftTop(gridPane);
            gridPane.requestFocus();
            this.opretPrislistePane.setPrisliste();
            this.opretPrislistePane.updaterInit();
            this.opretPrislistePane.setCheck(true);
        });
    }

    public void gaaTilRegistrerAndetSalg(Button btn, GridPane gridPane) {
        btn.setOnAction(event -> {
            this.skiftTop(gridPane);
            gridPane.requestFocus();
            this.registrerAndetSalgPane.setSalg();
            this.registrerAndetSalgPane.setCheck(true);
        });
    }


    public void gaaTilRegistrerFredagsbarSalg(Button btn, GridPane gridPane) {
        btn.setOnAction(event -> {
            this.skiftTop(gridPane);
            gridPane.requestFocus();
            this.registrerSalgFredagsbarPane.setSalg();
            this.registrerSalgFredagsbarPane.setCheck(true);
        });
    }

    public void gaaTilOversigtAfleveringsprodukter(Button btn) {
        btn.setOnAction(event -> {
            this.skiftTop(this.oversigtAfleveringsprodukterPane);
            oversigtAfleveringsprodukterPane.requestFocus();
            oversigtAfleveringsprodukterPane.opdaterControls();
        });
    }

    public void gaaTilSePrislisteMenu(Button btn) {
        btn.setOnAction(event -> {
            this.skiftTop(this.sePrislisteMenuPane);
            sePrislisteMenuPane.requestFocus();
        });
    }

    private void gaaTilSePrislistr(Button btn) {
        btn.setOnAction(event -> {
            this.skiftTop(this.sePrislisterPane);
            sePrislisterPane.requestFocus();
            sePrislisterPane.setPrislisteListe();
        });
    }

    private void gaaTilRetOgSletPrisliste(Button btn) {
        btn.setOnAction(event -> {
            this.skiftTop(this.retOgSletPrislistePane);
            retOgSletPrislistePane.requestFocus();
            retOgSletPrislistePane.setPrislisteListe();
        });
    }

    private void gaaTilOpretProduktkategori(Button btn) {
        btn.setOnAction(event -> {
            this.skiftTop(opretProduktkategoriPane);
            opretProduktkategoriPane.requestFocus();
            opretProduktkategoriPane.setProguktKategorier();
        });
    }

    private void gaaTilOpretKunde(Button btn) {
        btn.setOnAction(event -> {
            this.skiftTop(this.opretKundePane);
            opretKundePane.requestFocus();
            opretKundePane.setKundeListe();
        });
    }

    public void opretPrislisteBtnActionPaaOpretLPrislistePane(Button btn) {
        btn.setOnAction(actionEvent -> {
            this.opretPrislistePane.opretPrisliste();
            if (!this.opretPrislistePane.isCheck())
                this.aendreTopTilbage();
        });
    }

    public void opretProduktBtnAction(Button btn) {
        btn.setOnAction(actionEvent -> {
            this.opretProduktPane.opretProduktAction();
            if (!this.opretProduktPane.isCheck())
                this.aendreTopTilbage();
        });

    }

    private void registrerFredagsbarSalg(Button btn) {
        btn.setOnAction(actionEvent -> {
            this.registrerSalgFredagsbarPane.registrerSalgBtnAction();
            if (!this.registrerSalgFredagsbarPane.isCheck())
                this.aendreTopTilbage();
        });
    }

    private void registrerAndetSalgBtnAction(Button btn) {
        btn.setOnAction(actionEvent -> {
            this.registrerAndetSalgPane.registrerSalgBtnAction();
            if (!this.registrerAndetSalgPane.isCheck())
                this.aendreTopTilbage();
        });
    }

    private void gaaTilSeOversigter(Button btn) {
        btn.setOnAction(actionEvent -> {
            this.skiftTop(this.seOversigtMenuPane);
        });
    }

    private void gaaTilOpretProdukt(Button btn) {
        btn.setOnAction(actionEvent -> {
            this.skiftTop(this.opretProduktPane);
            opretProduktPane.opdaterInit();
        });
    }

    public void btnPutOnAction() {
        this.gaaTilOpretProdukt(this.startsidePane.getBtnProdukt());
        this.registrerAndetSalgBtnAction(this.registrerAndetSalgPane.getBtnRegistrerSalg());

        this.gaaTilOpretPrislistePane(this.sePrislisteMenuPane.getBtnOpretPrisliste(), this.opretPrislistePane);
        this.opretPrislisteBtnActionPaaOpretLPrislistePane(this.opretPrislistePane.getBtnOpretPrisliste());
        this.gaaTilRegistrerFredagsbarSalg(this.startsidePane.getBtnRegistrerSalgIFredagsbar(),
                this.registrerSalgFredagsbarPane);
        this.gaaTilRegistrerAndetSalg(this.startsidePane.getBtnRegistrerSalg(), this.registrerAndetSalgPane);
        this.opretProduktBtnAction(this.opretProduktPane.getBtnOpretProdukt());
        this.registrerFredagsbarSalg(this.registrerSalgFredagsbarPane.getBtnRegistrerSalg());
        this.gaaTilTilbageLevering(this.startsidePane.getBtnBetalSalg());
        this.gaaTilKlippekortOversigt(this.seOversigtMenuPane.getBtnKlippekortStatus());
        this.gaaTilSeOversigter(startsidePane.getBtnSeOversigter());
        this.gaaTilOversigtAfleveringsprodukter(this.seOversigtMenuPane.getBtnSeAfleveringsprodukter());
        this.gaaTilOversigtDagssalg(this.seOversigtMenuPane.getBtnOversigtDagssalg());
        this.gaaTilSePrislisteMenu(this.startsidePane.getBtnPrisliste());
        this.gaaTilSePrislistr(this.sePrislisteMenuPane.getBtnSePrislister());
        this.gaaTilRetOgSletPrisliste(this.sePrislisteMenuPane.getBtnRetOgSletPrislister());
        this.gaaTilOpretKunde(startsidePane.getBtnOpretKunde());
        gaaTilOpretProduktkategori(startsidePane.getBtnOpretProduktkategori());
    }


    /*
     * Hvis man vil ændre rækkefølgen i stackpane med alle panes tilføjet. Dvs. man kun ændrer visibility.
     */
    private void skiftTop(Pane pane) {
        ObservableList<Node> childs = this.stackPane.getChildren();
        Node topNode = childs.get(childs.size() - 1);

        if (topNode == pane)
            return;


        // This node will be brought to the front
        int indexOf = childs.indexOf(pane);
        Node newTopNode = childs.get(indexOf);

        var fadeInTransition = new FadeTransition(Duration.millis(50));

        fadeInTransition.setOnFinished(actionEvent -> {
            newTopNode.toFront();
            topNode.toBack();
        });
        this.tilbageTaeller++;
//        topNode.toBack();

        fadeInTransition.setNode(pane);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);
        fadeInTransition.play();

    }

    /*
     * Hvis man vil ændre rækkefølgen i stackpane med alle panes tilføjet. Dvs. man kun ændrer visibility.
     */
    private void aendreTopTilbage() {
        ObservableList<Node> childs = this.stackPane.getChildren();
        Node topNode = childs.get(childs.size() - 1);

        if (this.tilbageTaeller == 0)
            return;

        this.opretPrislistePane.tilbageOgSletTempPL();
        this.registrerAndetSalgPane.tilbageOgSletTempSalg();
        this.registrerSalgFredagsbarPane.tilbageOgSletTempSalg();

        // This node will be brought to the front
        Node newTopNode = childs.get(0);

        var fadeInTransition = new FadeTransition(Duration.millis(50));

        fadeInTransition.setOnFinished(actionEvent -> newTopNode.toFront());

        fadeInTransition.setNode(newTopNode);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);
        fadeInTransition.play();
        this.tilbageTaeller--;
    }

    /**
     * Der er kun en pane i stackpane hele tiden. Dvs. når denne metode køres, så fjernes den der var,
     * og den nye tilføjes. Det gøres med fade.
     */
//    private void doSlide(Pane pane) {
//        if (stackPane.getChildren().contains(pane))
//            return;
//
//        var paneToRemove = stackPane.getChildren().get(0);
//
//        stackPane.getChildren().add(pane);
//        var fadeInTransition = new FadeTransition(Duration.millis(50));
//
//        fadeInTransition.setOnFinished(actionEvent -> stackPane.getChildren().remove(paneToRemove));
//
//        fadeInTransition.setNode(pane);
//        fadeInTransition.setFromValue(0);
//        fadeInTransition.setToValue(1);
//        fadeInTransition.play();
//
//    }
    public void closeProgram(Stage stage) {
        alertConfirmation.setTitle("Information");
        alertConfirmation.setHeaderText("Du er ved at lukke!");
        alertConfirmation.setContentText("Er du sikker?");
        if (alertConfirmation.getOwner() != stage)
            alertConfirmation.initOwner(stage);
        Optional<ButtonType> result = alertConfirmation.showAndWait();
        if (result.isPresent()) {
            if (result.get() != ButtonType.OK) {
                return;
            }
        }
        stage.close();

    }

}


