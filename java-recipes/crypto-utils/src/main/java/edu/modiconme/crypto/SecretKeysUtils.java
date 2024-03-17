package edu.modiconme.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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
     * <a href="https://docs.oracle.com/en/java/javase/20/docs/specs/security/standard-names.html#keygenerator-algorithms">...</a>
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
    public static SecretKey genDefaultSecretKey() throws Exception {
        return genSecretKey("1234".toCharArray(), "salt".getBytes(), 256, SecretKeyAlgorithm.PBKDF2_WITH_HMAC_SHA_256);
    }

    public static SecretKey genSecretKey(
            char[] password,
            byte[] salt,
            int keySize,
            SecretKeyAlgorithm algorithm) throws Exception {
        return SecretKeyFactory.getInstance(algorithm.algorithm).
                generateSecret(new PBEKeySpec(password, salt, 10_000, keySize));
    }

    /**
     * <a href="https://docs.oracle.com/en/java/javase/20/docs/specs/security/standard-names.html#keygenerator-algorithms">...</a>
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
