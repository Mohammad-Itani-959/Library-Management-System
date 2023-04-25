package com.example.project.user;

import com.example.project.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Librarian extends User{

    public List<User> allBorrowers = new ArrayList<User>();// List of all users


    public Librarian(String email , String password){
        super(email,password);
    }
    public Librarian(int id , String username , String password, String email){
        super(id,username,password,email);
        try {
            fillAllBorrowers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ResultSet getUsersWithBorrowedBooks(){
        return null;
    }
    public void getUserBorrowedBooks(User user){return ;}
    public boolean addBook(Book book) throws SQLException {
        if(database.addBook(book))return true;
        return false;
    }

    public void fillAllBorrowers()throws SQLException{
        // id here is the id of the librarian
        ResultSet resultSet = database.getAllBorrowers();
        while(resultSet.next()){
            Borrower borrower = new Borrower(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email"));
            allBorrowers.add(borrower);
        }
    }

    public void notifyUsers()throws SQLException{
        for(int i = 0 ; i<this.allBorrowers.size();i++){
            if(database.addMessage("New Book is Added !! ",allBorrowers.get(i))){
            }
        }
    }


}
