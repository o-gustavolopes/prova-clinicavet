package com.prova.clinica_vet.decorator;

/**
 * Componente base do padrão Decorator.
 * Define o contrato para calcular o valor final de um atendimento,
 * permitindo que acréscimos e descontos sejam empilhados livremente.
 */
public interface CalculadorValor {

    double calcular();

    String descricao();
}