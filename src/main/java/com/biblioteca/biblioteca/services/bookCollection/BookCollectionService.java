package com.biblioteca.biblioteca.services.bookCollection;

// Utilitários do projeto
import com.biblioteca.biblioteca.model.Book;
import com.biblioteca.biblioteca.model.BookCollection;
import com.biblioteca.biblioteca.repository.BookCollectionRepository;
import com.biblioteca.biblioteca.repository.BookRepository;
// Utilitários do springBoot
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Utilitários do java
import java.util.Optional;
import java.util.List;

@Service
public class BookCollectionService {
    @Autowired
    private BookCollectionRepository bookCollectionRepository;

    @Autowired
    private BookRepository bookRepository;

    // Método para verificar se o livro existe, se não, cria e adiciona ao acervo, caso exista, apenas adiciona ao acervo
    public BookCollection addOrCreateBookAndAddToCollection(String title, String author, String isbn, int publishedYear,
            int copies, String genre) {

        // Verifica se o livro já existe pelo ISBN
        Optional<Book> existingBook = bookRepository.findBookByIsbn(isbn);

        Book book;
        if (existingBook.isPresent()) {
            // Se o livro já existe, usa o existente
            book = existingBook.get();
        } else {
            // Caso contrário, cria um novo livro
            book = new Book(title, author, isbn, genre, true);
            bookRepository.save(book); // Salva o novo livro na tabela de livros
        }

        // Agora, adiciona ao acervo
        List<BookCollection> collections = bookCollectionRepository.findByBookId(book.getId());

        if (!collections.isEmpty()) {
            // Se já existe um registro de acervo para este livro, atualiza as cópias
            // disponíveis
            BookCollection bookCollection = collections.get(0); // Supondo que só tenha uma localização por livro
            bookCollection.addCopies(copies);
            return bookCollectionRepository.save(bookCollection);
        } else {
            // Se ainda não existe um acervo para este livro, cria um novo registro no
            // acervo
            BookCollection newCollection = new BookCollection(book, copies);
            return bookCollectionRepository.save(newCollection);
        }
    }

    // Atualiza um acervo de livros
    public BookCollection updateBookCollection(Long id, int availableCopies) {
        // Busca o exemplar pelo ID
        BookCollection bookCollection = bookCollectionRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Exemplar não encontrado"));

        // Atualiza a quantidade disponível
        bookCollection.setAvailableCopies(availableCopies);

        // Salva e retorna o exemplar atualizado
        return bookCollectionRepository.save(bookCollection);
    }

    // Método para deletar um exemplar do acervo
    public boolean deleteBookCollection(Long id) {
        if (bookCollectionRepository.existsById(id)) {
            bookCollectionRepository.deleteById(id);
            return true; // Exemplar deletado com sucesso
        } else {
            return false; // Exemplar não encontrado
        }
    }

    // Busca todos os livros cadastrados na coleção
    public List<BookCollection> findAllBooksOnCollection() {
        List<BookCollection> collections = bookCollectionRepository.findAll();

        return collections;
    }

    // Busca um livro no acervo a partir de seu ISBN
    public List<BookCollection> findBookByIsbn(String isbn) {
        // Lógica para encontrar livros pela ISBN e retorná-los como uma lista
        List<BookCollection> bookCollections = bookCollectionRepository.findByBookIsbn(isbn);
        return bookCollections; // Certifique-se de que isso retorna uma lista
    }

    // Busca um livro no acervo a partir de seu título
    public List<BookCollection> findBookByTitle(String title) {
        return bookCollectionRepository.findByBookTitle(title);
    }

    // Busca um livro no acervo a partir de seu Autor
    public List<BookCollection> findBookByAuthor(String author) {
        return bookCollectionRepository.findByBookAuthor(author);
    }

    // Busca de livros ativos
    public List<BookCollection> findAllActiviesBooks() {
        List<BookCollection> bookCollections = bookCollectionRepository.findAllActivesBooks();
        return bookCollections;
    }

    // Busca um livro no acervo a partir de seu ISBN
    public List<BookCollection> findBookByIsbnAndActive(String isbn) {
        List<BookCollection> bookCollections = bookCollectionRepository.findByBookIsbnAndActive(isbn);
        return bookCollections;
    }

    // Busca um livro no acervo a partir de seu título
    public List<BookCollection> findBookByTitleAndActive(String title) {
        return bookCollectionRepository.findByBookTitleAndActive(title);
    }

    // Busca um livro no acervo a partir de seu Autor
    public List<BookCollection> findBookByAuthorAndActive(String author) {
        return bookCollectionRepository.findByBookAuthorAndActive(author);
    }

}
