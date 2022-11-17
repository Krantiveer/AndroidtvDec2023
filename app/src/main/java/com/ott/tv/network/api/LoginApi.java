package com.ott.tv.network.api;


import com.ott.tv.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginApi {

    @FormUrlEncoded
    @POST("login")
    Call<User> postLoginStatus(
                               @Field("email") String email,
                               @Field("password") String password);
}
