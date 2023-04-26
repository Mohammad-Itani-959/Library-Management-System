package com.example.project.user;

import javafx.scene.control.Label;
import org.w3c.dom.Text;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Borrower extends User {
    public Borrower(String email , String password){
        super(email,password);
    }
    public Borrower(int id ,String username ,String password ,String email){
        super(id,username,password,email);
    }
    public void borrowBook(){
        //Code Here
    }
    public void returnBook(){
        //Code Here
    }
    public void notifyUser(int id , Label label)throws SQLException {

        ResultSet resultSet = database.getMessage(""+id);
        if(resultSet.next()){
            label.setText(resultSet.getString("message"));
            label.setOnMouseClicked(
                    mouseEvent -> {
                        try {
                            database.deleteMessage(""+id);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        label.setText("No New Notifications");
                    });
        }

    }
}
