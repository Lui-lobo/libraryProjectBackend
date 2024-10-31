package com.biblioteca.biblioteca.controller;

import java.util.List;
import java.util.Optional;

// Importando utilitários do springBoot
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;

// Importando modelo do livro
import com.biblioteca.biblioteca.model.Book;
// Importando serviço de livro
import com.biblioteca.biblioteca.services.book.BookService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/books")
public class BooksController {
    // Metodos pertecentes a esse controller também serão consumidos pela classe de Acervo!

    @Autowired
    private BookService bookService;

    // Rota para buscar todos os livros cadastrados
    @GetMapping("/all")
    public List<Book> listBooks() {
        return bookService.listBooks();
    }

    // Rota para adicionar um novo livro
    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book livro) {
        Book novoLivro = bookService.addBook(livro);
        return ResponseEntity.ok(novoLivro);
    }

    // Busca uma lista de livros por seu autor
    @GetMapping("/getByAuthor")
    public Optional<List<Book>> searchForBookByAuthor(@RequestParam String author) {
        return bookService.searchForBookByAuthor(author);
    }

    // Busca um livro por seu ISBN
    @GetMapping("/getByIsbn")
    public Optional<Book> searchForBookByIsbn(@RequestParam String isbn) {
        return bookService.searchForBookByIsbn(isbn);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<String> deleteBook(@RequestParam String bookId) {
        // Convertendo o id para o tipo long
        Long convertedId = Long.parseLong(bookId);

        Boolean deleteResponse = bookService.deleteBook(convertedId);

        if(deleteResponse) {
            return ResponseEntity.ok("Livro deletado com sucesso");
        } else {
            return ResponseEntity.ok("Livro enviado não encontrado!");
        }
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<?> updateBookById(@PathVariable Long id, @RequestBody Book newBookData) {
            // Verifica se o ISBN já está cadastrado em outro livro
        if (bookService.isIsbnAlreadyRegistered(newBookData.getIsbn(), id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("O ISBN enviado já está em uso."); // Retorna 401 se o ISBN já existir
        }
        
        try {
            Book updatedBook = bookService.updateBook(id, newBookData);
            return ResponseEntity.ok(updatedBook); // Retorna o livro atualizado
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Retorna uma resposta de erro
        }
    }

    @PutMapping("/inactivate/{id}")
    public ResponseEntity<String> inactivateBook(@PathVariable Long id) {
        try {
            bookService.inactivateBook(id); // Chama o método do serviço para inativar o livro
            return ResponseEntity.ok("Livro inativado com sucesso.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<String> activateBook(@PathVariable Long id) {
        try {
            bookService.activateBook(id); // Chama o método do serviço para inativar o livro
            return ResponseEntity.ok("Livro inativado com sucesso.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }
    
}
