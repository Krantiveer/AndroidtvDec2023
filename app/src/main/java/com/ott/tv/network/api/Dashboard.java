package com.ott.tv.network.api;

import com.ott.tv.fragments.ShowWatchlistFragment;
import com.ott.tv.model.BrowseData;
import com.ott.tv.model.home_content.HomeContent;
import com.ott.tv.model.phando.MediaplaybackData;
import com.ott.tv.model.phando.ShowWatchlist;
import com.ott.tv.model.phando.Wishlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Dashboard {

    @GET("dashboard")
    Call<List<BrowseData>> getBrowseDataList(
            @Header("Authorization") String token,
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

    @GET("mediaplayback")
    Call<MediaplaybackData> getSingleDetailAPI(
            @Header("Authorization") String token,
            @Query("id") String id,
            @Query("type") String type,
            @Query("test") String test
    );


    @FormUrlEncoded
    @POST("addwishlist")
    Call<Wishlist> updateWatchList(
            @Header("Authorization") String token,
            @Field("id") String id,
            @Field("type") String type,
            @Field("value") Integer value);


    @GET("showwishlist")
    Call<List<ShowWatchlist>> getShowWishListAPI(
            @Header("Authorization") String token
    );
}
