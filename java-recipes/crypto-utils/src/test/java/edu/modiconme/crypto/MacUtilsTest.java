package edu.modiconme.crypto;

import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MacUtilsTest {

    @Test
    void testMac() throws Exception {
        String src = TestFixture.uniqString();
        SecretKey secretKeySpec = SecretKeysUtils.genSecretKey(SecretKeysUtils.KeyGeneratorAlgorithm.AES);
        SecretKeySpec secretKeySpec1 = SecretKeysUtils.genDefaultSecretKey();

        String enc1 = MacUtils.enc(src, secretKeySpec, MacUtils.MacAlgorithm.HMAC_SHA_256);
        String enc2 = MacUtils.enc(src, secretKeySpec, MacUtils.MacAlgorithm.HMAC_SHA_256);

        assertEquals(enc1, enc2);
    }
}
