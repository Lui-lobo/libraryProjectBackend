package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

     @Query("SELECT l FROM Loan l") // Retorna todos os empréstimos
    List<Loan> findAllLoans();

    @Query("SELECT l FROM Loan l WHERE l.idCliente = :customerId") // Retorna os empréstimos de um usuário
    List<Loan> findByCustomerId(Long customerId);

    @Query("SELECT l FROM Loan l WHERE l.idCliente = :customerId AND l.status = :status")
    List<Loan> findByCustomerIdAndStatus(Long customerId, String status);

    @Query("SELECT l FROM Loan l WHERE l.id = :loanId") // Retorna os empréstimos de um usuário
    List<Loan> findByIdAndReturnInList(Long loanId);
}
