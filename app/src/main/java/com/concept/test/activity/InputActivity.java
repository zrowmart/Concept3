package com.concept.test.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.concept.test.R;
import com.concept.test.helper.DatabaseHelper;
import com.concept.test.helper.Messages;
import com.concept.test.rest.RestHandler;
import com.concept.test.rest.request.PostRequest;
import com.concept.test.rest.response.PostResponse;
import com.concept.test.util.ConnectionDetector;
import com.concept.test.util.ZrowActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputActivity extends ZrowActivity {

    EditText enterMsg;
    ImageView start, stop;
    String finalMsg = "";
    String autoId;
    DatabaseHelper dbHelper;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_input );
        thisActivity = InputActivity.this;
        init();
        dbHelper = new DatabaseHelper( thisActivity );
        checkPermission();
        ConnectionDetector connectionDetector = new ConnectionDetector( thisActivity );
        if (!connectionDetector.isConnectedToInternet()) {
            Toast.makeText( thisActivity, "You are using app in offline mode!!!", Toast.LENGTH_SHORT ).show();
        }
        enterMsg = findViewById( R.id.enter_message );
        start = findViewById( R.id.playButton );
        stop = findViewById( R.id.stopButton );
        start.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeechInput( v );
            }
        } );
        autoId = userLocalStore.fetchUserData().getAutoId();
    }


    public void getSpeechInput(View view) {
        Intent intent = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        intent.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM );
        intent.putExtra( RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 500000 );
        intent.putExtra( RecognizerIntent.EXTRA_LANGUAGE, "hi-IN" );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.putExtra( RecognizerIntent.EXTRA_PREFER_OFFLINE, "hi-IN" );
        }
        intent.putExtra( RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "hi-IN" );
        intent.putExtra( RecognizerIntent.EXTRA_PROMPT, "बोलना शुरू करें" );
        if (intent.resolveActivity( getPackageManager() ) != null) {
            startActivityForResult( intent, 10 );
        } else {
            toastMsg( "Your Device does not support speech Input" );
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
                    finalMsg += result.get( 0 ) + "| ";
                    enterMsg.setText( finalMsg );
                }
                break;
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission( this, Manifest.permission.RECORD_AUDIO )
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }
    }

    private void requestPermission() {
        Dexter.withActivity( this )
                .withPermissions(
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.INTERNET )
                .withListener( new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            Toast.makeText( getApplicationContext(), "Without Permission you can not speak and type..", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                } ).
                withErrorListener( new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText( getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT ).show();
                    }
                } )
                .onSameThread()
                .check();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.send, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                //add the function to perform here
                sendPost();
                return (true);
            case R.id.list_data:
                Intent in = new Intent( this, ListPost.class );
                startActivity( in );
                return (true);
        }
        return (super.onOptionsItemSelected( item ));
    }

    private void sendPost() {
        String added = enterMsg.getText().toString();
        if (enterMsg.getText().toString().trim().length() > 80) {
            PostRequest postRequest = new PostRequest();
            if (!connectionDetector.isConnectedToInternet()) {
                dbHelper.insertData( added );
                Toast.makeText( thisActivity, "You are using app in offline mode!!!", Toast.LENGTH_SHORT ).show();
            } else {
                postRequest.setAutoId( autoId );
                boolean isInsert = dbHelper.insertData( added );
                if (!isInsert) {
                    toastMsg( "Something went wrong" );
                }
                postRequest.setPost( added );
                insertUserPost( postRequest );
                enterMsg.getText().clear();
            }
            finish();
//            toastMsg( "We will verify story and publish it soon! " );
        } else {
            toastMsg( "आपकी कहानी काफी छोटी है, कुछ और शब्द लिखे " );
        }
    }


    private void toastMsg(String msg) {
        Toast.makeText( this, msg, Toast.LENGTH_LONG ).show();
    }

    private void insertUserPost(PostRequest postRequest) {
        final Call<PostResponse> call = RestHandler.getApiService().insertUserPost( postRequest );
        progressDialog.setMessage( Messages.AUTHENTICATE_USER_PD_MSG );
        progressDialog.show();
        call.enqueue( new Callback<PostResponse>() {
            @Override
            public void onResponse(@NotNull Call<PostResponse> call, @NotNull Response<PostResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase( "success" )) {
                                progressDialog.dismiss();
                                finish();
                            } else {
                                progressDialog.dismiss();
                                String msg = response.body().getMsg();
                                //messageHelper.shortMessage(Messages.WRONG_VALUE);
                                messageHelper.shortMessage( msg );
                            }
                        }
                    } catch (Exception err) {
                        err.printStackTrace();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                } else {
                    if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                        progressDialog.dismiss();
                        //messageHelper.shortMessage(Messages.WRONG_VALUE);
                        messageHelper.shortMessage( Messages.NO_INTERNET_CONNECTIVITY );

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
            public void onFailure(@NotNull Call<PostResponse> call, @NotNull Throwable t) {
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
