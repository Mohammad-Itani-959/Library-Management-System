package com.example.project;

import com.example.project.proxyUser.ProxyLibrarian;
import com.example.project.proxyUser.ProxyUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibrarianController {
    @com.example.project.FXML
    private Pane pane;
    @FXML
    private TextField bookTitle;
    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField bookAuthor;
    @FXML
    private TextField bookCat;

    @FXML
    private TextField bookDesc;

    @FXML
    private TextField bookLength;

    @FXML
    private TextField bookQuantity;

    @FXML
    private Node addBook;

    ResultSet resultSet ;
    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private File selectedFile;
    public ProxyLibrarian proxyUser;
    public void Logout(ActionEvent actionEvent) throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Entry.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();


        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1350);
        stage.setHeight(810);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    //let the librarian choose an image to upload

    public void chooseImg(ActionEvent actionEvent) {
        FileChooser fileChooser= new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        selectedFile = fileChooser.showOpenDialog(null);
    }
    //add book to the db

    public void addBook(ActionEvent actionEvent) throws SQLException{
        String title= bookTitle.getText();
        String cat= bookCat.getText();
        String desc= bookDesc.getText();
        String author= bookAuthor.getText();
        String length= bookLength.getText();
        String quantity= bookQuantity.getText();
        String imagePath = "images/books/" + selectedFile.getName();
        String destinationDirPath = "src/main/resources/images/books";

        try {
            // Create the destination directory if it does not exist
            Path destinationDir = Paths.get(destinationDirPath);
            if (!Files.exists(destinationDir)) {
                Files.createDirectories(destinationDir);
            }

            // Copy the selected file to the destination directory
            Path destinationPath = Paths.get(destinationDirPath, selectedFile.getName());
            Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Failed to copy image: " + e.getMessage());
        }
        if(!title.isEmpty() && !author.isEmpty() && !length.isEmpty() && !cat.isEmpty() && !quantity.isEmpty() && !desc.isEmpty() && selectedFile != null){
            Book newbook= new Book(title,author,desc,cat,imagePath,length,""+proxyUser.getRealUser().getId());
            newbook.setQuantity(quantity);
            proxyUser.getRealUser().addBook(newbook);
            proxyUser.getRealUser().notifyUsers();
        }
    }
    public void cancel() {

    }
    public void addBookHandler(ActionEvent actionEvent)throws SQLException ,IOException{
        this.pane = (Pane) this.borderPane.getChildren().get(2);
        if(pane.getChildren().get(0) != addBook){
            pane.getChildren().remove(0);
            pane.getChildren().add(0,addBook);
        }
        return;

    }

    //fetchSubscribers is activated whenever i click on list of Users
    public void fetchSubscribers(ActionEvent actionEvent) throws SQLException {
        this.pane = (Pane) this.borderPane.getChildren().get(2);
        if(this.pane.getChildren().get(0)==addBook) {
            this.addBook = pane.getChildren().get(0);
            pane.getChildren().removeAll(pane.getChildren());
            VBox vBox = new VBox();

            resultSet = database.getBorrowers("" + this.proxyUser.getRealUser().getId(), this.proxyUser.getRealUser());
            resultSet.next();
            do {
                Label label = new Label(resultSet.getString("username"));
                label.setFont(Font.font("Corbel", 16));
                label.setTextFill(Color.web("#f0824f"));
                vBox.getChildren().add(label);
            } while (resultSet.next());

            vBox.setPadding(new Insets(10));
            pane.getChildren().add(vBox);
        }
        else return;

    }

    public void setProxyUser(ProxyUser proxyUser){
        this.proxyUser = (ProxyLibrarian)proxyUser;
    }

}
