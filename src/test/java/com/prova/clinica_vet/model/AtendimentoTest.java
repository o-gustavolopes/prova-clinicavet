package com.prova.clinica_vet.model;

import com.prova.clinica_vet.decorator.*;
import com.prova.clinica_vet.state.SituacaoEstadoAgendado;
import com.prova.clinica_vet.state.SituacaoEstadoEmAtendimento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.*;

class AtendimentoTest {

    private Atendimento atendimento;
    private Tutor tutor;
    private Animal animal;
    private ServicoVeterinario servico;

    @BeforeEach
    void setUp() {
        tutor = new Tutor();
        tutor.setNome("Maria Silva");
        tutor.setCpf("123.456.789-00");

        animal = new Animal();
        animal.setNome("Rex");

        servico = new ServicoVeterinario();
        servico.setNome("Consulta Clinica");
        servico.setValorBase(200.00);

        atendimento = new Atendimento();
        atendimento.setTutor(tutor);
        atendimento.setAnimal(animal);
        atendimento.setServicoVeterinario(servico);
        atendimento.setValorBase(servico.getValorBase());
        atendimento.setSituacaoEstado(SituacaoEstadoAgendado.getInstance());
    }

    @Nested
    @DisplayName("State: mudancas de situacao")
    class StateTests {

        @Test
        @DisplayName("Situacao inicial deve ser Agendado")
        void situacaoInicialAgendado() {
            assertEquals("Agendado", atendimento.getSituacaoEstado().getEstado());
        }

        @Test
        @DisplayName("Agendado -> Em atendimento: transicao valida")
        void agendadoParaEmAtendimento() {
            SituacaoEstadoAgendado estado = (SituacaoEstadoAgendado) atendimento.getSituacaoEstado();
            boolean resultado = estado.iniciarAtendimento(atendimento);

            assertTrue(resultado);
            assertEquals("Em atendimento", atendimento.getSituacaoEstado().getEstado());
        }

        @Test
        @DisplayName("Agendado -> Cancelado: transicao valida")
        void agendadoParaCancelado() {
            SituacaoEstadoAgendado estado = (SituacaoEstadoAgendado) atendimento.getSituacaoEstado();
            boolean resultado = estado.cancelar(atendimento);

            assertTrue(resultado);
            assertEquals("Cancelado", atendimento.getSituacaoEstado().getEstado());
        }

        @Test
        @DisplayName("Em atendimento -> Finalizado: transicao valida")
        void emAtendimentoParaFinalizado() {
            // Leva ate Em atendimento
            SituacaoEstadoAgendado agendado = (SituacaoEstadoAgendado) atendimento.getSituacaoEstado();
            agendado.iniciarAtendimento(atendimento);

            SituacaoEstadoEmAtendimento emAtendimento =
                    (SituacaoEstadoEmAtendimento) atendimento.getSituacaoEstado();
            boolean resultado = emAtendimento.finalizar(atendimento);

            assertTrue(resultado);
            assertEquals("Finalizado", atendimento.getSituacaoEstado().getEstado());
        }

        @Test
        @DisplayName("Agendado nao pode finalizar: retorna false")
        void agendadoNaoPodeFinalizar() {
            boolean resultado = atendimento.getSituacaoEstado().finalizado(atendimento);
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Finalizado nao pode ser cancelado: retorna false")
        void finalizadoNaoPodeCancelar() {
            // Avanca ate Finalizado
            SituacaoEstadoAgendado agendado = (SituacaoEstadoAgendado) atendimento.getSituacaoEstado();
            agendado.iniciarAtendimento(atendimento);
            SituacaoEstadoEmAtendimento emAt =
                    (SituacaoEstadoEmAtendimento) atendimento.getSituacaoEstado();
            emAt.finalizar(atendimento);

            // Tenta cancelar o estado Finalizado via metodo base (deve retornar false)
            boolean resultado = atendimento.getSituacaoEstado().cancelado(atendimento);
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Cancelado nao pode iniciar: retorna false")
        void canceladoNaoPodeIniciar() {
            SituacaoEstadoAgendado agendado = (SituacaoEstadoAgendado) atendimento.getSituacaoEstado();
            agendado.cancelar(atendimento);

            boolean resultado = atendimento.getSituacaoEstado().emAtendimento(atendimento);
            assertFalse(resultado);
        }
    }

    @Nested
    @DisplayName("Observer: notificacoes automaticas")
    class ObserverTests {

        @Test
        @DisplayName("Tutor recebe notificacao ao iniciar atendimento")
        void tutorNotificadoAoIniciar() {
            List<String> mensagensRecebidas = new ArrayList<>();

            Observer capturador = (obs, arg) -> mensagensRecebidas.add((String) arg);
            atendimento.addObserver(capturador);

            SituacaoEstadoAgendado agendado = (SituacaoEstadoAgendado) atendimento.getSituacaoEstado();
            agendado.iniciarAtendimento(atendimento);

            assertEquals(1, mensagensRecebidas.size());
            assertTrue(mensagensRecebidas.get(0).contains("Rex"));
            assertTrue(mensagensRecebidas.get(0).contains("Em atendimento"));
        }

        @Test
        @DisplayName("Observer notificado ao cancelar: mensagem contem Cancelado")
        void notificacaoCancelamento() {
            List<String> mensagens = new ArrayList<>();
            atendimento.addObserver((obs, arg) -> mensagens.add((String) arg));

            SituacaoEstadoAgendado agendado = (SituacaoEstadoAgendado) atendimento.getSituacaoEstado();
            agendado.cancelar(atendimento);

            assertFalse(mensagens.isEmpty());
            assertTrue(mensagens.get(0).contains("Cancelado"));
        }

        @Test
        @DisplayName("Observer notificado ao finalizar: mensagem contem Finalizado")
        void notificacaoFinalizacao() {
            List<String> mensagens = new ArrayList<>();
            atendimento.addObserver((obs, arg) -> mensagens.add((String) arg));

            SituacaoEstadoAgendado agendado = (SituacaoEstadoAgendado) atendimento.getSituacaoEstado();
            agendado.iniciarAtendimento(atendimento);
            SituacaoEstadoEmAtendimento emAt =
                    (SituacaoEstadoEmAtendimento) atendimento.getSituacaoEstado();
            emAt.finalizar(atendimento);

            assertEquals(2, mensagens.size());
            assertTrue(mensagens.get(1).contains("Finalizado"));
        }

        @Test
        @DisplayName("Multiplos observers sao todos notificados")
        void multiplosObservers() {
            List<String> log1 = new ArrayList<>();
            List<String> log2 = new ArrayList<>();

            atendimento.addObserver((obs, arg) -> log1.add((String) arg));
            atendimento.addObserver((obs, arg) -> log2.add((String) arg));

            SituacaoEstadoAgendado agendado = (SituacaoEstadoAgendado) atendimento.getSituacaoEstado();
            agendado.iniciarAtendimento(atendimento);

            assertEquals(1, log1.size());
            assertEquals(1, log2.size());
        }
    }

    @Nested
    @DisplayName("Decorator: calculo de valor")
    class DecoratorTests {

        private static final double BASE = 200.00;
        private static final double DELTA = 0.001;

        private CalculadorValor base() {
            return new ValorBase(BASE, "Consulta Clinica");
        }

        @Test
        @DisplayName("Valor base sem regras: R$ 200,00")
        void valorBaseSemRegras() {
            assertEquals(200.00, base().calcular(), DELTA);
        }

        @Test
        @DisplayName("Desconto adocao (-15%): R$ 170,00")
        void descontoAdocao() {
            CalculadorValor cv = new DescontoAdocao(base());
            assertEquals(170.00, cv.calcular(), DELTA);
        }

        @Test
        @DisplayName("Taxa domiciliar (+R$ 50): R$ 250,00")
        void taxaDomiciliar() {
            CalculadorValor cv = new TaxaAtendimentoDomiciliar(base());
            assertEquals(250.00, cv.calcular(), DELTA);
        }

        @Test
        @DisplayName("Banho pos-consulta (+R$ 80): R$ 280,00")
        void banhoPosConsulta() {
            CalculadorValor cv = new BanhoPosConsulta(base());
            assertEquals(280.00, cv.calcular(), DELTA);
        }

        @Test
        @DisplayName("Combinacao: adocao + domiciliar + banho = R$ 300,00")
        void combinacaoTresRegras() {
            // 200 * 0.85 = 170 | +50 = 220 | +80 = 300
            CalculadorValor cv =
                    new BanhoPosConsulta(
                            new TaxaAtendimentoDomiciliar(
                                    new DescontoAdocao(base())));

            assertEquals(300.00, cv.calcular(), DELTA);
        }

        @Test
        @DisplayName("Combinacao: domiciliar + banho (sem desconto) = R$ 330,00")
        void combinacaoDomiciliarBanho() {
            CalculadorValor cv =
                    new BanhoPosConsulta(
                            new TaxaAtendimentoDomiciliar(base()));

            assertEquals(330.00, cv.calcular(), DELTA);
        }

        @Test
        @DisplayName("Descricao contem nome do servico e regras aplicadas")
        void descricaoContemRegras() {
            CalculadorValor cv =
                    new BanhoPosConsulta(
                            new DescontoAdocao(base()));

            String desc = cv.descricao();
            assertTrue(desc.contains("Consulta Clinica"));
            assertTrue(desc.contains("Desconto adocao"));
            assertTrue(desc.contains("Banho pos-consulta"));
        }
    }
}