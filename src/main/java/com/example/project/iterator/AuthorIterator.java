package com.example.project.iterator;

import com.example.project.Database;

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
    public ResultSet getBooks() throws SQLException {
        return database.get_books_by_author(this.authorName);
    }
}
