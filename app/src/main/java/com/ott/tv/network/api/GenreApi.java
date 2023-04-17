package com.ott.tv.network.api;


import com.ott.tv.model.Genre;
import com.ott.tv.model.phando.ShowWatchlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface GenreApi {

    @GET("all_genre")
    Call<List<Genre>> getGenres(@Header("API-KEY") String apiKey,
                                @Query("page") int page);



    @GET("mediabygenresid")
    Call<List<ShowWatchlist>> getGenresViewall(
            @Header("Authorization") String token,
            @Query("genres_id") String genres_id,
            @Query("limit") String limit,
            @Query("content_type") String type);



}
