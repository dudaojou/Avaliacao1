package com.example.avaliacao1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.avaliacao1.pacote.ServicoDeTransporte;

public class MainActivity2 extends AppCompatActivity {

    private TextView textViewServico;
    private TextView textViewsDtInicio;
    private TextView textViewsDtFim;
    private TextView textViewsItemServico;
    private TextView textViewsMotorista;
    private Button buttonVoltar;
    private Button buttonRelatorio;

    private ServicoDeTransporte servicoDeTransporte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_relatorio);
        Context context = MainActivity2.this;
        textViewServico = findViewById(R.id.id_servico);
        textViewsDtInicio = findViewById(R.id.id_dtInicio);
        textViewsDtFim = findViewById(R.id.id_dtFim);
        textViewsItemServico = findViewById(R.id.id_itemServico);
        textViewsMotorista = findViewById(R.id.id_motorista);
        buttonVoltar = findViewById(R.id.id_voltar);
        buttonRelatorio = findViewById(R.id.id_CarregarRel);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(in);

            }
        });

        buttonRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servicoDeTransporte = new ServicoDeTransporte();
                atualizarTextos();

            }
        });

    }

    public void atualizarTextos() {

                textViewServico.setText(String.valueOf(servicoDeTransporte.getIdServico()));
                textViewsDtInicio.setText(String.valueOf(servicoDeTransporte.getDataInicio()));
                textViewsDtFim.setText(String.valueOf(servicoDeTransporte.getDataFim()));
                textViewsItemServico.setText(String.valueOf(servicoDeTransporte.getItensServicos()));
                textViewsMotorista.setText(String.valueOf(servicoDeTransporte.getMotoristas()));

    }
}