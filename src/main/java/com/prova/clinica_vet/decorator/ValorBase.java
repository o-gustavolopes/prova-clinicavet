package com.prova.clinica_vet.decorator;

public class ValorBase implements CalculadorValor {

    private final double valor;
    private final String nomeServico;

    public ValorBase(double valor, String nomeServico) {
        this.valor = valor;
        this.nomeServico = nomeServico;
    }

    @Override
    public double calcular() {
        return valor;
    }

    @Override
    public String descricao() {
        return nomeServico + " (base: R$ " + String.format("%.2f", valor) + ")";
    }
}