package com.example.avaliacao1.mensagens;

import com.example.avaliacao1.pacote.Localizacao;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class CommunicationManager {
    private static final String AES = "AES";
    private static final String KEY = "mySecretKey";

    public static String encrypt(String message) throws Exception {
        Key key = new SecretKeySpec(KEY.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedMessage) throws Exception {
        Key key = new SecretKeySpec(KEY.getBytes(), AES);
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static void sendEncryptedMessage(String message) throws Exception {
        // Aqui você implementaria a lógica para enviar a mensagem criptografada para o outro aplicativo
    }

    public static String receiveEncryptedMessage() throws Exception {
        // Aqui você implementaria a lógica para receber a mensagem criptografada do outro aplicativo
        return null;
    }

    public static void main(String[] args) throws Exception {

        LocationMessage location = new LocationMessage("",new Localizacao(0,0,0));

        String json = location.toJson();
        String encryptedJson = encrypt(json);

        // Simulando o envio da mensagem criptografada
        sendEncryptedMessage(encryptedJson);

        // Simulando a recepção da mensagem criptografada
        String receivedEncryptedJson = receiveEncryptedMessage();
        String decryptedJson = decrypt(receivedEncryptedJson);

        LocationMessage receivedLocation = LocationMessage.fromJson(decryptedJson);

        System.out.println("Device ID: " + receivedLocation.getDeviceId());
        System.out.println("Latitude: " + receivedLocation.getLocalizacao().getLat());
        System.out.println("Longitude: " + receivedLocation.getLocalizacao().getLog());
    }
}

