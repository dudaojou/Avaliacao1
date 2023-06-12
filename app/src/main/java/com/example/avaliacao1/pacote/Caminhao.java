package com.example.avaliacao1.pacote;

import android.content.Context;

public class Caminhao extends Thread{

    private double velocidade;
    private String nome;
    private Localizacao localizacao;
    private GPS gps;
    private Context context;
    private Localizacao locInicial;
    int conta = 0;

    public GPS getGps() {
        return gps;
    }

    public void setConta(int conta) {
        this.conta = conta;
    }

    public Caminhao(Context context) {
        this.context = context;
        gps = new GPS(context);
        localizacao = new Localizacao(0,0,0);

    }

    @Override
    public void run(){
        gps.inicializarGPS(this);
        gps.atualizacaoGPS(this);
    }

    public double getVelocidade() {
        return (velocidade * 3.6);
    }

    public void setVelocidade(double velocidade) {
        this.velocidade = velocidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Localizacao getLocInicial() {
        return locInicial;
    }

    public void setLocInicial(Localizacao locInicial) {
        this.locInicial = locInicial;
    }

}
