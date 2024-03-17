package edu.modiconme.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecretKeysUtilsTest {

    @Test
    void shouldGenerate256BitAesSecretKeySuccess() throws Exception {
        SecretKeysUtils.KeyGeneratorAlgorithm aes = SecretKeysUtils.KeyGeneratorAlgorithm.AES;
        SecretKey aesSecretKey = SecretKeysUtils.genSecretKey(aes);
        assertEquals(aes.getAlgorithm(), aesSecretKey.getAlgorithm());
        assertEquals(32, aesSecretKey.getEncoded().length);
    }

    @Test
    void shouldGenerate256BitHmacSha256SecretKeySuccess() throws Exception {
        SecretKeysUtils.KeyGeneratorAlgorithm hmacSha256 = SecretKeysUtils.KeyGeneratorAlgorithm.HMAC_SHA_256;
        SecretKey hmacSecretKey = SecretKeysUtils.genSecretKey(hmacSha256);
        assertEquals(hmacSha256.getAlgorithm(), hmacSecretKey.getAlgorithm());
        assertEquals(32, hmacSecretKey.getEncoded().length);
    }

    @Test
    void shouldGenerateAdvancedSecretKeySuccess() throws Exception {
        SecretKey secretKey = SecretKeysUtils.genDefaultSecretKey();
        assertEquals(SecretKeysUtils.SecretKeyAlgorithm.PBKDF2_WITH_HMAC_SHA_256.getAlgorithm(), secretKey.getAlgorithm());
        assertEquals(32, secretKey.getEncoded().length);
    }

}
