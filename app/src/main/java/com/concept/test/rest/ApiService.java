package com.concept.test.rest;

import com.concept.test.model.BaseDomain;
import com.concept.test.model.Category;
import com.concept.test.model.CategoryResult;
import com.concept.test.rest.request.CategoryRequest;
import com.concept.test.rest.request.LikedOrNotRequest;
import com.concept.test.rest.request.PostRequest;
import com.concept.test.rest.request.SettingRequest;
import com.concept.test.rest.request.ShowMyPost;
import com.concept.test.rest.request.UserRequest;
import com.concept.test.rest.response.PostResponse;
import com.concept.test.rest.response.SettingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


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

    @GET("showMyPost.php")
    Call<List<ShowMyPost>> getMyPostDetail(@Query("autoId") String autoId);


    @GET("fetchCategory.php")
    Call<List<Category>> getCategory();

    @GET("categoryResult.php")
    Call<List<CategoryRequest>> getCategoryResult(@Query("category") String category);

    @GET("likedOrNot.php")
    Call<LikedOrNotRequest> getLikeStatus(@Query( "autoId" ) String autoId,@Query( "postId" ) String postId);

}
