package com.example.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class bookController implements Initializable {

    @FXML
    private ImageView bookImage;
    @FXML
    private Text bookTitle;
    @FXML
    private Text bookAuthor;

    private String email ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void backHandler(ActionEvent actionEvent)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("allbooks.fxml"));
        AnchorPane root = fxmlLoader.load();
        allBooksController allBooksController = fxmlLoader.getController();
        allBooksController.setUser(email);
        Stage stage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public void setAuthor(String name ){this.bookAuthor.setText(name);}
    public void setTitle(String title){this.bookTitle.setText(title);}

    public void setEmail(String email){
        if(email != null){
            this.email = email ;
        }

        System.out.println(this.email);
    }
}
