package br.com.eventos.controller;

import br.com.eventos.model.Ingresso;
import br.com.eventos.service.ValidacaoService;

public class ValidacaoController {

    private final ValidacaoService validacaoService = new ValidacaoService();


    public void validarIngresso(int Idingresso, String operador) {

        try {
            validacaoService.validarIngresso(Idingresso, operador);
            System.out.print("Ingresso validado com sucesso!");
        } catch (Exception e) {
            System.out.print("Erro ao validar o ingresso!" + e.getMessage());
        }
    }

    public void relatorioIngressosValidados(int idEvento) {
        try {
            System.out.println("Relatório de validações do evento: " + idEvento);
            validacaoService.relatorioIngressosValidados(idEvento);
        }catch (Exception e) {
            System.out.print("Erro ao emitir o relatorio!" + e.getMessage());
        }
    }
}
