package com.example.project.iterator;

import com.example.project.Database;
import com.example.project.proxyUser;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LengthIterator implements Iterator {
    private int length;
    FXMLLoader fxmlLoader;
    private proxyUser proxyUser;

    private ResultSet resultSet;
    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LengthIterator(int length){
        this.length=length;

    }

    @Override
    public void showBooks(GridPane gridPane) throws SQLException {

    }

    @Override
    public void setFXMLLoader(FXMLLoader fxmlLoader) {

    }

    @Override
    public void setProxyUser(com.example.project.proxyUser proxyUser) {

    }
}
