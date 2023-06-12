package com.example.avaliacao1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.avaliacao1.pacote.Caminhao;
import com.example.avaliacao1.pacote.DistanciaTempo;
import com.example.avaliacao1.pacote.Google;
import com.example.avaliacao1.pacote.Localizacao;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textViewLong;
    private TextView textViewLat;
    private TextView textViewDisPer;
    private TextView textViewTempoDesl;
    private TextView textViewConsumoCom;
    private TextView textViewVelocidade;
    private TextView textViewVelEsperada;
    private Button buttonIniciar;
    private Button buttonComecar;
    private double distTotal;
    private double tempoTotal;

    private double distPercorrida;
    private double tempoPercorrido;

    private double tempoInicial;


    private int cont;
    Caminhao caminhao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = MainActivity.this;
        cont = 0;
        textViewLong = findViewById(R.id.id_Long);
        textViewLat = findViewById(R.id.id_Lat);
        textViewDisPer = findViewById(R.id.id_DisPer);
        textViewTempoDesl = findViewById(R.id.id_TempoDesl);
        textViewConsumoCom = findViewById(R.id.id_ConsumoCom);
        textViewVelocidade = findViewById(R.id.id_Velocidade);
        buttonIniciar = findViewById(R.id.id_Iniciar);
        buttonComecar = findViewById(R.id.id_Comecar);
        textViewVelEsperada = findViewById(R.id.id_VelEsperada);

        buttonIniciar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context threadContext = MainActivity.this;
                caminhao= new Caminhao(context,cont);
                //caminhao.getLocalizacao().setTempo(System.currentTimeMillis() / 1000);
                tempoInicial = System.currentTimeMillis() / 1000.0;
                caminhao.start();

            }
        } );

        buttonComecar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(caminhao != null) {
                    double latAjuda = caminhao.getLocalizacao().getLat();
                    double longAjuda = caminhao.getLocalizacao().getLog();

                    caminhao.getLocalizacao().setTempo(System.currentTimeMillis() / 1000);
                    caminhao.setLocInicial(new Localizacao(longAjuda,latAjuda,0));
                    caminhao.getGps().setContaa(1);
                }
            }
        } );
    }

    public void atualizarTextos() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewLat.setText(String.valueOf(caminhao.getLocalizacao().getLat()));
                textViewLong.setText(String.valueOf(caminhao.getLocalizacao().getLog()));
                textViewVelocidade.setText(String.valueOf(caminhao.getVelocidade()));
                textViewTempoDesl.setText(String.valueOf(tempoTotal));
                textViewDisPer.setText(String.valueOf(distTotal));
                textViewConsumoCom.setText(String.valueOf(distPercorrida));
                textViewVelEsperada.setText(String.valueOf(tempoPercorrido));
            }
        });
    }

    public void setDistTempo(ArrayList<DistanciaTempo> a1,boolean ok) {
        if(!ok) {
            distTotal = a1.get(0).getDistancia();
            tempoTotal = a1.get(0).getTempo();
            Log.d("xaxx:", distTotal +" "+ tempoTotal );
        }
        else{
            distPercorrida = a1.get(0).getDistancia();
            tempoPercorrido = (System.currentTimeMillis() / 1000.0) - tempoInicial;
            Log.d("xbxx:", distPercorrida +" "+ tempoPercorrido );
        }

    }
}