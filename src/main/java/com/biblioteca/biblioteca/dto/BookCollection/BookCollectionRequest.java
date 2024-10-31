package com.biblioteca.biblioteca.dto.BookCollection;

public class BookCollectionRequest {
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private int copies;
    private String genre;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public int getCopies() {
        return copies;
    }

    public String getGenre() {
        return genre;
    }
}
