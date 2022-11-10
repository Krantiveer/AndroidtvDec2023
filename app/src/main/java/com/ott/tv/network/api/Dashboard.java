package com.ott.tv.network.api;

import com.ott.tv.model.BrowseData;
import com.ott.tv.model.home_content.HomeContent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Dashboard {

    @GET("dashboard")
    Call<List<BrowseData>> getBrowseDataList(
            @Header("API-KEY") String apiKey,
            @Query("type") String type,
            @Query("genre_id") String genre_id,
            @Query("filter") String filter,
            @Query("filter_type") String filter_type,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("category_v1")
    Call<List<BrowseData>> getCategoryList(
            @Header("API-KEY") String apiKey,
            @Query("type") String type,
            @Query("genre_id") String genre_id,
            @Query("filter") String filter,
            @Query("filter_type") String filter_type,
            @Query("limit") int limit,
            @Query("offset") int offset
    );


}
