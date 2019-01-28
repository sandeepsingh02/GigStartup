package com.example.gigstartup.view.spalsh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.gigstartup.R;
import com.example.gigstartup.view.main.MainActivity;

public class SpalshActivity extends AppCompatActivity {
    private static final long SPLASH_TIME_OUT = 3000;
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.mContext=this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
