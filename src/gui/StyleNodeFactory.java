package gui;

import javafx.scene.Node;

public class StyleNodeFactory {

    public StyleNodeFactory(Node node) {
        node.setStyle("-fx-font-size:18;" +
                "-fx-font-weight: bold;" +
                "-fx-opacity: 1.0");
    }
}
