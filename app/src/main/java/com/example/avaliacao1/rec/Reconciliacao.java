package com.example.avaliacao1.rec;

import android.util.Log;

import com.example.avaliacao1.pacote.DistanciaTempo;
import com.example.avaliacao1.pacote.Localizacao;

import java.util.ArrayList;

public class Reconciliacao extends Thread{

    private double[] y;
    private double[] v;
    private double[][] A;

    ArrayList<DistanciaTempo> reconciliacao;

    public Reconciliacao (ArrayList<DistanciaTempo> reconciliacao){
        this.reconciliacao = reconciliacao;
        y = new double[reconciliacao.size()];
        v = new double[reconciliacao.size()];
        A = new double[1][reconciliacao.size()];
    }

    public void setRec(){
        for (int i = 0; i < reconciliacao.size(); i++) {
            y[i] = reconciliacao.get(i).getTempo();
            v[i]= 0.0001;
            if(i == 0){
                A[0][i] = 1;
            }
            else {
                A[0][i] = -1;
            }

        }
        Reconciliation rec = new Reconciliation(y,v,A);
        ArrayList<Double> recPronto = new ArrayList<>();
        recPronto = rec.printMatrix(rec.getReconciledFlow());
        Log.d("rec resultado:",recPronto.toString());
    }

    @Override
    public void run(){
        setRec();
    }


}
