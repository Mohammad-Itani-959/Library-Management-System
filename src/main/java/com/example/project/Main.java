package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Entry.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

        Screen screen= Screen.getPrimary();
        Rectangle2D bounds= screen.getVisualBounds();
        root.setPrefSize(bounds.getWidth(), bounds.getHeight());
        stage.setWidth(bounds.getWidth());
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }
    public static void main(String[] args) throws SQLException {
        Database database = new Database();
        database.createTables();
        database.addData();

        launch();

    }
}
