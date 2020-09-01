package com.example.callofdutymw_stats.rest.retrofit;

import com.example.callofdutymw_stats.rest.okhttp.OkHttpClientConfiguration;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfiguration {

    //Private constructor to hide the implicit public one
    private RetrofitConfiguration() {
    }

    private static final String URL = "https://call-of-duty-modern-warfare.p.rapidapi.com/";

    public static Retrofit getClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        OkHttpClientConfiguration.configureHttpClientHeader(client);
        return new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).client(client.build()).build();
    }
}
