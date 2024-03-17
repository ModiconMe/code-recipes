package edu.modiconme.crypto;

import com.google.common.io.Resources;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import static edu.modiconme.crypto.CipherAlgorithms.*;
import static edu.modiconme.crypto.CipherUtils.*;
import static edu.modiconme.crypto.KeyPairsUtils.loadRSAPrivateKeyJava;
import static edu.modiconme.crypto.KeyPairsUtils.loadX509PublicKeyJava;
import static edu.modiconme.crypto.TestFixture.uniqString;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CipherUtilsTest {

    @Test
    void testEncryptAndDecryptFlow() throws Exception {
        String srcString = uniqString();
        SecretKey secretKey = SecretKeysUtils.genSecretKey(SecretKeysUtils.KeyGeneratorAlgorithm.AES);
        byte[] encrypt = CipherUtils.encryptAesGcmNoPadding(secretKey, srcString.getBytes(UTF_8));
        byte[] decrypt = decryptAesGcmNoPadding(secretKey, encrypt);
        assertEquals(srcString, new String(decrypt));
    }

    /**
     * Гибридное шифрование позволяем обойти ограничение асинхронного шифрования в размере данных
     * и шифровать данные с помощью быстрого синхронного шифрования, а ключ синхронного шифрования (секрет)
     * шифруем с помощью асинхронного шифрования и можем безопасно его передавать по незащищенного каналу
     */
    @Test
    void testHybridCipher() throws Exception {
        SecretKey secretKey = SecretKeysUtils.genSecretKey(SecretKeysUtils.KeyGeneratorAlgorithm.AES);

        // Шифруем данные с помощью синхронного ключа
        String srcString = uniqString();
        CipherAlgorithms algorithm = AES_GCM_NO_PADDING;
        byte[] encrypt = CipherUtils.encryptAesGcmNoPadding(secretKey, srcString.getBytes(UTF_8));

        // Шифруем секретный ключ публичным ключом
        var pubKeyPath = Path.of(Resources.getResource("certs/public.key").getPath());
        PublicKey pubKey = loadX509PublicKeyJava(pubKeyPath);
        byte[] encryptAESKey = encryptWithPublicKey(RSA_ECB_OAEP_WITH_SHA_256_AND_MGF_1_PADDING, pubKey, secretKey.getEncoded());

        // Расшифровываем секретный ключ приватным ключом
        var noEncPrivateKeyPath = Path.of(Resources.getResource("certs/private_noenc.key").getPath());
        PrivateKey privateKey = loadRSAPrivateKeyJava(noEncPrivateKeyPath);
        byte[] decryptAESKey = decryptWithPrivateKey(RSA_ECB_OAEP_WITH_SHA_256_AND_MGF_1_PADDING, privateKey, encryptAESKey);
        SecretKeySpec decryptedSecretKey = new SecretKeySpec(decryptAESKey, "AES");

        // Расшифровываем данные с помощью синхронного ключа
        byte[] decrypt = decryptAesGcmNoPadding(secretKey, encrypt);
        assertEquals(srcString, new String(decrypt));
        assertEquals(secretKey, decryptedSecretKey);
    }

    @Test
    void testHybridCipher1() throws Exception {
        SecretKey secretKey = SecretKeysUtils.genSecretKey(SecretKeysUtils.KeyGeneratorAlgorithm.AES);

        // Шифруем данные с помощью синхронного ключа
        String srcString = uniqString();
        CipherAlgorithms algorithm = AES_CBC_PKCS5PADDING;
        byte[] vector = new byte[16];
        new SecureRandom().nextBytes(vector);

        byte[] encrypt = CipherUtils.encryptAesCbcPkcs5Padding(srcString.getBytes(UTF_8), secretKey, vector);

        // Шифруем секретный ключ публичным ключом
        var pubKeyPath = Path.of(Resources.getResource("certs/public.key").getPath());
        PublicKey pubKey = loadX509PublicKeyJava(pubKeyPath);
        byte[] encryptAESKey = encryptWithPublicKey(RSA_ECB_OAEP_WITH_SHA_256_AND_MGF_1_PADDING, pubKey, secretKey.getEncoded());

        // Расшифровываем секретный ключ приватным ключом
        var noEncPrivateKeyPath = Path.of(Resources.getResource("certs/private_noenc.key").getPath());
        PrivateKey privateKey = loadRSAPrivateKeyJava(noEncPrivateKeyPath);
        byte[] decryptAESKey = decryptWithPrivateKey(RSA_ECB_OAEP_WITH_SHA_256_AND_MGF_1_PADDING, privateKey, encryptAESKey);
        SecretKeySpec decryptedSecretKey = new SecretKeySpec(decryptAESKey, "AES");

        // Расшифровываем данные с помощью синхронного ключа
        byte[] decrypt = decryptAesCbcPkcs5Padding(encrypt, decryptedSecretKey, vector);
        assertEquals(srcString, new String(decrypt));
        assertEquals(secretKey, decryptedSecretKey);
    }
}