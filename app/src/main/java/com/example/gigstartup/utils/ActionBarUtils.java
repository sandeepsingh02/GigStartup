package com.example.gigstartup.utils;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.gigstartup.R;
import com.example.gigstartup.view.main.MainActivity;

import java.util.Objects;

public class ActionBarUtils {
    public static void disableActionBar(AppCompatActivity appCompatActivity) {
        try {
            Objects.requireNonNull(appCompatActivity.getSupportActionBar()).hide();

        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
    }
    public  static void fullScreenActivity(Activity activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                activity.getWindow().setGravity(Gravity.CENTER);
               /* activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void finishActivity(Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    activity.finishAffinity();
                } else {
                    activity.finish();
                }
            }
        },1000);

    }
}
