package com.biblioteca.biblioteca.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos representando os IDs das entidades relacionadas
    private Long idCliente;
    private Long idFuncionario;
    private Long idLivro;

    private String status;
    private LocalDate dataAprovacao;
    private LocalDate dataDevolucaoEstimada;

    // Construtores
    public Loan() {}

    public Loan(Long idCliente, Long idFuncionario, Long idLivro, String status, LocalDate dataAprovacao, LocalDate dataDevolucaoEstimada) {
        this.idCliente = idCliente;
        this.idFuncionario = idFuncionario;
        this.idLivro = idLivro;
        this.status = status;
        this.dataAprovacao = dataAprovacao;
        this.dataDevolucaoEstimada = dataDevolucaoEstimada;
    }

    // Getters e Setters para todos os campos
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

    public Long getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(Long idLivro) {
        this.idLivro = idLivro;
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

    public LocalDate getDataDevolucaoEstimada() {
        return dataDevolucaoEstimada;
    }

    public void setDataDevolucaoEstimada(LocalDate dataDevolucaoEstimada) {
        this.dataDevolucaoEstimada = dataDevolucaoEstimada;
    }
}
