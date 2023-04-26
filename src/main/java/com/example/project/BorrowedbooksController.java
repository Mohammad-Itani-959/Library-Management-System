package com.example.project;

import com.example.project.iterator.MyBooksIterator;
import com.example.project.proxyUser.ProxyUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowedbooksController {

    public ProxyUser proxyUser;
    public ResultSet resultSet;
    @javafx.fxml.FXML
    @FXML
    private GridPane gridPane;
    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    @FXML
    private Label email;
    public void Logout(ActionEvent actionEvent) throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AllBooks.fxml"));
        AnchorPane root = fxmlLoader.load();

        AllBooksController allBooksController = fxmlLoader.getController();
        allBooksController.setProxyUser(this.proxyUser);
        allBooksController.start();


        Stage stage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1350);
        stage.setHeight(810);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void start()throws SQLException{
        MyBooksIterator iterator = new MyBooksIterator(this.proxyUser);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Book.fxml"));
        iterator.setProxyUser(this.proxyUser);
        iterator.setFXMLLoader(fxmlLoader);
        iterator.show(gridPane);
        
    }

    public void setProxyUser(ProxyUser proxyUser){
        this.proxyUser = proxyUser;
        this.email.setText(proxyUser.getRealUser().getEmail());
    }

    public ResultSet getBooks()throws SQLException{
       return database.getBorrowedBooks(proxyUser.getRealUser());
    }


}
