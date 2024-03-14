package edu.modiconme.crypto;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

import static edu.modiconme.crypto.EncodingUtils.*;
import static edu.modiconme.crypto.HashingAlgorithm.SHA_512;
import static edu.modiconme.crypto.TestFixture.uniqString;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;
import static edu.modiconme.crypto.HashingUtils.*;

class HashingUtilsTest {

    @Test
    void testSha256() throws NoSuchAlgorithmException {
        byte[] bytes = sha256(uniqString());
        assertEquals(32, bytes.length);
    }

    @Test
    void testSha384() throws NoSuchAlgorithmException {
        byte[] bytes = sha384(uniqString());
        assertEquals(48, bytes.length);
    }

    @Test
    void testSha512() throws NoSuchAlgorithmException {
        byte[] bytes = sha512(uniqString());
        assertEquals(64, bytes.length);
    }

    /*
        соль нужна для того, чтобы злоумышленник
        не смог вычислить хэш-функцию самостоятельно и подменить данные
        третья сторона, которой мы передаем данные, эту соль знает и может проверить
        чтобы данные не подменялись (если злоумышленник подменит данные без соли, то digest
        проверку не пройдет)
    */
    @Test
    void testVerify() throws NoSuchAlgorithmException {
        String salt = "SALT";
        String secretData = "Вася" + ":+79520009939:" + salt;

        String encodedUrl = "?name=%s&phone=%s&digest=%s%n".formatted(
                urlEncode("Вася"),
                urlEncode("+79520009939"),
                urlEncode(new String(sha512(secretData))));
        System.out.println(encodedUrl);

        String encString = base64EncodeBytes(sha512(secretData));

        // проверка хэша 3ей стороной
        boolean verify = verify(SHA_512,
                (secretData).getBytes(UTF_8),
                base64DecodeBytes(encString.getBytes()));
        assertTrue(verify);
    }


}