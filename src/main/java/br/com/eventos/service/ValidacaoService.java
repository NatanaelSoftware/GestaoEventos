package br.com.eventos.service;

import br.com.eventos.dao.ValidacaoDAO;
import br.com.eventos.model.Validacao;

import java.util.List;

public class ValidacaoService {
    private IngressoService ingressoService = new IngressoService();
    private ValidacaoDAO validacaoDAO = new ValidacaoDAO();



    public void validarIngresso (int idIngresso, String operador) {
        try {
            ingressoService.validarEntrada(idIngresso, operador);
        }catch (Exception e) {
            System.out.println("Erro ao validar o ingresso!" + e.getMessage());
        }
    }

    public void relatorioIngressosValidados (int idEvento) {
        try {
            List<Validacao> lista = validacaoDAO.listarPorEvento(idEvento);

            if (lista.isEmpty()) {
                System.out.println("Nenhuma validação encontrada para esse evento.");
                return;
            }

            int aprovadas = 0;
            int recusadas = 0;

            for (Validacao v : lista) {
                if ("APROVADA".equals(v.getResultado())) {
                    aprovadas++;
                } else if ("RECUSADA".equals(v.getResultado())) {
                    recusadas++;
                }
            }

            System.out.println("Total de validações: " + lista.size());
            System.out.println("Aprovadas: " + aprovadas);
            System.out.println("Recusadas: " + recusadas);

        } catch (Exception e) {
            System.out.println("Erro ao gerar o relatorio de validações! " + e.getMessage());
        }
    }


}
