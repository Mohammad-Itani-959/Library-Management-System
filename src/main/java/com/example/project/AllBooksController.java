package com.example.project;

import com.example.project.iterator.*;
import com.example.project.proxyUser.ProxyUser;
import com.example.project.user.Borrower;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AllBooksController {
    @javafx.fxml.FXML
    @FXML
    private GridPane gridPane;
    @javafx.fxml.FXML
    @FXML
    private TextField searchText;

    @javafx.fxml.FXML
    @FXML
    private ScrollPane scrollPane;
    @javafx.fxml.FXML
    @FXML
    private Label email;
    @javafx.fxml.FXML
    @FXML
    private ChoiceBox<String> choiceBox = new ChoiceBox<String>();
    @javafx.fxml.FXML
    @FXML
    private HBox hbox ;

    @javafx.fxml.FXML
    @FXML
    private TextField searchAuthor;

    @javafx.fxml.FXML
    @FXML
    private TextField searchLength;

    public ProxyUser proxyUser;
    Database database;

    Iterator iterator;


    @javafx.fxml.FXML
    @FXML
    private Label text;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws SQLException,IOException {
        this.showMessage(text);
        try {
            this.getAllbooks(gridPane);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setChoiceBoxElements();
    }

    public void getAllbooks(GridPane gridPane) throws SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
        iterator = new GeneralIterator();
        iterator.setProxyUser(this.proxyUser);
        iterator.show(gridPane);
        iterator.setFXMLLoader(fxmlLoader);
    }
    //Handler for filtering that works with the iteration design pattern //
    private void setChoiceBoxElements() throws SQLException{
        ResultSet rs = database.getCategories();
        ObservableList<String> elements = FXCollections.observableArrayList();
        while (rs.next()) {
            String category = rs.getString("category");
            elements.add(category);

        }
        elements.add("All");
        choiceBox.setItems(elements);
    }
    public void searchCategoryHandler(ActionEvent actionEvent) throws SQLException{

        String selectedItem = choiceBox.getValue();
        if(selectedItem.equals("All")){
            iterator = new GeneralIterator();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
            iterator.setProxyUser(this.proxyUser);
            iterator.setFXMLLoader(fxmlLoader);
            iterator.show(gridPane);
            return;
        }
        iterator = new CategoryIterator(selectedItem);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
        iterator.setProxyUser(this.proxyUser);
        iterator.setFXMLLoader(fxmlLoader);
        iterator.show(gridPane);

    }
    public void searchAuthorHandler(ActionEvent actionEvent )throws SQLException{
            String authorName = searchAuthor.getText();
            if(authorName.isEmpty()){
                searchAuthor.setPromptText("Please enter an author name");
                this.getAllbooks(gridPane);
            }
            else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
                iterator = new AuthorIterator(authorName);
                iterator.setProxyUser(this.proxyUser);
                iterator.show(gridPane);
                iterator.setFXMLLoader(fxmlLoader);
            }
    }
    public void searchLengthHandler(ActionEvent actionEvent )throws SQLException{
        if(searchLength.getText().equals("")){
            searchAuthor.setPromptText("Please enter an author name");
            this.getAllbooks(gridPane);

        }
        else{
            Integer length = Integer.parseInt(searchLength.getText());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
            iterator = new LengthIterator(length);
            iterator.setProxyUser(this.proxyUser);
            iterator.show(gridPane);
            iterator.setFXMLLoader(fxmlLoader);
        }
    }

    public void searchBookNameHandler(ActionEvent actionEvent)throws SQLException{
        if(searchText.getText().equals("")){
            searchText.setPromptText("Please enter a book name");
            this.getAllbooks(gridPane);
        }
        else{

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("book.fxml"));
            iterator = new BookNameIterator(searchText.getText());
            iterator.setProxyUser(this.proxyUser);
            iterator.show(gridPane);
            iterator.setFXMLLoader(fxmlLoader);
        }
    }
    public void Logout(ActionEvent actionEvent) throws SQLException,IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("entry.fxml"));
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
    public void setProxyUser(ProxyUser proxyUser){
        this.proxyUser = proxyUser;
        this.email.setText(proxyUser.getRealUser().getEmail());
    }
    public void YourBooks(ActionEvent actionEvent) throws SQLException, IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("borrowedbooks.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        AnchorPane root = fxmlLoader.load();

        BorrowedbooksController borrowedbooksController = fxmlLoader.getController();
        borrowedbooksController.setProxyUser(this.proxyUser);
        borrowedbooksController.start();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1350);
        stage.setHeight(810);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public void showMessage(Label label)throws SQLException,IOException{
        ResultSet resultSet = database.getMessage(""+this.proxyUser.getRealUser().getId());
        String Message="";
        if(!resultSet.next()){
            label.setText("No New Notifications");
            return;
        }
        else{
            do{
                Message = Message+""+ resultSet.getString("message")+"\n";
            }while(resultSet.next());
        }
        label.setText(Message);
        label.setOnMouseClicked(event->{
            try {
                database.deleteMessage(""+this.proxyUser.getRealUser().getId());
                label.setText("No New Notifications");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
