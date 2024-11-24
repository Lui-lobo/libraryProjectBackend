package com.biblioteca.biblioteca.repository;

import com.biblioteca.biblioteca.model.BookReservation;
import com.biblioteca.biblioteca.model.BookReservationRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {

    // Busca reservas pelo status
    List<BookReservation> findByStatus(String status);

    @Query("SELECT r FROM BookReservation r WHERE r.customerId = :customerId")
    List<BookReservation> findAllByCustomerId(Long customerId);

    @Query("SELECT r FROM BookReservation r WHERE r.customerId = :customerId AND r.status = :status")
    List<BookReservation> findAllByCustomerIdAndStatus(Long customerId, String status);

    Optional<BookReservation> findById(Long id);

    @Query("SELECT r FROM BookReservation r")
    List<BookReservation> findAllReservations();

    @Query("SELECT r FROM BookReservation r WHERE r.id = :reservationId")
    List<BookReservation> findReservationByIdAsList(@Param("reservationId") Long reservationId);

    @Query("SELECT r FROM BookReservation r WHERE r.bookId = :bookId")
    List<BookReservation> findReservationsByBookId(@Param("bookId") Long bookId);

    @Query("SELECT r FROM BookReservation r WHERE r.customerId = :customerId")
    List<BookReservation> findReservationsByCustomerId(@Param("customerId") Long customerId);
}
