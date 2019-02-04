package com.example.gigstartup.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
    public static String checkJSONStr(JSONObject jsonObject, String key)  {
        if (jsonObject.has(key)) {
            try {
                return jsonObject.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static int checkJSONSInt(JSONObject jsonObject,String key)  {
        if (jsonObject.has(key)) {
            try {
                return jsonObject.getInt(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    public static boolean checkJSONSbool(JSONObject jsonObject,String key)  {
        if (jsonObject.has(key)) {
            try {
                return jsonObject.getBoolean(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
