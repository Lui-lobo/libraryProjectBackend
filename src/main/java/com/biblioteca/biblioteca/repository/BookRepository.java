package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// Importando utilitários do java
import java.util.List;
import java.util.Optional;

// Interface responsável por realizar a comunicação com o banco de dados
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // Consulta para buscar um livro por seu autor
    @Query("SELECT b FROM Book b WHERE b.author = ?1")
    Optional<List<Book>> findBooksByAuthor(String author);

     // Consulta para buscar um livro por seu isbn
     @Query("SELECT b FROM Book b WHERE b.isbn = ?1")
     Optional<Book> findBookByIsbn(String isbn);
 
     Optional<Book> findByIsbnAndIdNot(String isbn, Long excludeBookId);

     Optional<Book> findById(Long bookId);

}

