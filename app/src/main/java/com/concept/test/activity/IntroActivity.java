package com.concept.test.activity;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.Toast;

import com.concept.test.R;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;


public class IntroActivity extends MaterialIntroActivity {
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        addSlide( new SlideFragmentBuilder()
                        .backgroundColor( R.color.color1 )
                        .buttonsColor( R.color.colorAccent )
                        .image( agency.tango.materialintroscreen.R.drawable.ic_next )
                        .title( "title 3" )
                        .description( "Description 3" )
                        .build(),
                new MessageButtonBehaviour( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        showMessage("We provide solutions to make you love your work");
                        openSpeechRecognitionSettings();
                    }
                }, "Work with love" ) );
        addSlide( new SlideFragmentBuilder()
                        .backgroundColor( R.color.color2 )
                        .buttonsColor( R.color.colorAccent )
                        .image( agency.tango.materialintroscreen.R.drawable.ic_next )
                        .title( "Enable Gboard Keyboard" )
                        .description( "First Enable the keyboard you will find in settings" )
                        .build(),
                new MessageButtonBehaviour( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction( "android.settings.INPUT_METHOD_SETTINGS" );
                        intent.addCategory( "android.intent.category.DEFAULT" );
                        startActivity( intent );
                    }
                }, "Enable keyboard" ) );
        addSlide( new SlideFragmentBuilder()
                        .backgroundColor( R.color.color3 )
                        .buttonsColor( R.color.colorAccent )
                        .image( agency.tango.materialintroscreen.R.drawable.ic_next )
                        .title( "Set Gboard " )
                        .description( "Set Gboard as default keyboard for better result, If you dont have one donwoad it." )
                        .build(),
                new MessageButtonBehaviour( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
                        if (imm != null) {
                            imm.showInputMethodPicker();
                        }
                    }
                }, "Keyboard type" ) );

        hideBackButton();
    }

    public boolean openSpeechRecognitionSettings() {
        Intent intent = new Intent( Intent.ACTION_MAIN );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        boolean started = false;
        ComponentName[] components = new ComponentName[]{
                new ComponentName( "com.google.android.googlequicksearchbox", "com.google.android.apps.gsa.settingsui.VoiceSearchPreferences" ),
                new ComponentName( "com.google.android.voicesearch", "com.google.android.voicesearch.VoiceSearchPreferences" ),
                new ComponentName( "com.google.android.googlequicksearchbox", "com.google.android.voicesearch.VoiceSearchPreferences" ),
                new ComponentName( "com.google.android.googlequicksearchbox", "com.google.android.apps.gsa.velvet.ui.settings.VoiceSearchPreferences" ),
        };
        for (ComponentName componentName : components) {
            try {
                intent.setComponent( componentName );
                startActivity( intent );
                started = true;
                break;
            } catch (final Exception e) {
            }
        }
        return started;
    }

    @Override
    public void onFinish() {
        super.onFinish();
        Intent finishIntro = new Intent( this,RegisterActivity.class );
        startActivity( finishIntro );
    }


}