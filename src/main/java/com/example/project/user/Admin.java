package com.example.project.user;

import javafx.scene.layout.GridPane;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User{

    List<Librarian> librarians = new ArrayList<Librarian>();
    public Admin(String email , String password  ){
        super(email,password);
    }
    public Admin(int id, String username , String password, String email){
        super(id,username, password, email);

    }
    public void getLibrarians() throws SQLException{
            for(int i = 0 ; i<this.librarians.size();i++){
                this.librarians.remove(i);
            }
            ResultSet resultSet =  database.getAllLibrarians();

            while(resultSet.next()){
                Librarian librarian = new Librarian(
                        Integer.parseInt(resultSet.getString("id")),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email")
                );
                this.librarians.add(librarian);
            }
    }
    public void addLibrarian(String username ,String password, String email)throws SQLException{
        database.librarianRegister(username,password,email);
        this.getLibrarians();
    }
    public void removeLibrarian(Librarian librarian)throws SQLException {
        for(int i=0 ; i<this.librarians.size();i++){
            if(librarians.get(i).getEmail().equals(librarian.getEmail())){
                System.out.println("in if clause");
                database.DeleteLibrarian(librarian);
            }
        }

    }
}
