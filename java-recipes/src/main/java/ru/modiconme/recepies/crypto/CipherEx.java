package ru.modiconme.recepies.crypto;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public class CipherEx {

    public static final int IV_LENGTH_BYTES = 12;
    public static final int TAG_SIZE_BITS = 128;
    public static final String AES_GCM_NO_PADDING = "AES/GCM/NoPadding";

    public static void main(String[] args) throws Exception {
        byte[] salt = "Salt1".getBytes(StandardCharsets.UTF_8);
        char[] secretPhrase = "mySecretPassword".toCharArray(); // может храниться в пропсах
        SecretKey secretKey = generateKey(secretPhrase, salt);

        byte[] openText = "Hello world!".getBytes(StandardCharsets.UTF_8);
        byte[] result = encrypt(secretKey, openText);
        System.out.println(result.length);
        System.out.println(Arrays.toString(result));

        byte[] decryptText = decrypt(secretKey, result);
        System.out.println(new String(decryptText));
    }

    private static SecretKey generateKey(char[] secretPhrase, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(secretPhrase, salt, 1, TAG_SIZE_BITS); // любая secretPhrase будет преобразована в 16 байт
        SecretKey temp = factory.generateSecret(spec);
        return new SecretKeySpec(temp.getEncoded(), "AES");
    }

    private static byte[] encrypt(SecretKey secretKey, byte[] openText) throws Exception {
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

    private static byte[] decrypt(SecretKey secretKey, byte[] message) throws Exception {
        byte[] iv = Arrays.copyOfRange(message, 0, IV_LENGTH_BYTES); // параметры
        byte[] cipherText = Arrays.copyOfRange(message, IV_LENGTH_BYTES, message.length); // текст

        GCMParameterSpec gcmParams = new GCMParameterSpec(TAG_SIZE_BITS, iv);
        Cipher cipher = Cipher.getInstance(AES_GCM_NO_PADDING); // алгоритм
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmParams); // режим расшифрования

        return cipher.doFinal(cipherText);
    }
}
