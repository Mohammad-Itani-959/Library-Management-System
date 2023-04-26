package com.example.project.iterator;

import com.example.project.Database;
import com.example.project.LibrarianDetailController;
import com.example.project.proxyUser.ProxyAdmin;
import com.example.project.proxyUser.ProxyUser;
import com.example.project.user.Librarian;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibrarianIterator implements Iterator{
    Database database;
    public ProxyUser proxyUser;
    FXMLLoader fxmlLoader;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ResultSet resultSet;
    public LibrarianIterator(ProxyUser proxyUser)throws SQLException{
        this.resultSet = this.getLibrarians();
        this.proxyUser = proxyUser;
    }
    public ResultSet getLibrarians() throws SQLException {
        return database.getAllLibrarians();
    }
    public ResultSet getNext(){
        return resultSet;
    }
    @Override
    public void show(GridPane gridPane) throws SQLException {//I will rename it to show the librarian
        int i = 0 ;
        int a = gridPane.getChildren().size();
        while(i<a){
            gridPane.getChildren().remove(0);
            i++;
        }

        int columnIndex = 0;
        int rowIndex = 0;

        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(15);
        gridPane.setVgap(10);

        while (has_Next()) {
            resultSet = getNext();
            String librarianName= resultSet.getString("username");
            String librarianEmail= resultSet.getString("email");
            Librarian l = new Librarian(Integer.parseInt(resultSet.getString("id")),librarianName,resultSet.getString("password"),librarianEmail);
            Label label = new Label(librarianName);
            label.setFont(Font.font("Corbel", 16));
            label.setTextFill(Color.web("#f0824f"));
            VBox newVbox = new VBox();


            ImageView imageView = new ImageView();
            Image image = new Image(getClass().getResourceAsStream("/images/books/librarian.jpg"));
            imageView.setImage(image);
            imageView.setFitWidth(207);
            imageView.setFitHeight(300);

            newVbox.getChildren().add(label);
            newVbox.setPrefWidth(207);
            newVbox.setPrefHeight(320);
            newVbox.getStyleClass().add("librarian-vbox");
            newVbox.setOnMouseClicked(event-> {
                AnchorPane root;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                LibrarianDetailController librarianDetails = fxmlLoader.getController();
                librarianDetails.setProxyUser((ProxyAdmin) this.proxyUser);
                librarianDetails.createLibrarian(l);
                librarianDetails.start();

                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setFullScreen(true);
                stage.setScene(scene);
            });
            //add the content in the gridpane
            gridPane.add(newVbox, columnIndex % 4, rowIndex);

            if (columnIndex % 4 == 1) {
                rowIndex++;
                columnIndex = 0;
            } else {
                columnIndex++;
            }
        }
    }
    public boolean has_Next() {
        try {
            if(resultSet.next()){
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ResultSet get_Next() {
        return resultSet;
    }

    @Override
    public ResultSet getBooks() throws SQLException {
        return null;
    }

    @Override
    public void setFXMLLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader =fxmlLoader;
    }

    @Override
    public void setProxyUser(ProxyUser proxyUser) {
        this.proxyUser = proxyUser;
    }

}
