package com.example.project;

import com.example.project.iterator.Iterator;
import com.example.project.proxyUser.ProxyUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class LibrarianController {
    @FXML
    private TextField bookTitle;

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

    private File selectedFile;

    public ProxyUser proxyUser;

    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void Logout(ActionEvent actionEvent) throws SQLException, IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("entry.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        AnchorPane root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.show();
    }

    //let the librarian choose an image to upload
    public void chooseimg(ActionEvent actionEvent) {
        FileChooser fileChooser= new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        selectedFile = fileChooser.showOpenDialog(null);
    }


    //add book to the db
    public void addbook(ActionEvent actionEvent) throws SQLException{
        String title= bookTitle.getText();
        String cat= bookCat.getText();
        String desc= bookDesc.getText();
        String author= bookAuthor.getText();
        String length= bookLength.getText();
        String quantity= bookQuantity.getText();
        String imagePath = "images/books/" + selectedFile.getName();
        String destinationDirPath = "src/main/resources/images/books";
        System.out.println(this.proxyUser);

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
            newbook.setQuantity(""+quantity);
            database.addBookLibrarian(newbook);
        }
    }

    //remove the info the librarian entered in the fields
    public void cancel() {

    }

    public void setProxyUser(ProxyUser proxyUser){
        this.proxyUser = proxyUser;
    }

}
