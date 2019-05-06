package com.concept.test.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.concept.test.R;
import com.concept.test.helper.Messages;
import com.concept.test.rest.RestHandler;
import com.concept.test.rest.request.LikedOrNotRequest;
import com.concept.test.util.ZrowActivity;

import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullStoryActivity extends ZrowActivity {

    TextView textTitle, textDetail;
    ImageView likeButton;
    Boolean liked = false;
    String autoId = "STITICV51I" ;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_full_story );
        textTitle = findViewById( R.id.fullTextTitle );
        textDetail = findViewById( R.id.fullTextStory );
        likeButton = findViewById( R.id.likeButtonOff );
//        autoId = userLocalStore.fetchUserData().getAutoId();
//        Log.d( "uyhj",autoId );
        Intent result = getIntent();
        String titleresult = null;
        String Detailresult = null;
        String postId = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            titleresult = Objects.requireNonNull( result.getExtras() ).getString( "PostTitle" );
            Detailresult = result.getExtras().getString( "PostDetail" );
            postId = result.getExtras().getString( "PostId" );
        }

        likeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!liked) {
                    likeButton.setImageResource( R.drawable.ic_thumb_up_black_24dp );
                    liked = true;
                } else {
                    likeButton.setImageResource( R.drawable.ic_thumb_up_like_24dp );
                    liked = false;
                }
            }
        } );
        textDetail.setText( Detailresult );
        textTitle.setText( titleresult );
        fetchFromCategory( autoId,postId );
    }

    private void fetchFromCategory(final String autoId, final String postId) {
        final Call<LikedOrNotRequest> call = RestHandler.getApiService().getLikeStatus( autoId, postId );
        Log.d( "lkop", "1" );
        call.enqueue( new Callback<LikedOrNotRequest>() {
            @Override
            public void onResponse(@NotNull Call<LikedOrNotRequest> call, @NotNull Response<LikedOrNotRequest> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            Log.d( "lkop","2" );
                            if (response.body().getStatus().equalsIgnoreCase( "Liked" )) {
//                                progressDialog.dismiss();
                                likeButton.setImageResource( R.drawable.ic_thumb_up_like_24dp );
                            } else {
                                likeButton.setImageResource( R.drawable.ic_thumb_up_black_24dp );
                            }
                        }
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
            public void onFailure(@NotNull Call<LikedOrNotRequest> call, @NotNull Throwable t) {
                Log.d( "lkop", "4" );
                try {
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

