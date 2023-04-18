package com.example.project;

import com.example.project.iterator.AuthorIterator;
import com.example.project.iterator.GeneralIterator;
import com.example.project.iterator.Iterator;
import com.example.project.iterator.LengthIterator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class allBooksController implements Initializable {
    @javafx.fxml.FXML
    @FXML
    private GridPane gridPane;

    @javafx.fxml.FXML
    @FXML
    private ScrollPane scrollPane;
    @javafx.fxml.FXML
    @FXML
    private Label email;
    @javafx.fxml.FXML
    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<String>();

    @javafx.fxml.FXML
    @FXML
    private HBox hbox ;

    @javafx.fxml.FXML
    @FXML
    private TextField searchAuthor;

    @javafx.fxml.FXML
    @FXML
    private TextField searchLength;

    private proxyUser proxyUser;
    Database database;

    Iterator iterator;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.getAllbooks(gridPane);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setChoiceBoxElements();
    }

    public void getAllbooks(GridPane gridPane) throws SQLException {
        ResultSet resultSet = this.database.selectAllBooks();
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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
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

    //Handler for filtering that works with the iteration design pattern //

    private void setChoiceBoxElements(){
        String[] elements = {"Fiction" ,"Science Fiction" ,"Romance","Fantasy","Satire"};
        choiceBox.getItems().addAll(elements);
    }
    public void choiceBoxHandler(ActionEvent actionEvent) throws SQLException{
        String selectedItem = choiceBox.getValue();
        TextField textField = new TextField();
        Button search = new Button("Search");

        switch (selectedItem){
            case "Fiction":
                updateBooks(gridPane,database.get_books_by_category("Fiction"));
                return ;

            case "Science Fiction":
                updateBooks(gridPane,database.get_books_by_category("Science Fiction"));
                return ;

            case "Fantasy":
                updateBooks(gridPane,database.get_books_by_category("Fantasy"));
                return ;
            case "Romance":
                updateBooks(gridPane,database.get_books_by_category("Romance"));
                return ;

            case "Satire":
                updateBooks(gridPane,database.get_books_by_category("Satire"));
                return ;

            default:return ;
        }
    }

    public void searchAuthorHandler(ActionEvent actionEvent )throws SQLException{
            String authorName = searchAuthor.getText();
            if(authorName.isEmpty()){
                searchAuthor.setPromptText("Please enter an author name");
                this.getAllbooks(gridPane);
            }
            else{
                try {
                    updateBooks(this.gridPane,database.get_books_by_author(authorName));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
    }
    public void searchLengthHandler(ActionEvent actionEvent )throws SQLException{
        Integer length = Integer.parseInt(searchLength.getText());
        if(length == null){
            searchAuthor.setPromptText("Please enter an author name");
            this.getAllbooks(gridPane);
        }
        else{
            try {
                updateBooks(this.gridPane,database.get_books_by_length(length));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateBooks(GridPane gridPane , ResultSet allbooks) throws  SQLException {

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

        while (allbooks.next()) {
            String bookTitle= allbooks.getString(2);
            String bookDesc = allbooks.getString(3);
            String bookImage = allbooks.getString(5);
            String bookCat = allbooks.getString(7);
            String bookAuth = allbooks.getString(8);


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
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
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

    public void Logout(ActionEvent actionEvent) throws SQLException,IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("entry.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }
    public void setProxyUser(proxyUser proxyUser){
        this.proxyUser = proxyUser;
        this.email.setText(proxyUser.getRealUser().getEmail());
    }
}
