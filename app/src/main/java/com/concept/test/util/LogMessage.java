package com.concept.test.util;

import android.util.Log;

import com.concept.test.helper.Values;

public class LogMessage {

    private String component;
    public LogMessage(String component){
        this.component=component;
        Log.e("INSIDE",component);
    }
    public void logERROR(String tag, String message){
        if(Values.loggingEnabled)
            Log.e(tag,message);
    }
    public void logInfo(String tag, String message){
        if(Values.loggingEnabled)
            Log.i(tag,message);
    }

}