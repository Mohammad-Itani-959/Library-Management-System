package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    @FXML
    private Label welcomeText;

    @FXML

    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void userHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("borrowerLogin.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage1 = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage1.setTitle("Borrower Login");
        stage1.setWidth(800);
        stage1.setHeight(600);

        stage1.setScene(scene);
        stage1.show();

    }
    public void librarianHandler(ActionEvent event){
        System.out.printf("Librarian button is clicked");

    }

    public void adminHandler(ActionEvent event){
        System.out.printf("Admin button is clicked");

    }

}