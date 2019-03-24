package com.concept.test.helper;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

public class MessageHelper {
    Activity thisActivity;
    View v;
    public MessageHelper(View v, Activity thisActivity)
    {
        this.v = v;
        this.thisActivity=thisActivity;
    }

    public void shortMessage(final String message){
        thisActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(v!=null)
                    Snackbar.make(v,message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    public void longMessage(final String message){
        thisActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(v!=null)
                    Snackbar.make(v,message, Snackbar.LENGTH_LONG).show();
            }
        });
    }
}