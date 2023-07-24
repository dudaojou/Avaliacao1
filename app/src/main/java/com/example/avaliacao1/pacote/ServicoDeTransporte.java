package com.example.avaliacao1.pacote;

import java.util.ArrayList;

public class ServicoDeTransporte {
    private int idServico;
    private String dataInicio;
    private String dataFim;
    private ArrayList<String> itensServicos;
    private ArrayList<String> motoristas;

    public ServicoDeTransporte(int idServico, String dataInicio, String dataFim, ArrayList<String> itensServicos, ArrayList<String> motoristas) {
        this.idServico = idServico;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.itensServicos = itensServicos;
        this.motoristas = motoristas;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public ArrayList<String> getItensServicos() {
        return itensServicos;
    }

    public void setItensServicos(ArrayList<String> itensServicos) {
        this.itensServicos = itensServicos;
    }

    public ArrayList<String> getMotoristas() {
        return motoristas;
    }

    public void setMotoristas(ArrayList<String> motoristas) {
        this.motoristas = motoristas;
    }
}
