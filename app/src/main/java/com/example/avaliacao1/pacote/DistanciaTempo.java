package com.example.avaliacao1.pacote;

public class DistanciaTempo {
    private double distancia;

    private double tempo;

    public DistanciaTempo(double distancia, double tempo) {
        this.distancia = distancia;
        this.tempo = tempo;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public double getTempo() {
        return tempo;
    }

    public void setTempo(double tempo) {
        this.tempo = tempo;
    }
}
