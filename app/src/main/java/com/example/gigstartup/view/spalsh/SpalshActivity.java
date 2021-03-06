package com.example.gigstartup.view.spalsh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.gigstartup.R;
import com.example.gigstartup.utils.ActionBarUtils;
import com.example.gigstartup.utils.Constants;
import com.example.gigstartup.utils.SharedPref;
import com.example.gigstartup.view.account.login.LoginActivity;
import com.example.gigstartup.view.main.MainActivity;

public class SpalshActivity extends AppCompatActivity {
    private static final long SPLASH_TIME_OUT = 3000;
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtils.fullScreenActivity(this);
        setContentView(R.layout.activity_splash);
        this.mContext=this;
        new Handler().postDelayed(() -> {
            if (SharedPref.read(Constants.IS_LOGIN, false)) {
                startActivity(new Intent(mContext, MainActivity.class));
                ActionBarUtils.finishActivity(this);
            } else {
                startActivity(new Intent(mContext, LoginActivity.class));
                ActionBarUtils.finishActivity(this);
            }
        },SPLASH_TIME_OUT);
    }
}
