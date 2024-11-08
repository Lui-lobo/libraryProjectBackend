package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.BookReservation;
import com.biblioteca.biblioteca.services.bookReservation.BookReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class BookReservationController {

    @Autowired
    private BookReservationService bookReservationService;

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
}
