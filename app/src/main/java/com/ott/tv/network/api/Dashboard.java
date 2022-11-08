package com.ott.tv.network.api;

import com.ott.tv.model.BrowseData;
import com.ott.tv.model.home_content.HomeContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Dashboard {

/*
    */
/*@GET("home_content")
    Call<List<HomeContent>> getHomeContent(@Header("API-KEY") String apiKey);*//*

    @GET("v1/home_content_for_android")
    Call<HomeContent> getHomeContent(@Header("API-KEY") String apiKey,
                                     @Query("user_id") String userId);
*/

    @GET("dashboard")
    Call<List<BrowseData>> getBrowseDataList(
            @Query("type") String type,
            @Query("genre_id") String genre_id,
            @Query("filter") String filter,
            @Query("filter_type") String filter_type,
            @Query("limit") int limit,
            @Query("offset") int offset
    );
}
