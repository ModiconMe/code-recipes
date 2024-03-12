package ru.modiconme.recepies.crypto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;

import static java.text.Normalizer.normalize;

/**
 * Нужен для того, чтобы быть уверенным, что данные в зашифрованном сообщении не подменились.
 * При изменении данных сообщения его хэш тоже измениться.
 * <p>
 * Для увеличения надежности желательно использовать SALT, чтобы злоумышленник не смог перегенерировать
 * хэш сообщения при изменении сообщения, поскольку, чтобы хэши совпали, нужно знать SALT.
 */
public class MessageDigests {

    /*
        соль нужна для того, чтобы злоумышленник
        не смог вычислить хэш-функцию самостоятельно и подменить данные
        третья сторона, которой мы передаем данные, эту соль знает и может проверить
        чтобы данные не подменялись (если злоумышленник подменит данные без соли, то digest
        проверку не пройдет)
    */
    public static final String SALT = "secretSALTsecret";

    public static void main(String[] args) throws Exception {
        System.out.printf(
                "?name=%s&phone=%s&digest=%s%n", encode("Вася"),
                encode("+79520009939"),
                encode(digest("Вася", "+79520009939", SALT))
        );
        // ?name=%D0%92%D0%B0%D1%81%D1%8F
        // &phone=%2B79520009939
        // &digest=5doWkY9KuNU%2BE%2F%2BdxIxMtp0uYTwV48EseI8ZVLmDbyk%3D

        // проверка хэша 3ей стороной
        String hello = "Hello";
        String enc = digest(hello+SALT);
        System.out.println(verify(hello + SALT, new String(Base64.getDecoder().decode(enc))));
    }

    private static String digest(String... data) throws Exception {
        String srcString = Arrays.stream(data)
                .map(s -> s == null ? "" : s)
                .map(String::trim)
                .map(s -> normalize(s, Normalizer.Form.NFC))
                .collect(Collectors.joining("|"));
        byte[] srcBytes = srcString.getBytes(StandardCharsets.UTF_8);
        MessageDigest md = getSha256MessageDigestInstance();
        byte[] result = md.digest(srcBytes);
        return Base64.getEncoder().encodeToString(result);
    }

    private static String encode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    public static boolean verify(String originalString, String encryptedString) throws NoSuchAlgorithmException {
        MessageDigest md = getSha256MessageDigestInstance();
        md.update(originalString.getBytes());
        byte[] digest = md.digest();
        String hashedString = new String(digest);
        return hashedString.equals(encryptedString);
    }

    private static MessageDigest getSha256MessageDigestInstance() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("SHA-256");
    }
}
