package com.example.project.iterator;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Iterator {

    public void showBooks(GridPane gridPane,ResultSet resultSet) throws SQLException;
}
