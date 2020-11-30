package com.ftninformatika.moviesactors.Net.WebService;

import com.ftninformatika.moviesactors.Models.Movie;
import com.ftninformatika.moviesactors.Models.Result;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface APIEndpointInterface {
    @GET("/")
    Call<Result> getMoviesByTitle(@QueryMap Map<String, String> query);

    @GET("/")
    Call<Movie> getMoviesByIMDBID(@QueryMap Map<String, String> query);
}

