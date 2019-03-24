package com.concept.test.rest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level;

public class RestHandler {

    private static ApiService API_SERVICE;

    //public static String BASE_URL = "http://zrow.in/api/";
    public static String BASE_URL = "https://ruchirishi.net/concept/";

    public static ApiService getApiService()
    {
        if (API_SERVICE == null)
        {
            //logging interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel( Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging);
            httpClient.connectTimeout(200, TimeUnit.SECONDS);
            httpClient.readTimeout(200, TimeUnit.SECONDS);


            Retrofit adapter = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

            API_SERVICE = adapter.create(ApiService.class);
        }
        return API_SERVICE;
    }
}