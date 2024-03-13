package ru.modiconme.recepies.crypto;

import com.google.common.io.Resources;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkhttpClientTest {

    @SneakyThrows
    public static void main(String[] args) {
//        X509TrustManager trustManagerFactory = Ssls.getUnsafeTrustManagerFactory();
//        X509TrustManager trustManagerFactory = Ssls.getDefaultTrustManagerFactory();
        Path trustStorePath = Path.of("/home/saeed/code/code-recepies/spring-recipes/spring-https-server/src/main/resources/certs/rootCA.jks");
        X509TrustManager trustManagerFactory = Ssls.getUserTrustStore(trustStorePath, "123456");
        Path keyStorePath = Path.of("/home/saeed/code/code-recepies/spring-recipes/spring-https-server/src/main/resources/certs/domain.p12");
        KeyManagerFactory keyManagerFactory = Ssls.getKeyManagerFactory(keyStorePath, "1234");
        SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
        sslContext.init(keyManagerFactory.getKeyManagers(), List.of(trustManagerFactory).toArray(X509TrustManager[]::new), null);

        OkHttpClient okHttpClient = create(sslContext.getSocketFactory(), trustManagerFactory);

        Request req = new Request.Builder()
                .get()
                .url("https://localhost:8080/check")
                .build();
        Response response = okHttpClient.newCall(req).execute();
        System.out.println(response.body());
    }

    public static OkHttpClient create(SSLSocketFactory socketFactory, X509TrustManager x509TrustManager) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .cache(null);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(log::info);
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);

        builder.connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS))
                .hostnameVerifier(Ssls.getUnsafeHostnameVerifier())
                .sslSocketFactory(socketFactory, x509TrustManager)
                .build();

        return builder.build();
    }
}
