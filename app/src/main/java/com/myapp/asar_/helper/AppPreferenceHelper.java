package com.myapp.asar_.helper;

import android.content.Context;

public class AppPreferenceHelper {
    public static final String STORAGE_NAME = "asar_db";
    public static final String CURRENT_USERID ="currentUserID";
    public static final String LOGGED_IN = "LoggedIn";
    public static final String SIGNED_UP = "SignedUp";
    public static final String Dark_mode = "Dark_mode";

    private Context context;

    public static AppPreferenceHelper instance;
    public AppPreferenceHelper(Context context){
        this.context = context;
    }

    public static AppPreferenceHelper getInstance(Context context){
        if(instance == null){
            instance = new AppPreferenceHelper(context);
        }
        return instance;
    }

    public void setCurrentUserID(int currentUserID){
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit().putInt(CURRENT_USERID,currentUserID).commit();
    }


    public int getCurrentUserID(){
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).getInt(CURRENT_USERID, 0);
    }

    public void setLoggedInInfo(boolean loggedIn){
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit().putBoolean(LOGGED_IN,loggedIn).commit();
    }

    public boolean getLoggedInfo(){
        return context.getSharedPreferences(STORAGE_NAME,Context.MODE_PRIVATE).getBoolean(LOGGED_IN,false);
    }

    public void saveUserSignupInfo(boolean signup){
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit().putBoolean(SIGNED_UP,signup).commit();
    }

    public boolean getUserSignupInfo(){
        return context.getSharedPreferences(STORAGE_NAME,Context.MODE_PRIVATE).getBoolean(SIGNED_UP,false);
    }

    public void saveDarkModeInfo(boolean darkMode){
        context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE).edit().putBoolean(Dark_mode,darkMode).commit();
    }

    public boolean getDarkModeInfo(){
        return context.getSharedPreferences(STORAGE_NAME,Context.MODE_PRIVATE).getBoolean(Dark_mode,false);
    }
}
