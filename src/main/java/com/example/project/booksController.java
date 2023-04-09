package com.example.project;

import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class booksController implements  Initializable {
    @javafx.fxml.FXML
    @FXML
    private ListView<String> booklistview;

    @javafx.fxml.FXML
    @FXML
    private Label bookName;
    @javafx.fxml.FXML
    @FXML
    private Label bookDesc;
    @javafx.fxml.FXML
    @FXML
    private Label bookAuth;
    @javafx.fxml.FXML
    @FXML
    private Label bookCat ;

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
            addToList(booklistview);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        booklistview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                selectedItem(observable, oldValue, newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void addToList(ListView<String> listView) throws SQLException{

        ResultSet resultSet = database.selectAllBooks();
        while(resultSet.next()){
            String bookName = resultSet.getString(2);
            listView.getItems().add(bookName);
        }
    }

    public void selectedItem(ObservableValue<? extends String> Observable , String oldValue , String newValue) throws SQLException{
        String selectedItem = booklistview.getSelectionModel().getSelectedItem().toString();
        if(!selectedItem.isEmpty()){

            ResultSet resultSet = database.getSelectedBook(selectedItem);

            if(resultSet.next()){
                bookName.setText(resultSet.getString(2));
                bookDesc.setText(resultSet.getString(3));
                bookAuth.setText(resultSet.getString(6));
                bookCat.setText(resultSet.getString(5));
            }

        }

    }




}
