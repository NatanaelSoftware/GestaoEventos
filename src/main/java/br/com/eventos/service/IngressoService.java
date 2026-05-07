    package br.com.eventos.service;

    import br.com.eventos.model.*;
    import br.com.eventos.dao.*;
    import java.time.LocalDateTime;

    public class IngressoService {

        private IngressoDAO bancoIngressos = new IngressoDAO();
        private ValidacaoDAO bancoValidacoes = new ValidacaoDAO();
        private EventoDAO bancoEventos = new EventoDAO();

        // --- EMISSÃO DE INGRESSOS ---

        public void venderIngressoInteira(int idDoEvento, String nomeComprador) throws Exception {
            verificarSeTemVaga(idDoEvento);

            Evento evento = bancoEventos.buscarporId(idDoEvento);

            Ingresso ingresso = new IngressoInteira(
                    0,
                    evento,
                    nomeComprador
            );

            bancoIngressos.inserir(ingresso);
        }

        public void venderIngressoMeia(int idDoEvento, String nomeComprador, String comprovante) throws Exception {
            verificarSeTemVaga(idDoEvento);
            Evento evento = bancoEventos.buscarporId(idDoEvento);
            Ingresso ingresso = new IngressoMeia(0, evento, nomeComprador, comprovante);
            bancoIngressos.inserir(ingresso);
        }


        // --- CANCELAMENTO ---
        public void cancelarIngresso(int idDoIngresso) throws Exception {

            Ingresso ingresso = bancoIngressos.buscarPorId(idDoIngresso);

            if (ingresso == null) {
                throw new Exception("Ingresso não encontrado.");
            }

            if ("VALIDADO".equals(ingresso.getStatus())) {
                throw new Exception("Ingresso já validado não pode ser cancelado.");
            }

            if ("CANCELADO".equals(ingresso.getStatus())) {
                throw new Exception("Ingresso já está cancelado.");
            }

            bancoIngressos.mudarStatus(idDoIngresso, "CANCELADO");
        }

        // --- VALIDAÇÃO NA ENTRADA ---
        public void validarEntrada(int idDoIngresso, String operador) throws Exception {
            Ingresso ingresso = bancoIngressos.buscarPorId(idDoIngresso);

            if (ingresso == null) {
                throw new Exception("Ingresso não encontrado.");
            }

            String resultado;
            String motivo = "";

            if ("VALIDADO".equals(ingresso.getStatus())) {
                resultado = "RECUSADA";
                motivo = "Ingresso já utilizado.";
            } else if ("CANCELADO".equals(ingresso.getStatus())) {
                resultado = "RECUSADA";
                motivo = "Ingresso cancelado.";
            } else {
                resultado = "APROVADA";
                bancoIngressos.mudarStatus(idDoIngresso, "VALIDADO");
            }

            Validacao validacao = new Validacao(0, idDoIngresso, operador, resultado, LocalDateTime.now(), motivo);
            bancoValidacoes.inserir(validacao);
        }

        // --- RELATÓRIO ---
        public double calcularReceitaEvento(int idEvento) throws Exception {
            return bancoIngressos.somarDinheiroDasVendas(idEvento);
        }

        // --- REGRA DE CAPACIDADE ---
        private void verificarSeTemVaga(int idDoEvento) throws Exception {
            Evento evento = bancoEventos.buscarporId(idDoEvento);

            if (evento == null) {
                throw new Exception("Evento não encontrado.");
            }

            if ("CANCELADO".equals(evento.getStatus()) || "ENCERRADO".equals(evento.getStatus())) {
                throw new Exception("Não é possível emitir ingresso para um evento cancelado ou encerrado.");
            }

            int vendidos = bancoEventos.contarIngressosAtivos(idDoEvento);

            if (vendidos >= evento.getCapacidade()) {
                throw new Exception("Evento lotado.");
            }


        }

        public int relatorioIngressosVendidos(int idEvento) throws Exception {
           return bancoIngressos.contarIngressosVendidos(idEvento);
        }

    }