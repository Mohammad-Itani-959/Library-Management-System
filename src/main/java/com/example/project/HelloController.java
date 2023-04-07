package com.example.project;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML

    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void userHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userLogin.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Library Management System");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();

    }
    public void librarianHandler(ActionEvent event){
        System.out.printf("Librarian button is clicked");

    }

    public void adminHandler(ActionEvent event){
        System.out.printf("Admin button is clicked");

    }

}