package com.example.callofdutymw_stats.rest.retrofit;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfiguration {

    private static final String URL = "https://call-of-duty-modern-warfare.p.rapidapi.com/";
    private static final String API_HOST = "call-of-duty-modern-warfare.p.rapidapi.com";
    private static final String API_KEY = "xx-xx";

    public static Retrofit getClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("x-rapidapi-host", API_HOST)
                        .header("x-rapidapi-key", API_KEY)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();

        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(client).build();
    }

}
