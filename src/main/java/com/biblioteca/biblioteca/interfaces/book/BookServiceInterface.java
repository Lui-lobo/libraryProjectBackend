package com.biblioteca.biblioteca.interfaces.book;

import com.biblioteca.biblioteca.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookServiceInterface {
    List<Book> listBooks();
    Book addBook(Book book);
    Optional<Book> searchForBookByIsbn(String isbn);
    Optional<List<Book>> searchForBookByAuthor(String author);
    Boolean deleteBook(Long id);
}
