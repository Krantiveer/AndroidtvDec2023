package com.ott.tv.network.api;


import com.ott.tv.model.Genre;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GenreApi {

    @GET("all_genre")
    Call<List<Genre>> getGenres(@Header("API-KEY") String apiKey,
                                @Query("page") int page);
}
