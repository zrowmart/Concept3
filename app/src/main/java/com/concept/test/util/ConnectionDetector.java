package com.concept.test.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class ConnectionDetector {

    private Context context;

    ConnectionDetector(Context context) {
        this.context = context;
    }

    boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

