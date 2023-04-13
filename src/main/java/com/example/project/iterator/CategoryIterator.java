package com.example.project.iterator;

import com.example.project.Database;

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
    public ResultSet getBooks() throws SQLException {
        return database.get_books_by_category(this.category);
    }
}
