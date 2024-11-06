package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.LoanRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {
    List<LoanRequest> findByIdCliente(Long idCliente);

    List<LoanRequest> findByStatus(String status); // Método para buscar solicitações por status
}
