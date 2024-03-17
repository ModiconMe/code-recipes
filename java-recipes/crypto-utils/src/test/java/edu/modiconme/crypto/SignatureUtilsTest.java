package edu.modiconme.crypto;

import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import static edu.modiconme.crypto.CipherUtils.decryptWithPublicKey;
import static edu.modiconme.crypto.CipherUtils.encryptWithPrivateKey;
import static edu.modiconme.crypto.KeyPairsUtils.loadKeyAndCert;
import static edu.modiconme.crypto.KeyPairsUtils.loadPKCS12KeyStore;
import static edu.modiconme.crypto.SignatureUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SignatureUtilsTest {

    @Test
    void testSignature1() throws Exception {
        String data = "Hello";
        KeyStore.PrivateKeyEntry keyEntry = getKeyEntry();
        SignatureAlgorithm algorithm = SignatureAlgorithm.SHA_256_WITH_RSA;
        String sign = signSha256(data, keyEntry.getPrivateKey(), algorithm);
        boolean verify = verifySha256(data, sign, keyEntry.getCertificate().getPublicKey(), algorithm);
        assertTrue(verify);
    }

    @Test
    void testSignature2() throws Exception {
        String data = "Hello";
        PublicKey publicKey = KeyPairsUtils.loadX509PublicKeyJava(Path.of(Resources.getResource("certs/public.key").getPath()));
        PrivateKey privateKey = KeyPairsUtils.loadRSAPrivateKeyJava(Path.of(Resources.getResource("certs/private_noenc.key").getPath()));
        SignatureAlgorithm algorithm = SignatureAlgorithm.SHA_256_WITH_RSA;
        String sign = signSha256(data, privateKey, algorithm);
        boolean verify = verifySha256(data, sign, publicKey, algorithm);
        assertTrue(verify);
    }

    @Test
    void testSignatureWithMessageDigestAndCipher() throws Exception {
        String data = "Hello";
        byte[] hash = HashingUtils.sha256(data.getBytes());
        KeyStore.PrivateKeyEntry keyEntry = getKeyEntry();
        byte[] encrypt = encryptWithPrivateKey(CipherAlgorithms.RSA, keyEntry.getPrivateKey(), hash);
        byte[] decrypt = decryptWithPublicKey(CipherAlgorithms.RSA, keyEntry.getCertificate().getPublicKey(), encrypt);

        assertEquals(new String(hash), new String(decrypt));
    }

    private static KeyStore.PrivateKeyEntry getKeyEntry() throws Exception {
        KeyStore keyStore = loadPKCS12KeyStore(Path.of(Resources.getResource("certs/server.p12").getPath()), "1234".toCharArray());
        return loadKeyAndCert(keyStore, "1234".toCharArray(), "my certificate");
    }
}
