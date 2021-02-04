package com.example.bookstore;

public class Book{
    public String bookTitle, author, date, category, desc ,imageUrl, language,bookUrl, numberOfPages;

    public Book(String bookTitle, String author, String imageUrl) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.imageUrl = imageUrl;
    }

    public Book(String bookTitle, String author, String date, String category, String desc, String language, String numberOfPages, String imageUrl,String bookUrl) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.date = date;
        this.category = category;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.language = language;
        this.numberOfPages = numberOfPages;
        this.bookUrl = bookUrl;
    }

}
