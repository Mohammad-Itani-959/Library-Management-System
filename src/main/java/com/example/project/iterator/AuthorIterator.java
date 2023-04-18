package com.example.project.iterator;

import com.example.project.Database;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorIterator implements Iterator {
    private String authorName;


    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthorIterator(String authorName){
        this.authorName = authorName;
    }
    @Override
    public void showBooks(GridPane gridPane,ResultSet resultSet) throws SQLException {

    }

    public ResultSet getBooks() throws SQLException{
        return database.selectAllBooks();
    }
}
