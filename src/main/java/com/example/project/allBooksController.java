package com.example.project;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class allBooksController implements Initializable {
    @javafx.fxml.FXML
    @FXML
    private GridPane gridPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Label label = new Label("Hello");
        gridPane.add(label,0,0);
    }
}
