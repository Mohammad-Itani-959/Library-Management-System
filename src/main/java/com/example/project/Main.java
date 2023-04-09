package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {
    Button user;
    Button librarian;
    Button admin;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("books.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Library Management System");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        //database.createSuppliers();
        //database.createBooks();
        launch();

    }
}
