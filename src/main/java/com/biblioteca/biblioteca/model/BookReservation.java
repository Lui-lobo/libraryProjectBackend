package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class BookReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente", nullable = false)
    private Long customerId;

    @Column(name = "id_funcionario", nullable = false)
    private Long employeeId;

    @Column(name = "id_book", nullable = false)
    private Long bookId;

    private String status; // "reservado", "cancelado", "convertido_para_emprestimo"
    private LocalDate dataAprovacao;
    private LocalDate dataExpiracao;

    // Construtores, getters e setters
    public BookReservation() {}

    public BookReservation(Long customerId, Long employeeId, Long bookId, String status, LocalDate dataAprovacao, LocalDate dataExpiracao) {
        this.customerId = customerId;
        this.employeeId = employeeId;
        this.bookId = bookId;
        this.status = status;
        this.dataAprovacao = dataAprovacao;
        this.dataExpiracao = dataExpiracao;
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
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

    public LocalDate getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(LocalDate dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public LocalDate getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(LocalDate dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }
}
