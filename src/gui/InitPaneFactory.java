package gui;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class InitPaneFactory {


    public InitPaneFactory(GridPane pane) {
        //Afstand fra kant og ind.
        pane.setPadding(new Insets(20));
        //Afstand mellem noder horisontalt.
        pane.setHgap(70);
        //Afstand mellem noder vertikalt.
        pane.setVgap(10);
        //Synlighed for linjerne i gridpane.
        pane.setGridLinesVisible(false);
        //Baggrundsfarve
        pane.setStyle("-fx-background-color: #fff");

    }
}
