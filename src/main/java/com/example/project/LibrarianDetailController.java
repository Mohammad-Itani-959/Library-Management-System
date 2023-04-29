package com.example.project;

import com.example.project.iterator.Iterator;
import com.example.project.proxyUser.ProxyAdmin;
import com.example.project.proxyUser.ProxyUser;
import com.example.project.user.Admin;
import com.example.project.user.Librarian;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LibrarianDetailController {
    @FXML
    Label labelName;
    @FXML
    Label labelemail;
    ProxyUser proxyUser;
    private Iterator iterator;
    private Librarian librarian;
    private String email;

    Database database;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void start(){
        this.labelName.setText(librarian.getUsername());
        this.labelemail.setText(librarian.getEmail());
    }
    public void setProxyUser(ProxyAdmin proxyUser){
        this.proxyUser = proxyUser;
        this.email = proxyUser.getRealUser().getEmail();
    }
    public void delete(ActionEvent actionEvent) throws SQLException, IOException {
        Admin admin = (Admin)this.proxyUser.getRealUser();
        if(!admin.removeLibrarian(this.librarian)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Cannot Remove Librarian... Last One...");

            // Set the font and color of the content text
            Label label = new Label(alert.getContentText());
            label.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
            label.setTextFill(Color.RED);

            // Set the content of the alert to the label
            alert.getDialogPane().setContent(label);
            alert.show();

            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        AnchorPane root = fxmlLoader.load();

        AdminController adminController = fxmlLoader.getController();
        adminController.setProxyUser((ProxyAdmin)this.proxyUser);
        adminController.start();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1350);
        stage.setHeight(810);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

    }
    public void goBack(ActionEvent actionEvent) throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("admin.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        AnchorPane root = fxmlLoader.load();
        AdminController adminController = fxmlLoader.getController();
        adminController.setProxyUser(this.proxyUser);
        adminController.start();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1350);
        stage.setHeight(810);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    public void createLibrarian(Librarian librarian){this.librarian=librarian;}

}
