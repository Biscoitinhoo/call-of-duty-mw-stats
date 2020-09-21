package com.example.callofdutymw_stats.rest.okhttp;

import com.example.callofdutymw_stats.BuildConfig;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpClientConfiguration {

    private OkHttpClientConfiguration() {
    }

    private static final String API_HOST = "call-of-duty-modern-warfare.p.rapidapi.com";

    public static void configureHttpClientHeader(OkHttpClient.Builder okHttpClient) {
        okHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("x-rapidapi-host", API_HOST)
                        .header("x-rapidapi-key", BuildConfig.API_TOKEN)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
    }
}
