package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.Loan;
import com.biblioteca.biblioteca.services.loanService.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Endpoint para buscar empréstimos pelo status
    @GetMapping("/status/{status}")
    public List<Loan> getLoansByStatus(@PathVariable String status) {
        return loanService.findLoansByStatus(status);
    }

    // Endpoint para buscar empréstimos pelo ID do cliente
    @GetMapping("/client/{clientId}")
    public List<Loan> getLoansByClientId(@PathVariable Long clientId) {
        return loanService.findLoansByClientId(clientId);
    }

    // Endpoint para buscar empréstimos pelo ID do livro
    @GetMapping("/book/{bookId}")
    public List<Loan> getLoansByBookId(@PathVariable Long bookId) {
        return loanService.findLoansByBookId(bookId);
    }

    // Endpoint para buscar empréstimo por ID
    @GetMapping("/{loanId}")
    public Optional<Loan> getLoanById(@PathVariable Long loanId) {
        return loanService.findLoanById(loanId);
    }

    @GetMapping("listId/{loanId}")
    public ResponseEntity<List<Loan>> getLoanByIdAndReturnInList(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.findLoansByIdAndReturnInList(loanId));
    }

    // Endpoint para a devolução de um livro
    @PutMapping("/return/{loanId}")
    public String returnBook(@PathVariable Long loanId) {
        return loanService.returnBook(loanId);
    }

    // Endpoint para listar todos os empréstimos (acesso para administrador)
    @GetMapping("/all")
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    // Endpoint para listar todos os empréstimos de um usuário específico
    @GetMapping("/client/new/{customerId}")
    public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable Long customerId) {
        List<Loan> loans = loanService.getLoansByCustomerId(customerId);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/findByCustomerAndStatus")
    public ResponseEntity<List<Loan>> getLoansByCustomerIdAndStatus(
            @RequestParam Long customerId,
            @RequestParam String status) {
        List<Loan> loans = loanService.getLoansByCustomerIdAndStatus(customerId, status);
        return ResponseEntity.ok(loans);
    }
}
