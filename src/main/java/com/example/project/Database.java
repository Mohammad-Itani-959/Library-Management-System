package com.example.project;
import com.example.project.user.Borrower;
import com.example.project.user.Librarian;
import com.example.project.user.User;

import java.sql.*;
import java.util.ArrayList;


public class Database {

    Connection connection ;
    Statement statement ;

    public Database() throws SQLException {
        this.connection= DriverManager.getConnection(String.format("jdbc:mysql://localhost:3306/%s", "guiprroject"), "root", "");
        this.statement = this.connection.createStatement();
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
                "  ('Alice In WonderLand', 'Alice In Wonderland is a classic novel written by Lewis Carroll. It tells the story of a young girl named Alice who falls down a rabbit hole and discovers a strange and fantastical world. The book is filled with whimsical characters, such as the Cheshire Cat, the Mad Hatter, and the Queen of Hearts, and is known for its use of surrealism and wordplay. Alice In Wonderland is a timeless tale that continues to captivate readers of all ages.','images/books/aliceinwonderland.jpg',200, 10, 'Fiction', 'Lewis Carroll', 5),\n" +
                "  ('And Then There Were None', 'And Then There Were None is a thrilling mystery novel written by Agatha Christie. The story follows ten strangers who are invited to a remote island under false pretenses, only to find themselves trapped and hunted by an unknown killer. As the guests begin to die off one by one, the tension and suspense mount until the shocking conclusion. And Then There Were None is widely regarded as one of Christie greatest works, and is a must-read for fans of the mystery genre','images/books/and then there were none.jpg',300, 10, 'Mystery','Agatha Christie', 4),\n" +
                "  ('Harry Potter', 'The Harry Potter book series is a beloved fantasy series written by J.K. Rowling. The story follows a young boy named Harry Potter, who discovers on his eleventh birthday that he is a wizard. He enrolls in Hogwarts School of Witchcraft and Wizardry, where he makes friends and enemies, learns magic, and uncovers the truth about his family and his destiny. The series is known for its intricate plot, well-developed characters, and immersive world-building. It has been widely acclaimed for its ability to captivate readers of all ages, and has become a cultural phenomenon, inspiring a film franchise, merchandise, and even a theme park','images/books/harrypotter.png',142,14, 'Fantasy', 'J. K. Rowling', 5),\n" +
                "  ('IT', 'This book is a horror story about a group of friends who are terrorized by a malevolent entity that takes on the form of a clown named Pennywise. The book deals with themes of childhood trauma, friendship, and the struggle between good and evil','images/books/it.jpg' ,111,7, 'Horror', 'Stephen King', 4),\n" +
                "  ('Java', 'This book is designed for individuals who are new to programming and wish to learn how to develop software using Java. The book covers the basics of object-oriented programming, data types, control structures, arrays, functions, classes, inheritance, polymorphism, and exception handling, among other topics','images/books/java.jpg', 231,2, 'Nonfiction','Mr Kotiyana', 4),\n" +
                "  ('Le monde Secret De Sombreterre', 'It is a children fantasy novel that tells the story of two young siblings, Max and Lili, who discover a hidden world beneath the Earth surface called Sombreterre. In this world, they meet a variety of magical creatures and embark on a thrilling adventure to save Sombreterre from an evil queen who seeks to destroy it','images/books/lemondesecret.jpg',122, 8, 'Children', 'Cassandra ODonnell', 5),\n" +
                "  ('Percy Jackson', 'Percy Jackson and the Olympians is a series of five books written by Rick Riordan. The series follows the adventures of a young boy named Percy Jackson who discovers that he is a demigod, the son of Poseidon, the god of the sea. As Percy navigates the challenges of being a demigod, he also becomes embroiled in a larger conflict between the gods of Olympus and the Titans, ancient beings who seek to overthrow the gods and rule the world', 'images/books/percyjackson.jpg',122,4, 'Fantasy', 'Rick Riordan',5),\n"+
                "  ('Arthurs Eyes', '  It tells the story of Arthur, an eight-year-old aardvark, who gets glasses and is nervous about wearing them to school. The book deals with themes of self-acceptance and the fear of being different', 'images/books/arthur eyes.jpg',250,6, 'Children', 'Marc Brown',5),\n"+
                "  ('Arthur Turns Green', 'It tells the story of Arthur, who eats too many sugary snacks and turns green. He becomes self-conscious and tries to hide his green skin from his friends. The book deals with themes of healthy eating and body image', 'images/books/arthur turns green.jpg',150,16, 'Children','Marc Brown',4),\n"+
                "  ('Believe in Yourself', 'The book offers practical advice and techniques for developing a positive attitude and mindset, building self-confidence, and achieving success. It deals with themes of personal growth and empowerment', 'images/books/believe in yourself.jpg',248,20, 'Motivational','Dr. Joseph Murphy',5),\n"+
                "  ('One of Us Is Lying', 'The book follows the story of five high school students who go into detention, but only four of them come out alive. The four remaining students become suspects in the death of their classmate and must work together to clear their names and find the true killer. The book deals with themes of friendship, betrayal, and trust', 'images/books/one of us is lying.jpg',400,25, 'Mystery','Karen M. McManus',4),\n"+
                "  ('One of Us Is Next', 'The story follows a new group of students at Bayview High School who become embroiled in a new dangerous game of truth or dare that mimics the events of the previous year. The book deals with themes of friendship, secrets, and consequences', 'images/books/one of us is next.png',384 ,15, 'Mystery','Karen M. McManus',5),\n"+
                "  ('Romeo and Juliet', 'The story is about two young lovers, Romeo and Juliet, who belong to feuding families in Verona. The play explores the themes of love, passion, violence, and fate', 'images/books/romeo and juliet.png',384 ,15, 'Classic','William Shakespeare',5),\n"+
                "  ('The Little Prince', 'The book tells the story of a young prince who travels from planet to planet and ultimately comes to Earth. Along the way, he meets a pilot who has crashed in the desert, and the two form an unlikely friendship. The book deals with themes of loneliness, love, and the importance of human connections', 'images/books/thelittleprince.jpg',100 ,15, 'Children','Antoine de Saint-Exupéry',5),\n"+
                "  ('How to Kill a Mockingbird', 'The novel tells the story of a young girl named Scout Finch and her experiences growing up in a small town in Alabama during the 1930s. The book deals with themes of racism, injustice, and the loss of innocence', 'images/books/how to kill.jpg',365 ,30, 'Coming-of-age','Harper Lee',4)\n");
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
    public ResultSet librarianLogin(String email , String password) throws SQLException{
       ResultSet resultSet= statement.executeQuery("Select * from Users where email = '"+email+"' and password = '"+password+"' and type = 'librarian'");
       return  resultSet;
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
    public ResultSet get_books_by_name(String name) throws SQLException{
        return statement.executeQuery("Select * From Books where title ='"+name+"'");
    }
    public ResultSet getCategories() throws SQLException{
        return statement.executeQuery("Select DISTINCT category From Books");
    }
    public ResultSet getBorrowers( Librarian librarian) throws SQLException{

        ResultSet resultSet = statement.executeQuery("SELECT DISTINCT * FROM Users WHERE id IN(" +
                "SELECT userId FROM Borrows WHERE librarian = '"+librarian.getId()+"') and type ='borrower'");
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

        return resultSet;
    }
    public void DeleteLibrarian(Librarian librarian) throws SQLException {
        String query = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, ""+librarian.getId());
            stmt.executeUpdate();
        }
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
    public ResultSet getAllBorrowers()throws SQLException{
        return statement.executeQuery("Select * FROM Users WHERE type ='borrower'");
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

    public ArrayList<User> getUsersOfLibrarian(Librarian librarian)throws SQLException{
        ResultSet resultSet = this.getBorrowers(librarian);
        ArrayList<User> users = new ArrayList<User>();
        int i = 0 ;
        while(resultSet.next()){
            User user = new Borrower(
                    Integer.parseInt(resultSet.getString("id")),
                    resultSet.getString("username"),
                    resultSet.getString("password"),
                    resultSet.getString("email")
            );
            users.add(user);
        }
        return users;
    }
}