package com.example.project.user;

import java.sql.ResultSet;

public class Admin extends User{

    public Admin(String email , String password  ){
        super(email,password);
    }
    public Admin(String username , String password, String email) {
        super(username, password, email);
    }
    public ResultSet getLibrarians(){return null;}
    public void addLibrarian(){return;}
    public void removeLibrarian(){return;}
}
