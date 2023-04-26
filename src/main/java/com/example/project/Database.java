package com.example.project;
import com.example.project.user.User;

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
             /*statement.execute("CREATE TABLE Users ("+
                "id int AUTO_INCREMENT Primary Key,"+
                " username VARCHAR(50) NOT NULL," +
                " email VARCHAR(50) NOT NULL," +
                " password VARCHAR(255) NOT NULL," +
                " type VARCHAR(20));");*/
          resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'Users')");
            if(!(resultSet.next() && resultSet.getBoolean(1))){
                statement.execute("CREATE TABLE Users ("+
                        " username VARCHAR(50) NOT NULL," +
                        " email VARCHAR(50) NOT NULL," +
                        " password VARCHAR(255) NOT NULL," +
                        " type VARCHAR(20));");
            }

            resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'Messages')");
            if(!(resultSet.next() && resultSet.getBoolean(1))){
                statement.execute("CREATE TABLE Messages (" +
                        "id int AUTO_INCREMENT Primary Key,"+
                        "  message TEXT," +
                        "  userId int );");
            }

            resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'Books')");
            if(!(resultSet.next() && resultSet.getBoolean(1))){
                statement.execute("CREATE TABLE Books  ( " +
                        "id int AUTO_INCREMENT Primary Key,"+
                        "title VARCHAR(50) ," +
                        "description TEXT," +
                        "image VARCHAR(50)," +
                        "bookLength int,"+
                        "quantity int," +
                        "category varchar(30)," +
                        "authorName varchar(30), " +
                        "librarianId int)");
            }

            resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'Borrows')");
            if(!(resultSet.next() && resultSet.getBoolean(1))){
            statement.execute("CREATE TABLE Borrows ("+
                    "id int AUTO_INCREMENT Primary Key,"+
                    "bookId int,"+
                    "userId int,"+
                    "librarian int,"+
                    "startDate varchar(50),"+
                    "endDate varchar(50));");
        }

    }
    public void addData() throws SQLException{
        createUsers();
        createBooks();
    }
    public void createUsers() throws SQLException{
            ResultSet resultSet = statement.executeQuery("Select * from Users");
            if(resultSet.next()){
                return ;
            }
            statement.execute("INSERT INTO Users (username, email, password, type) VALUES\n" +
                    "('john.doe', 'john.doe@example.com', 'password1', 'admin'),\n" +
                    "('jane.doe', 'jane.doe@example.com', 'password2', 'borrower'),\n" +
                    "('alice', 'alice@example.com', 'password3', 'borrower'),\n" +
                    "('bob', 'bob@example.com', 'password4', 'librarian'),\n" +
                    "('charlie', 'charlie@example.com', 'password5', 'librarian');\n");

    }
    public void createBooks() throws SQLException {
        ResultSet resultSet = statement.executeQuery("Select * from Books");
        if(resultSet.next()){
            return ;
        }
        statement.execute("INSERT INTO Books (title, description,image,bookLength,quantity, category, authorName, librarianId)\n" +
                "VALUES\n" +
                "  ('Alice In WonderLand', 'A novel by Lewis Carroll','images/books/aliceinwonderland.jpg',200, 5, 'Fiction', 'Lewis Carroll', 5),\n" +
                "  ('How To Kill A Mockingbird', 'A novel by Harper Lee','images/books/how to kill.jpg',244, 10, 'Fiction','Harper Lee', 4),\n" +
                "  ('Harry Potter', 'A dystopian novel by J. K. Rowling','images/books/harrypotter.png',142,3, 'Science Fiction', 'J. K. Rowling', 5),\n" +
                "  ('IT', 'A novel by Stephen King','images/books/it.jpg' ,111,7, 'Horror', 'Stephen King', 4),\n" +
                "  ('Java', 'A novel by Mr Kotiyana','images/books/java.jpg', 231,2, 'Nonfiction','Mr Kotiyana', 4),\n" +
                "  ('Le monde Secret De Sombreterre', 'Children book by Cassandra ODonnell','images/books/lemondesecret.jpg',122, 8, 'Fiction', 'Cassandra ODonnell', 5),\n" +
                "  ('Percy Jackson', 'A fantasy novel by Rick Riordan', 'images/books/percyjackson.jpg',122,4, 'Fantasy', 'Rick Riordan',5)\n");
    }
    public ResultSet selectAllBooks() throws SQLException{
        return statement.executeQuery("Select * from Books ");
    }
    public ResultSet getSelectedBook(String bookName) throws SQLException{
        return statement.executeQuery("Select * from book where title = '"+bookName+"'");
    }
    public ResultSet borrowerLogin(String email , String password ) throws SQLException{
        ResultSet resultSet = statement.executeQuery("Select * from Users where email = '"+email+"' and password = '"+password+"' and " +
                "type = 'borrower'");

       return resultSet;
    }
    public boolean borrowerRegister(String username , String email , String password)throws SQLException{
        statement.execute("INSERT INTO Users(username , email , password ,type) " +
                "Values('"+username+"','"+email+"','"+password+"', 'borrower');");
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
        statement.execute("INSERT INTO Users(username , email , password ,type) " +
                "Values('"+username+"','"+email+"','"+password+"', 'librarian');");
        return true;
    }
    public ResultSet get_books_by_author(String authorName) throws SQLException {
        String query = "SELECT * FROM Books WHERE REPLACE(LOWER(authorName), ' ', '') = ?";
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
    public ResultSet getBorrowers(String username) throws SQLException{

        ResultSet resultSet = statement.executeQuery("SELECT * FROM Borrows WHERE id =(" +
                "SELECT id FROM Users WHERE type='librarian' and username ='"+username +"')");
        return resultSet;
        
    }
    public boolean Borrow(Book book , User user,String startDate, String endDate)throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM Books Where title ='"+book.getBookTitle()+"' and quantity = 0 ");

        if(!resultSet.next()) {
            statement.execute("INSERT INTO Borrows(bookId,userId,librarian,startDate,endDate)" +
                    "Values('" + book.getBookId() + "','" + user.getId() + "','" + book.getBookLibrarian() + "','" +
                    startDate + "','" + endDate + "')");
            statement.execute("Update Books SET quantity = quantity -1 WHERE title = '"+book.getBookTitle()+"'");
            System.out.println("1"+user.getUsername());
            return true;
        }
        System.out.println(user.getUsername());
        return false;
    }
    public ResultSet getBorrowedBooks(User user)throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM BOOKS WHERE id IN (" +
                "SELECT bookId FROM Borrows WHERE userId = '"+user.getId()+"')");
       /* display(
                resultSet
        );*/
        return resultSet;
    }

    public void DeleteLibrarian(String email) throws SQLException {
        statement.executeQuery("delete from Users where email="+email);
    }

    public ResultSet getAllLibrarians() throws SQLException {
        return statement.executeQuery("select * from Users where type='librarian'");
    }

    public void addBookLibrarian(Book book) throws SQLException {
        String sql = "INSERT INTO Books (title, description, image, bookLength, quantity, category, authorName, librarianId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, book.getBookTitle());
        statement.setString(2, book.getBookDesc());
        statement.setString(3, book.getBookImage());
        statement.setInt(4, Integer.parseInt(book.getBookLength()));
        statement.setInt(5, Integer.parseInt(book.getQuantity()));
        statement.setString(6, book.getBookCat());
        statement.setString(7, book.getBookAuthor());
        statement.setInt(8, Integer.parseInt(book.getBookLibrarian()));
        statement.executeUpdate();
    }
    public ResultSet getMessage(String id)throws SQLException{
        return statement.executeQuery("SELECT * FROM Messages WHERE userId ='"+id+"'");
    }
    public void deleteMessage(String id) throws SQLException{
        statement.execute("DELETE FROM Messages WHERE userId='"+id+"'");
    }
    public boolean addMessage(String message,User user)throws SQLException{
        statement.execute("INSERT INTO Messages(message,userId) Values('"+message+"','"+user.getId()+"')");
        return true;
    }


}