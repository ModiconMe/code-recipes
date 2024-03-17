package edu.modiconme.crypto;

import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;

import static edu.modiconme.crypto.KeyPairsUtils.KeyFactoryAlgorithm.RSA;
import static edu.modiconme.crypto.KeyPairsUtils.loadKeyAndCert;
import static edu.modiconme.crypto.KeyPairsUtils.loadPKCS12KeyStore;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KeyPairsUtilsTest {

    @Test
    void shouldLoadPrivateKeySuccess() throws Exception {
        Path path = Path.of(Resources.getResource("certs/private_noenc.key").getPath());
        String rsaAlgorithm = RSA.getAlgorithm();

        PrivateKey privateKeyJava = KeyPairsUtils.loadRSAPrivateKeyJava(path);
        assertEquals(rsaAlgorithm, privateKeyJava.getAlgorithm());
        PrivateKey privateKeyBC1 = KeyPairsUtils.loadRSAPrivateKeyBC1(path);
        assertEquals(rsaAlgorithm, privateKeyBC1.getAlgorithm());
        PrivateKey privateKeyBC2 = KeyPairsUtils.loadRSAPrivateKeyBC2(path);
        assertEquals(rsaAlgorithm, privateKeyBC2.getAlgorithm());
    }

    @Test
    void shouldThrowWhenLoadEncodedPrivateKeyJava() {
        Path path = Path.of(Resources.getResource("certs/private.key").getPath());
        assertThrows(InvalidKeySpecException.class, () -> KeyPairsUtils.loadRSAPrivateKeyJava(path));
        assertThrows(InvalidKeySpecException.class, () -> KeyPairsUtils.loadRSAPrivateKeyBC1(path));
        assertThrows(IllegalArgumentException.class, () -> KeyPairsUtils.loadRSAPrivateKeyBC2(path));
    }

    @Test
    void shouldLoadX509CertificateSuccess() throws Exception {
        Path path = Path.of(Resources.getResource("certs/server.crt").getPath());
        Certificate certificate = KeyPairsUtils.loadX509CertFromPemJava(path);
        assertEquals("X.509", certificate.getType());
    }

    @Test
    void shouldLoadPublicKeySuccess() throws Exception {
        Path path = Path.of(Resources.getResource("certs/public.key").getPath());
        String rsaAlgorithm = RSA.getAlgorithm();

        PublicKey publicKeyJava = KeyPairsUtils.loadX509PublicKeyJava(path);
        assertEquals(rsaAlgorithm, publicKeyJava.getAlgorithm());
        PublicKey publicKeyBC = KeyPairsUtils.loadX509PublicKeyBC(path);
        assertEquals(rsaAlgorithm, publicKeyBC.getAlgorithm());
    }

    @Test
    void shouldLoadKeyStoreSuccess() throws Exception {
        Path keyStorePath = Path.of(Resources.getResource("certs/server.p12").getPath());
        char[] password = "1234".toCharArray();
        String certificateAlias = "my certificate";
        KeyStore keyStore = loadPKCS12KeyStore(keyStorePath, password);
        Certificate certificate = keyStore.getCertificate(certificateAlias);
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(certificateAlias, password);
        assertEquals("pkcs12", keyStore.getType());
        assertEquals("X.509", certificate.getType());
        assertEquals(RSA.getAlgorithm(), privateKey.getAlgorithm());

//        keyStore.aliases().asIterator().next();
        KeyStore.PrivateKeyEntry keyEntry = loadKeyAndCert(keyStore, password, certificateAlias);
        assertEquals(privateKey, keyEntry.getPrivateKey());
        assertEquals(certificate, keyEntry.getCertificate());
    }

    @Test
    void shouldGenerateKeyPair() throws Exception {
        KeyPairsUtils.KeyPairGeneratorAlgorithm rsa = KeyPairsUtils.KeyPairGeneratorAlgorithm.RSA;
        KeyPair keyPair = KeyPairsUtils.genKeyPair(rsa);
        assertEquals(rsa.getAlgorithm(), keyPair.getPrivate().getAlgorithm());
        assertEquals(rsa.getAlgorithm(), keyPair.getPublic().getAlgorithm());
    }
}
