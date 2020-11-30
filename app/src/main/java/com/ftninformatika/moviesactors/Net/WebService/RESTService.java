package com.ftninformatika.moviesactors.Net.WebService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTService {
    public static final String BASE_URL = "";

    public static Retrofit getRetrofitInstance() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static APIEndpointInterface apiInterface() {

        return getRetrofitInstance().create(APIEndpointInterface.class);
    }
}
