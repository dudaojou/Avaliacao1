package com.example.avaliacao1.pacote;

import android.content.Context;

public class Caminhao extends Thread{

    private int cont;
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

    public Caminhao(Context context, int cont) {
        this.context = context;
        gps = new GPS(context);
        localizacao = new Localizacao(0,0,0);
        this.cont = cont;
    }

    @Override
    public void run(){
        gps.inicializarGPS(this, cont);
        gps.atualizacaoGPS(this,cont);
    }

    public double getVelocidade() {
        return velocidade;
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

    public void setCont(int cont) {
        this.cont = cont;
    }
}
