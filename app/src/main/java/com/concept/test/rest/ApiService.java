package com.concept.test.rest;

import com.concept.test.model.BaseDomain;
import com.concept.test.rest.request.PostRequest;
import com.concept.test.rest.request.SettingRequest;
import com.concept.test.rest.request.UserRequest;
import com.concept.test.rest.response.PostResponse;
import com.concept.test.rest.response.SettingResponse;
import com.concept.test.rest.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiService {
    //@Headers({"Accept: application/json"})
    @POST("register.php")
    Call<BaseDomain> registerUser(@Body UserRequest userRequest);

    @POST("login.php")
    Call<SettingResponse> hitSetting(@Body SettingRequest settingRequest);

    @GET("showPost.php")
    Call<List<PostResponse>> getPostDetail();
}
