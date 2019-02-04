package com.example.gigstartup.view.notification;

import android.arch.lifecycle.ViewModel;

import com.example.gigstartup.adapters.NotificationAdapter;
import com.example.gigstartup.dtos.DtoNotification;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.utils.JSONUtils;
import com.example.gigstartup.viewModel.BaseViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel extends BaseViewModel {
    // TODO: Implement the ViewModel
    public NotificationAdapter notificationAdapter;
    public List<DtoNotification> dtoNotificationList = new ArrayList<>();
    private IAPICall iapiCall;

    public NotificationViewModel(IAPICall iapiCall) {
        this.iapiCall = iapiCall;
        notificationAdapter = new NotificationAdapter();
        getNotification();
    }

    public void getNotification() {
        for (int i = 0; i < 10; i++) {
            DtoNotification dtoNotification = new DtoNotification();
            dtoNotification.setId(i);
            dtoNotification.setTitle("Title Here");
            dtoNotification.setBody("Notification Message is comming here!");
            dtoNotificationList.add(dtoNotification);
        }

        notificationAdapter.addAllItems(dtoNotificationList);
    }

  /*  public void getNotification(boolean b) {
        Call call = iapiCall.getSkills();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                try {
                    setShowProgress(false);
                    JSONObject jsonObjectResponse = new JSONObject(new Gson().toJson(response.body()));
                    if (JSONUtils.checkJSONSbool(jsonObjectResponse, "message")) {
                        JSONArray jsonArray = jsonObjectResponse.getJSONArray("Data");
                        if (jsonArray.length() > 0) {
                            if (b) {
                                dtoNotificationList.clear();
                            }
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectChild = jsonArray.getJSONObject(i);
                                DtoNotification dtoNotification = new DtoNotification();
                                dtoNotification.setId(JSONUtils.checkJSONSInt(jsonObjectChild,"id"));
                                dtoNotification.setTitle(JSONUtils.checkJSONStr(jsonObjectChild,"title"));
                                dtoNotification.setBody(JSONUtils.checkJSONStr(jsonObjectChild,"body"));
                                dtoNotificationList.add(dtoNotification);
                            }

                            notificationAdapter.addAllItems(dtoNotificationList);
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

    }*/
}