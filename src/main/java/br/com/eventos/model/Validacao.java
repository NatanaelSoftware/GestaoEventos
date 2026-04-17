package br.com.eventos.model;

import java.time.LocalDateTime;

public class Validacao {

    private int id;
    private int ingressosId;
    private String operador;
    private String resultado;
    private LocalDateTime dataHora;
    private String obs;


    public Validacao (int id, int ingressosId, String operador, String resultado, LocalDateTime dataHora, String obs) {

        this.id = id;
        this.ingressosId =  ingressosId;
        this.operador = operador;
        this.resultado = resultado;
        this.dataHora = dataHora;
        this.obs = obs;
    }


    //Getters
    public int getId() {return id;}

    public int getIngressosId() {return ingressosId;}

    public LocalDateTime getDataHora() {return dataHora;}

    public String getOperador() {return operador;}

    public String getResultado() {return resultado;}

    public String getObs() {return obs;}

    //Setters
    public void setId(int id) {this.id = id;}

    public void setIngressosId(int ingressosId) {this.ingressosId = ingressosId;}

    public void setOperador(String operador) {this.operador = operador;}

     public void setResultado(String resultado) {this.resultado = resultado;}

    public void setDataHora(LocalDateTime dataHora) {this.dataHora = dataHora;}

    public void setObs(String obs) {this.obs = obs;}
}