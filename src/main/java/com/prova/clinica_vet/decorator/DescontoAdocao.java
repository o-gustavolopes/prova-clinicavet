package com.prova.clinica_vet.decorator;

public class DescontoAdocao extends DecoradorValor {

    private static final double PERCENTUAL = 0.15;

    public DescontoAdocao(CalculadorValor calculador) {
        super(calculador);
    }

    @Override
    public double calcular() {
        return calculador.calcular() * (1 - PERCENTUAL);
    }

    @Override
    public String descricao() {
        return calculador.descricao()
                + "\n  - Desconto adocao (-15%): -R$ "
                + String.format("%.2f", calculador.calcular() * PERCENTUAL);
    }
}