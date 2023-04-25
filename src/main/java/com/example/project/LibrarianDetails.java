package com.example.project;

import com.example.project.iterator.Iterator;
import com.example.project.proxyUser.ProxyUser;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class LibrarianDetails {
    @FXML
    Label labelname;
    @FXML
    Label labelemail;
    ProxyUser proxyUser;
    private Iterator iterator;
    private librarian lb;
    private String email;

    public void setLb(librarian lb) {
        this.lb = lb;
    }
    public void setinfo(){
        this.labelname.setText(lb.getName());
        this.labelemail.setText(lb.getEmail());
    }
    public void setProxyUser(ProxyUser proxyUser){
        this.proxyUser = proxyUser;
        this.email = proxyUser.getRealUser().getEmail();
    }

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


    public void delete() throws SQLException {
        String email = this.proxyUser.getEmail().toString();
        database.DeleteLibrarian(email);
    }
}
