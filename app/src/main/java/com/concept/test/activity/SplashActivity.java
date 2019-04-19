package com.concept.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.widget.Toast;

import com.concept.test.R;
import com.concept.test.util.ConnectionDetector;

public class SplashActivity extends Activity {

    static int SPLASH_TIME_OUT = 3000;
    ConnectionDetector connectionDetector = new ConnectionDetector( this );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        ImageView logo = findViewById( R.id.imgLogo );

        new Handler().postDelayed( new Runnable() {

            @Override
            public void run() {
                if(connectionDetector.isConnectedToInternet()){
                    Intent i = new Intent(SplashActivity.this, RegisterActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(SplashActivity.this, InputActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);

    }
}
