package br.com.eventos.model;

import java.time.LocalDateTime;

public class IngressoInteira extends Ingresso {

    public IngressoInteira(int id, Evento evento, String compradorNome) {
        super(id, evento, "INTEIRA", compradorNome, "EMITIDO", LocalDateTime.now(), evento.getValorBase());
    }

    @Override
    public double calcularPreco() {

        return getEvento().getValorBase();
    }

    @Override
    public String validarEntrada() {
        System.out.println("Validando ingresso inteira para o evento: " + getEvento().getNome());
        return super.validarEntrada();
    }
}