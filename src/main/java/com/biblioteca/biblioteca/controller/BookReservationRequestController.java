package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.BookReservationRequest;
import com.biblioteca.biblioteca.services.bookReservationRequest.BookReservationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservationRequests")
public class BookReservationRequestController {

    @Autowired
    private BookReservationRequestService bookReservationRequestService;

    @PostMapping
    public ResponseEntity<BookReservationRequest> createReservationRequest(@RequestParam Long customerId, @RequestParam Long bookId) {
        BookReservationRequest request = bookReservationRequestService.createReservationRequest(customerId, bookId);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<BookReservationRequest>> getRequestsByStatus(@PathVariable String status) {
        List<BookReservationRequest> requests = bookReservationRequestService.findRequestsByStatus(status);
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Boolean> updateRequestStatus(@PathVariable Long id, @RequestParam String status, @RequestParam Long approverId) {
        bookReservationRequestService.updateRequestStatus(id, status, approverId);
        return ResponseEntity.ok().build();
    }
}
