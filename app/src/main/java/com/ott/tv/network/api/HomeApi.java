package com.ott.tv.network.api;

import com.ott.tv.model.home_content.HomeContent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface HomeApi {

    /*@GET("home_content")
    Call<List<HomeContent>> getHomeContent(@Header("API-KEY") String apiKey);*/
    @GET("v1/home_content_for_android")
    Call<HomeContent> getHomeContent(
            @Header("Authorization") String token);
}
