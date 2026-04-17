package br.com.eventos.view;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import br.com.eventos.controller.EventoController;
import br.com.eventos.controller.IngressoController;
import br.com.eventos.controller.ValidacaoController;

public class Menu {

    private final Scanner scanner;
    private final EventoController eventoController;
    private final IngressoController ingressoController;
    private final ValidacaoController validacaoController;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.eventoController = new EventoController();
        this.ingressoController = new IngressoController();
        this.validacaoController = new ValidacaoController();
    }

    public void exibir() {
        int opcao;
        do {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA DE GESTÃO DE EVENTOS");
            System.out.println("========================================");
            System.out.println("1 - Cadastrar evento");
            System.out.println("2 - Listar eventos");
            System.out.println("3 - Emitir ingresso");
            System.out.println("4 - Cancelar ingresso");
            System.out.println("5 - Validar ingresso");
            System.out.println("6 - Relatórios");
            System.out.println("0 - Sair");
            System.out.println("========================================");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    cadastrarEvento();
                    break;
                case 2:
                    listarEventos();
                    break;
                case 3:
                    emitirIngresso();
                    break;
                case 4:
                    cancelarIngresso();
                    break;
                case 5:
                    validarIngresso();
                    break;
                case 6:
                    exibirRelatorios();
                    break;
                case 0:
                    System.out.println("Saindo do sistema... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }

    private void cadastrarEvento() {


        System.out.println("\n--- CADASTRAR EVENTO ---");

        System.out.print("Nome do evento: ");
        String nome = scanner.nextLine();

        System.out.print("Local: ");
        String local = scanner.nextLine();

        System.out.print("Data (dd/MM/yyyy): ");
        String dataTexto = scanner.nextLine();

        System.out.print("Horário HH:mm: ");
        String horatioTexto = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime data  = LocalDateTime.parse (dataTexto + " " + horatioTexto, formatter);

        System.out.print("Capacidade máxima: ");
        int capacidade = lerInteiro();

        System.out.print("Preço base do ingresso (R$): ");
        double precoBase = lerDouble();

        try {
            eventoController.cadastrarEvento(nome, local, data, capacidade, precoBase);
            System.out.println("Evento cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar evento: " + e.getMessage());
        }
    }

    private void listarEventos() {
        System.out.println("\n--- LISTA DE EVENTOS ---");
        try {
            eventoController.listarEventos();
        } catch (Exception e) {
            System.out.println("Erro ao listar eventos: " + e.getMessage());
        }
    }

    private void emitirIngresso() {
        System.out.println("\n--- EMITIR INGRESSO ---");

        System.out.print("ID do evento: ");
        int idEvento = lerInteiro();

        System.out.print("Nome do participante: ");
        String nomeParticipante = scanner.nextLine();

        System.out.println("Tipo de ingresso:");
        System.out.println("1 - Inteira");
        System.out.println("2 - Meia-entrada");
        System.out.print("Escolha: ");
        int tipo = lerInteiro();

        String comprovante = null;
        if (tipo == 2) {
            System.out.print("Informe o comprovante para meia-entrada: ");
            comprovante = scanner.nextLine();
        }

        try {
            String tipoIngresso = (tipo == 1) ? "INTEIRA" : "MEIA";
            ingressoController.emitirIngresso(idEvento, nomeParticipante, tipoIngresso, comprovante);
            System.out.println("Ingresso emitido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao emitir ingresso: " + e.getMessage());
        }
    }

    private void cancelarIngresso() {
        System.out.println("\n--- CANCELAR INGRESSO ---");

        System.out.print("ID do ingresso: ");
        int idIngresso = lerInteiro();

        try {
            ingressoController.cancelarIngresso(idIngresso);
            System.out.println("Ingresso cancelado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cancelar ingresso: " + e.getMessage());
        }
    }

    private void validarIngresso() {
        System.out.println("\n--- VALIDAR INGRESSO ---");

        System.out.print("ID do ingresso: ");
        int idIngresso = lerInteiro();

        try {
            validacaoController.validarIngresso(idIngresso);
            System.out.println("Ingresso validado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao validar ingresso: " + e.getMessage());
        }
    }

    private void exibirRelatorios() {
        int opcao;
        do {
            System.out.println("\n--- RELATÓRIOS ---");
            System.out.println("1 - Ingressos vendidos por evento");
            System.out.println("2 - Ingressos validados por evento");
            System.out.println("3 - Receita por evento");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    System.out.print("ID do evento: ");
                    int idEvento1 = lerInteiro();
                    try {
                        ingressoController.relatorioIngressosVendidos(idEvento1);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("ID do evento: ");
                    int idEvento2 = lerInteiro();
                    try {
                        validacaoController.relatorioIngressosValidados(idEvento2);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.print("ID do evento: ");
                    int idEvento3 = lerInteiro();
                    try {
                        eventoController.relatorioReceita(idEvento3);
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);
    }

    private int lerInteiro() {
        while (true) {
            try {
                String linha = scanner.nextLine().trim();
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido. Digite um número inteiro: ");
            }
        }
    }

    private double lerDouble() {
        while (true) {
            try {
                String linha = scanner.nextLine().trim();
                return Double.parseDouble(linha.replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido. Digite um número: ");
            }
        }
    }
}
