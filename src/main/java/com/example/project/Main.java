package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("entry.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Library Management System");
        stage.setWidth(1350);
        stage.setHeight(810);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

    }

    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        database.createTables();
        database.addData();

        launch();

    }
}
