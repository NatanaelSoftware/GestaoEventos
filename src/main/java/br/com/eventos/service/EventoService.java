/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package br.com.eventos.service;

import java.util.List;
import java.time.LocalDateTime;
import br.com.eventos.model.Evento;
import br.com.eventos.dao.EventoDAO;

public class EventoService {

    // Aqui usamos o DAO para conversar com o Banco de Dados
    private EventoDAO bancoDeDados = new EventoDAO();

    // 1. Cadastrar um novo evento
    public void cadastrarEvento(Evento novoEvento) throws Exception {
        // Verificamos se a data do evento é uma data compativel não permitindo criar um evento no passado.
        LocalDateTime agora = LocalDateTime.now();
        
        if (novoEvento.getData().isBefore(agora)) {
            throw new Exception("Erro 001: Você não pode criar um evento nesse dia, tente novamente com uma data posterior!");
        }

        // Se a data estiver ok, salvamos no banco
        bancoDeDados.inserir(novoEvento);
    }

    // 2. Atualizar dados de um evento que já existe
    public void atualizarEvento(Evento eventoEditado) throws Exception {
        // Primeiro, verificamos se esse evento realmente existe no banco
        Evento eventoAntigo = bancoDeDados.buscarPorId(eventoEditado.getId());
        
        if (eventoAntigo == null) {
            throw new Exception("Erro 002: Esse evento não existe no sistema.");
        }

        // Regra: Não podemos diminuir o tamanho do evento se já vendemos muitos ingressos
        int ingressosVendidos = bancoDeDados.contarIngressosAtivos(eventoEditado.getId());
        
        if (eventoEditado.getCapacidade() < ingressosVendidos) {
            throw new Exception("Erro 003: A nova capacidade é menor que a quantidade de ingressos já vendidos!");
        }

        // Se passou pelas validações, atualiza
        bancoDeDados.atualizar(eventoEditado);
    }

    // 3. Deletar um evento
    public void removerEvento(int idDoEvento) throws Exception {
        // Não podemos deletar um evento que já tem gente confirmada
        int ingressosVendidos = bancoDeDados.contarIngressosAtivos(idDoEvento);
        
        if (ingressosVendidos > 0) {
            throw new Exception("Erro 004: Não é possivel excluir evento, Ingressos já emitidos!");
        }

        bancoDeDados.deletar(idDoEvento);
    }

    // 4. Ver quantas vagas ainda restam
    public int calcularVagasRestantes(int idDoEvento) throws Exception {
        Evento evento = bancoDeDados.buscarPorId(idDoEvento);
        
        if (evento == null) {
            throw new Exception("Erro 005: Evento não encontrado.");
        }

        int vendidos = bancoDeDados.contarIngressosAtivos(idDoEvento);
        
        // Vagas totais menos o que já foi vendido
        int resultado = evento.getCapacidade() - vendidos;
        return resultado;
    }

    // 5. Métodos simples de busca
    public Evento buscarUm(int id) throws Exception {
        return bancoDeDados.buscarPorId(id);
    }

    public List<Evento> listarTudo() throws Exception {
        return bancoDeDados.listarTodos();
    }
}