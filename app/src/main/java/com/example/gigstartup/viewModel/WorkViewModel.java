package com.example.gigstartup.viewModel;

import android.arch.lifecycle.MutableLiveData;

import com.example.gigstartup.adapters.WorkStatusAdapter;
import com.example.gigstartup.dtos.DtoUserInfo;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.utils.JSONUtils;
import com.example.gigstartup.utils.SortWorker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.gigstartup.view.base.BaseApplication.simpleDateFormat;

public class WorkViewModel extends BaseViewModel{
    public MutableLiveData<String> title = new MutableLiveData<>();
    public List<DtoUserInfo> listUser = new ArrayList<>();
    public WorkStatusAdapter workStatusAdapter;
    private IAPICall iapiCall;

    public WorkViewModel(IAPICall iapiCall,String type) {
        this.iapiCall = iapiCall;
        workStatusAdapter=new WorkStatusAdapter();
        setShowProgress(true);
        getWorkData(type);
    }

    public void getWorkData(String type) {
        for (int i = 0; i <= 20; i++) {
            DtoUserInfo dtoUserInfo = new DtoUserInfo();
            dtoUserInfo.setId(i);
            dtoUserInfo.setUserImage("https://picsum.photos/200/300?image=" + i);
            Random rand = new Random();
            int n = rand.nextInt(50) + i;
            dtoUserInfo.setRatePerHour(n);
            if (i % 2 == 0) {
                dtoUserInfo.setRating(5);
                dtoUserInfo.setSkill("Communication");
                dtoUserInfo.setName("Sandeep Singh");
                dtoUserInfo.setAddress("1120 N");
                dtoUserInfo.setDiscription("A “Me in 30 Seconds” statement is a simple way to present to someone else a balanced understanding of who you are. It piques the interest of a listener who invites you to “Tell me a little about yourself,” and it provides a brief and compelling answer to the question “Why should I hire you?” ");
            } else {
                dtoUserInfo.setRating(4);
                dtoUserInfo.setSkill("Creativity");
                dtoUserInfo.setName("Urvashi Gupta");
                dtoUserInfo.setAddress("District 0");
                dtoUserInfo.setDiscription("My name is Randy Patterson, and I’m currently looking for a job in youth services. I have 10 years of experience working with youth agencies. I have a bachelor’s degree in outdoor education. I raise money, train leaders, and organize units. I have raised over $100,000 each of the last six years. I consider myself a good public speaker, and I have a good sense of humor. “Who do you know who works with youth?");
            }
            if (type.equalsIgnoreCase("completed")) {
                String dateString = simpleDateFormat.format(new Date());
                dtoUserInfo.setFromTime(dateString);
                dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()+24*60*60*1000));
                dtoUserInfo.setToTime(dateString);
                dtoUserInfo.setStatus("Completed");
            } else if (type.equalsIgnoreCase("inprogress")) {
                String dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()));
                dtoUserInfo.setFromTime(dateString);
                dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()+48*60*60*1000));
                dtoUserInfo.setToTime(dateString);
                dtoUserInfo.setStatus("InProgress");
            }
            else if (type.equalsIgnoreCase("denied")) {
                String dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()-120*60*60*1000));
                dtoUserInfo.setFromTime(dateString);
                dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()-24*60*60*1000));
                dtoUserInfo.setToTime(dateString);
                dtoUserInfo.setStatus("Denied");
            }
            else if (type.equalsIgnoreCase("pending")) {
                String dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()-96*60*60*1000));
                dtoUserInfo.setFromTime(dateString);
                dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()+48*60*60*1000));
                dtoUserInfo.setToTime(dateString);
                dtoUserInfo.setStatus("Pending");
            }
            listUser.add(dtoUserInfo);
        }
        Collections.sort(listUser,new SortWorker());
        workStatusAdapter.addAllItems(listUser);
    }
   /* public void getWorkData(String type) {

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
                        for (int i = 0; i <= 20; i++) {
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
                                dtoUserInfo.setName("Urvashi Gupta");
                                dtoUserInfo.setAddress("District 0");
                                dtoUserInfo.setDiscription("My name is Randy Patterson, and I’m currently looking for a job in youth services. I have 10 years of experience working with youth agencies. I have a bachelor’s degree in outdoor education. I raise money, train leaders, and organize units. I have raised over $100,000 each of the last six years. I consider myself a good public speaker, and I have a good sense of humor. “Who do you know who works with youth?");
                            }
                            if (type.equalsIgnoreCase("completed")) {
                                String dateString = simpleDateFormat.format(new Date());
                                dtoUserInfo.setFromTime(dateString);
                                dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()+24*60*60*1000));
                                dtoUserInfo.setToTime(dateString);
                                dtoUserInfo.setStatus("Completed");
                            } else if (type.equalsIgnoreCase("inprogress")) {
                                String dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()));
                                dtoUserInfo.setFromTime(dateString);
                                dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()+48*60*60*1000));
                                dtoUserInfo.setToTime(dateString);
                                dtoUserInfo.setStatus("InProgress");
                            }
                            else if (type.equalsIgnoreCase("denied")) {
                                String dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()-120*60*60*1000));
                                dtoUserInfo.setFromTime(dateString);
                                dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()-24*60*60*1000));
                                dtoUserInfo.setToTime(dateString);
                                dtoUserInfo.setStatus("Denied");
                            }
                            else if (type.equalsIgnoreCase("pending")) {
                                String dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()-96*60*60*1000));
                                dtoUserInfo.setFromTime(dateString);
                                dateString = simpleDateFormat.format(new Date(Calendar.getInstance().getTimeInMillis()+48*60*60*1000));
                                dtoUserInfo.setToTime(dateString);
                                dtoUserInfo.setStatus("Pending");
                            }
                            listUser.add(dtoUserInfo);
                        }
                        workStatusAdapter.addAllItems(listUser);
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
