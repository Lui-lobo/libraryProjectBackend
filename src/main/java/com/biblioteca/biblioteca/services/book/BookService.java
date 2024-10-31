package com.biblioteca.biblioteca.services.book;

// Importando modelo do livro
import com.biblioteca.biblioteca.model.Book;
import com.biblioteca.biblioteca.model.BookCollection;
// Interface do livro
import com.biblioteca.biblioteca.interfaces.book.BookServiceInterface;
// Importando repositório com querys de banco para o serviço
import com.biblioteca.biblioteca.repository.BookRepository;
import com.biblioteca.biblioteca.repository.BookCollectionRepository;
// Importando utilitários do springBoot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

// Importando utilitários do java
import java.util.List;
import java.util.Optional;

@Service
public class BookService implements BookServiceInterface  {

    @Autowired
    private BookRepository BookRepository;

    @Autowired
    private BookCollectionRepository bookCollectionRepository;

    @Override
    public List<Book> listBooks() {
        return BookRepository.findAll();
    }

    @Override
    public Book addBook(Book livro) {
        return BookRepository.save(livro);
    }

    @Override
    public Optional<List<Book>> searchForBookByAuthor(String author) {
        return BookRepository.findBooksByAuthor(author);
    }

    @Override
    public Optional<Book> searchForBookByIsbn(String isbn) {
        return BookRepository.findBookByIsbn(isbn);
    }

    @Override
    public Boolean deleteBook(Long bookId) {
          // Verifica se o livro existe
        if (BookRepository.existsById(bookId)) {
            // Busca todos os exemplares no acervo que estão associados ao livro
            List<BookCollection> collections = bookCollectionRepository.findByBookId(bookId);

            // Deleta todos os exemplares relacionados ao livro
            if (!collections.isEmpty()) {
                bookCollectionRepository.deleteAll(collections);
            }

            // Deleta o livro
            BookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }

    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> existingBook = BookRepository.findById(id);

        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setIsbn(updatedBook.getIsbn());
            book.setGenre(updatedBook.getGenre());

            return BookRepository.save(book); // Salva as alterações no banco de dados
        } else {
            throw new RuntimeException("Livro não encontrado");
        }
    }

    public boolean isIsbnAlreadyRegistered(String isbn, Long excludeBookId) {
        // Busca todos os livros com o ISBN fornecido
        Optional<Book> booksWithIsbn = BookRepository.findByIsbnAndIdNot(isbn, excludeBookId);
        // Verifica se existe algum livro com o ISBN, exceto o livro que está sendo atualizado
        return booksWithIsbn.isPresent();
    }

     // Método para inativar um livro pelo ID
    public void inactivateBook(Long id) {
        Optional<Book> bookOptional = BookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setActive(false); // Define o livro como inativo
            BookRepository.save(book); // Salva a alteração no banco de dados
        } else {
            // Caso o livro não seja encontrado, lança uma exceção com o status NOT_FOUND
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado.");
        }
    }

    public void activateBook(Long id) {
        Optional<Book> bookOptional = BookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setActive(true); // Define o livro como inativo
            BookRepository.save(book); // Salva a alteração no banco de dados
        } else {
            // Caso o livro não seja encontrado, lança uma exceção com o status NOT_FOUND
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado.");
        }
    }
}
