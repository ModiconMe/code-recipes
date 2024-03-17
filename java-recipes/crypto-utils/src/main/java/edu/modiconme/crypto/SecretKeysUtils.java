package edu.modiconme.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@UtilityClass
public class SecretKeysUtils {

    /**
     * Простая генерация ключа
     */
    public static SecretKey genSecretKey(KeyGeneratorAlgorithm algorithm) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm.algorithm);
        keyGenerator.init(256, new SecureRandom());
        return keyGenerator.generateKey();
    }

    /**
     * https://docs.oracle.com/en/java/javase/20/docs/specs/security/standard-names.html#keygenerator-algorithms
     */
    @Getter
    @RequiredArgsConstructor
    public enum KeyGeneratorAlgorithm {
        AES("AES"),
        HMAC_SHA_256("HmacSHA256"),
        ;

        private final String algorithm;
    }

    /**
     * Продвинутая генерация ключа, настраивается соль и пароль
     */
    public static SecretKeySpec genDefaultSecretKey() throws Exception {
        return genSecretKey("1234", "salt", 256, SecretKeyAlgorithm.PBKDF2_WITH_HMAC_SHA_256);
    }

    public static SecretKeySpec genSecretKey(
            String password,
            String salt,
            int keySize,
            SecretKeyAlgorithm algorithm) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm.algorithm);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 10_000, keySize);
        SecretKey temp = factory.generateSecret(spec);
        return new SecretKeySpec(temp.getEncoded(), algorithm.algorithm);
    }

    /**
     * https://docs.oracle.com/en/java/javase/20/docs/specs/security/standard-names.html#keygenerator-algorithms
     */
    @Getter
    @RequiredArgsConstructor
    public enum SecretKeyAlgorithm {
        AES("AES"),
        PBKDF2_WITH_HMAC_SHA_256("PBKDF2WithHmacSHA256"),
        ;

        private final String algorithm;
    }

}
