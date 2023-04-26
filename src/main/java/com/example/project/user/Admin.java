package com.example.project.user;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends User{

    public Admin(String email , String password  ){
        super(email,password);
    }
    public Admin(int id, String username , String password, String email) {
        super(id,username, password, email);
    }
    public ResultSet getLibrarians(){return null;}
    public void addLibrarian(String username ,String password, String email)throws SQLException{
        database.librarianRegister(username,password,email);
    }
    public void removeLibrarian(Librarian librarian)throws SQLException {
        database.DeleteLibrarian(librarian);
    }
}
