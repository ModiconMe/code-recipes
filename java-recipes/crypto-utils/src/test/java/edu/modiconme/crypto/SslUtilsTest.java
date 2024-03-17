package edu.modiconme.crypto;

import org.junit.jupiter.api.Test;

import javax.net.ssl.KeyManager;
import javax.net.ssl.X509TrustManager;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SslUtilsTest {

    @Test
    void shouldLoadDefaultJavaTrustManager() throws Exception {
        X509TrustManager defaultTrustManager = SslUtils.getDefaultTrustManager();
        assertEquals(138, defaultTrustManager.getAcceptedIssuers().length);
    }

    @Test
    void shouldLoadUnsafeJavaTrustManager() {
        X509TrustManager unsafeTrustManager = SslUtils.getUnsafeTrustManagerFactory();
        assertEquals(0, unsafeTrustManager.getAcceptedIssuers().length);
    }

    @Test
    void shouldLoadUserTrustManager() throws Exception {
        Path trustStorePath = Path.of("/home/saeed/code/code-recepies/spring-recipes/spring-https-server/src/main/resources/certs/rootCA.jks");
        X509TrustManager[] userTrustManagers = SslUtils.getUserTrustManager(trustStorePath, "123456".toCharArray());
        assertEquals(1, userTrustManagers.length);
        assertEquals(1, userTrustManagers[0].getAcceptedIssuers().length);
    }

    @Test
    void shouldLoadUserKetManager() throws Exception {
        Path keyStorePath = Path.of("/home/saeed/code/code-recepies/spring-recipes/spring-https-server/src/main/resources/certs/domain.p12");
        KeyManager[] keyManagers = SslUtils.getClientKeyManagers(keyStorePath, "1234".toCharArray());
        assertEquals(1, keyManagers.length);
    }
}
