package com.concept.test.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.concept.test.model.UserData;

public class UserLocalStore {
    private static final String user_pref = "userdetails";
    private SharedPreferences userLocalDatabase;
    private static final String email = "email";
    public static final String password = "password";
    private static final String autoId = "autoId";
    private static final String isLoggedIn = "isLoggedIn";
    private Context context;

     UserLocalStore(Context context) {
        this.context = context;
        userLocalDatabase = context.getSharedPreferences(user_pref, 0);
    }

    public void storeUserData(UserData userData) {
        SharedPreferences.Editor peditor = userLocalDatabase.edit();
        peditor.putString(email, userData.getEmail());
        peditor.putString(password, userData.getPassword());
        peditor.putString( autoId,userData.getAutoId() );
        peditor.apply();
    }

    public void resetUserData() {
        SharedPreferences.Editor peditor = userLocalDatabase.edit();
        peditor.putString(email, "");
        peditor.putString(password, "");
        peditor.putBoolean(isLoggedIn, false);
        peditor.apply();
    }

    public UserData fetchUserData() {
        UserData userData = new UserData();
        userData.setEmail(userLocalDatabase.getString(email, ""));
        userData.setPassword(userLocalDatabase.getString(password, ""));
        userData.setAutoId( userLocalDatabase.getString( autoId,"" ) );
        return userData;
    }

    public boolean isLoggedIn() {
        return userLocalDatabase.getBoolean(isLoggedIn, false);
    }

    public void setLoggedIn(Boolean state) {
        SharedPreferences.Editor peditor = userLocalDatabase.edit();
        peditor.putBoolean(isLoggedIn, state);
        peditor.apply();
    }
}
