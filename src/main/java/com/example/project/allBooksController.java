package com.example.project;

import com.example.project.iterator.AuthorIterator;
import com.example.project.iterator.LengthIterator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    Database database;

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
        this.email.setText("No username");
    }

    public void setUser(String email) {
        if(email !=null){
            this.email.setText(email);
        }
        else{
            this.email.setText("No username");
        }

    }

    public void getAllbooks(GridPane gridPane) throws SQLException {
        ResultSet allbooks = this.database.selectAllBooks();
        int columnIndex = 0;
        int rowIndex = 0;

        while (allbooks.next()) {
            String bookTitle= allbooks.getString(2);
            String bookDesc = allbooks.getString(3);
            String bookImage = allbooks.getString(5);
            String bookCat = allbooks.getString(6);
            String bookAuth = allbooks.getString(7);


            ImageView imageView = new ImageView();
            Image image = new Image("file:C:/Users/Moustafar/Documents/GitHub/GUI/gui/src/main/resources/"+bookImage);
            imageView.setImage(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);


            Label label = new Label(bookTitle);
            VBox newVbox = new VBox();
            newVbox.getChildren().add(imageView);
            newVbox.getChildren().add(label);

            imageView.setOnMouseClicked(event-> {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
                VBox root ;
                try {
                     root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                bookController bookcontroller = fxmlLoader.getController();
                bookcontroller.setAuthor(bookAuth);
                bookcontroller.setTitle(bookTitle);
                if(this.email != null){
                    bookcontroller.setEmail(this.email.getText());
                }


                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.show();
            });

            gridPane.add(newVbox,rowIndex,columnIndex);

            if(gridPane.getColumnCount() == columnIndex){
                rowIndex++;
                columnIndex = 0 ;
                continue;
            }
            columnIndex++;

        }

    }

    //Handler for filtering that works with the iteration design pattern //

    private void setChoiceBoxElements(){
        String[] elements = {"By author", "By category" , "By length"};
        choiceBox.getItems().addAll(elements);
    }
    public void choiceBoxHandler(ActionEvent actionEvent){
        String selectedItem = choiceBox.getValue();
        TextField textField = new TextField();
        Button search = new Button("Search");

        switch(selectedItem){
            case "By author":

                        if( hbox.getChildren().toArray().length > 2 ){
                            hbox.getChildren().remove(hbox.getChildren().get(2));
                            hbox.getChildren().remove(hbox.getChildren().get(2));
                        }
                        hbox.getChildren().add(textField);
                        hbox.getChildren().add(search);



                        search.setOnAction(event-> {
                                try {
                                    String authorName = textField.getText();
                                    AuthorIterator authorIterator = new AuthorIterator(authorName);
                                    ResultSet resultSet = authorIterator.getBooks();
                                    updateBooks(this.gridPane, resultSet);
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                        });
                        return ;

            case "By category":
                return ;

            case "By length":
                    if(hbox.getChildren().toArray().length >2){
                        hbox.getChildren().remove(hbox.getChildren().get(2));
                        hbox.getChildren().remove(hbox.getChildren().get(2));
                    }

                    hbox.getChildren().add(textField);
                    hbox.getChildren().add(search);



                    search.setOnAction(event-> {
                        try {
                            String length = textField.getText();
                            LengthIterator lengthIterator = new LengthIterator(Integer.parseInt(length));
                            updateBooks(this.gridPane, lengthIterator.getBooks());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                return;
            default:
                return ;

        }
    }

    public void updateBooks(GridPane gridPane , ResultSet allbooks) throws  SQLException {

        gridPane.getChildren().clear();

        int columnIndex = 0;
        int rowIndex = 0;
        while(allbooks.next()){

            String bookTitle= allbooks.getString(2);
            String bookDesc = allbooks.getString(3);
            String bookImage = allbooks.getString(5);
            String bookCat = allbooks.getString(6);
            String bookAuth = allbooks.getString(7);


            ImageView imageView = new ImageView();
            Image image = new Image("file:C:/Users/Moustafar/Documents/GitHub/GUI/gui/src/main/resources/"+bookImage);
            imageView.setImage(image);
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);


            Label label = new Label(bookTitle);
            VBox newVbox = new VBox();
            newVbox.getChildren().add(imageView);
            newVbox.getChildren().add(label);

            imageView.setOnMouseClicked(event-> {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
                VBox root ;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                bookController bookcontroller = fxmlLoader.getController();
                bookcontroller.setAuthor(bookAuth);
                bookcontroller.setTitle(bookTitle);
                if(this.email != null){
                    bookcontroller.setEmail(this.email.getText());
                }


                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.show();
            });

            gridPane.add(newVbox,rowIndex,columnIndex);



            if(gridPane.getColumnCount() == columnIndex){
                rowIndex++;
                columnIndex = 0 ;
                continue;
            }
            columnIndex++;

        }

    }
}
