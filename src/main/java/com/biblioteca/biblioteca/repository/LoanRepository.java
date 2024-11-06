package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    // Buscar empréstimos pelo status
    List<Loan> findByStatus(String status);

    // Buscar empréstimos pelo ID do cliente
    List<Loan> findByIdCliente(Long clientId);

    // Buscar empréstimos pelo ID do livro
    List<Loan> findByIdLivro(Long bookId);
}
