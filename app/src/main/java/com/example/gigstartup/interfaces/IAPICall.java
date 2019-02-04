package com.example.gigstartup.interfaces;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface IAPICall {
    @GET("/gigstartup/getUser.php")
    Call<Object> getSkills(@Query("id") String  id);

    @GET("/gigstartup/getSkills.php")
    Call<Object> getSkills();

    @POST("/gigstartup/signupPage.php")
    Call<Object> signupUser(@QueryMap  HashMap<String,String> hashMap);

    @FormUrlEncoded
    @POST("/gigstartup/signupPage.php")
    Call<Object> signUp(@Field("name") String name,@Field("email") String email,@Field("prefrence") int prefrence,
                        @Field("password") String password,@Field("mobileNumber") String mobileNumber,@Field("ratePerHour") double ratePerHour,
                        @Field("skillId") int skillId,@Field("tokenId") String tokenId);
    @FormUrlEncoded
    @POST("/gigstartup/loginPage.php")
    Call<Object> checkLogin(@Field("email") String email,@Field("password") String password);

    @FormUrlEncoded
    @POST("/gigstartup/getSkillById.php")
    Call<Object> getSkillsByID(@Field("skillId") String skillId);
}
