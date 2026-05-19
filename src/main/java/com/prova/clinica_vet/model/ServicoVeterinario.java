package com.prova.clinica_vet.model;

public class ServicoVeterinario {

    private Long id;
    private String nome;
    private Double valorBase;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getValorBase() { return valorBase; }
    public void setValorBase(Double valorBase) { this.valorBase = valorBase; }
}