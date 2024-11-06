package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.Loan;
import com.biblioteca.biblioteca.services.loanService.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
