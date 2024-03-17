package edu.modiconme.crypto;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@UtilityClass
public class OkhttpClientFactory {

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
                .hostnameVerifier(SslUtils.getUnsafeHostnameVerifier())
                .sslSocketFactory(socketFactory, x509TrustManager)
                .build();

        return builder.build();
    }
}
