package com.example.project;

import com.example.project.iterator.Iterator;
import com.example.project.iterator.LibrarianIterator;
import com.example.project.proxyUser.ProxyUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    GridPane gridPane;
    @FXML
    TextField username;
    @FXML
    TextField email;
    @FXML
    TextField password;
    @FXML
    TextField confirmpassword;
    Iterator iterator;
    ProxyUser proxyUser;
    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setProxyUser(ProxyUser proxyUser) {
        this.proxyUser = proxyUser;
    }

    public void start() throws SQLException {
        try {
            this.getAllLibrarian(this.gridPane);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void add(ActionEvent actionEvent) throws IOException, SQLException {
        String Username = username.getText();
        String Password = password.getText();
        String ConfirmPassword = confirmpassword.getText();
        String Email = email.getText();

        if (Username.length() > 0 && (Password.length() > 0 && Password.equals(ConfirmPassword)) && Email.length() > 0 && Email.contains("@")) {
            boolean flag = database.librarianRegister(Username, Password, Email);

        }
    }


    private void getAllLibrarian(GridPane gridPane) throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LibrarianDetails.fxml"));
        iterator = new LibrarianIterator(this.proxyUser);
        iterator.show(gridPane);
        iterator.setFXMLLoader(fxmlLoader);

    }
    public ProxyUser getProxyUser(){return this.proxyUser ;}
    public void Logout(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("entry.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1350);
        stage.setHeight(810);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //gridPane.getChildren().add(new Text("HELLOOOO"));
        try {
            this.getAllLibrarian(this.gridPane);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

