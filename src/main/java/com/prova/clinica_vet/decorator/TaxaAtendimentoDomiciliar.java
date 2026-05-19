package com.prova.clinica_vet.decorator;

public class TaxaAtendimentoDomiciliar extends DecoradorValor {

    private static final double TAXA = 50.00;

    public TaxaAtendimentoDomiciliar(CalculadorValor calculador) {
        super(calculador);
    }

    @Override
    public double calcular() {
        return calculador.calcular() + TAXA;
    }

    @Override
    public String descricao() {
        return calculador.descricao()
                + "\n  + Taxa atendimento domiciliar: +R$ "
                + String.format("%.2f", TAXA);
    }
}