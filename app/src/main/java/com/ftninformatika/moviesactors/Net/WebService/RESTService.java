package com.ftninformatika.moviesactors.Net.WebService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RESTService {
    public static final String BASE_URL = "https://www.omdbapi.com";
    public static final String API_KEY = "d3824e34";

    public static Retrofit getRetrofitInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    public static APIEndpointInterface apiInterface() {
        APIEndpointInterface apiService = getRetrofitInstance().create(APIEndpointInterface.class);

        return apiService;
    }
}
