package com.example.project;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class allBooksController implements Initializable {
    @javafx.fxml.FXML
    @FXML
    private GridPane gridPane;
    AnchorPane anchorPane;
    protected  proxyBorrower proxyBorrower;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(this.proxyBorrower);
    }

    public void setUser(proxyBorrower proxyBorrower){
        this.proxyBorrower = proxyBorrower;
    }
}
