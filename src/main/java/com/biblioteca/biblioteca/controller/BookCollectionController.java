package com.biblioteca.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.biblioteca.model.BookCollection;
import com.biblioteca.biblioteca.services.bookCollection.BookCollectionService;

// Importando DTO de requisição
import com.biblioteca.biblioteca.dto.BookCollection.BookCollectionRequest;
import com.biblioteca.biblioteca.dto.BookCollection.BookCollectionUpdateDto;

@RestController
@RequestMapping("/api/bookCollection")
public class BookCollectionController {

    @Autowired
    private BookCollectionService bookCollectionService;

    // Endpoint para criar ou adicionar um livro ao acervo
    @PostMapping("/add")
    public ResponseEntity<BookCollection> addOrCreateBookAndAddToCollection(
            @RequestBody BookCollectionRequest bookCollectionRequest) {
        BookCollection newBookCollection = bookCollectionService.addOrCreateBookAndAddToCollection(
            bookCollectionRequest.getTitle(),
            bookCollectionRequest.getAuthor(),
            bookCollectionRequest.getIsbn(),
            bookCollectionRequest.getPublishedYear(),
            bookCollectionRequest.getCopies(),
            bookCollectionRequest.getGenre()
        );
        return ResponseEntity.ok(newBookCollection);
    }

    // Rota para editar um exemplar no acervo, sem alterar o livro associado
    @PutMapping("/edit")
    public ResponseEntity<BookCollection> editBookCollection(
            @RequestParam Long id, 
            @RequestBody BookCollectionUpdateDto updateDto) {
        try {
            BookCollection bookCollection = bookCollectionService.updateBookCollection(id, updateDto.getAvailableCopies());
            return ResponseEntity.ok(bookCollection); // Retorna o exemplar atualizado
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null); // Retorna uma resposta de erro
        }
    }

    // Método para deletar um exemplar do acervo
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBookCollection(@RequestParam Long id) {
        try {
            boolean isDeleted = bookCollectionService.deleteBookCollection(id);
            if (isDeleted) {
                return ResponseEntity.ok("Exemplar deletado com sucesso.");
            } else {
                return ResponseEntity.status(404).body("Exemplar não encontrado.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao tentar deletar o exemplar.");
        }
    }

    // Busca todos os livros cadastrados no acervo
    @GetMapping("/getAllBooksOnCollection")
    public ResponseEntity<List<BookCollection>> getAllBooksOnCollection() {
        List<BookCollection> bookCollections = bookCollectionService.findAllBooksOnCollection();
        return ResponseEntity.ok(bookCollections); // Retorna todos os livros
    }

    // Busca um livro no acervo pelo seu isbn
    @GetMapping("/getBookOnCollectionByIsbn")
    public ResponseEntity<List<BookCollection>> getBookOnCollectionByIsbn(@RequestParam String isbn) {
        List<BookCollection> bookCollections = bookCollectionService.findBookByIsbn(isbn);
        return ResponseEntity.ok(bookCollections); // Retorna a lista de livros encontrados
    }
    
    @GetMapping("/getBookOnCollectionByTitle")
    public ResponseEntity<List<BookCollection>> getBookOnCollectionByTitle(@RequestParam String title) {
        List<BookCollection> bookCollections = bookCollectionService.findBookByTitle(title);
        return ResponseEntity.ok(bookCollections); // Retorna os livros encontrados
    }

    @GetMapping("/getBookOnCollectionByAuthor")
    public ResponseEntity<List<BookCollection>> getBookOnCollectionByAuthor(@RequestParam String author) {
        List<BookCollection> bookCollections = bookCollectionService.findBookByAuthor(author);
        return ResponseEntity.ok(bookCollections); // Retorna os livros encontrados
    }

     // Busca todos os livros ativos cadastrados no acervo
     @GetMapping("/active/getAllBooksOnCollection")
     public ResponseEntity<List<BookCollection>> getAllActiviesBooksOnCollection() {
         List<BookCollection> bookCollections = bookCollectionService.findAllActiviesBooks();
         return ResponseEntity.ok(bookCollections); // Retorna todos os livros
     }
 
     // Busca um livro no acervo pelo seu isbn
     @GetMapping("/active/getBookOnCollectionByIsbn")
     public ResponseEntity<List<BookCollection>> getBookOnCollectionByIsbnAndActive(@RequestParam String isbn) {
         List<BookCollection> bookCollections = bookCollectionService.findBookByIsbnAndActive(isbn);
         return ResponseEntity.ok(bookCollections); // Retorna a lista de livros encontrados
     }
     
     @GetMapping("/active/getBookOnCollectionByTitle")
     public ResponseEntity<List<BookCollection>> getBookOnCollectionByTitleAndActive(@RequestParam String title) {
         List<BookCollection> bookCollections = bookCollectionService.findBookByTitleAndActive(title);
         return ResponseEntity.ok(bookCollections); // Retorna os livros encontrados
     }
 
     @GetMapping("/active/getBookOnCollectionByAuthor")
     public ResponseEntity<List<BookCollection>> getBookOnCollectionByAuthorAndActive(@RequestParam String author) {
         List<BookCollection> bookCollections = bookCollectionService.findBookByAuthorAndActive(author);
         return ResponseEntity.ok(bookCollections); // Retorna os livros encontrados
     }
}
