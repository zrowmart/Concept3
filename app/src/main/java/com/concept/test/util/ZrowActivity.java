package com.concept.test.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.concept.test.helper.MessageHelper;
import com.concept.test.helper.Messages;
import com.concept.test.helper.ZrowProgressDialog;

@SuppressLint("Registered")
public class ZrowActivity extends AppCompatActivity {
    public MessageHelper messageHelper;
    public Activity thisActivity;
    public View snackbarLayout;
    public String component;
    public UserLocalStore userLocalStore;

    public LogMessage logMessage;
    public ZrowProgressDialog progressDialog;
    public ConnectionDetector connectionDetector;
    int currentAPIVersion;
    public ZrowActivity() {
    }
    public void init() {
        currentAPIVersion = Build.VERSION.SDK_INT;
        component = thisActivity.getClass().getName();
        messageHelper = new MessageHelper(snackbarLayout, thisActivity);
        userLocalStore = new UserLocalStore(thisActivity);
        logMessage = new LogMessage(component);
        progressDialog = new ZrowProgressDialog(thisActivity);
        connectionDetector = new ConnectionDetector(thisActivity);
        if (!connectionDetector.isConnectedToInternet()) {
            messageHelper.shortMessage( Messages.NO_INTERNET_CONNECTIVITY);
        }
    }
}