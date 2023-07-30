package com.example.avaliacao1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.avaliacao1.mensagens.CriptografiaAES;
import com.example.avaliacao1.pacote.Caminhao;
import com.example.avaliacao1.pacote.DistanciaTempo;
import com.example.avaliacao1.pacote.Localizacao;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView textViewLong;
    private TextView textViewLat;
    private TextView textViewDisPer;
    private TextView textViewTempoDesl;
    private TextView textViewTemPerc;
    private TextView textViewConsumoCom;
    private TextView textViewVelocidade;
    private TextView textViewVelEsperada;
    private Button buttonIniciar;
    private Button buttonComecar;
    private Button buttonRelatorio;
    private double distTotal;
    private double tempoTotal;
    private double distPercorrida;
    private double tempoPercorrido;

    private double tempoInicial;

    private CriptografiaAES criptografiaAES;
    private Caminhao caminhao;
    private Caminhao caminhao2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = MainActivity.this;
        FirebaseApp.initializeApp(this);
        textViewLong = findViewById(R.id.id_Long);
        textViewLat = findViewById(R.id.id_Lat);
        textViewDisPer = findViewById(R.id.id_DisPer);
        textViewTempoDesl = findViewById(R.id.id_TempoDesl);
        textViewTemPerc = findViewById(R.id.id_TemPerc);
        textViewConsumoCom = findViewById(R.id.id_ConsumoCom);
        textViewVelocidade = findViewById(R.id.id_Velocidade);
        buttonIniciar = findViewById(R.id.id_Iniciar);
        buttonComecar = findViewById(R.id.id_Comecar);
        textViewVelEsperada = findViewById(R.id.id_VelEsperada);
        buttonRelatorio = findViewById(R.id.id_relatorio);

        buttonIniciar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context threadContext = MainActivity.this;
                caminhao= new Caminhao(context);
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

        buttonRelatorio.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(in);
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
                textViewConsumoCom.setText(String.valueOf(getConsumoComb(caminhao.getVelocidade())));
                textViewVelEsperada.setText(String.valueOf(caminhao.getVelocidade()));
                textViewTemPerc.setText(String.valueOf(tempoPercorrido));
                enviarDados();
                lerDados();
            }
        });
    }

    public void setDistTempo(ArrayList<DistanciaTempo> a1,boolean ok) {
        if(!ok) {
            distTotal = a1.get(0).getDistancia();
            tempoTotal = a1.get(0).getTempo();
        }
        else{
            distPercorrida = a1.get(0).getDistancia();
            tempoPercorrido = (System.currentTimeMillis() / 1000.0) - tempoInicial;
        }
    }
    public double getConsumoComb(double velocidade){
            double velocidadeAux = 0.0;

            if (velocidade <= 0) {
                velocidadeAux = 0.0;
            } else if (velocidade <= 60) {
                velocidadeAux = 0.1;
            } else if (velocidade <= 100) {
                velocidadeAux = 0.15;
            } else {
                velocidadeAux = 0.2;
            }
            double consumoCombustivel = velocidade * velocidadeAux;

            return consumoCombustivel;
    }

    public void enviarDados(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://avancada-3de05-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference = firebaseDatabase.getReference("Veiculo1");
        try {
            criptografiaAES = new CriptografiaAES();
            String veiculo1 = criptografiaAES.criptografar(caminhao.getLocalizacao().toString());
            databaseReference.setValue(veiculo1);
        }catch (Exception e){
            Log.d("FireBase", "Error: " + e.getMessage());
        }
    }

    public void lerDados(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://avancada-3de05-default-rtdb.firebaseio.com/");
        DatabaseReference databaseReference = firebaseDatabase.getReference("Veiculo2");

    }
}