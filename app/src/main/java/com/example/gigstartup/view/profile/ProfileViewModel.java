package com.example.gigstartup.view.profile;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.gigstartup.dtos.DtoSkills;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.utils.Constants;
import com.example.gigstartup.utils.JSONUtils;
import com.example.gigstartup.view.main.MainActivity;
import com.example.gigstartup.viewModel.BaseViewModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends BaseViewModel {
    // TODO: Implement the ViewModel
    private  IAPICall iapiCall;
    public MutableLiveData<String> title= new MutableLiveData<>();
    public MutableLiveData<String> imageUrl= new MutableLiveData<>();
    public MutableLiveData<String> name= new MutableLiveData<>();
    public MutableLiveData<String> email= new MutableLiveData<>();
    public MutableLiveData<String> phone= new MutableLiveData<>();
    public MutableLiveData<String> skillName= new MutableLiveData<>();
    public MutableLiveData<Float> rate= new MutableLiveData<>();

    public ProfileViewModel(IAPICall iapiCall,String skillId) {
        this.iapiCall=iapiCall;
        setShowProgress(true);
        getSkillData(skillId);
    }


    private void getSkillData(String skillId) {
        Call call = iapiCall.getSkillsByID(skillId);
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
                            skillName.postValue(JSONUtils.checkJSONStr(jsonObjectChild,"skillName"));
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

}
