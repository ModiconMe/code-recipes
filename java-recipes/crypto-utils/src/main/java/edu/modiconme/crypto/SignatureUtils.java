package edu.modiconme.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

/**
 * Цифровая подпись с помощью пары ключей.
 * Заменяет MessageDigest(хэширование) + Cipher(шифрование с помощью ключей)
 * Пример: хэш MassageDigest(SHA-256) + шифрование Cipher(RSA) ~ Signature(SHA256WithRSA)
 */
@UtilityClass
public class SignatureUtils {

    public static String signSha256(String data, PrivateKey privateKey,
                                    SignatureAlgorithm algorithm) throws Exception {
        Signature signature = Signature.getInstance(algorithm.algorithm);
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        byte[] digest = signature.sign();
        return Base64.getEncoder().encodeToString(digest);
    }

    public static boolean verifySha256(String rowData, String base64EncData,
                                       PublicKey publicKey, SignatureAlgorithm algorithm) throws Exception {
        Signature signature = Signature.getInstance(algorithm.algorithm);
        byte[] encData = Base64.getDecoder().decode(base64EncData);
        signature.initVerify(publicKey);
        signature.update(rowData.getBytes());
        return signature.verify(encData);
    }

    @Getter
    @RequiredArgsConstructor
    public enum SignatureAlgorithm {

        SHA_256_WITH_RSA("SHA256WithRSA"),
        SHA_384_WITH_RSA("SHA384WithRSA"),
        SHA_512_WITH_RSA("SHA512WithRSA"),
        ;

        private final String algorithm;
    }
}
