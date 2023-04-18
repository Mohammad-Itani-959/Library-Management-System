package com.example.project.iterator;

import com.example.project.Database;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryIterator implements Iterator {
    private String category;
    Database database;
    public CategoryIterator(String category){this.category=category;}

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showBooks(GridPane gridPane,ResultSet resultSet) throws SQLException {

    }
}
