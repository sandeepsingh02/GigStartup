package com.example.gigstartup.utils;

import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUtils {

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean checkFeildContainkEmailOrMobile(String value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return (value.chars().allMatch(Character::isLetter));
        }
        else {
            return value.contains("@");
        }
    }
}
