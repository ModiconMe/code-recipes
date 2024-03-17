package edu.modiconme.crypto;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import java.nio.file.Path;

import static edu.modiconme.crypto.OkhttpClientFactory.create;
import static edu.modiconme.crypto.SslUtils.*;

class OkHttpTest {

    @Test
    @Disabled("server request")
    void shouldAuthenticateHttpsSuccess() throws Exception {
//        X509TrustManager unsafeTrustManager = getUnsafeTrustManagerFactory();
//        X509TrustManager defaultTrustManager = getDefaultTrustManager();

        Path trustStorePath = Path.of("/home/saeed/code/code-recepies/spring-recipes/spring-https-server/src/main/resources/certs/rootCA.jks");
        X509TrustManager[] trustManagers = getUserTrustManager(trustStorePath, "changeit".toCharArray());

        Path keyStorePath = Path.of("/home/saeed/code/code-recepies/spring-recipes/spring-https-server/src/main/resources/certs/domain.p12");
        KeyManager[] keyManagers = getClientKeyManagers(keyStorePath, "1234".toCharArray());

        SSLContext sslContext = SSLContext.getInstance(SslAlgorithms.TLS_V_12.getAlgorithm());
        sslContext.init(keyManagers, trustManagers, null);

        OkHttpClient okHttpClient = create(
                sslContext.getSocketFactory(),
                /*
                 * Данный trustManager не играет роли, главный тот, что в SSLContext
                 */
                trustManagers[0]);

        Request req = new Request.Builder()
                .get()
                .url("https://localhost:8080/check")
                .build();
        try (Response response = okHttpClient.newCall(req).execute()) {
            System.out.println(response.body());
        }
    }
}
