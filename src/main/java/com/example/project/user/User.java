package com.example.project.user;

import com.example.project.Database;

import java.sql.SQLException;

public abstract class User {
    String username , password,email;
    String message;
    int id;

    Database database;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public User(String email , String password ){
        this.email = email;
        this.password = password;
    }
    public User(int id, String username , String password, String email){
        this.username = username ;
        this.password = password;
        this.email =email;
        this.id = id ;
    }
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getEmail(){return this.email;}
    public int getId(){return this.id;}

}
