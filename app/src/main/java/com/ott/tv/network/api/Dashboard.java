package com.ott.tv.network.api;

import com.google.gson.annotations.SerializedName;
import com.ott.tv.model.BrowseData;
import com.ott.tv.model.LanguageItem;
import com.ott.tv.model.Video;
import com.ott.tv.model.phando.CategoryType;
import com.ott.tv.model.phando.LatestMoviesTVSeriesList;
import com.ott.tv.model.phando.MapList;
import com.ott.tv.model.phando.MediaplaybackData;
import com.ott.tv.model.phando.ShowWatchlist;
import com.ott.tv.model.phando.UpdateMyListResponse;
import com.ott.tv.model.phando.UserProfile;
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
    Call<List<CategoryType>> getCategoryList(
            @Header("Authorization") String token

    );

    @GET("userappversion")
    Call<AppInfo> getAppInfo(
            @Query("app_type") String appType, @Query("device_id") String device_id

    );

    //menu_v1
    @GET("menu_v1")
    Call<List<CategoryType>> menu(
            @Header("Authorization") String token);

    @GET("mediaplayback")
    Call<MediaplaybackData> getSingleDetailAPI(
            @Header("Authorization") String token,
            @Query("id") String id,
            @Query("type") String type,
            @Query("test") String test
    );

    @GET("tvseries")
    Call<LatestMoviesTVSeriesList> getSingleDetailAPITvSeries(
            @Header("Authorization") String token,
            @Query("id") String id

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

    @GET("mediabygenresid")
    Call<List<ShowWatchlist>> getViewAllListAPI(
            @Header("Authorization") String token,
            @Query("genres_id") String categoryId,
            @Query("limit") String limit,
            @Query("content_type") String type);


    @GET("search-suggestions")
    Call<List<RecommendedModel>> getRecommendedList(
            @Header("Authorization") String token);

    @GET("userProfile")
    Call<UserProfile> getUserProfileAPI(
            @Header("Authorization") String token
    );

    @GET("states-data")
    Call<List<MapList>> getMapDataList(
            @Header("Authorization") String token,
            @Query("state_name") String state_name);

    @GET("mediasearch")
    Call<List<ShowWatchlist>> searchVideo(
            @Header("Authorization") String token,
            @Query("term") String search,
            @Query("genre_id") String genre_id,
            @Query("language_id") String filter,
            @Query("limit") String limit);

    @GET("setContinueWatchingTime")
    Call<UpdateMyListResponse> setContinueWatchingTime(@Header("Authorization") String token,
                                                       @Query("document_id") String documentId,
                                                       @Query("time") String time);

    @GET("languages")
    Call<List<LanguageItem>> getLanguage(
            @Header("Authorization") String token
    );

}
