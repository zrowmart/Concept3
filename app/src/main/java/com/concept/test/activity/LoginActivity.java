package com.concept.test.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.concept.test.R;
import com.concept.test.helper.Messages;
import com.concept.test.helper.Values;
import com.concept.test.rest.RestHandler;
import com.concept.test.rest.request.SettingRequest;
import com.concept.test.rest.response.SettingResponse;
import com.concept.test.util.ZrowActivity;

import org.jetbrains.annotations.NotNull;

import java.net.HttpURLConnection;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends ZrowActivity {

    public EditText email, password;
    AppCompatButton login;
    TextInputLayout textError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        thisActivity = LoginActivity.this;
        init();

        email = findViewById( R.id.input_email );
        password = findViewById( R.id.input_password );
        login = findViewById( R.id.btn_login );

        if (userLocalStore.isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkText();
            }
        } );
    }

    private void checkText(){
        String femail = email.getText().toString().trim();
        String fpassword = password.getText().toString().trim();

        if (femail.isEmpty() || fpassword.isEmpty()) {
            textError.setError("Please Enter data in all field");
        }else{
            Values.username = femail;
            Log.d("popo",femail + " "+ fpassword);
            SettingRequest settingRequest = new SettingRequest();
            settingRequest.setEmail(femail);
            settingRequest.setPassword( fpassword );
            signInUser(settingRequest);
        }

    }

    private void signInUser(SettingRequest settingRequest){
        final Call<SettingResponse> call = RestHandler.getApiService().hitSetting(settingRequest);
        progressDialog.setMessage( Messages.AUTHENTICATE_USER_PD_MSG);
        progressDialog.show();
        call.enqueue(new Callback<SettingResponse>() {
            @Override
            public void onResponse(@NotNull Call<SettingResponse> call, @NotNull Response<SettingResponse> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body() != null) {
                            if (response.body().getStatus().equalsIgnoreCase("success")) {
                                progressDialog.dismiss();
                                Intent signIn = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(signIn);
                                finish();
                            } else {
                                progressDialog.dismiss();
                                String msg = response.body().getMsg();
                                messageHelper.shortMessage(msg);
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
                        messageHelper.shortMessage(Messages.NO_INTERNET_CONNECTIVITY);

                    } else {
                        try {
                            messageHelper.shortMessage(Messages.PROBLEM_CONNECT_SERVER);
                            assert response.errorBody() != null;
                            Log.e("--> onResponse", "error -> " + response.errorBody().string());
                        } catch (Exception err) {
                            Log.e("--> Exception", Arrays.toString( err.getStackTrace() ) );
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SettingResponse> call, @NotNull Throwable t) {
                try {
                    progressDialog.dismiss();
                    messageHelper.shortMessage(Messages.PROBLEM_CONNECT_SERVER);
                    if (t instanceof Exception) {
                        t.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
