package edu.modiconme.crypto;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Objects;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.Normalizer.normalize;

@UtilityClass
public class HashingUtils {

    public static byte[] sha256(String data) throws NoSuchAlgorithmException {
        return hashing(HashingAlgorithm.SHA_256, data);
    }

    public static byte[] sha256(byte[] data) throws NoSuchAlgorithmException {
        return hashing(HashingAlgorithm.SHA_256, data);
    }

    public static byte[] sha384(String data) throws NoSuchAlgorithmException {
        return hashing(HashingAlgorithm.SHA_384, data);
    }

    public static byte[] sha384(byte[] data) throws NoSuchAlgorithmException {
        return hashing(HashingAlgorithm.SHA_384, data);
    }

    public static byte[] sha512(String data) throws NoSuchAlgorithmException {
        return hashing(HashingAlgorithm.SHA_512, data);
    }

    public static byte[] sha512(byte[] data) throws NoSuchAlgorithmException {
        return hashing(HashingAlgorithm.SHA_512, data);
    }

    public static byte[] hashing(HashingAlgorithm algorithm, String data) throws NoSuchAlgorithmException {
        byte[] srcBytes = normalize(data.trim(), Normalizer.Form.NFC).getBytes(UTF_8);
        return hashing(algorithm, srcBytes);
    }

    private static byte[] hashing(HashingAlgorithm algorithm, byte[] data) throws NoSuchAlgorithmException {
        Objects.requireNonNull(data);
        MessageDigest md = getMessageDigest(algorithm);
        return md.digest(data);
    }

    public static boolean verify(HashingAlgorithm algorithm, byte[] originalBytes, byte[] encryptedBytes) throws NoSuchAlgorithmException {
        MessageDigest md = getMessageDigest(algorithm);
        md.update(originalBytes);
        byte[] digest = md.digest();
        return Arrays.equals(digest, encryptedBytes);
    }

    private static MessageDigest getMessageDigest(HashingAlgorithm algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm.getAlgorithm());
    }
}
