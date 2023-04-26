package com.example.project.iterator;

import com.example.project.proxyUser.ProxyUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Iterator {
    /*
    // showBooks is used to set the books fetched from the database to the grid Pane
    // setFXMLoader is used to get the Book.fxml and send it to the iterator
    // setProxyUser is used to transfer the user between the pages
    // has_Next is for finding whether the result set has a next element or not
    // get_Next is used to move to the next row in the result set
    // getBooks is used to get the books from the database
    */ //<<<--- here are the definitions of the functions below !!!

    public void show(GridPane gridPane) throws SQLException;
    public void setFXMLLoader(FXMLLoader fxmlLoader);
    public void setProxyUser(ProxyUser proxyUser);
    public boolean has_Next();
    public ResultSet get_Next();
    public ResultSet getBooks() throws SQLException;
}
