package com.myapp.asar_.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.myapp.asar_.MainActivity;
import com.myapp.asar_.R;
import com.myapp.asar_.helper.AppPreferenceHelper;

public class SplashActivity extends AppCompatActivity {

    AppPreferenceHelper appPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appPreferenceHelper = AppPreferenceHelper.getInstance(getApplicationContext());

        if(appPreferenceHelper.getDarkModeInfo()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        Log.d("TAG", "User Sign up info:  " + appPreferenceHelper.getUserSignupInfo());
        Log.d("TAG", "User Sign in info:  " + appPreferenceHelper.getLoggedInfo());
        Log.d("TAG", "User Sign up info and Current UserID:  " + appPreferenceHelper.getCurrentUserID());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(appPreferenceHelper.getLoggedInfo()){
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }
                else {
                    Log.d("TAG", "User Sign up info not found   : ");
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }


            }
        }, 400);

    }
}