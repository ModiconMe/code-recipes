package edu.modiconme.crypto;

import com.google.common.io.Resources;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;
import static java.nio.file.Files.newBufferedReader;
import static java.util.stream.Collectors.joining;

/**
 * PKI - инфраструктура публичных ключей
 * Взаимодействие из java осуществляется несколькими способами
 * 1) Мы можем сгенерировать ключи или связку ключей
 * 2) Мы можем достать ключ или сертификат из внешнего файла (jks, p12, pem)
 */
@UtilityClass
public class KeysUtils {

    public static final String PKCS12 = "pkcs12";
    public static final String KEYSTORE_PASSWORD = "1234";
    public static final String KEY_PASSWORD = "1234";
    // keyStore.aliases().asIterator().next();
    public static final String CERTIFICATE_ALIAS = "my certificate";
    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    public static final String BEGIN_ENCRYPTED_PRIVATE_KEY = "-----BEGIN ENCRYPTED PRIVATE KEY-----";
    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
    public static final String END_ENCRYPTED_PRIVATE_KEY = "-----END ENCRYPTED PRIVATE KEY-----";
    public static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
    public static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";

    public static void main(String[] args) throws Exception {
        var certPath = Path.of(Resources.getResource("certs/server.crt").getPath());
        Certificate cert = readX509CertFromPemJava(certPath);
        System.out.println(cert);

        var pubKeyPath = Path.of(Resources.getResource("certs/public.key").getPath());
        RSAPublicKey pubKey1 = readX509PublicKeyJava(pubKeyPath);
        RSAPublicKey pubKey2 = readX509PublicKeyBC(pubKeyPath);
        System.out.println(pubKey1);
        System.out.println(pubKey2);

        var noEncPrivateKeyPath = Path.of(Resources.getResource("certs/private_noenc.key").getPath());
        PrivateKey privateKey1 = readPEMPrivateKeyJava(noEncPrivateKeyPath);
        PrivateKey privateKey2 = readPEMPrivateKeyBC1(noEncPrivateKeyPath);
        PrivateKey privateKey3 = readPEMPrivateKeyBC2(noEncPrivateKeyPath);
        System.out.println(privateKey1);
        System.out.println(privateKey2);
        System.out.println(privateKey3);

        var keyStorePath = Path.of(Resources.getResource("certs/server.p12").getPath());
        KeyStore keyStore = loadPKCS12KeyStore(keyStorePath, KEYSTORE_PASSWORD);
        Certificate certificate = keyStore.getCertificate(CERTIFICATE_ALIAS);
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(CERTIFICATE_ALIAS, KEY_PASSWORD.toCharArray());
        KeyStore.PrivateKeyEntry keyEntry = loadKeyAndCert(keyStore);
        System.out.println(certificate);
        System.out.println(privateKey);
        System.out.println(keyEntry.getPrivateKey());
        System.out.println(keyEntry.getCertificate());

        SecretKeySpec defaultSecretKey = genDefaultSecretKey();
        System.out.println(defaultSecretKey);
        SecretKeySpec secretKey = genSecretKey();
        System.out.println(secretKey);
        KeyPair keyPair = gen2048RsaKeyPair();
        System.out.println(keyPair);
    }

    /**
     * Предварительно надо расшифровать ключ командой
     * openssl pkcs8 \
     * -topk8 \
     * -in private.key \
     * -passin pass:1234 \
     * -out private_unenc.key \
     * -nocrypt
     */
    public static PrivateKey readPEMPrivateKeyJava(Path privateKeyPath) throws Exception {
        try (Stream<String> keyStream = lines(privateKeyPath)) {
            String res = keyStream
                    .filter(el -> !BEGIN_PRIVATE_KEY.equals(el))
                    .filter(el -> !BEGIN_ENCRYPTED_PRIVATE_KEY.equals(el))
                    .filter(el -> !END_PRIVATE_KEY.equals(el))
                    .filter(el -> !END_ENCRYPTED_PRIVATE_KEY.equals(el))
                    .collect(joining());
            byte[] decoded = Base64.getDecoder().decode(res);
            var spec = new PKCS8EncodedKeySpec(decoded);
            return KeyFactory.getInstance("RSA").generatePrivate(spec);
        }
    }

    /**
     * BouncyCastle
     */
    public static RSAPrivateKey readPEMPrivateKeyBC1(Path privateKeyPath) throws Exception {
        try (var keyReader = newBufferedReader(privateKeyPath);
             PemReader pemReader = new PemReader(keyReader)) {
            PemObject pemObject = pemReader.readPemObject();
            byte[] content = pemObject.getContent();
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content, "RSA");
            return (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(privKeySpec);
        }
    }

    /**
     * BouncyCastle
     */
    public static RSAPrivateKey readPEMPrivateKeyBC2(Path privateKeyPath) throws Exception {
        try (var keyReader = newBufferedReader(privateKeyPath)) {
            var pemParser = new PEMParser(keyReader);
            var converter = new JcaPEMKeyConverter();
            var privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());
            return (RSAPrivateKey) converter.getPrivateKey(privateKeyInfo);
        }
    }

    public static Certificate readX509CertFromPemJava(Path path) throws Exception {
        try (var inputStream = Files.newInputStream(path, StandardOpenOption.READ)) {
            return CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
        }
    }

    public static RSAPublicKey readX509PublicKeyJava(Path pubKeyPath) throws Exception {
        try (Stream<String> keyStream = lines(pubKeyPath)) {
            String res = keyStream
                    .filter(el -> !BEGIN_PUBLIC_KEY.equals(el))
                    .filter(el -> !END_PUBLIC_KEY.equals(el))
                    .collect(joining());
            byte[] encoded = Base64.getDecoder().decode(res);
            var keyFactory = KeyFactory.getInstance("RSA");
            var keySpec = new X509EncodedKeySpec(encoded);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        }
    }

    public static RSAPublicKey readX509PublicKeyBC(Path path) throws Exception{
        try (var keyReader = newBufferedReader(path)) {
            var pemParser = new PEMParser(keyReader);
            var converter = new JcaPEMKeyConverter();
            var publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
            return (RSAPublicKey) converter.getPublicKey(publicKeyInfo);
        }
    }

    /**
     * Можно загружать зашифрованные ключи
     * KeyStore.PrivateKeyEntry keyEntry = KeyStoreEx.loadKeyAndCert();
     * PrivateKey privateKey = keyEntry.getPrivateKey();
     * Certificate certificate = keyEntry.getCertificate();
     * PublicKey publicKey = certificate.getPublicKey();
     */
    public static KeyStore.PrivateKeyEntry loadKeyAndCert(KeyStore keyStore) throws Exception {
        var entryPassword = new KeyStore.PasswordProtection(KEY_PASSWORD.toCharArray());
        return ((KeyStore.PrivateKeyEntry) keyStore.getEntry(CERTIFICATE_ALIAS, entryPassword));
    }

    public static KeyStore loadPKCS12KeyStore(Path path, String keystorePassword) throws Exception {
        try (var is = Files.newInputStream(path)) {
            KeyStore keyStore = KeyStore.getInstance(PKCS12);
            keyStore.load(is, keystorePassword.toCharArray());
            return keyStore;
        }
    }

    public static KeyGenerator keyGenerator(String algo) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algo);
        SecureRandom secureRandom = new SecureRandom();
        int keyBitSize = 256;
        keyGenerator.init(keyBitSize, secureRandom);
        return keyGenerator;
    }

    public static KeyPair gen2048RsaKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.genKeyPair();
    }

    public static SecretKeySpec genSecretKey() throws Exception {
        return (SecretKeySpec) aesKeyGenerator().generateKey();
    }

    public static SecretKeySpec genDefaultSecretKey() throws Exception {
        return genSecretKey("1234", "salt", 256, "PBKDF2WithHmacSHA256");
    }

    /**
     * Ключи для синхронного шифрования
     * https://docs.oracle.com/en/java/javase/17/docs/specs/security/standard-names.html#secretkeyfactory-algorithms
     */
    public static SecretKeySpec genSecretKey(
            String password,
            String salt,
            int keySize,
            String algo) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(algo);
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 10_000, keySize);
        SecretKey temp = factory.generateSecret(spec);
        return new SecretKeySpec(temp.getEncoded(), "AES");
    }

    /**
     * https://docs.oracle.com/en/java/javase/17/docs/specs/security/standard-names.html#keygenerator-algorithms
     */
    public static KeyGenerator aesKeyGenerator() throws Exception {
        return keyGenerator("AES");
    }

    public static KeyGenerator hmacSHA3512KeyGenerator() throws Exception {
        return keyGenerator("HmacSHA3-512");
    }
}
