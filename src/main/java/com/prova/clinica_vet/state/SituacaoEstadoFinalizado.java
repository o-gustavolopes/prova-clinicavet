package com.prova.clinica_vet.state;

public class SituacaoEstadoFinalizado extends SituacaoEstado{

    private SituacaoEstadoFinalizado() {};
    private static SituacaoEstadoFinalizado instance = new SituacaoEstadoFinalizado();
    public static SituacaoEstadoFinalizado getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Finalizado";
    }
}
