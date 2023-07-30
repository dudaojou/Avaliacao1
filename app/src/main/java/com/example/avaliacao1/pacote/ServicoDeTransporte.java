package com.example.avaliacao1.pacote;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class ServicoDeTransporte {
    private int idServico;
    private String dataInicio;
    private String dataFim;
    private List<String> itensServicos;
    private List<String> motoristas;

    public ServicoDeTransporte(int idServico, String dataInicio, String dataFim, ArrayList<String> itensServicos, ArrayList<String> motoristas) {
        this.idServico = idServico;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        itensServicos = new ArrayList<>();
        motoristas = new ArrayList<>();
        this.itensServicos = itensServicos;
        this.motoristas = motoristas;
    }

    public ServicoDeTransporte() {
        this.idServico = 123;
        this.dataInicio = "31/07/2023";
        this.dataFim = "31/07/2023";
        itensServicos = new ArrayList<>();
        itensServicos.add("cama");
        itensServicos.add("box cama");

        motoristas = new ArrayList<>();
        motoristas.add("motorista 1");
        motoristas.add("motorista 2");
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

    public List<String> getItensServicos() {
        return itensServicos;
    }

    public void setItensServicos(ArrayList<String> itensServicos) {
        this.itensServicos = itensServicos;
    }

    public List<String> getMotoristas() {

        return motoristas;
    }

    public void setMotoristas(ArrayList<String> motoristas) {
        this.motoristas = motoristas;
    }

}
