package com.example.project.proxyUser;

import com.example.project.user.Admin;
import com.example.project.Database;
import com.example.project.user.User;

import java.sql.SQLException;

public class ProxyAdmin extends ProxyUser {
    private Admin realAdmin;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ProxyAdmin(){}

    public void login(String email ,String password) throws SQLException{
        if(database.adminLogin(email,password)){
            realAdmin= new Admin(email, password);
            return;
        }
        else{
            realAdmin = null;
        }
    }
    public User getRealUser(){
        return this.realAdmin;
    }

}
