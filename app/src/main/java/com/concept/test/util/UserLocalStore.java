package com.concept.test.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.concept.test.model.UserData;

public class UserLocalStore {
    public static final String user_pref = "userdetails";
    SharedPreferences userLocalDatabase;
    public static final String username = "username";
    public static final String password = "password";
    public static final String isLoggedIn = "isLoggedIn";
    Context context;

    public UserLocalStore(Context context) {
        this.context = context;
        userLocalDatabase = context.getSharedPreferences(user_pref, 0);
    }

    public void storeUserData(UserData userData) {
        SharedPreferences.Editor peditor = userLocalDatabase.edit();
        peditor.putString(username, userData.getUsername());
        peditor.putString(password, userData.getPassword());
        peditor.commit();
    }

    public void resetUserData() {
        SharedPreferences.Editor peditor = userLocalDatabase.edit();
        peditor.putString(username, "");
        peditor.putString(password, "");
        peditor.putBoolean(isLoggedIn, false);
        peditor.commit();
    }

    public UserData fetchUserData() {
        UserData userData = new UserData();
        userData.setUsername(userLocalDatabase.getString(username, ""));
        userData.setPassword(userLocalDatabase.getString(password, ""));
        return userData;
    }

    public boolean isLoggedIn() {
        return userLocalDatabase.getBoolean(isLoggedIn, false);
    }

    public void setLoggedIn(Boolean state) {
        SharedPreferences.Editor peditor = userLocalDatabase.edit();
        peditor.putBoolean(isLoggedIn, state);
        peditor.commit();
    }

}
