package com.concept.test.rest;

import com.concept.test.model.BaseDomain;
import com.concept.test.rest.request.PostRequest;
import com.concept.test.rest.request.SettingRequest;
import com.concept.test.rest.request.ShowMyPost;
import com.concept.test.rest.request.UserRequest;
import com.concept.test.rest.response.PostResponse;
import com.concept.test.rest.response.SettingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface ApiService {
    //@Headers({"Accept: application/json"})
    @POST("register.php")
    Call<BaseDomain> registerUser(@Body UserRequest userRequest);

    @POST("login.php")
    Call<SettingResponse> hitSetting(@Body SettingRequest settingRequest);

    @GET("showPost.php")
    Call<List<PostResponse>> getPostDetail();

    @POST("addPost.php")
    Call<PostResponse> insertUserPost(@Body PostRequest postRequest);

    @POST("showMyPost.php")
    Call<ShowMyPost> showMyPost(@Body ShowMyPost showMyPost);

//    @GET("fetchCategory.php")
//    Call<List<CategoryResponse>> getCategory();
}
