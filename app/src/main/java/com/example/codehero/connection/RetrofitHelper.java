package com.example.codehero.connection;

import com.example.codehero.service.MarvelService;
import com.example.codehero.util.Keys;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static RetrofitHelper instance = null;
    private final MarvelService marvelService;

    public MarvelService getMarvelService() {
        return marvelService;
    }

    private RetrofitHelper() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Keys.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        marvelService = retrofit.create(MarvelService.class);
    }

    public static synchronized RetrofitHelper getInstance() {
        if (instance == null) {
            instance = new RetrofitHelper();
        }

        return instance;
    }
}
