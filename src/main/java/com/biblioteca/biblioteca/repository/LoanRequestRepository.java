package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
    List<LoanRequest> findByIdCliente(Long idCliente);

    List<LoanRequest> findByStatus(String status); // Método para buscar solicitações por status

    Optional<LoanRequest> findById(Long loanRequestId);

    @Query("SELECT lr FROM LoanRequest lr WHERE lr.idCliente = :idCliente AND lr.status = :status")
    List<LoanRequest> findByCustomerIdAndStatus(Long idCliente, String status);
}
