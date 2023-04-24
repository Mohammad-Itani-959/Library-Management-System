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
    public void login(String email , String password) throws SQLException{
        String username = database.borrowerLogin(email,password);
        if(username!= null){
            realBorrower = new Borrower(username, password,email);
            System.out.println(username);
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
