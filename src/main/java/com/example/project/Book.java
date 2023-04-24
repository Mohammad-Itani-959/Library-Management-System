package com.example.project;

public class Book {
    private String bookDesc,bookAuthor,bookTitle,bookLength,bookCat,bookId,bookImage,bookLibrarian;
    public Book(
            String bookTitle,
            String bookAuthor,
            String bookDesc,
            String bookCat,
            String bookId,
            String bookImage,
            String bookLength ,
            String bookLibrarian
    ){
        this.bookAuthor =bookAuthor;
        this.bookCat = bookCat;
        this.bookId = bookId;
        this.bookImage = bookImage;
        this.bookLength = bookLength;
        this.bookLibrarian = bookLibrarian;
        this.bookTitle = bookTitle;
        this.bookDesc=bookDesc;
    }

    public String getBookDesc(){return this.bookDesc;}
    public String getBookAuthor() {
        return bookAuthor;
    }
    public String getBookTitle() {
        return bookTitle;
    }
    public String getBookLength() {
        return bookLength;
    }
    public String getBookCat() {
        return bookCat;
    }
    public String getBookId() {
        return bookId;
    }
    public String getBookImage() {
        return bookImage;
    }
    public String getBookLibrarian() {
        return bookLibrarian;
    }
}
