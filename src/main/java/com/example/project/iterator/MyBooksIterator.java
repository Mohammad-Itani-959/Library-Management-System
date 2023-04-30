package com.example.project.iterator;

import com.example.project.Book;
import com.example.project.BookDetailController;
import com.example.project.BorrowedbooksController;
import com.example.project.Database;
import com.example.project.proxyUser.ProxyUser;
import com.example.project.user.Borrower;
import com.example.project.user.User;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyBooksIterator implements Iterator {
    private User user;
    private ResultSet resultSet;
    private ProxyUser proxyUser;
    private FXMLLoader fxmlLoader;
    Database database;
    public MyBooksIterator(ProxyUser proxyUser ){
        this.setProxyUser(proxyUser);
        try {
            this.resultSet = this.getBooks();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void show(GridPane gridPane) throws SQLException {

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
            VBox titleandreturn= new VBox();
            titleandreturn.setAlignment(Pos.CENTER);
            titleandreturn.setSpacing(5);
            Label booktitle = new Label(bookTitle);
            booktitle.setFont(Font.font("Corbel", FontWeight.BOLD ,16));
            booktitle.setTextFill(Color.web("#f0824f"));
            booktitle.setPadding(new Insets(5, 0, 0, 0));
            Button returnBtn = new Button("Return");
            returnBtn.setFont(Font.font("Corbel",16));
            returnBtn.getStyleClass().add("borrow-btn");
            returnBtn.setId("returnBtn");
            returnBtn.setOnMouseClicked(mouseEvent -> {
                Borrower borrower =(Borrower) this.proxyUser.getRealUser();
                try {
                    if(borrower.returnBook(book)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success!");
                        alert.setHeaderText(null);
                        alert.setContentText("Book Returned Successfully!");

                        // Set the font and color of the content text
                        Label label = new Label(alert.getContentText());
                        label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
                        label.setTextFill(Color.GREEN);

                        // Set the content of the alert to the label
                        alert.getDialogPane().setContent(label);
                        alert.show();

                        this.show(gridPane);
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            titleandreturn.getChildren().addAll(booktitle,returnBtn);
            VBox newVbox = new VBox();
            newVbox.getChildren().add(imageView);
            newVbox.getChildren().add(titleandreturn);
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
                bookcontroller.setProxyUser(this.proxyUser);


                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                Screen screen= Screen.getPrimary();
                Rectangle2D bounds= screen.getVisualBounds();
                root.setPrefSize(bounds.getWidth(), bounds.getHeight());
                stage.setWidth(bounds.getWidth());
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
            });

            gridPane.add(newVbox, columnIndex % 5, rowIndex);

            if (columnIndex % 4 == 3) {
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
        this.user = proxyUser.getRealUser();
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
    public ResultSet get_Next() {
        return resultSet;
    }

    @Override
    public ResultSet getBooks() throws SQLException {
        return database.getBorrowedBooks(this.user);
    }
}
