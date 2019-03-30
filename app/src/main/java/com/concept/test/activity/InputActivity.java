package com.concept.test.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.concept.test.R;
import com.concept.test.helper.DbHelper;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.vikramezhil.droidspeech.DroidSpeech;
import com.vikramezhil.droidspeech.OnDSListener;
import com.vikramezhil.droidspeech.OnDSPermissionsListener;

import java.util.List;
import java.util.Random;

public class InputActivity extends AppCompatActivity implements View.OnClickListener, OnDSListener, OnDSPermissionsListener {

    EditText enterMsg;
    ImageView start, stop;
    DroidSpeech droidSpeech;
    String finalMsg = " ";
    DbHelper dbHelper;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_input );
        checkPermission();
        enterMsg = findViewById( R.id.enter_message );
        start = findViewById( R.id.playButton );
        stop = findViewById( R.id.stopButton );
        dbHelper = new DbHelper(this);
        start.setOnClickListener( this );
        stop.setOnClickListener( this );
        //micButton = findViewById( R.id.micButton );
        droidSpeech = new DroidSpeech( this, null );
        droidSpeech.setOnDroidSpeechListener( this );
        droidSpeech.setShowRecognitionProgressView( true );
        droidSpeech.setOneStepResultVerify( true );
        droidSpeech.setOfflineSpeechRecognition( true );
        droidSpeech.setRecognitionProgressMsgColor( Color.WHITE );
        droidSpeech.setOneStepVerifyConfirmTextColor( Color.WHITE );
        droidSpeech.setOneStepVerifyRetryTextColor( Color.WHITE );

    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission( this, Manifest.permission.RECORD_AUDIO )
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
            // Permission is not granted
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
    protected void onPause() {
        super.onPause();

        if (stop.getVisibility() == View.VISIBLE) {
            stop.performClick();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (stop.getVisibility() == View.VISIBLE) {
            stop.performClick();
        }
    }

    // MARK: OnClickListener Method

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.playButton:
                // Starting droid speech
                droidSpeech.startDroidSpeechRecognition();
                // Setting the view visibilities when droid speech is running
                start.setVisibility( View.GONE );
                stop.setVisibility( View.VISIBLE );

                break;

            case R.id.stopButton:
                // Closing droid speech
                droidSpeech.closeDroidSpeechOperations();
                stop.setVisibility( View.GONE );
                start.setVisibility( View.VISIBLE );
                break;
        }
    }

    // MARK: DroidSpeechListener Methods

    @Override
    public void onDroidSpeechSupportedLanguages(String currentSpeechLanguage, List<String> supportedSpeechLanguages) {

        if (supportedSpeechLanguages.contains( "hi-IN" )) {
            // Setting the droid speech preferred language as tamil if found
            droidSpeech.setPreferredLanguage( "hi-IN" );

        }
    }

    @Override
    public void onDroidSpeechRmsChanged(float rmsChangedValue) {
        Log.i( "TAG", "Rms change value = " + rmsChangedValue );
    }

    @Override
    public void onDroidSpeechLiveResult(String liveSpeechResult) {
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDroidSpeechFinalResult(String finalSpeechResult) {
        // Setting the final speech result
        finalMsg += " " + finalSpeechResult;
        this.enterMsg.setText( finalMsg );
        if (droidSpeech.getContinuousSpeechRecognition()) {
            int[] colorPallets1 = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA};
            int[] colorPallets2 = new int[]{Color.YELLOW, Color.RED, Color.CYAN, Color.BLUE, Color.GREEN};
            // Setting random color pallets to the recognition progress view
            droidSpeech.setRecognitionProgressViewColors( new Random().nextInt( 2 ) == 0 ? colorPallets1 : colorPallets2 );
        } else {
            stop.setVisibility( View.GONE );
            start.setVisibility( View.VISIBLE );
        }
    }

    @Override
    public void onDroidSpeechClosedByUser() {
        stop.setVisibility( View.GONE );
        start.setVisibility( View.VISIBLE );
    }

    @Override
    public void onDroidSpeechError(String errorMsg) {
        errorMsg = "Language Pack is not Installed! First Install Language Pack.";
        // Speech error
        Toast.makeText( this, errorMsg, Toast.LENGTH_LONG ).show();

        stop.post( new Runnable() {
            @Override
            public void run() {
                Intent errI = new Intent( InputActivity.this, MainActivity.class );
                startActivity( errI );
                finish();
                // Stop listening
                stop.performClick();
            }
        } );
    }

    // MARK: DroidSpeechPermissionsListener Method

    @Override
    public void onDroidSpeechAudioPermissionStatus(boolean audioPermissionGiven, String errorMsgIfAny) {
        if (audioPermissionGiven) {
            start.post( new Runnable() {
                @Override
                public void run() {
                    // Start listening
                    start.performClick();
                }
            } );
        } else {
            if (errorMsgIfAny != null) {
                // Permissions error
                Toast.makeText( this, errorMsgIfAny, Toast.LENGTH_LONG ).show();
            }

            stop.post( new Runnable() {
                @Override
                public void run() {
                    // Stop listening
                    stop.performClick();
                }
            } );
        }
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
                Intent in = new Intent( this,ListDataActivity.class );
                startActivity( in );
                return (true);
        }
        return (super.onOptionsItemSelected( item ));
    }

    private void sendPost() {
        String added = finalMsg;
        if(enterMsg.length() != 0){
            addData( added,"nothing" );
            enterMsg.getText().clear();
        }else{
            toastMsg( "Write something" );
        }
    }

    public void addData(String item1, String item2) {
        boolean insertdata = dbHelper.addData( finalMsg, "Ankit" );
        if (insertdata) {
            toastMsg( "Data Added" );
        } else {
            toastMsg( "Something went wrong" );
        }
    }


    private void toastMsg(String msg) {
        Toast.makeText( this, msg, Toast.LENGTH_LONG ).show();
    }

}
