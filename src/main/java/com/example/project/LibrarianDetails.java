package com.example.project;

import com.example.project.proxyUser.ProxyUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class LibrarianDetails {
    @FXML
    Label labelname;
    @FXML
    Label labelemail;
    ProxyUser proxyuser;
    Database database;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setName(String librarianName) {
        labelemail.setText(librarianName);
    }


    public void setEmail(String librarianEmail) {
        labelemail.setText(librarianEmail);
    }

    public void setProxyUser(ProxyUser proxyUser) {
        this.proxyuser = proxyUser;
    }
    public void delete() throws SQLException {
        String email = this.proxyuser.getEmail().toString();
        database.DeleteLibrarian(email);
    }
}
