package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.BookReservation;
import com.biblioteca.biblioteca.model.BookReservationRequest;
import com.biblioteca.biblioteca.repository.BookReservationRequestRepository;
import com.biblioteca.biblioteca.services.bookReservation.BookReservationService;
import com.biblioteca.biblioteca.services.bookReservationRequest.BookReservationRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class BookReservationController {

    @Autowired
    private BookReservationService bookReservationService;

    @Autowired
    private BookReservationRequestRepository bookReservationRequestRepository;

    
    @Autowired
    private BookReservationRequestService bookReservationRequestService;

    @PostMapping("/approve")
    public ResponseEntity<BookReservation> approveReservation(@RequestParam Long requestId, @RequestParam Long employeeId) {
        BookReservation reservation = bookReservationService.approveReservation(requestId, employeeId);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookReservation>> getReservationsByStatus(@PathVariable String status) {
        List<BookReservation> reservations = bookReservationService.findReservationsByStatus(status);
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
        bookReservationService.cancelReservation(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<BookReservationRequest>> getReservationsByClient(@PathVariable Long clientId) {
        List<BookReservationRequest> reservations = bookReservationRequestRepository.findByCustomerId(clientId);

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/client/{clientId}/status")
    public ResponseEntity<List<BookReservationRequest>> getReservationsByClientAndStatus(
            @PathVariable Long clientId,
            @RequestParam String status) {

        List<BookReservationRequest> reservations = bookReservationRequestRepository.findByCustomerIdAndStatus(clientId, status);

        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<BookReservationRequest> getReservationById(@PathVariable Long reservationId) {
        BookReservationRequest reservation = bookReservationRequestRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        return ResponseEntity.ok(reservation);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelReservationRequest(@PathVariable Long id) {
        try {
            bookReservationRequestService.cancelReservationRequest(id);
            return ResponseEntity.ok("Solicitação de reserva cancelada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/reservationRequests/all")
    public ResponseEntity<List<BookReservationRequest>> getAllReservationRequests() {
        List<BookReservationRequest> reservationRequests = bookReservationRequestService.findAllReservationRequests();
        return ResponseEntity.ok(reservationRequests);
    }
}
