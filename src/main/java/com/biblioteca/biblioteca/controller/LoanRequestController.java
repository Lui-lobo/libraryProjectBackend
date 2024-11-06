package com.biblioteca.biblioteca.controller;

import com.biblioteca.biblioteca.model.LoanRequest;
import com.biblioteca.biblioteca.services.loanRequest.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loanRequests")
public class LoanRequestController {

    @Autowired
    private LoanRequestService loanRequestService;

    @PostMapping("/create")
    public ResponseEntity<LoanRequest> createLoanRequest(@RequestParam Long idCliente, @RequestParam Long idLivro) {
        LoanRequest loanRequest = loanRequestService.createLoanRequest(idCliente, idLivro);
        return ResponseEntity.ok(loanRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LoanRequest>> getAllLoanRequests() {
        List<LoanRequest> loanRequests = loanRequestService.listLoanRequests();
        return ResponseEntity.ok(loanRequests);
    }

    @GetMapping("/status")
    public List<LoanRequest> getLoanRequestsByStatus(@RequestParam String status) {
        return loanRequestService.getLoanRequestsByStatus(status);
    }

    @GetMapping("/client/{idCliente}")
    public ResponseEntity<List<LoanRequest>> getLoanRequestsByClientId(@PathVariable Long idCliente) {
        List<LoanRequest> loanRequests = loanRequestService.findLoanRequestsByClientId(idCliente);
        return ResponseEntity.ok(loanRequests);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanRequest> updateLoanRequest(
            @PathVariable Long id,
            @RequestParam Long idFuncionario,
            @RequestParam String status) {
        LoanRequest updatedLoanRequest = loanRequestService.updateLoanRequest(id, idFuncionario, status);
        if (updatedLoanRequest != null) {
            return ResponseEntity.ok(updatedLoanRequest);
        }
        return ResponseEntity.notFound().build();
    }
}
