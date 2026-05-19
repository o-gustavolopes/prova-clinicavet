package com.prova.clinica_vet.state;

import com.prova.clinica_vet.model.Atendimento;

public class SituacaoEstadoEmAtendimento extends SituacaoEstado{

    private SituacaoEstadoEmAtendimento() {};
    private static SituacaoEstadoEmAtendimento instance = new SituacaoEstadoEmAtendimento();
    public static SituacaoEstadoEmAtendimento getInstance() {
        return instance;
    }

    public boolean finalizar(Atendimento atendimento) {
        atendimento.alterarEstado(SituacaoEstadoFinalizado.getInstance());
        return true;
    }

    @Override
    public String getEstado() {
        return "Em atendimento";
    }
}
