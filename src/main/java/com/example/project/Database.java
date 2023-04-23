package com.example.project;
import java.sql.*;


public class Database {

    Connection connection ;
    Statement statement ;

    public Database() throws SQLException {
        this.connection= DriverManager.getConnection(String.format("jdbc:mysql://localhost:3306/%s", "guiprroject"), "root", "");
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
                        "image VARCHAR(50),bookLength int,"+
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
        statement.execute("INSERT INTO Books (SN, title, description,image,bookLength,quantity, category, autherName, suplierId)\n" +
                "VALUES\n" +
                "  (1, 'Alice In WonderLand', 'A novel by Lewis Carroll','images/books/aliceinwonderland.jpg',200, 5, 'Fiction', 'Lewis Carroll', 1),\n" +
                "  (2, 'How To Kill A Mockingbird', 'A novel by Harper Lee','images/books/how to kill.jpg',244, 10, 'Fiction','Harper Lee', 2),\n" +
                "  (3, 'Harry Potter', 'A dystopian novel by J. K. Rowling','images/books/harrypotter.png',142,3, 'Science Fiction', 'J. K. Rowling', 3),\n" +
                "  (4, 'IT', 'A novel by Stephen King','images/books/it.jpg' ,111,7, 'Horror', 'Stephen King', 4),\n" +
                "  (5, 'Java', 'A novel by Mr Kotiyana','images/books/java.jpg', 231,2, 'Nonfiction','Mr Kotiyana', 1),\n" +
                "  (6, 'Le monde Secret De Sombreterre', 'Children book by Cassandra ODonnell','images/books/lemondesecret.jpg',122, 8, 'Fiction', 'Cassandra ODonnell', 1),\n" +
                "  (7, 'Percy Jackson', 'A fantasy novel by Rick Riordan', 'images/books/percyjackson.jpg',122,4, 'Fantasy', 'Rick Riordan', 2)\n");
    }
    public ResultSet selectAllBooks() throws SQLException{
        return statement.executeQuery("Select * from Books ");
    }
    public ResultSet getSelectedBook(String bookName) throws SQLException{
        return statement.executeQuery("Select * from book where title = '"+bookName+"'");
    }
    public boolean borrowerLogin(String email , String password) throws SQLException{
        ResultSet resultSet = statement.executeQuery("Select * from Users where email = '"+email+"' and password = '"+password+"' and " +
                "type = 'borrower'");
        if(resultSet.next()) return true;
        return false;
    }
    public boolean borrowerRegister(String username , String email , String password)throws SQLException{
        statement.execute("INSERT INTO Users(id,username , email , password ,type) " +
                "Values('"+idBorrowers+"','"+username+"','"+email+"','"+password+"', 'borrower');");
        idBorrowers++;
        return true;
    }
    public boolean adminLogin(String email , String password) throws SQLException{
        ResultSet resultSet = statement.executeQuery("Select * from Users where email = '"+email+"' and password = '"+password+"' and " +
                "type = 'admin'");
        if(resultSet.next()) return true;
        return false;
    }
    public boolean adminRegister(String username , String email , String password)throws SQLException{
        ResultSet resultSet = statement.executeQuery("Select * from Users where type = 'admin'");
        if(resultSet.next())return false;
        else{
            statement.execute("INSERT INTO Users(id,username , email , password ,type) " +
                    "Values('"+'1'+"','"+username+"','"+email+"','"+password+"', 'admin');");
            return true;
        }

    }
    public boolean librarianLogin(String email , String password) throws SQLException{
       ResultSet resultSet= statement.executeQuery("Select * from Users where email = '"+email+"' and password = '"+password+"' and type = 'librarian'");
        if(resultSet.next()) return true;
        return  false ;
    }
    public boolean librarianRegister(String username , String password , String email) throws SQLException{
        statement.execute("INSERT INTO Users(id,username , email , password ,type) " +
                "Values('"+idBorrowers+"','"+username+"','"+email+"','"+password+"', 'librarian');");
        idLibrarians++;
        return true;
    }
    public ResultSet get_books_by_author(String authorName) throws SQLException {
        String query = "SELECT * FROM Books WHERE REPLACE(LOWER(autherName), ' ', '') = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, authorName.toLowerCase().replaceAll("\\s+", ""));
        ResultSet resultSet = stmt.executeQuery();
        return resultSet;
    }
    public ResultSet get_books_by_category(String category) throws SQLException{
        return statement.executeQuery("Select * From Books where category ='"+category+"'");
    }
    public ResultSet get_books_by_length(int length) throws SQLException{
        return statement.executeQuery("Select * From Books where bookLength <='"+length+"'");
    }

    public ResultSet getCategories() throws SQLException{
        return statement.executeQuery("Select DISTINCT category From Books");
    }
    static int idBorrowers = 8;
    static int idLibrarians = 8;
}

