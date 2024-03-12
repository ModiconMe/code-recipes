package ru.modiconme.recepies.crypto;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;

import static ru.modiconme.recepies.crypto.Keys.KeyGeneration.getSecretKey;

/**
 * Класс Java Cipher (javax.crypto.Cipher) представляет собой алгоритм шифрования.
 * Вы можете использовать экземпляр Cipher для шифрования и расшифровки данных в Java.
 * https://habr.com/ru/articles/444814/
 */
public class Ciphers {

    public static final int IV_LENGTH_BYTES = 12;
    public static final int TAG_SIZE_BITS = 128;
    public static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";

    public static void main(String[] args) throws Exception {
        var salt = "Salt1";
        var secretPhrase = "mySecretPassword"; // может храниться в пропсах
        Key secretKey = getSecretKey(secretPhrase, salt, TAG_SIZE_BITS, "PBKDF2WithHmacSHA256");

        byte[] openText = "Hello world!".getBytes(StandardCharsets.UTF_8);
        byte[] result = encrypt(secretKey, openText);
        System.out.println(result.length);
        String base64EncodedBytes = Base64.getEncoder().encodeToString(result);
        System.out.println(base64EncodedBytes);

        byte[] decryptText = decrypt(secretKey, result);
        System.out.println(new String(decryptText));
    }

    public static byte[] encrypt(Key secretKey, byte[] openText) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING); // алгоритм
        cipher.init(Cipher.ENCRYPT_MODE, secretKey); // режим шифрования

        byte[] cipherText = cipher.doFinal(openText); // шифруем

        GCMParameterSpec gcmParams = cipher.getParameters().getParameterSpec(GCMParameterSpec.class);
        byte[] iv = gcmParams.getIV(); // достаем параметры

        byte[] result = new byte[iv.length + cipherText.length];
        System.arraycopy(iv, 0, result, 0, iv.length); // кладем параметры в результирующий массив байт
        System.arraycopy(cipherText, 0, result, iv.length, cipherText.length); // кладем зашифрованный текст в результирующий массив байт
        return result;
    }

    public static byte[] decrypt(Key secretKey, byte[] message) throws Exception {
        byte[] iv = Arrays.copyOfRange(message, 0, IV_LENGTH_BYTES); // параметры
        byte[] cipherText = Arrays.copyOfRange(message, IV_LENGTH_BYTES, message.length); // текст

        GCMParameterSpec gcmParams = new GCMParameterSpec(TAG_SIZE_BITS, iv);
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING); // алгоритм
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParams); // режим расшифрования

        return cipher.doFinal(cipherText);
    }
}
