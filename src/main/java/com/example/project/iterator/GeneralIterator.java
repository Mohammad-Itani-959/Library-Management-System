package com.example.project.iterator;

import com.example.project.Book;
import com.example.project.Database;
import com.example.project.BookDetailController;
import com.example.project.proxyUser.ProxyUser;
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

public class GeneralIterator implements Iterator{
    Database database;
    private ProxyUser proxyUser;
    FXMLLoader fxmlLoader;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ResultSet resultSet;
    public GeneralIterator()throws SQLException{
        this.resultSet = this.getBooks();
    }
    @Override
    public void show(GridPane gridPane) throws SQLException {
        int columnIndex = 0;
        int rowIndex = 0;

        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(15);
        gridPane.setVgap(10);

        ResultSet resultSet ;
        while (has_Next()) {
            resultSet = get_Next();
            String bookTitle= resultSet.getString("title");
            String bookDesc = resultSet.getString("description");
            String bookImage = resultSet.getString("image");
            String bookCat = resultSet.getString("category");
            String bookAuth = resultSet.getString("authorName");

            Book book = new Book(
                    bookTitle,
                    bookAuth,
                    bookDesc,
                    bookCat,
                    resultSet.getString("id"),
                    bookImage,
                    resultSet.getString("bookLength"),
                    resultSet.getString("librarianId")
            );


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
                bookcontroller.setBook(book);
                bookcontroller.putBookDetails();
                bookcontroller.setProxyUser(getProxyUser());

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.show();
            });

            gridPane.add(newVbox, columnIndex % 4, rowIndex);

            if (columnIndex % 4 == 3) {
                rowIndex++;
                columnIndex = 0;
            } else {
                columnIndex++;
            }
        }
    }
    @Override
    public ResultSet getBooks() throws SQLException{
        return database.selectAllBooks();
    }
    @Override
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
    @Override
    public ResultSet get_Next(){
        return resultSet;
    }
    @Override
    public void setFXMLLoader(FXMLLoader fxmlLoader){this.fxmlLoader =fxmlLoader;}
    @Override
    public void setProxyUser(ProxyUser proxyUser){this.proxyUser = proxyUser;}
    public ProxyUser getProxyUser(){return this.proxyUser ;}
 }
