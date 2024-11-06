package com.biblioteca.biblioteca.services.loanService;

import com.biblioteca.biblioteca.model.Loan;
import com.biblioteca.biblioteca.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    // Buscar empréstimos pelo status
    public List<Loan> findLoansByStatus(String status) {
        return loanRepository.findByStatus(status);
    }

    // Buscar empréstimos pelo ID do cliente
    public List<Loan> findLoansByClientId(Long clientId) {
        return loanRepository.findByIdCliente(clientId);
    }

    // Buscar empréstimos pelo ID do livro
    public List<Loan> findLoansByBookId(Long bookId) {
        return loanRepository.findByIdLivro(bookId);
    }

    // Buscar empréstimo por ID
    public Optional<Loan> findLoanById(Long loanId) {
        return loanRepository.findById(loanId);
    }
}
