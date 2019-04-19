package com.concept.test.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class MainActivity extends ZrowActivity implements AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener{

     List<PostResponse> postList = new ArrayList<>();
    List<ShowMyPost> myPostList = new ArrayList<>();

    private PostAdapter postAdapter;
    private MyPostAdapter myPostAdapter;
    private RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Spinner categorySpinner;
    RadioGroup filter;
    String myId = null;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        thisActivity = MainActivity.this;
        Locale.setDefault( Locale.forLanguageTag( "hi" ) );
        Log.d("lklk",Locale.getDefault().getLanguage());
        init();
        myId = userLocalStore.fetchUserData().getAutoId();
        Log.d( "lolo",myId );
        recyclerView = findViewById( R.id.recycler_view );
        postAdapter = new PostAdapter( postList, R.layout.post_list, getApplicationContext() );
        myPostAdapter = new MyPostAdapter( myPostList,R.layout.post_list,getApplicationContext() );
        categorySpinner = findViewById( R.id.categories );
        categorySpinner.setOnItemSelectedListener( this );
        filter = findViewById( R.id.filter_post );
        filter.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.all:
                        // do operations specific to this selection
                        fetchPost();

                        break;
                    case R.id.mypost:
                        postList.clear();
                        fetchMyPost();
                        // do operations specific to this selection
                        break;
                    case R.id.favourite:
                        // do operations specific to this selectio
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
                fetchMyPost();
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

    private void fetchMyPost() {
        final Call<List<ShowMyPost>> call = RestHandler.getApiService().getMyPostDetail();
        progressDialog.setMessage( Messages.FETCHING_POST );
        progressDialog.show();
        call.enqueue( new Callback<List<ShowMyPost>>() {
            @Override
            public void onResponse(@NotNull Call<List<ShowMyPost>> call, @NotNull Response<List<ShowMyPost>> response) {
                if (response.isSuccessful()) {
                    try {
//                        long listLength = response.body().size();
                        myPostList = response.body();
                        myPostAdapter = new MyPostAdapter( myPostList, R.layout.post_list, getApplicationContext() );
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
            public void onFailure(@NotNull Call<List<ShowMyPost>> call, @NotNull Throwable t) {
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate( R.menu.menu, menu );
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView( menuItem );
        searchView.setOnQueryTextListener(MainActivity.this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                MenuItem searchItem = findViewById( R.id.action_search );
                SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService( Context.SEARCH_SERVICE );
                SearchView searchView = null;
                if (searchItem != null) {
                    searchView = (SearchView) searchItem.getActionView();
                }
                if (searchView != null) {
                    if (searchManager != null) {
                        searchView.setSearchableInfo( searchManager.getSearchableInfo( MainActivity.this.getComponentName() ) );
                    }
                }
                return (true);
            case R.id.myPost:
                Intent postIntent = new Intent( thisActivity,MyPostActivity.class );
                startActivity(postIntent);
        }
        return(super.onOptionsItemSelected(item));
    }


    public void fetchCategory(){
        final Call<List<Category>> call = RestHandler.getApiService().getCategory();
        call.enqueue( new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categories = response.body();

                ArrayAdapter aa = new ArrayAdapter(thisActivity,android.R.layout.simple_spinner_item,categories);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySpinner.setAdapter(aa);
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                messageHelper.longMessage( Messages.UNABLE_FETCH_CATEGORY );
            }
        } );

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        messageHelper.longMessage( "Yess..." );
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        messageHelper.longMessage( "No..." );

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText = newText.toLowerCase();
        List<PostResponse> searchList = new ArrayList<>(  );

        for(PostResponse searchEntity: postList){
            String title = searchEntity.getTitle().toLowerCase();
            if(title.contains( newText )){
                searchList.add( searchEntity );
            }
        }
        postAdapter.setFilter(searchList);
        return true;
    }
}
