package com.biblioteca.biblioteca.dto.BookCollection;

public class BookCollectionDTO {
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private int copies;
    private String genre;

    // Construtor, Getters e Setters
    public BookCollectionDTO(String title, String author, String isbn, int publishedYear, int copies, String genre) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.copies = copies;
        this.genre = genre;
    }

    // Getters e setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

