package com.example.project.iterator;

import com.example.project.Database;
import com.example.project.LibrarianDetails;
import com.example.project.librarian;
import com.example.project.proxyUser.ProxyUser;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibrarianIterator implements Iterator{
    Database database;
    public ProxyUser proxyUser;
    FXMLLoader fxmlLoader;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ResultSet resultSet;
    public LibrarianIterator(ProxyUser proxyUser)throws SQLException{
        this.resultSet = this.getLibrarians();
        this.proxyUser = proxyUser;
    }
    public ResultSet getLibrarians() throws SQLException {
        return database.getAllLibrarians();
    }


    public ResultSet getNext(){
        return resultSet;
    }

    @Override
    public void show(GridPane gridPane) throws SQLException {//I will rename it to show the librarian
        int columnIndex = 0;
        int rowIndex = 0;

        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(15);
        gridPane.setVgap(10);
        ResultSet resultSet ;
        while (has_Next()) {
            resultSet = getNext();
            String librarianName= resultSet.getString("username");
            String librarianEmail= resultSet.getString("email");
            librarian l = new librarian(librarianName,librarianEmail);




            Label label = new Label(librarianName);

            label.setFont(Font.font("Corbel", 16));
            label.setTextFill(Color.web("#f0824f"));
            VBox newVbox = new VBox();
            newVbox.getChildren().add(label);

            newVbox.setPrefWidth(207);
            newVbox.setPrefHeight(320);
            newVbox.getStyleClass().add("librarian-vbox");
            label.setOnMouseClicked(event-> {
                AnchorPane root;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                LibrarianDetails librariancontroller = fxmlLoader.getController();
                librariancontroller.setLb(l);
                librariancontroller.setinfo();
                librariancontroller.setProxyUser(getProxyUser());
                //librariancontroller.setName(librarianName);
                //librariancontroller.setEmail(librarianEmail);
                //librariancontroller.setProxyUser(getProxyUser());

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.show();
            });
            //add the content in the gridpane
            gridPane.add(newVbox, columnIndex % 4, rowIndex);

            if (columnIndex % 4 == 1) {
                rowIndex++;
                columnIndex = 0;
            } else {
                columnIndex++;
            }
        }
    }

    public boolean has_Next() {
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
        return null;
    }

    @Override
    public void setFXMLLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader =fxmlLoader;
    }

    @Override
    public void setProxyUser(ProxyUser proxyUser) {
        this.proxyUser = proxyUser;
    }

    public ProxyUser getProxyUser(){return this.proxyUser ;}
}
