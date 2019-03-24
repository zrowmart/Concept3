package com.concept.test.helper;

import android.app.Activity;
import android.app.ProgressDialog;

public class ZrowProgressDialog {
    Activity activity;
    ProgressDialog progressDialog;

    public ZrowProgressDialog(Activity activity) {
        this.activity = activity;
        progressDialog = new ProgressDialog( activity, ProgressDialog.STYLE_SPINNER );
    }

    public boolean isShowing() {
        if (progressDialog != null) {
            return progressDialog.isShowing();
        } else {
            return false;
        }
    }

    public void setMessage(String message) {
        if (progressDialog != null) {
            progressDialog.setMessage( message );
        }
    }

    public void setCancelable(boolean cancelable) {
        if (progressDialog != null) {
            progressDialog.setCancelable( cancelable );
        }
    }

    public void setTitle(String title) {
        if (progressDialog != null) {
            progressDialog.setTitle( title );
        }
    }

    public void show() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            activity.runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    progressDialog.show();
                }
            } );
        }
    }

    public void dismiss() {
        if (progressDialog != null && progressDialog.isShowing()) {
            activity.runOnUiThread( new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                }
            } );
        }
    }

}
