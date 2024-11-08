package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.BookReservationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReservationRequestRepository extends JpaRepository<BookReservationRequest, Long> {

    // Busca solicitações de reserva pelo status
    List<BookReservationRequest> findByStatus(String status);
}
