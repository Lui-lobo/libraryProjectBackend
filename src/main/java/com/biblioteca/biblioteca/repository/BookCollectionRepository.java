package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.BookCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCollectionRepository extends JpaRepository<BookCollection, Long> {
    List<BookCollection> findByBookId(Long bookId);  // Buscar acervo pelo ID do livro

    @Query("SELECT bc FROM BookCollection bc WHERE bc.book.isbn = :isbn")
    List<BookCollection> findByBookIsbn(@Param("isbn") String isbn);

    @Query("SELECT bc FROM BookCollection bc WHERE bc.book.author = :author")
    List<BookCollection> findByBookAuthor(@Param("author") String author);

    @Query("SELECT bc FROM BookCollection bc WHERE bc.book.title = :title")
    List<BookCollection> findByBookTitle(@Param("title") String title);

    // Rotas para os usuários poderem apenas buscar livros ativos.
    @Query("SELECT bc FROM BookCollection bc where bc.book.id = :bookId AND bc.book.active = true")
    List<BookCollection> findByBookIdAndActive(Long bookId);  // Buscar acervo pelo ID do livro e se está ativo

    @Query("SELECT bc FROM BookCollection bc WHERE bc.book.active = true")
    List<BookCollection> findAllActivesBooks();

    @Query("SELECT bc FROM BookCollection bc WHERE bc.book.isbn = :isbn AND bc.book.active = true")
    List<BookCollection> findByBookIsbnAndActive(@Param("isbn") String isbn);

    @Query("SELECT bc FROM BookCollection bc WHERE bc.book.author = :author AND bc.book.active = true")
    List<BookCollection> findByBookAuthorAndActive(@Param("author") String author);

    @Query("SELECT bc FROM BookCollection bc WHERE bc.book.title = :title AND bc.book.active = true")
    List<BookCollection> findByBookTitleAndActive(@Param("title") String title);
}
