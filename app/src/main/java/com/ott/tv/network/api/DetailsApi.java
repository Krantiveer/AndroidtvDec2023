package com.ott.tv.network.api;

import com.ott.tv.model.MovieSingleDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface DetailsApi {

    @GET("single_details")
    Call<MovieSingleDetails> getSingleDetail(@Header("API-KEY") String apiKey,
                                             @Query("type") String videoType,
                                             @Query("id") String videoId,
                                             @Query("user_id") String userId);
}
