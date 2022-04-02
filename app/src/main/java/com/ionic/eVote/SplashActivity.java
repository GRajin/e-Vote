package com.ionic.eVote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences pref = getSharedPreferences("sharedPref", MODE_PRIVATE);
            if (pref.getString("isFirstTime", "true").equals("false")) {
                Intent authIntent = new Intent(SplashActivity.this, AuthActivity.class);
                startActivity(authIntent);
            } else {
                SharedPreferences.Editor myEdit = pref.edit();
                myEdit.putString("isFirstTime", "false");
                myEdit.apply();
                Intent registerIntent = new Intent(SplashActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
            finish();
        }, 1500);

    }
}