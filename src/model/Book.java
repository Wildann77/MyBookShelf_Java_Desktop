package model;

import java.io.Serializable;

public class Book implements Serializable {

    private String title;
    private String author;
    private int year;
    private boolean isRead;
    private boolean isPrivate;
    private String username;

    public Book() {
    }

    public Book(String title, String author, int year, boolean isRead, boolean isPrivate, String username) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isRead = isRead;
        this.isPrivate = isPrivate;
        this.username = username;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public boolean isRead() {
        return isRead;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
    
     public String getUsername() {
       return username; 
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

  

   
}
