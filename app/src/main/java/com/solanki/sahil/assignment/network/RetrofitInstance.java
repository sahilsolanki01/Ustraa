package com.solanki.sahil.assignment.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;

    public Api getApi(){
        OkHttpClient client = new OkHttpClient.Builder().build();

        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://backend.ustraa.com/rest/V1/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(Api.class);
    }
}
