package com.example.project;
import java.sql.*;


public class Database {

    Connection connection ;
    Statement statement ;

    public Database() throws SQLException {
        this.connection= DriverManager.getConnection(String.format("jdbc:mysql://localhost:3306/%s", "guiprroject"), "root", "AlaaKanso2002.@.");
        this.statement = this.connection.createStatement();
        /* statement.execute("create function numberOfBooks (@category varchar(30))" +
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

        */

    }

    public void createTables() throws SQLException{
            ResultSet resultSet ;
            resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'Users')");
            if(!(resultSet.next() && resultSet.getBoolean(1))){
                statement.execute("CREATE TABLE Users (" +
                        " id int PRIMARY KEY," +
                        " username VARCHAR(50) NOT NULL," +
                        " email VARCHAR(50) NOT NULL," +
                        " password VARCHAR(255) NOT NULL," +
                        " type VARCHAR(20));");
            }




            resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'Suppliers')");
            if(!(resultSet.next() && resultSet.getBoolean(1))){
                statement.execute("CREATE TABLE Suppliers (" +
                        "  suplierId int  PRIMARY KEY," +
                        "  name VARCHAR(50) NOT NULL," +
                        "  phoneNumber varchar(20));");
            }

            resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'Books')");
            if(!(resultSet.next() && resultSet.getBoolean(1))){
                statement.execute("CREATE TABLE Books  ( SN int PRIMARY KEY," +
                        "title VARCHAR(50) ,description TEXT,quantity int," +
                        "category varchar(30),autherName varchar(30), suplierId int," +
                        "FOREIGN KEY (suplierId) REFERENCES Suppliers(suplierId));");
            }

            resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'Borrowers')");
            if(!(resultSet.next() && resultSet.getBoolean(1))){
                statement.execute("CREATE TABLE Borrowers (" +
                        "  SN int," +
                        "  id int," +
                        "  borrowDate DATETIME," +
                        "  returnTime DATETIME," +
                        "  FOREIGN KEY (SN) REFERENCES Books(SN)" +
                        ");");
            }
    }

    public void addData() throws SQLException{
        createSuppliers();
        createBooks();
        createUsers();
    }

    public void createUsers() throws SQLException{
            ResultSet resultSet = statement.executeQuery("Select * from Users");
            if(resultSet.next()){
                return ;
            }
            statement.execute("INSERT INTO Users (id, username, email, password, type) VALUES\n" +
                    "( 1, 'john.doe', 'john.doe@example.com', 'password1', 'admin'),\n" +
                    "( 2, 'jane.doe', 'jane.doe@example.com', 'password2', 'borrower'),\n" +
                    "( 3, 'alice', 'alice@example.com', 'password3', 'borrower'),\n" +
                    "( 4, 'bob', 'bob@example.com', 'password4', 'librarian'),\n" +
                    "( 5, 'charlie', 'charlie@example.com', 'password5', 'librarian');\n");

    }
    public void createSuppliers() throws SQLException{
        ResultSet resultSet = statement.executeQuery("Select * from Suppliers");
        if(resultSet.next()){
            return ;
        }
        statement.execute("INSERT INTO Suppliers (suplierId, name, phoneNumber) VALUES\n" +
                "(1, 'Acme Inc.', '555-1234'),\n" +
                "(2, 'Globex Corporation', '555-5678'),\n" +
                "(3, 'Soylent Industries', '555-9012'),\n" +
                "(4, 'Initech LLC', '555-3456');\n");
    }
    public void createBooks() throws SQLException {
        ResultSet resultSet = statement.executeQuery("Select * from Books");
        if(resultSet.next()){
            return ;
        }
        statement.execute("INSERT INTO Books (SN, title, description, quantity, category, autherName, suplierId)\n" +
                "VALUES\n" +
                "  (1, 'The Great Gatsby', 'A novel by F. Scott Fitzgerald', 5, 'Fiction', 'F. Scott Fitzgerald', 1),\n" +
                "  (2, 'To Kill a Mockingbird', 'A novel by Harper Lee', 10, 'Fiction', 'Harper Lee', 2),\n" +
                "  (3, '1984', 'A dystopian novel by George Orwell', 3, 'Science Fiction', 'George Orwell', 3),\n" +
                "  (4, 'Pride and Prejudice', 'A novel by Jane Austen', 7, 'Romance', 'Jane Austen', 4),\n" +
                "  (5, 'The Catcher in the Rye', 'A novel by J.D. Salinger', 2, 'Fiction', 'J.D. Salinger', 1),\n" +
                "  (6, 'Animal Farm', 'A political allegory by George Orwell', 8, 'Satire', 'George Orwell', 1),\n" +
                "  (7, 'The Hobbit', 'A fantasy novel by J.R.R. Tolkien', 4, 'Fantasy', 'J.R.R. Tolkien', 2),\n" +
                "  (8, 'Brave New World', 'A dystopian novel by Aldous Huxley', 6, 'Science Fiction', 'Aldous Huxley', 3),\n" +
                "  (9, 'Jane Eyre', 'A novel by Charlotte Bronte', 1, 'Romance', 'Charlotte Bronte', 4),\n" +
                "  (10, 'The Adventures of Sherlock Holmes', 'A collection of stories by Arthur Conan Doyle', 9, 'Mystery', 'Arthur Conan Doyle', 1);\n");
    }

    public ResultSet selectAllBooks() throws SQLException{
        return statement.executeQuery("Select * from Books ");
    }

    public ResultSet getSelectedBook(String bookName) throws SQLException{
        return statement.executeQuery("Select * from book where title = '"+bookName+"'");
    }

    public boolean login(String email , String password) throws SQLException{
        ResultSet resultSet = statement.executeQuery("Select * from Users where email = '"+email+"' and password = '"+password+"'");
        if(resultSet.next()) return true;
        return false;
    }
}

