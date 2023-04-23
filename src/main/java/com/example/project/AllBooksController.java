package com.example.project;

import com.example.project.iterator.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllBooksController {
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

    public ProxyUser proxyUser;
    Database database;

    Iterator iterator;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void start() throws SQLException {
        try {
            this.getAllbooks(gridPane);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setChoiceBoxElements();
    }

    public void getAllbooks(GridPane gridPane) throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
        iterator = new GeneralIterator();
        iterator.setProxyUser(this.proxyUser);
        iterator.showBooks(gridPane);
        iterator.setFXMLLoader(fxmlLoader);
    }
    //Handler for filtering that works with the iteration design pattern //
    private void setChoiceBoxElements() throws SQLException{
        ResultSet rs = database.getCategories();
        ObservableList<String> elements = FXCollections.observableArrayList();
        while (rs.next()) {
            String category = rs.getString("category");
            elements.add(category);
        }
        choiceBox.setItems(elements);
    }
    public void searchCategoryHandler(ActionEvent actionEvent) throws SQLException{
        String selectedItem = choiceBox.getValue();
        iterator = new CategoryIterator(selectedItem);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
        iterator.setProxyUser(this.proxyUser);
        iterator.setFXMLLoader(fxmlLoader);
        iterator.showBooks(gridPane);

    }
    public void searchAuthorHandler(ActionEvent actionEvent )throws SQLException{
            String authorName = searchAuthor.getText();
            if(authorName.isEmpty()){
                searchAuthor.setPromptText("Please enter an author name");
                this.getAllbooks(gridPane);
            }
            else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
                iterator = new AuthorIterator(authorName);
                iterator.setProxyUser(this.proxyUser);
                iterator.showBooks(gridPane);
                iterator.setFXMLLoader(fxmlLoader);
            }
    }
    public void searchLengthHandler(ActionEvent actionEvent )throws SQLException{
        if(searchLength.getText().equals("")){
            searchAuthor.setPromptText("Please enter an author name");
            this.getAllbooks(gridPane);

        }
        else{
            Integer length = Integer.parseInt(searchLength.getText());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
            iterator = new LengthIterator(length);
            iterator.setProxyUser(this.proxyUser);
            iterator.showBooks(gridPane);
            iterator.setFXMLLoader(fxmlLoader);
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

                BookDetailController bookcontroller = fxmlLoader.getController();
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
    public void setProxyUser(ProxyUser proxyUser){
        this.proxyUser = proxyUser;
        this.email.setText(proxyUser.getRealUser().getEmail());
    }
}
