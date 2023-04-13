package com.example.project;

import com.example.project.Database;


import java.sql.SQLException;

public class proxyBorrower {
    private borrower realBorrower ;
    private Database database;

    {
        try {
            database = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public proxyBorrower(String email , String password){
        try {
            this.login(email ,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void login(String email , String password) throws SQLException{
        if(database.borrowerLogin(email,password)){
            this.realBorrower = new borrower(email,password);

        }
        else{
            this.realBorrower = null ;
        }
    }

    public borrower getRealBorrower(){
        return this.realBorrower;
    }
}

