package com.example.project.user;

import java.sql.ResultSet;

public class Librarian extends User{

    public Librarian(String email , String password){
        super(email,password);
    }
    public Librarian(String username , String password, String email){
        super(username,password,email);
    }

    public ResultSet getUsersWithBorrowedBooks(){
        return null;
    }
    public void getUserBorrowedBooks(User user){return ;}
    public void addBook(String bookName,
                        String bookTitile,
                        String bookDescription,
                        String bookAuthor,
                        String image,
                        String Category){
        return ;
    }

}
