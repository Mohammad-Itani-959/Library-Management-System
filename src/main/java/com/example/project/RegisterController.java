package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {
    @javafx.fxml.FXML
    @FXML
    private TextField username;

    @javafx.fxml.FXML
    @FXML
    private TextField email;

    @javafx.fxml.FXML
    @FXML
    private TextField password;

    @javafx.fxml.FXML
    @FXML
    private TextField confirmPassword;

    private ProxyUser proxyUser;
    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerHandler(ActionEvent actionEvent)throws IOException, SQLException{
        String Username = username.getText();
        String Password =password.getText();
        String ConfirmPassword = confirmPassword.getText();
        String Email = email.getText();

        if(Username.length()>0 && (Password.length()>0 && Password.equals(ConfirmPassword)) && Email.length()>0){
            boolean flag = database.borrowerRegister(Username , Email , Password);
            if(flag == true){
                proxyUser = new ProxyUser(Email,Password,"borrower");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("allbooks.fxml"));
                AnchorPane root = fxmlLoader.load();
                AllBooksController allBooksController = fxmlLoader.getController();
                allBooksController.setProxyUser(proxyUser);

                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

}
