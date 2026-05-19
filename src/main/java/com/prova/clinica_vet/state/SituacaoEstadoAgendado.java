package com.prova.clinica_vet.state;

import com.prova.clinica_vet.model.Atendimento;

public class SituacaoEstadoAgendado extends SituacaoEstado{

    private SituacaoEstadoAgendado() {};
    private static SituacaoEstadoAgendado instance = new SituacaoEstadoAgendado();
    public static SituacaoEstadoAgendado getInstance() {
        return instance;
    }

    @Override
    public String getEstado() {
        return "Agendado";
    }

    public boolean cancelar(Atendimento atendimento) {
        atendimento.alterarEstado(SituacaoEstadoCancelado.getInstance());
        return true;
    }

    public boolean iniciarAtendimento(Atendimento atendimento) {
        atendimento.alterarEstado(SituacaoEstadoEmAtendimento.getInstance());
        return true;
    }
}
