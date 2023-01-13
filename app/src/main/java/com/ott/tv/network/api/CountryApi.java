package com.ott.tv.network.api;

import com.ott.tv.model.CountryModel;
import com.ott.tv.video_service.PlaybackModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CountryApi {



    @GET("states-data")
    Call<List<PlaybackModel>> getAllCountry(@Query("state_name") String type);

}
