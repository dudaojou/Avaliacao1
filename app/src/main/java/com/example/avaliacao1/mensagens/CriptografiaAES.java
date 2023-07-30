package com.example.avaliacao1.mensagens;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CriptografiaAES {

    private static final String chave = "chave_secreta_para_AES";
    private static final String vetorInicializacao = "vetor_inicializacao"; // Deve ter 16 caracteres para AES-128

    public static String criptografar(String mensagem) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec chaveAES = new SecretKeySpec(chave.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec iv = new IvParameterSpec(vetorInicializacao.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, chaveAES, iv);

            byte[] mensagemCriptografada = cipher.doFinal(mensagem.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(mensagemCriptografada);
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

            byte[] mensagemDescriptografada = cipher.doFinal(Base64.getDecoder().decode(mensagemCriptografada));
            return new String(mensagemDescriptografada, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String mensagem = "Hello, world!";

        String mensagemCriptografada = criptografar(mensagem);
        System.out.println("Mensagem criptografada: " + mensagemCriptografada);

        String mensagemDescriptografada = descriptografar(mensagemCriptografada);
        System.out.println("Mensagem descriptografada: " + mensagemDescriptografada);
    }
}
