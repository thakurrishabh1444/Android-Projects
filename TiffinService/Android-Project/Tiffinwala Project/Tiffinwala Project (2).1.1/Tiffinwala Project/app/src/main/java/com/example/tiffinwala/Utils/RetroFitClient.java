package com.example.tiffinwala.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {

    private static RetroFitClient retroFitClient=null;
    private API api;

     private RetroFitClient()
    {
        api=new Retrofit.Builder()
                     .addConverterFactory(GsonConverterFactory.create()).baseUrl(API.BASE_URL).build().create(API.class);
    }

    public static RetroFitClient getInstance(){
        if(retroFitClient==null)
            retroFitClient = new RetroFitClient();
        return  retroFitClient;
    }

    public API getApi() {
        return api;
    }

}
