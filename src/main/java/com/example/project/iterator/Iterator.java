package com.example.project.iterator;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import com.example.project.ProxyUser;

import java.sql.SQLException;

public interface Iterator {
    FXMLLoader fxmlLoader = null;
    public ProxyUser proxyUser = null;

    public void showBooks(GridPane gridPane) throws SQLException;
    public void setFXMLLoader(FXMLLoader fxmlLoader);
    public void setProxyUser(ProxyUser proxyUser);
}
