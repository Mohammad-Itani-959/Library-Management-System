package com.example.project.proxyUser;

import com.example.project.Database;
import com.example.project.user.Borrower;
import com.example.project.user.Librarian;
import com.example.project.user.User;

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
    public void login(String username , String password) throws SQLException{
        if(database.librarianLogin(username,password)){
            realLibrarian = new Librarian(username, password);
            return ;
        }
        else{
            realLibrarian =null ;
            return ;
        }
    }
    public User getRealUser(){
        return this.realLibrarian;
    }
}
