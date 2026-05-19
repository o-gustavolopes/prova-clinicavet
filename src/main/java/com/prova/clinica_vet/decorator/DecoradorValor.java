package com.prova.clinica_vet.decorator;

public abstract class DecoradorValor implements CalculadorValor {

    protected final CalculadorValor calculador;

    protected DecoradorValor(CalculadorValor calculador) {
        this.calculador = calculador;
    }

    @Override
    public double calcular() {
        return calculador.calcular();
    }

    @Override
    public String descricao() {
        return calculador.descricao();
    }
}