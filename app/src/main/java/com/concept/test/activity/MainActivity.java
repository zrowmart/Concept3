package com.concept.test.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.concept.test.R;
import com.concept.test.helper.Messages;
import com.concept.test.model.BaseDomain;
import com.concept.test.rest.RestHandler;
import com.concept.test.rest.request.PostRequest;
import com.concept.test.rest.response.PostResponse;
import com.concept.test.util.ZrowActivity;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends ZrowActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        Button bi = findViewById( R.id.inputActivity );
        Button reg = findViewById( R.id.register );
        Button login = findViewById( R.id.login );
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



    }

    private void fetchPost(final PostResponse postResponse){
        final Call<List<PostResponse>> call = RestHandler.getApiService().getPostDetail( postResponse );

        call.enqueue( new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body().getStatus().equalsIgnoreCase( "success" )) {
                            Toast.makeText( thisActivity, postResponse.get() + " successfully registerd", Toast.LENGTH_SHORT ).show();
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
}
