package com.example.project.iterator;

import com.example.project.Database;
import com.example.project.bookController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.project.proxyUser;

public class GeneralIterator implements Iterator{

    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private proxyUser proxyUser;
    private FXMLLoader fxmlLoader;

    public GeneralIterator(proxyUser proxyUser, GridPane gridPane , FXMLLoader fxmlLoader){
        this.setProxyUser(proxyUser);
        this.fxmlLoader = fxmlLoader;
        try {
            this.showBooks(gridPane, this.getBooks());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void showBooks(GridPane gridPane, ResultSet resultSet) throws SQLException {
        resultSet = this.database.selectAllBooks();
        int columnIndex = 0;
        int rowIndex = 0;

        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(15);
        gridPane.setVgap(10);

        while (resultSet.next()) {
            String bookTitle= resultSet.getString(2);
            String bookDesc = resultSet.getString(3);
            String bookImage = resultSet.getString(5);
            String bookCat = resultSet.getString(7);
            String bookAuth = resultSet.getString(8);

            ImageView imageView = new ImageView();
            Image image = new Image(getClass().getResourceAsStream("/" + bookImage));
            imageView.setImage(image);
            imageView.setFitWidth(207);
            imageView.setFitHeight(300);

            Label label = new Label(bookTitle);
            label.setFont(Font.font("Corbel", 16));
            label.setTextFill(Color.web("#f0824f"));
            VBox newVbox = new VBox();
            newVbox.getChildren().add(imageView);
            newVbox.getChildren().add(label);
            newVbox.setPrefWidth(207);
            newVbox.setPrefHeight(320);
            newVbox.getStyleClass().add("book-vbox");


            imageView.setOnMouseClicked(event-> {
                FXMLLoader fxmlLoader = this.fxmlLoader;
                AnchorPane root ;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                bookController bookcontroller = fxmlLoader.getController();
                bookcontroller.setAuthor(bookAuth);
                bookcontroller.setTitle(bookTitle);
                bookcontroller.setDescription(bookDesc);
                bookcontroller.setCategory(bookCat);
                bookcontroller.setImage(bookImage);
                bookcontroller.setProxyUser(this.proxyUser);

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.show();
            });

            gridPane.add(newVbox, columnIndex % 5, rowIndex);

            if (columnIndex % 5 == 4) {
                rowIndex++;
                columnIndex = 0;
            } else {
                columnIndex++;
            }
        }
    }
    public ResultSet getBooks() throws SQLException{
        return database.selectAllBooks();
    }
    public void setProxyUser(proxyUser proxyUser){this.proxyUser=proxyUser;}
}
