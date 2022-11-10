package com.ott.tv.network.api;


import com.ott.tv.model.Genre;
import com.ott.tv.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

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



  /*  //qr api call
    GET {{url}}/check-access-code?access_code={{access_code}}
    headers {
        Accept:application/json
        publisherid:{{publisherid}}
        Params {
            access_code:{{access_code}}
        }
    }*/
    @GET("check-access-code")
    Call<User> getCheckAccessCode(@Header("API-KEY") String apiKey,
                                @Query("access_code") String accessCode);



}
