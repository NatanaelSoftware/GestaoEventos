        package br.com.eventos.controller;

        import br.com.eventos.model.Evento;
        import br.com.eventos.service.EventoService;
        import br.com.eventos.service.IngressoService;

        import java.time.LocalDateTime;
        import java.time.format.DateTimeFormatter;
        import java.util.List;

        public class EventoController {

            private final EventoService eventoService = new EventoService();
            private final IngressoService ingressoService = new IngressoService();

            public void cadastrarEvento(String nome, String local, LocalDateTime data, int capacidade, double precoBase) {
                try {
                    Evento evento = new Evento(nome, data, local, 0, capacidade, precoBase, "ATIVO");
                    eventoService.cadastrarEvento(evento);

                } catch (IllegalArgumentException e) {
                    System.out.println("Erro ao cadastrar evento: " + e.getMessage());
                }catch  (Exception e) {
                    System.out.println("Erro inesperado ao cadastrar: " + e.getMessage());
                }
            }

            public List<Evento> listarEventos() throws Exception {
                List<Evento> eventos = eventoService.listarTudo();

                if (eventos.isEmpty()) {
                    System.out.println("Nenhum evento cadastrado.");
                } else {
                    for (Evento e : eventos) {
                        System.out.println("ID: " + e.getId());
                        System.out.println("Nome: " + e.getNome());
                        System.out.println("Local: " + e.getLocal());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        System.out.println("Data: " + e.getData().format(formatter));
                        System.out.println("Capacidade: " + e.getCapacidade());
                        System.out.println("Preço: R$" + String.format("%.2f", e.getValorBase()));
                        System.out.println("Status: " + e.getStatus());
                        System.out.println("-------------------------");
                    }
                }

                return eventos;
            }


            public Evento buscarEvento(int id) throws  Exception{
                Evento evento = eventoService.buscarUm(id);

                if (evento == null) {
                    System.out.println("Evento não encontrado: " + id);
                }

                return evento;
            }

            public void removerEvento(int id) {
                try {
                    eventoService.removerEvento(id);
                    System.out.println("Evento removido com sucesso!");
                } catch (Exception e) {
                    System.out.println("Erro ao remover: " + e.getMessage());
                }
            }

            public void relatorioReceita(int idEvento) {

                try {
                    double receita = ingressoService.calcularReceitaEvento(idEvento);
                    System.out.printf("Relatorio do evento: R$%.2f%n" , receita);
                } catch (Exception e) {
                    System.out.println("Erro ao gerar relatorio "+ e.getMessage());
                }
            }

            public void atualizarStatusEvento(int id, String novoStatus) {
                try {
                    Evento evento = eventoService.buscarUm(id);
                    evento.setStatus(novoStatus);
                    eventoService.atualizarEvento(evento);
                    System.out.println("Evento atualizado com sucesso!");
                }catch (Exception e) {
                    System.out.println("Erro ao atualizar evento!" + e.getMessage());
                }
            }

        }
