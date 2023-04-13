package com.example.project.iterator;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Iterator {

    public ResultSet getBooks() throws SQLException;
}
