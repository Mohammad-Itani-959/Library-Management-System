package com.example.project.user;

public abstract class User {
    String username , password,email;
    public User(String username , String password){
        this.username = username;
        this.password = password;
    }
    public User(String username , String password, String email){
        this.username = username ;
        this.password = password;
        this.email =email;
    }
    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getEmail(){return this.email;}
}
