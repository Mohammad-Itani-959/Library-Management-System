package com.example.project;

public class borrower {
    private String email ;
    private String username ;
    private String password;
    private boolean isLogged ;


    public borrower (String email ,String password ){
        this.email = email ;
        this.password= password;
        this.isLogged = false ;
    }
    public borrower (String email , String username , String password ){
        this.email= email;
        this.password = password;
        this.username= username ;
        this.isLogged =false ;
    }

    public boolean  login(String email , String password ){
        if(this.email.equals(email) && this.password.equals(password)){
            this.isLogged = true;
            return true;
        }
        else{
            this.isLogged= false;
            return false ;
        }
    }

    public String getUsername(){return  this.username;}
    public String getEmail(){return this.email ;}
    public String getPassword(){return this.password;}


}
