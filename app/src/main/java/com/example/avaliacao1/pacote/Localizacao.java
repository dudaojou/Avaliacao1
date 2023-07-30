package com.example.avaliacao1.pacote;

public class Localizacao {

    private double log;
    private double lat;
    private long tempo;


    public Localizacao(double log, double lat, long tempo) {
        this.log = log;
        this.lat = lat;
        this.tempo = tempo;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public long getTempo() {
        return tempo;
    }

    public void setTempo(long tempo) {
        this.tempo = tempo;
    }

    @Override
    public String toString(){
        return Double.toString(getLog()) + "/" + Double.toString(getLat()) + "/" + Long.toString(getTempo());
    }
}
