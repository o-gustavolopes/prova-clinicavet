package com.prova.clinica_vet.model;

import com.prova.clinica_vet.state.SituacaoEstado;

import java.util.Observable;

public class Atendimento extends Observable {

    private Tutor tutor;
    private Animal animal;
    private ServicoVeterinario servicoVeterinario;
    private Double valorBase;
    private SituacaoEstado situacaoEstado;

    public void alterarEstado(SituacaoEstado novoEstado) {
        this.situacaoEstado = novoEstado;

        setChanged();

        notifyObservers(
                "Atendimento do animal "
                + animal.getNome()
                + "alterado para "
                + novoEstado.getEstado()
        );
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public ServicoVeterinario getServicoVeterinario() {
        return servicoVeterinario;
    }

    public void setServicoVeterinario(ServicoVeterinario servicoVeterinario) {
        this.servicoVeterinario = servicoVeterinario;
    }

    public Double getValorBase() {
        return valorBase;
    }

    public void setValorBase(Double valorBase) {
        this.valorBase = valorBase;
    }

    public SituacaoEstado getSituacaoEstado() {
        return situacaoEstado;
    }

    public void setSituacaoEstado(SituacaoEstado situacaoEstado) {
        this.situacaoEstado = situacaoEstado;
    }
}
