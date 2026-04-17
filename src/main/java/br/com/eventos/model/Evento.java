package br.com.eventos.model;

import java.time.LocalDateTime;

public class Evento {

    private String nome;
    private LocalDateTime data;
    private String local;
    private int id;
    private int capacidade;
    private double valorBase;
    private String status;



    public Evento(String nome, LocalDateTime data, String local, int id, int capacidade, double valorBase, String status) {
        // VALIDAÇÕES AQUI

        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do evento inválido");
        }

        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade deve ser maior que zero");
        }
        if (valorBase < 0) {
            throw new IllegalArgumentException("O valor base não pode ser negativo");
        }


        this.nome = nome;
        this.local = local;
        this.data = data;
        this.id = id;
        this.capacidade = capacidade;
        this.valorBase = valorBase;
        this.status = status;

    }

    // Getters
    public String getNome() {return nome;}

    public LocalDateTime getData() {return data;}

    public String getLocal() {return local;}

    public int getId() {return id;}

    public int getCapacidade() {return capacidade;}

    public double getValorBase() {return valorBase;}

    public String getStatus() {return status;}

    //Setters
    public void setNome(String nome) {this.nome = nome;}

    public void setLocal(String local) {this.local =  local;}

    public void setData(LocalDateTime data) {this.data =  data;}

    public void setId(int id) {this.id = id;}

    public void setCapacidade(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade inválida!");
        }
        this.capacidade = capacidade;
    }

    public void setValorBase(double valorBase) {this.valorBase =  valorBase;}

    public void setStatus(String status) {this.status =  status;}


}