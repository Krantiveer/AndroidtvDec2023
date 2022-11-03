package com.ott.tv.network.api;


import com.ott.tv.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SendOTPApi {

    @FormUrlEncoded
    @POST("send-otp")
    Call<User> postSendOTP(@Header("API-KEY") String apiKey,
                               @Field("mobile") String mobile,
                               @Field("country_code") String country_code);
    @FormUrlEncoded
    @POST("verify-otp")
    Call<User> postVerifyOTP(@Header("API-KEY") String apiKey,
                               @Field("mobile") String mobile,
                               @Field("otp") String otp);




}
