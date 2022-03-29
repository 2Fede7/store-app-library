package it.gruppopam.app_common.network.api.util;

import com.annimon.stream.Stream;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public final class LongTimeoutHttpClientBuilder {

    private LongTimeoutHttpClientBuilder() {
    }

    public static OkHttpClient build(List<Interceptor> interceptors) {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(10);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(10, TimeUnit.SECONDS);
        addAllInterceptors(interceptors, builder);
        return builder.build();
    }

    private static void addAllInterceptors(List<Interceptor> interceptors, OkHttpClient.Builder builder) {
        if (interceptors != null) {
            Stream.of(interceptors).forEach(builder::addInterceptor);
        }
    }
}
