package com.example.gigstartup.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SharedPref {
    private static SharedPreferences mSharedPref;
    private static final String DEFAULT_SUFFIX = "_preferences";
    private static void initPrefs(Context context, String prefsName, int mode) {
        mSharedPref = context.getSharedPreferences(prefsName, mode);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor;
        if (mSharedPref != null){
            prefsEditor = mSharedPref.edit();
            prefsEditor.putBoolean(key, value);
            prefsEditor.apply();
        }
    }
    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static void write(String key, int value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }


    public static boolean read(String key, boolean defValue) {
        if(mSharedPref != null)
            return mSharedPref.getBoolean(key, defValue);
        else
            return  defValue;
    }
    public static String read(String key, String defValue) {
        if(mSharedPref != null)
            return mSharedPref.getString(key, defValue);
        else
            return defValue;
    }
    public static long read(String key, long defValue) {
        if(mSharedPref != null)
            return mSharedPref.getLong(key, defValue);
        else
            return defValue;
    }
    public static void clear(String key) {
        if (mSharedPref.contains(key)) {
            SharedPreferences.Editor editor = mSharedPref.edit();
            editor.remove(key);
            editor.apply();
        }
    }
    public static void clearData(Context context){
        SharedPreferences settings = mSharedPref;
        settings.edit().clear().apply();
    }

    //Initialize sharepref
    public final static class Builder {

        private String mKey;
        private Context mContext;
        private int mMode = -1;
        private boolean mUseDefault = false;

        /**
         * Set the filename of the SharedPreference instance. Usually this is the application's
         * packagename.xml but it can be modified for migration purposes or customization.
         *
         * @param prefsName the filename used for the SharedPreference
         * @return the {@link SharedPref.Builder} object.
         */
        public Builder setPrefsName(final String prefsName) {
            mKey = prefsName;
            return this;
        }

        /**
         * Set the Context used to instantiate the SharedPreferences
         *
         * @param context the application context
         * @return the {@link SharedPref.Builder} object.
         */
        public Builder setContext(final Context context) {
            mContext = context;
            return this;
        }

        /**
         * Set the mode of the SharedPreference instance.
         *
         * @param mode Operating mode.  Use 0 or {@link Context#MODE_PRIVATE} for the
         *             default operation, {@link Context#MODE_WORLD_READABLE}
         * @return the {@link SharedPref.Builder} object.
         * @see Context#getSharedPreferences
         */
        @SuppressLint({"WorldReadableFiles", "WorldWriteableFiles"})
        public Builder setMode(final int mode) {
            if (mode == ContextWrapper.MODE_PRIVATE || mode == ContextWrapper.MODE_WORLD_READABLE || mode == ContextWrapper.MODE_WORLD_WRITEABLE || mode == ContextWrapper.MODE_MULTI_PROCESS) {
                mMode = mode;
            } else {
                throw new RuntimeException("The mode in the SharedPreference can only be set too ContextWrapper.MODE_PRIVATE, ContextWrapper.MODE_WORLD_READABLE, ContextWrapper.MODE_WORLD_WRITEABLE or ContextWrapper.MODE_MULTI_PROCESS");
            }

            return this;
        }

        /**
         * Set the default SharedPreference file name. Often the package name of the application is
         * used, but if the {@link android.preference.PreferenceActivity} or {@link
         * android.preference.PreferenceFragment} is used the system will append that with
         * _preference.
         *
         * @param defaultSharedPreference true if default SharedPreference name should used.
         * @return the {@link SharedPref.Builder} object.
         */
        @SuppressWarnings("SameParameterValue")
        public Builder setUseDefaultSharedPreference(boolean defaultSharedPreference) {
            mUseDefault = defaultSharedPreference;
            return this;
        }

        /**
         * Initialize the SharedPreference instance to used in the application.
         *
         * @throws RuntimeException if Context has not been set.
         */
        public void build() {
            if (mContext == null) {
                throw new RuntimeException("Context not set, please set context before building the Prefs instance.");
            }

            if (TextUtils.isEmpty(mKey)) {
                mKey = mContext.getPackageName();
            }

            if (mUseDefault) {
                mKey += DEFAULT_SUFFIX;
            }

            if (mMode == -1) {
                mMode = ContextWrapper.MODE_PRIVATE;
            }

            SharedPref.initPrefs(mContext, mKey, mMode);
        }

    }
}
