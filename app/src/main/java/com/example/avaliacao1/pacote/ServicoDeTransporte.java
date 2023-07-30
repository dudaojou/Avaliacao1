package com.example.avaliacao1.pacote;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class ServicoDeTransporte {
    private int idServico;
    private String dataInicio;
    private String dataFim;
    private List<String> itensServicos;
    private List<Caminhao> motoristas;

    public ServicoDeTransporte(int idServico, String dataInicio, String dataFim, ArrayList<String> itensServicos, ArrayList<Caminhao> motoristas) {
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
        return (ArrayList<String>) Collections.unmodifiableList(itensServicos);
    }

    public void setItensServicos(ArrayList<String> itensServicos) {
        this.itensServicos = itensServicos;
    }

    public ArrayList<Caminhao> getMotoristas() {

        return (ArrayList<Caminhao>) Collections.unmodifiableList(motoristas);
    }

    public void setMotoristas(ArrayList<Caminhao> motoristas) {
        this.motoristas = motoristas;
    }

}
