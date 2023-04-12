package com.example.project;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class allBooksController implements Initializable {
    @javafx.fxml.FXML
    @FXML
    private GridPane gridPane;

    @javafx.fxml.FXML
    @FXML
    private Label email;

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
    }

    public void setUser(String email) {
        this.email.setText(email);
    }

    public void getAllbooks(GridPane gridPane) throws SQLException {
        ResultSet allbooks = this.database.selectAllBooks();
        int columnIndex = 0;
        int rowIndex = 0;

        while (allbooks.next()) {
            String bookTitle= allbooks.getString(2);
            String bookDesc = allbooks.getString(3);
            String bookCat = allbooks.getString(5);
            String bookAuth = allbooks.getString(6);
            Label label = new Label(bookTitle);
            label.setOnMouseClicked(event-> {
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
                bookcontroller.setEmail(this.email.getText());

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.show();
            });

            gridPane.add(label,rowIndex,columnIndex);

            if(gridPane.getColumnCount() == columnIndex){
                rowIndex++;
                columnIndex = 0 ;
                continue;
            }
            columnIndex++;

        }

    }
}
