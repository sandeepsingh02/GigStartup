package com.example.gigstartup.utils;

import com.example.gigstartup.dtos.DtoSkills;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo {
    public static  void saveUserInfoInSharedPref(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                SharedPref.write(Constants.ID,JSONUtils.checkJSONStr(jsonObject,Constants.ID));
                SharedPref.write(Constants.NAME,JSONUtils.checkJSONStr(jsonObject,Constants.NAME));
                SharedPref.write(Constants.EMAIL,JSONUtils.checkJSONStr(jsonObject,Constants.EMAIL));
                SharedPref.write(Constants.RATE,JSONUtils.checkJSONStr(jsonObject,Constants.RATE));
                SharedPref.write(Constants.SKILL_ID,JSONUtils.checkJSONStr(jsonObject,Constants.SKILL_ID));
                SharedPref.write(Constants.PREFERNCE_ID,JSONUtils.checkJSONStr(jsonObject,Constants.PREFERNCE_ID));
                SharedPref.write(Constants.MOBILE_NUMBER,JSONUtils.checkJSONStr(jsonObject,Constants.MOBILE_NUMBER));
                SharedPref.write(Constants.TOKEN_ID,JSONUtils.checkJSONStr(jsonObject,Constants.TOKEN_ID));
                SharedPref.write(Constants.IMAGE_URL,"https://picsum.photos/200/300/?random");
                SharedPref.write(Constants.IS_LOGIN,true);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
