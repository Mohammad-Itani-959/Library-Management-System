package com.example.project.iterator;

import com.example.project.Database;

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
    public ResultSet getBooks() throws SQLException {
        return database.get_books_by_length(this.length);
    }
}
