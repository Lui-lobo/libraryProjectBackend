package com.biblioteca.biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BookCollection {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Muitos exemplares podem estar associados a um livro
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int availableCopies; // Cópias disponíveis no acervo

    // Construtores, getters e setters
    public BookCollection() {}

    public BookCollection(Book book, int availableCopies) {
        this.book = book;
        this.availableCopies = availableCopies;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void addCopies(int copies) {
        this.availableCopies += copies;
    }

    public void removeCopies(int copies) {
        if (this.availableCopies >= copies) {
            this.availableCopies -= copies;
        }
    }
}
