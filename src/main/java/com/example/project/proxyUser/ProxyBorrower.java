package com.example.project.proxyUser;

import com.example.project.Database;
import com.example.project.user.Borrower;
import com.example.project.user.User;

import java.sql.ResultSet;
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
        ResultSet resultSet = database.borrowerLogin(email,password);
        if(resultSet.next()){
            realBorrower = new Borrower(Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email"));
            return ;
        }
        else{
            realBorrower =null ;
            return ;
        }
    }
    public Borrower getRealUser(){
        return realBorrower;
    }

}
