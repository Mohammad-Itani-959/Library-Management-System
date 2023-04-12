package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button userbtn;

    @FXML
    private Button librarianbtn;

    @FXML
    private Button adminbtn;


    public void userHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("borrowerLogin.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage1 = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage1.setTitle("Borrower Login");
        stage1.setWidth(1350);
        stage1.setHeight(810);

        stage1.setScene(scene);
        stage1.show();

    }
    public void librarianHandler(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminANDlibrarianLogin.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage1.setTitle("librarian Login");
            stage1.setWidth(1350);
            stage1.setHeight(810);

            stage1.setScene(scene);
            stage1.show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void adminHandler(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminANDlibrarianLogin.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        Stage stage1 = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage1.setTitle("admin Login");
        stage1.setWidth(1350);
        stage1.setHeight(810);

        stage1.setScene(scene);
        stage1.show();

    }

}