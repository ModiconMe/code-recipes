package ru.modiconme.recepies.crypto;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class AsyncEx {

    public static void main(String[] args) throws Exception {
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        InputStream certStream = AsyncEx.class.getClassLoader().getResourceAsStream("certs/cert.pem");
        Certificate certificate = factory.generateCertificate(certStream);
        PublicKey publicKey = certificate.getPublicKey();

        /*
         * Импортировать в java keystore
         * keytool -importcert -file certificate.cer -keystore keystore.jks -alias "Alias"
         * Потом можем его достать указав путь до keystore и алиас сертификата
         */
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        InputStream certStream1 = AsyncEx.class.getClassLoader().getResourceAsStream("certs/keystore.p12");
        keyStore.load(certStream1, "changeit".toCharArray());
        Certificate cert = keyStore.getCertificate("test-cert");
        PublicKey publicKey1 = cert.getPublicKey();


        System.out.println(certificate);

    }
}
