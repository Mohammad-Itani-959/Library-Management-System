package com.example.project;

import com.example.project.iterator.Iterator;
import com.example.project.iterator.LibrarianIterator;
import com.example.project.proxyUser.ProxyUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AdminController {
    @FXML
    GridPane gridpane;
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

    public void start() throws SQLException {
        try {
            this.getAllLibrarian(this.gridpane);
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
        iterator = new LibrarianIterator();
        iterator.setProxyUser(this.proxyUser);
        iterator.showBooks(gridPane);
        iterator.setFXMLLoader(fxmlLoader);

    }
    public void Logout(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("entry.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }
}

