package com.example.project;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class loginController {


    Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @javafx.fxml.FXML
    @FXML
    public TextField email;
    @FXML
    public PasswordField password;
    @FXML
    public Button register;
    @FXML
    public Button login;

    @javafx.fxml.FXML
    @FXML
    private Label errorMessage;

    private String type ;

    protected proxyUser proxyUser;
    public void login(ActionEvent actionEvent) throws SQLException, IOException {

        String Email = email.getText().toString();
        String Password = password.getText().toString();

        if(Email.isEmpty() || Password.isEmpty()){
            errorMessage.setText("Please fill the empty fields ... ! ");
            return ;
        }

        this.proxyUser = new proxyUser(Email,Password,this.type);


        if(this.proxyUser.getRealUser()==null){
            errorMessage.setText("Invalid username or password ... !");
            return ;
        }
        else{
            if(this.type == "borrower"){
                errorMessage.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("allbooks.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                AnchorPane root = fxmlLoader.load();


                allBooksController allBooksController = fxmlLoader.getController();
                allBooksController.setUser(this.proxyUser.getRealUser().getEmail());


                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setFullScreen(true);

                stage.show();
                return ;
            }
            if(this.type =="librarian"){
                errorMessage.setText("");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("librarianLayout.fxml"));
                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                AnchorPane root = fxmlLoader.load();


               /* allBooksController allBooksController = fxmlLoader.getController();
                allBooksController.setUser(this.proxyUser.getRealUser().getEmail());*/


                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setFullScreen(true);

                stage.show();
            }

        }

    }

    public void setType(String type){this.type = type;}
}
