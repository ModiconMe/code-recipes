package edu.modiconme.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.util.Base64;

/**
 * Используется для синхронного шифрования.
 * Альтернатива - класс Cipher
 */
public class MacUtils {

    public static String enc(String data, SecretKey key, MacAlgorithm algorithm) throws Exception {
        Mac mac = Mac.getInstance(algorithm.algorithm);
        mac.init(key);
        byte[] encBytes = mac.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encBytes);
    }

    @Getter
    @RequiredArgsConstructor
    public enum MacAlgorithm {

        HMAC_SHA_256("HmacSHA256"),
        HMAC_SHA_384("HmacSHA384"),
        HMAC_SHA_512("HmacSHA512"),
        ;

        private final String algorithm;
    }

}