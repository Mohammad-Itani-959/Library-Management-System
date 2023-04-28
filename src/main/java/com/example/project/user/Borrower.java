package com.example.project.user;

import com.example.project.Book;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Borrower extends User {
    public Borrower(String email , String password){
        super(email,password);
    }
    public Borrower(int id ,String username ,String password ,String email){
        super(id,username,password,email);
    }
    public boolean borrowBook(Book book , Borrower user , String start , String end)throws SQLException{
        database.Borrow(book,user,start,end);
        return true;
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
