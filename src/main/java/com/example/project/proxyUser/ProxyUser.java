package com.example.project.proxyUser;

import com.example.project.Database;
import com.example.project.user.Admin;
import com.example.project.user.User;

import java.sql.SQLException;

public abstract class ProxyUser {
    String username , password,email;
    public Database database;

    public String getUsername(){return this.username;}
    public String getPassword(){return this.password;}
    public String getEmail(){return this.email;}

    public void setUsername(String Username){this.username = username;}
    public void setPassword(String password){this.password = password;}
    public void setEmail(String email){this.email = email;}
    public void login(String email ,String password) throws SQLException {
        System.out.println("Proxy User : login");
    }
    public User getRealUser(){
        return null;}

}
