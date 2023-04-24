package com.example.project.proxyUser;

import com.example.project.Database;
import com.example.project.user.Borrower;
import com.example.project.user.User;

import java.sql.SQLException;

public class ProxyBorrower extends ProxyUser {
    private Borrower realBorrower;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProxyBorrower(){}
    public void login(String username , String password) throws SQLException{
        if(database.borrowerLogin(username,password)){
            realBorrower = new Borrower(username, password);
            return ;
        }
        else{
            realBorrower =null ;
            return ;
        }
    }
    public User getRealUser(){
        return realBorrower;
    }

}
