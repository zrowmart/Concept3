package com.concept.test.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.concept.test.R;
import com.concept.test.util.ZrowActivity;

import java.util.Objects;

public class FullStoryActivity extends ZrowActivity {

    TextView textTitle, textDetail;
    ImageView likeButton;
    Boolean liked = false;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_full_story );

        textTitle = findViewById( R.id.fullTextTitle );
        textDetail = findViewById( R.id.fullTextStory );
        likeButton = findViewById( R.id.likeButtonOff );
        Intent result = getIntent();
        String titleresult = null;
        String Detailresult = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            titleresult = Objects.requireNonNull( result.getExtras() ).getString( "PostTitle" );
            Detailresult = result.getExtras().getString( "PostDetail" );
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
    }
}
