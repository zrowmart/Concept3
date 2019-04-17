package com.concept.test.activity;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.concept.test.R;
import com.concept.test.adapter.PostAdapter;
import com.concept.test.helper.Messages;
import com.concept.test.rest.RestHandler;
import com.concept.test.rest.response.PostResponse;
import com.concept.test.util.ZrowActivity;

import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyPostActivity extends ZrowActivity {
    private List<PostResponse> postList = new ArrayList<>();
    private PostAdapter postAdapter;
    private RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_my_post );
        thisActivity = MyPostActivity.this;
        init();
        recyclerView =  findViewById( R.id.recycler_view );
        postAdapter = new PostAdapter( postList,R.layout.post_list,getApplicationContext() );
        mSwipeRefreshLayout = findViewById( R.id.swiperefresh );
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPost();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(thisActivity,InputActivity.class);
                startActivity( intent );
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter( postAdapter );
        fetchPost();
    }

    private void fetchPost(){
        final Call<List<PostResponse>> call = RestHandler.getApiService().getPostDetail();
        progressDialog.setMessage( Messages.FETCHING_POST);
        progressDialog.show();
        call.enqueue( new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<PostResponse>> call, @NotNull Response<List<PostResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            long listLength = response.body().size();
                        }
                        List<PostResponse> postList = response.body();
                        postAdapter = new PostAdapter( postList,R.layout.post_list,getApplicationContext() );
                        recyclerView.setAdapter( postAdapter );
                        if (mSwipeRefreshLayout.isRefreshing()){
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        progressDialog.dismiss();

                    } catch (Exception err) {
                        err.printStackTrace();
                    }
                } else {
                    if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                        progressDialog.dismiss();
                        messageHelper.shortMessage( Messages.WRONG_VALUE );

                    } else {
                        try {
                            messageHelper.shortMessage( Messages.PROBLEM_CONNECT_SERVER );
                            if (response.errorBody() != null) {
                                Log.e( "--> onResponse", "error -> " + response.errorBody().string() );
                            }
                        } catch (Exception err) {
                            Log.e( "--> Exception", Arrays.toString( err.getStackTrace() ) );
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<PostResponse>> call, @NotNull Throwable t) {
                try {
                    progressDialog.dismiss();
                    messageHelper.shortMessage( Messages.PROBLEM_CONNECT_SERVER );
                    if (t instanceof Exception) {
                        t.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } );
    }



}
