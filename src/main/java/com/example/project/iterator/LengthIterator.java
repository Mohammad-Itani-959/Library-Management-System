package com.example.project.iterator;

import com.example.project.Database;
import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LengthIterator implements Iterator {
    private int length;

    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LengthIterator(int length){this.length=length;}

    @Override
    public void showBooks(GridPane gridPane, ResultSet resultSet) throws SQLException {

    }
}
