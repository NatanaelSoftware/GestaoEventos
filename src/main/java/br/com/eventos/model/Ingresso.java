package br.com.eventos.model;

import java.time.LocalDateTime;

public abstract class Ingresso {

    private int id;
    private Evento evento; // Associação com a classe que você criou
    private String tipo;
    private String compradorNome;
    private String status;
    private LocalDateTime dataEmissao;
    private double precoPago;

    public Ingresso(int id, Evento evento, String tipo, String compradorNome, String status, LocalDateTime dataEmissao, double precoPago) {
        // Validações de integridade
        if (evento == null) {
            throw new IllegalArgumentException("Todo ingresso deve estar vinculado a um evento.");
        }
        if (compradorNome == null || compradorNome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do comprador é obrigatório.");
        }

        this.id = id;
        this.evento = evento;
        this.tipo = tipo;
        this.compradorNome = compradorNome;
        this.status = (status == null) ? "EMITIDO" : status;
        this.dataEmissao = (dataEmissao == null) ? LocalDateTime.now() : dataEmissao;
        this.precoPago = precoPago;
    }

    // Método abstrato: cada filho (Meia/Inteira) decide como calcula
    public abstract double calcularPreco();

    public String validarEntrada() {
        if ("VALIDADO".equalsIgnoreCase(this.status)) {
            return "Atenção: Este ingresso já foi validado anteriormente!";
        }
        if ("CANCELADO".equalsIgnoreCase(this.status)) {
            return "Erro: Este ingresso foi cancelado e não permite entrada.";
        }

        this.status = "VALIDADO";
        return "Sucesso: Entrada permitida!";
    }

    // Getters (
    public int getId() { return id; }
    public Evento getEvento() { return evento; }
    public String getTipo() { return tipo; }
    public String getCompradorNome() { return compradorNome; }
    public String getStatus() { return status; }
    public LocalDateTime getDataEmissao() { return dataEmissao; }
    public double getPrecoPago() { return precoPago; }

    // Setters
    public void setStatus(String status) { this.status = status; }
}