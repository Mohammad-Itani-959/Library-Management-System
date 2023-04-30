package com.example.project;

import com.example.project.proxyUser.ProxyAdmin;
import com.example.project.proxyUser.ProxyBorrower;
import com.example.project.proxyUser.ProxyLibrarian;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class EntryController {
    public void userHandler(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        AnchorPane root = loader.load();

        LoginController loginController = loader.getController();
        loginController.proxyUser = new ProxyBorrower();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        Screen screen= Screen.getPrimary();
        Rectangle2D bounds= screen.getVisualBounds();
        root.setPrefSize(bounds.getWidth(), bounds.getHeight());
        stage.setWidth(bounds.getWidth());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }
    public void librarianHandler(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            AnchorPane root = loader.load();

            LoginController loginController = loader.getController();
            loginController.proxyUser = new ProxyLibrarian();
            loginController.removeRegister();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            Screen screen= Screen.getPrimary();
            Rectangle2D bounds= screen.getVisualBounds();
            root.setPrefSize(bounds.getWidth(), bounds.getHeight());
            stage.setWidth(bounds.getWidth());
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void adminHandler(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
        AnchorPane root = loader.load();

        LoginController loginController = loader.getController();
        loginController.proxyUser = new ProxyAdmin();
        loginController.removeRegister();

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        Screen screen= Screen.getPrimary();
        Rectangle2D bounds= screen.getVisualBounds();
        root.setPrefSize(bounds.getWidth(), bounds.getHeight());
        stage.setWidth(bounds.getWidth());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }

}