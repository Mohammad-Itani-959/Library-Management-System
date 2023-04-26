package com.example.project;

import com.example.project.iterator.Iterator;
import com.example.project.proxyUser.ProxyAdmin;
import com.example.project.proxyUser.ProxyUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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


    public void delete(ActionEvent actionEvent) throws SQLException, IOException {
        String email = lb.getEmail();
        database.DeleteLibrarian(email);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

    }
    public void goBack(ActionEvent actionEvent) throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        AnchorPane root = fxmlLoader.load();
        AdminController adminhandler = fxmlLoader.getController();
        adminhandler.setProxyUser(this.proxyUser);

        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();

    }
}
