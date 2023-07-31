package com.example.avaliacao1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.avaliacao1.mensagens.Criptografia;
import com.example.avaliacao1.pacote.Caminhao;
import com.example.avaliacao1.pacote.DistanciaTempo;
import com.example.avaliacao1.pacote.Localizacao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private FirebaseFirestore db;
    private Criptografia criptografia;
    private Caminhao caminhao;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("Veiculo1");

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
        db = FirebaseFirestore.getInstance();
        criptografia = new Criptografia(context);

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
                //Intent in = new Intent(MainActivity.this, MainActivity2.class);
                //startActivity(in);

                escreverDados("dados", "colecao", "documento");
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

    public void escreverDados(String textoCriptografado,String colection, String document) {
        Map<String, Object> user = new HashMap<>();

        user.put("PublicKey", criptografia.getPublicKeyString());
        //Log.d("PublicKey",criptografia.getPublicKeyString());
        user.put("mensagem", textoCriptografado);
        db.collection(colection)
                .document(document)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("valorReal", "DocumentSnapshot added successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("valorReal", "Error adding document", e);
                    }
                });
    }
    String aux = "";
    public String lerDados(String collectionName,String documentNames) {
        String documentName =documentNames;
        final String[] textoCriptografado = {""};

        db.collection(collectionName)
                .document(documentNames)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.w("valorReal", "Listen failed.", e);
                            return;
                        }

                        if (documentSnapshot != null && documentSnapshot.exists()) {

                            Log.d("valorReal", "Current data: " + documentSnapshot.getData());
                            Object criptografadosObj = documentSnapshot.get("mensagem");
                            if (criptografadosObj != null) {
                                aux =  criptografadosObj.toString();
                                //criptografia.descriptografarTexto(textoCriptografado[0]);
                                Log.d("valorRealdovalor", "Current data: " +aux);

                            } } else {
                            Log.d("valorReal", "Current data: null");
                        }
                    }
                });

        Log.d("valorRealdovalor1", "Current data: " + aux);
        String aux2 = aux;
        return aux2;
    }

     /*

    public void enviarDados(){
            criptografiaAES = new CriptografiaAES();
            String veiculo1 = criptografiaAES.criptografar(caminhao.getLocalizacao().toString());
            db.collection("v1").add(veiculo1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d("firestore","DocumentSnapshot added with ID: " + documentReference.getId());
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w("firestore", "Error adding document", e);
                }
            });
    }

    public void lerDados(){
        db.collection("v1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("firestore", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.w("firestore", "Error getting documents.", task.getException());
                }
            }
        });
    }*/
}