package com.example.avaliacao1.mensagens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;



public class Criptografia {

    private static final String ALGORITHM = "RSA";
    private static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1;

    @SuppressLint("SdCardPath")
    private static final String PATH_CHAVE_PRIVADA ="/data/data/files/privates.key";

    @SuppressLint("SdCardPath")
    private static final String PATH_CHAVE_PUBLICA = "/data/data/files/publics.key";

    private ObjectInputStream inputStream = null;

    public Criptografia(Context context) {
        if (!verificaSeExisteChavesNoSO()) {
            geraChave(context);
        }
    }

    private String getDownloadsFolderPath() {
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        return path;
    }

    public static void geraChave(Context context) {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
            keyGen.initialize(2048); // Aumentado o tamanho da chave para 2048 bits
            final KeyPair key = keyGen.generateKeyPair();

            File chavePrivadaFile = new File(context.getFilesDir(), "privates.key");
            File chavePublicaFile = new File(context.getFilesDir(), "publics.key");

            // Salva a Chave Pública no arquivo
            try (ObjectOutputStream chavePublicaOS = new ObjectOutputStream(new FileOutputStream(chavePublicaFile))) {
                chavePublicaOS.writeObject(key.getPublic());
            }

            // Salva a Chave Privada no arquivo
            try (ObjectOutputStream chavePrivadaOS = new ObjectOutputStream(new FileOutputStream(chavePrivadaFile))) {
                chavePrivadaOS.writeObject(key.getPrivate());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean verificaSeExisteChavesNoSO() {

        File chavePrivada = new File(PATH_CHAVE_PRIVADA);
        File chavePublica = new File(PATH_CHAVE_PUBLICA);

        if (chavePrivada.exists() && chavePublica.exists()) {
            return true;
        }

        return false;
    }

    private static byte[] criptografa(String texto, PublicKey chave) {
        byte[] cipherText = null;

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // Criptografa o texto puro usando a chave Púlica
            cipher.init(Cipher.ENCRYPT_MODE, chave);
            cipherText = cipher.doFinal(texto.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cipherText;
    }

    private static String decriptografa(byte[] texto, PrivateKey chave) {
        byte[] dectyptedText = null;

        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM);
            // Decriptografa o texto puro usando a chave Privada
            cipher.init(Cipher.DECRYPT_MODE, chave);
            dectyptedText = cipher.doFinal(texto);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new String(dectyptedText);
    }

    public String criptografarTexto(String text,PublicKey chavePublica){
        try {
            // Criptografa a Mensagem usando a Chave Pública
            //inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
            //final PublicKey chavePublica = (PublicKey) inputStream.readObject();
            final byte[] textoCriptografado = criptografa(text, chavePublica);
            return convertByteArrayToString(textoCriptografado);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String descriptografarTexto(String textoCriptografado){
        try {
            Log.d("Entrei Na descriptografia", textoCriptografado);
            byte[] textoCriptografadoBytes = convertStringToByteArray(textoCriptografado);
            // Decriptografa a Mensagem usando a Chave Pirvada
            inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
            final PrivateKey chavePrivada = (PrivateKey) inputStream.readObject();
            final String textoPuro = decriptografa(textoCriptografadoBytes, chavePrivada);
            Log.d("textoPuroaquimeuirmaoaasd", textoPuro);
            return textoPuro;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String convertByteArrayToString(byte[] bytes){
        String s = "";
        for (byte b : bytes) {
            s += Byte.toString(b);
            s+= ";";
        }
        return s;
    }

    private byte[] convertStringToByteArray(String s){
        String[] array = s.split(";");
        byte[] bytes = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            bytes[i] = Byte.parseByte(array[i]);
        }
        return bytes;
    }

    public PrivateKey getPrivateKey() {
        try {
            // Read the Private Key from the file
            inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PRIVADA));
            PrivateKey privateKey = (PrivateKey) inputStream.readObject();
            //convert privateKey to String
            String s = "";


            return privateKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PublicKey getPublicKey() {
        try {
            // Read the Public Key from the file
            inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
            PublicKey publicKey = (PublicKey) inputStream.readObject();
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPublicKeyString() {
        try {
            // Read the Public Key from the file
            inputStream = new ObjectInputStream(new FileInputStream(PATH_CHAVE_PUBLICA));
            PublicKey publicKey = (PublicKey) inputStream.readObject();
            String publicKeyString = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            }

            return publicKeyString;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}