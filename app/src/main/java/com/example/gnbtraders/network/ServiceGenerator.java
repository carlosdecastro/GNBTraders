package com.example.gnbtraders.network;

import com.example.gnbtraders.utils.Config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Config.API_HOST)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static GnbApi gnbApi = retrofit.create(GnbApi.class);

    public static GnbApi getGnbApi(){
        return gnbApi;
    }
}
