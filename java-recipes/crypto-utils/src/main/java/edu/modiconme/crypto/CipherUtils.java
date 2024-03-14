package edu.modiconme.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

import static edu.modiconme.crypto.CipherAlgorithms.AES_CBC_PKCS5PADDING;
import static edu.modiconme.crypto.CipherAlgorithms.AES_GCM_NO_PADDING;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * Класс Java Cipher (javax.crypto.Cipher) представляет собой алгоритм шифрования.
 * Вы можете использовать экземпляр Cipher для шифрования и расшифровки данных в Java.
 * https://habr.com/ru/articles/444814/
 */
public class CipherUtils {

    private static final int IV_LENGTH_BYTES = 12;
    private static final int TAG_SIZE_BITS = 128;

    public static byte[] encryptAesCbcPkcs5Padding(byte[] input, Key secretKey, byte[] vector) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(vector);
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(input);
    }

    public static byte[] decryptAesCbcPkcs5Padding(byte[] input, final Key secretKey, byte[] vector) throws Exception {
        IvParameterSpec ivParameterSpec = new IvParameterSpec(vector);
        Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(input);
    }

    public static byte[] encryptAesGcmNoPadding(Key secretKey, byte[] openText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING.getAlgorithm()); // алгоритм
        cipher.init(ENCRYPT_MODE, secretKey); // режим шифрования

        byte[] cipherText = cipher.doFinal(openText); // шифруем

        GCMParameterSpec gcmParams = cipher.getParameters().getParameterSpec(GCMParameterSpec.class);
        byte[] iv = gcmParams.getIV(); // достаем параметры

        byte[] result = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, result, 0, iv.length); // кладем параметры в результирующий массив байт
        System.arraycopy(cipherText, 0, result, iv.length, cipherText.length); // кладем зашифрованный текст в результирующий массив байт
        return result;
    }

    public static byte[] decryptAesGcmNoPadding(Key secretKey, byte[] message) throws Exception {
        byte[] iv = Arrays.copyOfRange(message, 0, IV_LENGTH_BYTES); // параметры
        byte[] cipherText = Arrays.copyOfRange(message, IV_LENGTH_BYTES, message.length); // текст

        GCMParameterSpec gcmParams = new GCMParameterSpec(TAG_SIZE_BITS, iv);
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING.getAlgorithm()); // алгоритм
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParams); // режим расшифрования

        return cipher.doFinal(cipherText);
    }

    public static byte[] encryptWithPublicKey(CipherAlgorithms algorithm, PublicKey publicKey, byte[] openText) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm.getAlgorithm());
        cipher.init(ENCRYPT_MODE, publicKey);
        return cipher.doFinal(openText);
    }

    public static byte[] decryptWithPublicKey(CipherAlgorithms algorithm, PrivateKey publicKey, byte[] openText) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(openText);
    }
}
