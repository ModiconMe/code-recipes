package ru.modiconme.recepies.crypto;

import lombok.SneakyThrows;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Используется для синхронного шифрования.
 */
public class Macs {

    public static void main(String[] args) {
        String hello = "Hello";
        String enc = enc(hello);
        String enc2 = enc(hello);
        System.out.println(enc.equals(enc2));
    }

    @SneakyThrows
    private static String enc(String data) {
        Mac mac = getHmacSHA256Instance();
        SecretKeySpec key = Keys.KeyGeneration.getDefaultSecretKey();
        mac.init(key);
        byte[] encBytes = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encBytes);
    }

    @SneakyThrows
    private static Mac getHmacSHA256Instance() {
        return Mac.getInstance("HmacSHA256");
    }
}
