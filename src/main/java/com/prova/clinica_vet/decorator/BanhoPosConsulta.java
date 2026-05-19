package com.prova.clinica_vet.decorator;

public class BanhoPosConsulta extends DecoradorValor {

    private static final double VALOR_BANHO = 80.00;

    public BanhoPosConsulta(CalculadorValor calculador) {
        super(calculador);
    }

    @Override
    public double calcular() {
        return calculador.calcular() + VALOR_BANHO;
    }

    @Override
    public String descricao() {
        return calculador.descricao()
                + "\n  + Banho pos-consulta: +R$ "
                + String.format("%.2f", VALOR_BANHO);
    }
}