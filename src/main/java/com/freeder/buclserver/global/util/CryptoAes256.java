package com.freeder.buclserver.global.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class CryptoAes256 {
    @Value("${crypto.secretkey}")
    private String sKey;
    @Value("${crypto.iv}")
    private String ivKey;



    public String encrypt(String sPlaintext) throws Exception {
        byte[] plaintext = sPlaintext.getBytes();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), getIvSpec());

        byte[] ciphertext = cipher.doFinal(plaintext);
        return Base64.getEncoder().encodeToString(ciphertext);
    }

    public String decrypt(String encryptedText) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), getIvSpec());

        byte[] ciphertext = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(ciphertext, StandardCharsets.UTF_8);
    }

    /**************************************************************************************/

    private SecretKey getSecretKey(){
        byte[] key = sKey.getBytes();
        return new SecretKeySpec(key, "AES");
    }

    private IvParameterSpec getIvSpec(){
        byte[] iv =ivKey.getBytes();
        return new IvParameterSpec(iv);
    }
}
