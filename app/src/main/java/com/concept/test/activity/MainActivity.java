package com.concept.test.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.concept.test.R;
import com.concept.test.adapter.PostAdapter;
import com.concept.test.helper.Messages;
import com.concept.test.model.PostClass;
import com.concept.test.rest.RestHandler;
import com.concept.test.rest.response.PostResponse;
import com.concept.test.util.ZrowActivity;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends ZrowActivity {

    private List<PostClass> postClassList = new ArrayList<>();
    private PostAdapter postAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        recyclerView =  findViewById( R.id.recycler_view );
        postAdapter = new PostAdapter(postClassList);
        Button bi = findViewById( R.id.inputActivity );
        Button reg = findViewById( R.id.register );
        //Button login = findViewById( R.id.login );
        bi.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( MainActivity.this,InputActivity.class );
                startActivity( i );
            }
        } );


        reg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( MainActivity.this,RegisterActivity.class );
                startActivity( i );
            }
        } );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter( postAdapter );
        prepareMovieData();
    }

    private void fetchPost(final PostResponse postResponse){
        final Call<List<PostResponse>> call = RestHandler.getApiService().getPostDetail( postResponse );

        call.enqueue( new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body().toString().equalsIgnoreCase( "success" )) {
                            Toast.makeText( thisActivity, postResponse.getStatus() + " successfully registerd", Toast.LENGTH_SHORT ).show();
                            Intent i = new Intent( thisActivity, MainActivity.class );
                            startActivity( i );
                            finish();
                        } else {
                            progressDialog.dismiss();
                        }
                    } catch (Exception err) {
                        err.printStackTrace();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                } else {
                    if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                        progressDialog.dismiss();
                        messageHelper.shortMessage( Messages.WRONG_VALUE );

                    } else {
                        try {
                            messageHelper.shortMessage( Messages.PROBLEM_CONNECT_SERVER );
                            Log.e( "--> onResponse", "error -> " + response.errorBody().string() );
                        } catch (Exception err) {
                            Log.e( "--> Exception", err.getStackTrace().toString() );
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {
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
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) MainActivity.this.getSystemService( Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(MainActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }


    private void prepareMovieData() {
        PostClass postClass = new PostClass("Ankit Shukla", "Do nothing only sleeps, put phone silent mode 1000 missed call in a year ", "2015");
        postClassList.add(postClass);

        postClass = new PostClass("Abhishek Gupta", "Out of 1000 missed call, 800 call I made to ankit", "2015");
        postClassList.add(postClass);

        postClass = new PostClass("Israt ", "Android Developer, know Node.js as well.", "2015");
        postClassList.add(postClass);

        postClass = new PostClass("Kuch bhi", "Kuch bhi likh diya kyuki static data chahiye tha na", "2015");
        postClassList.add(postClass);


        postAdapter.notifyDataSetChanged();
    }
}
