package com.example.project.proxyUser;

import com.example.project.Database;
import com.example.project.user.Borrower;
import com.example.project.user.Librarian;
import com.example.project.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProxyLibrarian extends ProxyUser{
    private Librarian realLibrarian;
    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ProxyLibrarian(){}
    public void login(String email , String password) throws SQLException{
        ResultSet resultSet = database.librarianLogin(email, password) ;
        if(resultSet.next()){
            realLibrarian = new Librarian(Integer.parseInt(resultSet.getString("id")),resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("email"));
            return ;
        }
        else{
            realLibrarian =null ;
            return ;
        }
    }
    public Librarian getRealUser(){
        return this.realLibrarian;
    }

}
