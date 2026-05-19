package com.prova.clinica_vet.model;

import java.util.Observable;
import java.util.Observer;

public class Tutor implements Observer {

    private Long id;
    private String nome;
    private String cpf;

    @Override
    public void update(Observable atendimento, Object arg) {
        String mensagem = (String) arg;
        System.out.println("Tutor " + nome + " recebeu notificacao: " + mensagem);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}