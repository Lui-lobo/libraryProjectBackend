package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class BookReservationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente", nullable = false)
    private Long customerId;

    @Column(name = "id_book", nullable = false)
    private Long bookId;

    private String status; // "pendente", "aprovado", "reprovado"
    private LocalDate dataSolicitacao;

    // Construtores, getters e setters
    public BookReservationRequest() {}

    public BookReservationRequest(Long customerId, Long bookId, String status, LocalDate dataSolicitacao) {
        this.customerId = customerId;
        this.bookId = bookId;
        this.status = status;
        this.dataSolicitacao = dataSolicitacao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }
}
