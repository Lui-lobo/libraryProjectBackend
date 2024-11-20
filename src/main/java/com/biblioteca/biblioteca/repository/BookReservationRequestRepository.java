package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.BookReservationRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReservationRequestRepository extends JpaRepository<BookReservationRequest, Long> {

    // Busca solicitações de reserva pelo status
    List<BookReservationRequest> findByStatus(String status);

    
    List<BookReservationRequest> findByCustomerId(Long customerId);

    @Query("SELECT r FROM BookReservationRequest r WHERE r.customerId = :customerId AND r.status = :status")
    List<BookReservationRequest> findByCustomerIdAndStatus(Long customerId, String status);

    @Query("SELECT r FROM BookReservationRequest r WHERE r.bookId = :bookId")
    List<BookReservationRequest> findByBookId(Long bookId);
}
