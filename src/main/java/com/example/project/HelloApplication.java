package com.example.project;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    Button user;
    Button librarian;
    Button admin;
    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();*/

        // Hello there//

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Library Management System");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();


        //HelloController controller = loader.getController();
    }

    public static void main(String[] args) {
        /*try {
            Connection connection ;
            connection= DriverManager.getConnection(String.format("jdbc:mysql://localhost:3306/%s", "guiprroject"), "root", "");
            Statement statement = connection.createStatement();


            statement.execute("CREATE TABLE users (id int ,username VARCHAR(50)," +
                    "  email VARCHAR(50), password VARCHAR(255),type varchar(20),PRIMARY KEY(id) )" );

            statement.execute("CREATE TABLE suplier (" +
                    "  suplierId int  PRIMARY KEY," +
                    "  name VARCHAR(50) NOT NULL," +
                    "  phoneNumber varchar(20));");
            statement.execute("CREATE TABLE book  ( SN int PRIMARY KEY," +
                    "title VARCHAR(50) ,description TEXT,quantity int," +
                    "category varchar(30),autherName varchar(30), suplierId int," +
                    "FOREIGN KEY (suplierId) REFERENCES suplier(suplierId));");
            statement.execute("CREATE TABLE borrowers (" +
                    "  SN int," +
                    "  id int," +
                    "  borrowDate DATETIME," +
                    "  returnTime DATETIME," +
                    "  FOREIGN KEY (id) REFERENCES users(id)," +
                    "  FOREIGN KEY (SN) REFERENCES book(SN)" +
                    ");");

            statement.execute("create function numberOfBooks (@category varchar(30))" +
                    "returns int" +
                    "as" +
                    "begin" +
                    "" +
                    "declare @i int" +
                    "select @i=count(distinct(title))" +
                    "from book B" +
                    "where B.category=category" +
                    "" +
                    "return @i" +
                    "" +
                    "end" +
                    ");");
            statement.execute(" create function numberOfBorrowers () " +
                    "returns int " +
                    "as " +
                    "begin " +
                    " " +
                    " declare @i int " +
                    " select @i=count(distinct(id)) " +
                    " from borrowers B " +
                    " return @i " +
                    " " +
                    "end");

            statement.execute(" create function quantityOfABook (@SN int) " +
                    "returns int " +
                    "as " +
                    "begin " +
                    " " +
                    " declare @i int " +
                    " select @i=B.quantity " +
                    " from book B " +
                    " where B.SN = @SN " +
                    "  " +
                    " return @i " +
                    " " +
                    "end");
            statement.execute("  create trigger InsertBorrower " +
                    "on borrowers " +
                    "after insert" +
                    "AS\n" +
                    "begin\n" +
                    "    -- Check if the quantity of the book is greater than 0\n" +
                    "    if exists (\n" +
                    "        select *\n" +
                    "        from inserted i\n" +
                    "        JOIN book b on i.SN = b.SN\n" +
                    "        where b.quantity > 0\n" +
                    "    )\n" +
                    "    begin\n" +
                    "        -- Decrement the quantity by 1\n" +
                    "        update book\n" +
                    "        set quantity = quantity - 1\n" +
                    "        where SN IN (\n" +
                    "            select SN\n" +
                    "            from inserted\n" +
                    "        );\n" +
                    "    end\n" +
                    "    else\n" +
                    "    begin\n" +
                    "        -- Rollback the insertion\n" +
                    "        raiserror ('Quantity is 0. Insertion rolled back.', 16, 1);\n" +
                    "        rollback transaction;\n" +
                    "    end\n" +
                    "end;");
            statement.execute(" create trigger DeleteBorrower\n" +
                    "on borrowers\n" +
                    "after delete\n" +
                    "AS\n" +
                    "begin\n" +
                    "    \n" +
                    "    update book\n" +
                    "    set quantity = quantity + 1\n" +
                    "    where SN in (\n" +
                    "        select SN\n" +
                    "        from deleted\n" +
                    "    );\n" +
                    "end;\n");

            statement.execute(" create procedure allBorrowers (@category varchar(30))\n" +
                    "as\n" +
                    "begin\n" +
                    "\tselect *\n" +
                    "\tfrom borrowers b,book t\n" +
                    "\twhere b.SN=t.SN and t.category=@category\n" +
                    "\n" +
                    "\t\n" +
                    "\n" +
                    "end");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
       */
        launch();
    }
}
