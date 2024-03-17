package edu.modiconme.crypto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.net.ssl.*;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import static edu.modiconme.crypto.KeyPairsUtils.loadPKCS12KeyStore;

public class SslUtils {

    /**
     * Default Trust Store - $JAVA_HOME/lib/security/cacerts
     */
    public static X509TrustManager getDefaultTrustManager() throws Exception {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init((KeyStore) null);
        return (X509TrustManager) tmf.getTrustManagers()[0];
    }

    public static X509TrustManager getUnsafeTrustManagerFactory() {
        return new UnsafeX509TrustManager();
    }


    public static class UnsafeX509TrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            // trust all
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
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

    public static X509TrustManager[] getUserTrustManager(Path trustStorePath, char[] trustStorePassword) throws Exception {
        KeyStore keyStore = loadPKCS12KeyStore(trustStorePath, trustStorePassword);
        final TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmFactory.init(keyStore);
        return Arrays.stream(tmFactory.getTrustManagers())
                .filter(X509TrustManager.class::isInstance)
                .map(X509TrustManager.class::cast)
                .toArray(X509TrustManager[]::new);
    }

    /**
     * Загрузка клиентского сертификата
     */
    public static KeyManager[] getClientKeyManagers(Path keyStorePath, char[] keyStorePassword) throws Exception {
        KeyStore keyStore = loadPKCS12KeyStore(keyStorePath, keyStorePassword);
        final KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmFactory.init(keyStore, keyStorePassword);
        return kmFactory.getKeyManagers();
    }

    @Getter
    @RequiredArgsConstructor
    public enum SslAlgorithms {

        TLS_V_12("TLSv1.2"),
        TLS_V_13("TLSv1.3"),
        ;

        private final String algorithm;
    }
}
