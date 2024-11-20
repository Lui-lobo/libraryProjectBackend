package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.BookReservation;
import com.biblioteca.biblioteca.model.BookReservationRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {

    // Busca reservas pelo status
    List<BookReservation> findByStatus(String status);
}
