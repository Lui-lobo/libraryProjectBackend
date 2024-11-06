package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class LoanRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_funcionario")
    private Long idFuncionario; // Inicialmente será nulo

    @Column(name = "id_book")
    private Long idBook; // Novo campo para o ID do livro

    @Column(name = "status")
    private String status; // Pode ser "pendente", "aprovado" ou "reprovado"

    @Column(name = "data_solicitacao")
    private LocalDateTime dataSolicitacao;

    // Construtores, getters e setters

    public LoanRequest() {
        // Construtor padrão
    }

    public LoanRequest(Long idCliente, Long idBook) {
        this.idCliente = idCliente;
        this.status = "pendente"; // Status inicial
        this.dataSolicitacao = LocalDateTime.now(); // Data da solicitação
        this.idBook = idBook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Long idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public Long getIdBook() {
        return idBook;
    }

    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }
}
