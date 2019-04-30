package com.concept.test.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.concept.test.R;
import com.concept.test.adapter.MyPostAdapter;
import com.concept.test.adapter.PostAdapter;
import com.concept.test.helper.Messages;
import com.concept.test.model.Category;
import com.concept.test.model.CategoryResult;
import com.concept.test.rest.RestHandler;
import com.concept.test.rest.request.ShowMyPost;
import com.concept.test.rest.response.PostResponse;
import com.concept.test.util.ZrowActivity;

import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends ZrowActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener {

    List<PostResponse> postList = new ArrayList<>();
    List<ShowMyPost> myPostList = new ArrayList<>();
    List<CategoryResult> categoryPostList = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    Spinner categorySpinner;
    RadioGroup filter;
    String myId = "";
    private PostAdapter postAdapter;
    private MyPostAdapter myPostAdapter;
    private RecyclerView recyclerView;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        thisActivity = MainActivity.this;
        Locale.setDefault( Locale.forLanguageTag( "hi" ) );
        init();
//        myId = userLocalStore.fetchUserData().getAutoId();
        recyclerView = findViewById( R.id.recycler_view );
        postAdapter = new PostAdapter( postList, R.layout.post_list, getApplicationContext() );
        myPostAdapter = new MyPostAdapter( myPostList, R.layout.post_list, getApplicationContext() );
        categorySpinner = findViewById( R.id.categories );
        categorySpinner.setOnItemSelectedListener( this );
        filter = findViewById( R.id.filter_post );
        filter.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.all:
                        myPostList.clear();
                        fetchPost();
                        break;
                    case R.id.mypost:
                        postList.clear();
                        Log.d( "iuyiuy",myId );
                        fetchMyPost( myId);
                        break;
                    case R.id.favourite:
                        Toast.makeText( thisActivity, "Now!!!", Toast.LENGTH_SHORT ).show();
                        break;
                }
            }
        } );
        mSwipeRefreshLayout = findViewById( R.id.swiperefresh );
        mSwipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchPost();
                fetchMyPost( myId );
            }
        } );
        FloatingActionButton fab = findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( thisActivity, InputActivity.class );
                startActivity( intent );
            }
        } );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( mLayoutManager );
        recyclerView.setAdapter( postAdapter );
        recyclerView.setAdapter( myPostAdapter );
        fetchPost();
        fetchCategory();
    }


    //Fetch Post
    private void fetchPost() {
        final Call<List<PostResponse>> call = RestHandler.getApiService().getPostDetail();
        progressDialog.setMessage( Messages.FETCHING_POST );
        progressDialog.show();
        call.enqueue( new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<PostResponse>> call, @NotNull Response<List<PostResponse>> response) {
                if (response.isSuccessful()) {
                    try {
//                        long listLength = response.body().size();
                        postList = response.body();
                        postAdapter = new PostAdapter( postList, R.layout.post_list, getApplicationContext() );
                        recyclerView.setAdapter( postAdapter );
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing( false );
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

    //Fetch Post using autoId
    private void fetchMyPost(final String autoId) {
        final Call<List<ShowMyPost>> call = RestHandler.getApiService().getMyPostDetail( autoId );
        progressDialog.setMessage( Messages.FETCHING_POST );
        progressDialog.show();
        Log.d( "iuyiuy", "Log 3" );
        call.enqueue( new Callback<List<ShowMyPost>>() {
            @Override
            public void onResponse(@NotNull Call<List<ShowMyPost>> call, @NotNull Response<List<ShowMyPost>> response) {
                if (response.isSuccessful()) {

                    try {
                        Log.d( "iuyiuy", "Log 4" );
//                        long listLength = response.body().size();
                        myPostList = response.body();
                        Log.d( "pqpq", myPostList + " erre" );
                        myPostAdapter = new MyPostAdapter( myPostList, R.layout.post_list, getApplicationContext() );
                        recyclerView.setAdapter( myPostAdapter );
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing( false );
                        }
                        progressDialog.dismiss();

                    } catch (Exception err) {
                        Log.d( "iuyiuy", "Log 5" );

                        err.printStackTrace();
                    }
                } else {
                    Log.d( "iuyiuy", "Log 6" );

                    if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                        progressDialog.dismiss();
                        messageHelper.shortMessage( Messages.WRONG_VALUE );
                        Log.d( "iuyiuy", "Log 7" );

                    } else {
                        try {
                            messageHelper.shortMessage( Messages.PROBLEM_CONNECT_SERVER );
                            if (response.errorBody() != null) {
                                Log.e( "--> onResponse", "error -> " + response.errorBody().string() );
                            }
                        } catch (Exception err) {
                            Log.e( "--> Exception", Arrays.toString( err.getStackTrace() ) );
                        }
                        Log.d( "iuyiuy", "Log 8" );
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<ShowMyPost>> call, @NotNull Throwable t) {
                try {
                    Log.d( "iuyiuy", "Log 9" );

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

//    Fetch post using category
    private void fetchFromCategory(String categoryValue) {
        final Call<List<CategoryResult>> call = RestHandler.getApiService().getCategoryResult( categoryValue );
        progressDialog.setMessage( Messages.FETCHING_POST );
        progressDialog.show();
        call.enqueue( new Callback<List<CategoryResult>>() {
            @Override
            public void onResponse(@NotNull Call<List<CategoryResult>> call, @NotNull Response<List<CategoryResult>> response) {
                if (response.isSuccessful()) {
                    try {
//                        long listLength = response.body().size();
                        categoryPostList = response.body();
                        postAdapter = new PostAdapter( postList, R.layout.post_list, getApplicationContext() );
                        recyclerView.setAdapter( postAdapter );
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing( false );
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
            public void onFailure(@NotNull Call<List<CategoryResult>> call, @NotNull Throwable t) {
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

    //Fetch category for spinner
    public void fetchCategory() {
        final Call<List<Category>> call = RestHandler.getApiService().getCategory();
        call.enqueue( new Callback<List<Category>>() {
            @Override
            public void onResponse(@NotNull Call<List<Category>> call, @NotNull Response<List<Category>> response) {
                List<Category> categories = response.body();
                ArrayAdapter<Category> aa = null;
                if (categories != null) {
                    aa = new ArrayAdapter<>( thisActivity, android.R.layout.simple_spinner_item, categories );
                }
                if (aa != null) {
                    aa.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                }
                categorySpinner.setAdapter( aa );
            }

            @Override
            public void onFailure(@NotNull Call<List<Category>> call, @NotNull Throwable t) {
                messageHelper.longMessage( Messages.UNABLE_FETCH_CATEGORY );
            }
        } );

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        List<PostResponse> searchList = new ArrayList<>();

        for (PostResponse searchEntity : postList) {
            String title = searchEntity.getTitle().toLowerCase();
            if (title.contains( newText )) {
                searchList.add( searchEntity );
            }
        }
        postAdapter.setFilter( searchList );
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String categoryValue = categorySpinner.getSelectedItem().toString();
        Log.d( "lkop","Selected category is " + categoryValue );
        if(!categoryValue.isEmpty()){
            fetchFromCategory( categoryValue );
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

}
