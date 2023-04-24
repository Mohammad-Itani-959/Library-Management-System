package com.example.project.user;

public class Borrower extends User {

    public Borrower(String email ,String password){
        super(email,password);
    }
    public Borrower(String username ,String password ,String email){
        super(username,password,email);
    }
    public void borrowBook(){
        //Code Here
    }
    public void returnBook(){
        //Code Here
    }
}
