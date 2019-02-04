package com.example.gigstartup.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;
import android.widget.AdapterView;

import com.example.gigstartup.adapters.UserInfoAdapter;
import com.example.gigstartup.dtos.DtoSkills;
import com.example.gigstartup.dtos.DtoUserInfo;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.utils.Constants;
import com.example.gigstartup.utils.JSONUtils;
import com.example.gigstartup.utils.SortWorker;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gigstartup.view.base.BaseApplication.simpleDateFormat;

public class HomeViewModel extends BaseViewModel {
    public MutableLiveData<String> title = new MutableLiveData<>();
    public List<DtoUserInfo> listUser = new ArrayList<>();
    public UserInfoAdapter userInfoAdapter;
    public List<String> springSkills;
    private IAPICall iapiCall;
    public String skill;
    public  MutableLiveData<Boolean> isSearchClicked=new MutableLiveData<>();

    public HomeViewModel(IAPICall iapiCall) {
        springSkills = new ArrayList<>();
        springSkills.clear();
        springSkills.add("Select Skill");
        this.iapiCall = iapiCall;
        isSearchClicked.postValue(null);
        getSkills();
    }
    private void getSkills() {
        Call call = iapiCall.getSkills();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                try {
                    setShowProgress(false);
                    JSONObject jsonObjectResponse = new JSONObject(new Gson().toJson(response.body()));
                    if (JSONUtils.checkJSONSbool(jsonObjectResponse, "Status")) {
                        JSONArray jsonArray=jsonObjectResponse.getJSONArray(Constants.RESULT);
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObjectChild= jsonArray.getJSONObject(i);
                            DtoSkills dtoSkills=new DtoSkills();
                            dtoSkills.setId(JSONUtils.checkJSONSInt(jsonObjectChild,Constants.ID));
                            dtoSkills.setName(JSONUtils.checkJSONStr(jsonObjectChild,Constants.SKILL_NAME));
                            springSkills.add(JSONUtils.checkJSONStr(jsonObjectChild,Constants.SKILL_NAME));
                        }
                    }

                } catch (JSONException e) {
                    setShowProgress(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                setShowProgress(false);
            }
        });
    }

    public HomeViewModel(IAPICall iapiCall,String skills) {
        this.iapiCall = iapiCall;
        userInfoAdapter = new UserInfoAdapter();
        getData(skills);
    }

    public void getData(String skills){
        for (int i = 0; i <= 30; i++) {
            DtoUserInfo dtoUserInfo = new DtoUserInfo();
            dtoUserInfo.setId(i);
            dtoUserInfo.setUserImage("https://picsum.photos/200/300?image=" + i);
            Random rand = new Random();
            int n = rand.nextInt(50) + i;
            dtoUserInfo.setRatePerHour(n);
            dtoUserInfo.setSkill(skills);
            if (i % 2 == 0) {
                dtoUserInfo.setRating(4);
                dtoUserInfo.setName("Sandeep Singh");
                dtoUserInfo.setAddress("1120 N");
                dtoUserInfo.setDiscription("A “Me in 30 Seconds” statement is a simple way to present to someone else a balanced understanding of who you are. It piques the interest of a listener who invites you to “Tell me a little about yourself,” and it provides a brief and compelling answer to the question “Why should I hire you?” ");
            } else {
                dtoUserInfo.setName("Urvi Gupta");
                dtoUserInfo.setRating(5);
                dtoUserInfo.setAddress("District 0");
                dtoUserInfo.setDiscription("My name is Randy Patterson, and I’m currently looking for a job in youth services. I have 10 years of experience working with youth agencies. I have a bachelor’s degree in outdoor education. I raise money, train leaders, and organize units. I have raised over $100,000 each of the last six years. I consider myself a good public speaker, and I have a good sense of humor. “Who do you know who works with youth?");
            }
            String dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()));
            dtoUserInfo.setFromTime(dateString);
            dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()+24*60*60*1000));
            dtoUserInfo.setToTime(dateString);
            dtoUserInfo.setStatus("InProgress");
            listUser.add(dtoUserInfo);
        }
        Collections.sort(listUser,new SortWorker());
        userInfoAdapter.addAllItems(listUser);
    }

    public void getSkillSpinner(AdapterView<?> parent, View view, int pos, long id) {
        if (springSkills.size() > 0 && pos > 0) {
            showToast(springSkills.get(pos));
            skill=springSkills.get(pos);
        } else {
            skill= null;
        }

    }


    public void searchWorker(){
        if (skill != null) {
            isSearchClicked.postValue(true);
        } else {
            isSearchClicked.postValue(false);
            showToast("Please select skill of the worker to search");
        }
    }
    /*public void getData() {
        listUser.clear();
        Call call = iapiCall.getSkills();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                try {
                    setShowProgress(false);
                    JSONObject jsonObjectResponse = new JSONObject(new Gson().toJson(response.body()));
                    if (JSONUtils.checkJSONSbool(jsonObjectResponse, "Status")) {
                        showToast(jsonObjectResponse.toString());
                        for (int i = 0; i <= 30; i++) {
                            DtoUserInfo dtoUserInfo = new DtoUserInfo();
                            dtoUserInfo.setId(i);
                            dtoUserInfo.setUserImage("https://picsum.photos/200/300?image=" + i);
                            Random rand = new Random();
                            int n = rand.nextInt(50) + i;
                            dtoUserInfo.setRatePerHour("$" + n + " /hr");
                            if (i % 2 == 0) {
                                dtoUserInfo.setRating(5);
                                dtoUserInfo.setSkill("Communication");
                                dtoUserInfo.setName("Sandeep Singh");
                                dtoUserInfo.setAddress("1120 N");
                                dtoUserInfo.setDiscription("A “Me in 30 Seconds” statement is a simple way to present to someone else a balanced understanding of who you are. It piques the interest of a listener who invites you to “Tell me a little about yourself,” and it provides a brief and compelling answer to the question “Why should I hire you?” ");
                            } else {
                                dtoUserInfo.setRating(4);
                                dtoUserInfo.setSkill("Creativity");
                                dtoUserInfo.setAddress("District 0");
                                dtoUserInfo.setDiscription("My name is Randy Patterson, and I’m currently looking for a job in youth services. I have 10 years of experience working with youth agencies. I have a bachelor’s degree in outdoor education. I raise money, train leaders, and organize units. I have raised over $100,000 each of the last six years. I consider myself a good public speaker, and I have a good sense of humor. “Who do you know who works with youth?");
                            }
                            String dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()));
                            dtoUserInfo.setFromTime(dateString);
                            dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()+24*60*60*1000));
                            dtoUserInfo.setToTime(dateString);
                            dtoUserInfo.setStatus("InProgress");
                            listUser.add(dtoUserInfo);
                        }
                        userInfoAdapter.addAllItems(listUser);
                    }

                } catch (JSONException e) {
                    setShowProgress(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                setShowProgress(false);
            }
        });


    }*/
}
