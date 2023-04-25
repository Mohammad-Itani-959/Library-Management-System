package com.example.project;

import com.example.project.proxyUser.ProxyBorrower;
import com.example.project.proxyUser.ProxyUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

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
    @javafx.fxml.FXML
    @FXML
    private Label errorr;

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
        if(!EMAIL_PATTERN.matcher(Email).matches() && !(Password.equals(ConfirmPassword))){
            errorr.setText("the email and password are not valid");
            return;
        } else if (!EMAIL_PATTERN.matcher(Email).matches()) {
            errorr.setText("the email is not valid");
            return;
        }else if(!(Password.equals(ConfirmPassword))){
            errorr.setText("the password is not validd");
            return;
        }

        if(Username.length()>0 && (Password.length()>0 && Email.length()>0)){
            boolean flag = database.borrowerRegister(Username , Email , Password);
            if(flag == true){
                proxyUser = new ProxyBorrower();
                proxyUser.login(Email,Password);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("allbooks.fxml"));
                AnchorPane root = fxmlLoader.load();
                AllBooksController allBooksController = fxmlLoader.getController();
                allBooksController.setProxyUser(proxyUser);
                allBooksController.start();
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

}
