package com.prova.clinica_vet.state;

public class SituacaoEstadoCancelado extends SituacaoEstado{

    private SituacaoEstadoCancelado() {};
    private static SituacaoEstadoCancelado instance = new SituacaoEstadoCancelado();
    public static SituacaoEstadoCancelado getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Cancelado";
    }
}
