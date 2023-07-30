package com.example.avaliacao1.mensagens;

import android.os.Build;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CriptografiaAES {

    private static final String chave = "chave";
    private static final String vetorInicializacao = "vetor_inicializacao"; // Deve ter 16 caracteres para AES-128

    public static String criptografar(String mensagem) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec chaveAES = new SecretKeySpec(chave.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec iv = new IvParameterSpec(vetorInicializacao.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, chaveAES, iv);

            byte[] mensagemCriptografada = cipher.doFinal(mensagem.getBytes(StandardCharsets.UTF_8));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Base64.getEncoder().encodeToString(mensagemCriptografada);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String descriptografar(String mensagemCriptografada) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec chaveAES = new SecretKeySpec(chave.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec iv = new IvParameterSpec(vetorInicializacao.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, chaveAES, iv);

            byte[] mensagemDescriptografada = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mensagemDescriptografada = cipher.doFinal(Base64.getDecoder().decode(mensagemCriptografada));
            }
            return new String(mensagemDescriptografada, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
