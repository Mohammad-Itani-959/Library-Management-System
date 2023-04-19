package com.example.project.iterator;

import com.example.project.Database;
import com.example.project.BookDetailController;
import com.example.project.ProxyUser;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorIterator implements Iterator {
    private String authorName;
    private  ResultSet resultSet;
    private FXMLLoader fxmlLoader;
    private ProxyUser proxyUser;

    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthorIterator(String authorName){

        this.authorName = authorName;
        try {
            this.resultSet = this.getBooks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void showBooks(GridPane gridPane) throws SQLException {

        int i = 0 ;
        int a = gridPane.getChildren().size();
        while(i<a){
            gridPane.getChildren().remove(0);
            i++;
        }

        int columnIndex = 0;
        int rowIndex = 0;

        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(15);
        gridPane.setVgap(10);

        ResultSet resultSet ;
        while (has_Next()) {
            resultSet = getNext();
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
                AnchorPane root;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BookDetailController bookcontroller = fxmlLoader.getController();
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
    @Override
    public void setFXMLLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }
    @Override
    public void setProxyUser(ProxyUser proxyUser) {
        this.proxyUser = proxyUser;
    }
    public ResultSet getBooks() throws SQLException{
        return database.get_books_by_author(this.authorName);
    }
    public boolean has_Next(){
        try {
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getNext(){
        return resultSet;
    }
}
