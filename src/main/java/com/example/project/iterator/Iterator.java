package com.example.project.iterator;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import com.example.project.proxyUser;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Iterator {
    FXMLLoader fxmlLoader = null;
    public proxyUser proxyUser = null;

    public void showBooks(GridPane gridPane) throws SQLException;
    public void setFXMLLoader(FXMLLoader fxmlLoader);
    public void setProxyUser(proxyUser proxyUser);
}
