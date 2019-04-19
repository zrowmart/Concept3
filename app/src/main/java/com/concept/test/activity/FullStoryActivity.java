package com.concept.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.concept.test.R;
import com.concept.test.util.ZrowActivity;

import java.util.Objects;

public class FullStoryActivity extends ZrowActivity {

    TextView textTitle,textDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_full_story );

        textTitle = findViewById( R.id.fullTextTitle );
        textDetail = findViewById( R.id.fullTextStory );
        Intent result = getIntent();

        String titleresult = null;
        String Detailresult = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            titleresult = Objects.requireNonNull( result.getExtras() ).getString("PostTitle");
             Detailresult = result.getExtras().getString("PostDetail");

        }

        textDetail.setText( Detailresult );
        textTitle.setText( titleresult );
    }
}
