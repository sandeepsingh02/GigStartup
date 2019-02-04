package com.example.gigstartup.view.base;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import com.example.gigstartup.utils.SharedPref;

import java.text.SimpleDateFormat;

public class BaseApplication extends Application {
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm MMM dd, yyyy");
    @Override
    public void onCreate() {
        super.onCreate();
        new SharedPref.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
