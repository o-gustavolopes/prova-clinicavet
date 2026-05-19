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
}
