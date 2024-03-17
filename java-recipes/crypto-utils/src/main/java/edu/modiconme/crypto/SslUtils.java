package edu.modiconme.crypto;

import lombok.SneakyThrows;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import static edu.modiconme.crypto.KeyPairsUtils.loadPKCS12KeyStore;

public class SslUtils {

    public static void main(String[] args) {
//        test();
    }

    /**
     * Default Trust Store - $JAVA_HOME/lib/security/cacerts
     */
    @SneakyThrows
    public static X509TrustManager getDefaultTrustManagerFactory() {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null);
//        List<X509Certificate> certificates = asList(tmf.getTrustManagers())
//                .stream()
//                .filter(X509TrustManager.class::isInstance)
//                .map(X509TrustManager.class::cast)
//                .map(trustManager -> asList(trustManager.getAcceptedIssuers()))
//                .flatMap(Collection::stream)
//                .toList();
//        System.out.println(certificates);
        return (X509TrustManager) tmf.getTrustManagers()[0];
    }

    public static X509TrustManager getUnsafeTrustManagerFactory() {
        return new UnsafeX509TrustManager();
    }

    public static class UnsafeX509TrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // trust all
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // trust all
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    public static HostnameVerifier getUnsafeHostnameVerifier() {
        return (hostname, session) -> true;
    }

    public static X509TrustManager getUserTrustStore(Path trustStorePath, String trustStorePassword) throws Exception {
        KeyStore keyStore = loadPKCS12KeyStore(trustStorePath, trustStorePassword);
        final TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmFactory.init(keyStore);
        return (X509TrustManager) tmFactory.getTrustManagers()[0];
    }

    public static KeyManagerFactory getKeyManagerFactory(Path keyStorePath, String keyStorePassword) throws Exception {
        KeyStore keyStore = loadPKCS12KeyStore(keyStorePath, keyStorePassword);
        final KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmFactory.init(keyStore, keyStorePassword.toCharArray());
        return kmFactory;
    }
}
