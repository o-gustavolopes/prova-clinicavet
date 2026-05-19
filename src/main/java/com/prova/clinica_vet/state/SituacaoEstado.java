package com.prova.clinica_vet.state;

import com.prova.clinica_vet.model.Atendimento;

public abstract class SituacaoEstado {

    public abstract String getEstado();

    public boolean agendado(Atendimento atendimento) {
        return false;
    }

    public boolean emAtendimento(Atendimento atendimento) {
        return false;
    }

    public boolean finalizado(Atendimento atendimento) {
        return false;
    }

    public boolean cancelado(Atendimento atendimento) {
        return false;
    }
}
