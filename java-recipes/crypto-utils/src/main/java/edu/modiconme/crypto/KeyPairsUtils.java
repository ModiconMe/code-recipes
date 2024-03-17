package edu.modiconme.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.util.io.pem.PemReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
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
public class KeyPairsUtils {

    public static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    public static final String BEGIN_ENCRYPTED_PRIVATE_KEY = "-----BEGIN ENCRYPTED PRIVATE KEY-----";
    public static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
    public static final String END_ENCRYPTED_PRIVATE_KEY = "-----END ENCRYPTED PRIVATE KEY-----";
    public static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
    public static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";

    /**
     * Предварительно надо расшифровать ключ командой
     * openssl pkcs8 \
     * -topk8 \
     * -in private.key \
     * -passin pass:1234 \
     * -out private_unenc.key \
     * -nocrypt
     */
    public static PrivateKey loadRSAPrivateKeyJava(Path privateKeyPath) throws Exception {
        try (Stream<String> keyStream = lines(privateKeyPath)) {
            String res = keyStream
                    .filter(el -> !BEGIN_PRIVATE_KEY.equals(el))
                    .filter(el -> !BEGIN_ENCRYPTED_PRIVATE_KEY.equals(el))
                    .filter(el -> !END_PRIVATE_KEY.equals(el))
                    .filter(el -> !END_ENCRYPTED_PRIVATE_KEY.equals(el))
                    .collect(joining());
            byte[] decoded = Base64.getDecoder().decode(res);
            var spec = new PKCS8EncodedKeySpec(decoded);
            return KeyFactory.getInstance(KeyFactoryAlgorithm.RSA.algorithm).generatePrivate(spec);
        }
    }

    /**
     * BouncyCastle
     */
    public static PrivateKey loadRSAPrivateKeyBC1(Path privateKeyPath) throws Exception {
        try (var pemReader = new PemReader(newBufferedReader(privateKeyPath))) {
            byte[] content = pemReader.readPemObject().getContent();
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content, KeyFactoryAlgorithm.RSA.algorithm);
            return KeyFactory.getInstance(KeyFactoryAlgorithm.RSA.algorithm).generatePrivate(privKeySpec);
        }
    }

    /**
     * BouncyCastle
     */
    public static PrivateKey loadRSAPrivateKeyBC2(Path privateKeyPath) throws Exception {
        try (var pemParser = new PEMParser(newBufferedReader(privateKeyPath))) {
            var privateKeyInfo = PrivateKeyInfo.getInstance(pemParser.readObject());
            return new JcaPEMKeyConverter().getPrivateKey(privateKeyInfo);
        }
    }

    public static Certificate loadX509CertFromPemJava(Path path) throws Exception {
        try (var inputStream = Files.newInputStream(path, StandardOpenOption.READ)) {
            return CertificateFactory.getInstance("X.509").generateCertificate(inputStream);
        }
    }

    public static PublicKey loadX509PublicKeyJava(Path pubKeyPath) throws Exception {
        try (Stream<String> keyStream = lines(pubKeyPath)) {
            String res = keyStream
                    .filter(el -> !BEGIN_PUBLIC_KEY.equals(el))
                    .filter(el -> !END_PUBLIC_KEY.equals(el))
                    .collect(joining());
            byte[] encoded = Base64.getDecoder().decode(res);
            var keyFactory = KeyFactory.getInstance(KeyFactoryAlgorithm.RSA.algorithm);
            var keySpec = new X509EncodedKeySpec(encoded);
            return keyFactory.generatePublic(keySpec);
        }
    }

    public static PublicKey loadX509PublicKeyBC(Path path) throws Exception {
        try (var pemParser = new PEMParser(newBufferedReader(path))) {
            var publicKeyInfo = SubjectPublicKeyInfo.getInstance(pemParser.readObject());
            return new JcaPEMKeyConverter().getPublicKey(publicKeyInfo);
        }
    }

    /**
     * Можно загружать зашифрованные ключи
     * KeyStore.PrivateKeyEntry keyEntry = KeyStoreEx.loadKeyAndCert();
     * PrivateKey privateKey = keyEntry.getPrivateKey();
     * Certificate certificate = keyEntry.getCertificate();
     * PublicKey publicKey = certificate.getPublicKey();
     */
    public static KeyStore.PrivateKeyEntry loadKeyAndCert(KeyStore keyStore, char[] keyPassword,
                                                          String certificateAlias) throws Exception {
        var entryPassword = new KeyStore.PasswordProtection(keyPassword);
        return ((KeyStore.PrivateKeyEntry) keyStore.getEntry(certificateAlias, entryPassword));
    }

    public static KeyStore loadPKCS12KeyStore(Path path, char[] keystorePassword) throws Exception {
        try (var is = Files.newInputStream(path)) {
            KeyStore keyStore = KeyStore.getInstance(KeyStoreType.PKCS12.type);
            keyStore.load(is, keystorePassword);
            return keyStore;
        }
    }

    public static KeyPair genKeyPair(KeyPairGeneratorAlgorithm algorithm) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm.algorithm);
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.genKeyPair();
    }

    /**
     * <a href="https://docs.oracle.com/en/java/javase/20/docs/specs/security/standard-names.html#keystore-types">...</a>
     */
    @Getter
    @RequiredArgsConstructor
    public enum KeyStoreType {
        PKCS12("pkcs12"),
        JKS("jks"),
        ;

        private final String type;
    }

    /**
     * <a href="https://docs.oracle.com/en/java/javase/20/docs/specs/security/standard-names.html#keyfactory-algorithms">...</a>
     */
    @Getter
    @RequiredArgsConstructor
    public enum KeyFactoryAlgorithm {
        RSA("RSA"),
        ED25519("Ed25519"),
        ;

        private final String algorithm;
    }

    /**
     * <a href="https://docs.oracle.com/en/java/javase/20/docs/specs/security/standard-names.html#keypairgenerator-algorithms">...</a>
     */
    @Getter
    @RequiredArgsConstructor
    public enum KeyPairGeneratorAlgorithm {
        RSA("RSA"),
        ED25519("Ed25519"),
        ;

        private final String algorithm;
    }
}
