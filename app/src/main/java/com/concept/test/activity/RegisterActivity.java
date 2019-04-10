package com.concept.test.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.concept.test.R;
import com.concept.test.helper.Messages;
import com.concept.test.model.BaseDomain;
import com.concept.test.model.UserData;
import com.concept.test.rest.RestHandler;
import com.concept.test.rest.request.UserRequest;
import com.concept.test.util.ZrowActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static okhttp3.internal.Util.UTF_8;

public class RegisterActivity extends ZrowActivity {
    ArrayList<String> listSpinner = new ArrayList<>();
    ArrayList<String> listAll = new ArrayList<>();
    ArrayList<String> listState = new ArrayList<>();
    ArrayList<String> listCity = new ArrayList<>();
    EditText age, username, email, password;
    CheckBox tnc;
    Button register;
    RadioGroup gender;
    TextView tc;
    String stringGender = "";
    AutoCompleteTextView act;
    String unique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        thisActivity = RegisterActivity.this;
        init();
        if (userLocalStore.isLoggedIn()) {
            Intent intent = new Intent(thisActivity, MainActivity.class);
            startActivity(intent);
            finish();
        }
        age = findViewById( R.id.ageEditor );
        username = findViewById( R.id.input_username );
        email = findViewById( R.id.input_email );
        password = findViewById( R.id.input_password );
        tnc = findViewById( R.id.tnc_check );
        gender = findViewById( R.id.gender );
        register = findViewById( R.id.btn_register );
        tc = findViewById( R.id.terms_condition );
        gender.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        // do operations specific to this selection
                        stringGender = "Male";
                        break;
                    case R.id.female:
                        stringGender = "Female";
                        // do operations specific to this selection
                        break;
                    case R.id.other:
                        stringGender = "Third Gender";
                        // do operations specific to this selection
                        break;
                }
            }
        } );
        register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tnc.isChecked()) {
                    registerUser();
                } else {
                    Toast.makeText( thisActivity, "Please check Terms &amp; Condition", Toast.LENGTH_LONG ).show();
                }
            }
        } );
        callAll();
    }

    private void registerUser() {
        final String femail = email.getText().toString();
        final String fage = age.getText().toString();
        final String fpassword = password.getText().toString();
        String city = act.getText().toString();
        String fcity = city.substring( 0, city.indexOf( ',' ) ).trim();
        String fstate = city.substring( city.lastIndexOf( ',' ) + 1 ).trim();

        int maxAge = 61, minAge = 17;
        int iAge = Integer.parseInt( fage );
        unique = GenerateRandomString.randomString( 10 );

        if (TextUtils.isEmpty( fpassword )) {
            password.setError( "Please enter Phone number" );
            password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty( fage ) || iAge < minAge || iAge > maxAge) {
            age.setError( "Please Entetr value between 18 to 60" );
            age.requestFocus();
            return;
        }

        if (TextUtils.isEmpty( femail )) {
            email.setError( "Please enter Password" );
            email.requestFocus();
            return;
        }
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail( femail );
        userRequest.setAge( fage );
        userRequest.setGender( stringGender );
        userRequest.setState( fstate );
        userRequest.setCity( fcity );
        userRequest.setPassword( fpassword );
        userRequest.setAutoId( unique );
        registerUser( userRequest );
    }

    public void registerUser(final UserRequest userRequest) {
        final Call<BaseDomain> call = RestHandler.getApiService().registerUser( userRequest );
        progressDialog.setMessage( Messages.AUTHENTICATE_USER_PD_MSG );
        progressDialog.show();
        call.enqueue( new Callback<BaseDomain>() {
            @Override
            public void onResponse(Call<BaseDomain> call, Response<BaseDomain> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body().getStatus().equalsIgnoreCase( "success" )) {
                            progressDialog.dismiss();
                            UserData userData = new UserData();
                            userData.setAutoId( unique );
                            userLocalStore.storeUserData( userData );
                            userLocalStore.setLoggedIn( true );
                            Toast.makeText( thisActivity, userRequest.getEmail() + " successfully registerd", Toast.LENGTH_SHORT ).show();
                            Intent i = new Intent( thisActivity, MainActivity.class );
                            startActivity( i );
                            finish();
                        } else {
                            if((response.body().getStatus().equalsIgnoreCase( "user already exist" ))){
                                Toast.makeText( thisActivity,"Email already existed", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                            String msg = response.body().getMsg();
                            messageHelper.shortMessage( msg );
                        }
                    } catch (Exception err) {
                        err.printStackTrace();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                    }
                } else {
                    if (response.code() == HttpURLConnection.HTTP_FORBIDDEN) {
                        progressDialog.dismiss();
                        messageHelper.shortMessage( Messages.WRONG_VALUE );

                    } else {
                        try {
                            messageHelper.shortMessage( Messages.PROBLEM_CONNECT_SERVER );
                            Log.e( "--> onResponse", "error -> " + response.errorBody().string() );
                        } catch (Exception err) {
                            Log.e( "--> Exception", err.getStackTrace().toString() );
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseDomain> call, Throwable t) {
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

    public static class GenerateRandomString {

        public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        public static Random RANDOM = new Random();

        public static String randomString(int len) {
            StringBuilder sb = new StringBuilder( len );

            for (int i = 0; i < len; i++) {
                sb.append( DATA.charAt( RANDOM.nextInt( DATA.length() ) ) );
            }

            return sb.toString();
        }

    }

    public void callAll() {
        obj_list();
        addToAll();
    }

    public String getJson() {
        String json = null;
        try {
            // Opening cities.json file
            InputStream is = getAssets().open( "states.json" );
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read( buffer );
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String( buffer, UTF_8 );
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    // This add all JSON object's data to the respective lists
    void obj_list() {
        // Exceptions are returned by JSONObject when the object cannot be created
        try {
            // Convert the string returned to a JSON object
            JSONObject jsonObject = new JSONObject( getJson() );
            // Get Json array
            JSONArray array = jsonObject.getJSONArray( "array" );
            // Navigate through an array item one by one
            for (int i = 0; i < array.length(); i++) {
                // select the particular JSON data
                JSONObject object = array.getJSONObject( i );
                String city = object.getString( "name" );
                String state = object.getString( "state" );
                // add to the lists in the specified format
                listSpinner.add( String.valueOf( i + 1 ) + " : " + city + " , " + state );
                listAll.add( city + " , " + state );
                listCity.add( city );
                listState.add( state );
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void addToAll() {
        act = findViewById( R.id.actAll );
        adapterSetting( listAll );
    }

    void adapterSetting(ArrayList arrayList) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_dropdown_item_1line, arrayList );
        act.setAdapter( adapter );
        hideKeyBoard();
    }

    // hide keyboard on selecting a suggestion
    public void hideKeyBoard() {
        act.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                InputMethodManager imm = (InputMethodManager) getSystemService( Activity.INPUT_METHOD_SERVICE );
                imm.toggleSoftInput( InputMethodManager.HIDE_IMPLICIT_ONLY, 0 );
            }
        } );
    }

}
