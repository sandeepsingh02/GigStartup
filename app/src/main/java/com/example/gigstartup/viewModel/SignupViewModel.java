package com.example.gigstartup.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.gigstartup.dtos.DtoSkills;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.utils.Constants;
import com.example.gigstartup.utils.JSONUtils;
import com.example.gigstartup.utils.SharedPref;
import com.example.gigstartup.utils.UserInfo;
import com.example.gigstartup.utils.ValidationUtils;
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

public class SignupViewModel extends BaseViewModel {
    public MutableLiveData<List<String>> listSpinnerMutableLiveData = new MutableLiveData<>();
    public List<String> spinnerData;
    public List<DtoSkills> dtoSkillsList=new ArrayList<>();
    public List<String> springSkills;
    public MutableLiveData<String> prefeence =new MutableLiveData<>();
    public MutableLiveData<String> userName =new MutableLiveData<>();
    public MutableLiveData<String> emaildId =new MutableLiveData<>();
    public MutableLiveData<String> rate =new MutableLiveData<>();
    public MutableLiveData<String> mobileNumber =new MutableLiveData<>();
    public MutableLiveData<String> password =new MutableLiveData<>();
    public MutableLiveData<String> rePassword =new MutableLiveData<>();
    public MutableLiveData<Boolean> isWorkerSelected = new MutableLiveData<>();
    public MutableLiveData<Boolean> isCheckTandC = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSuccessSignUp = new MutableLiveData<>();
    private int skillId =0;

    private IAPICall iapiCall;

    public SignupViewModel(IAPICall iapiCall) {
        this.iapiCall=iapiCall;
        isSuccessSignUp.postValue(null);
        spinnerData = new ArrayList<>();
        springSkills = new ArrayList<>();
        spinnerData.clear();
        springSkills.clear();
        springSkills.add("Select");
        spinnerData.add("Select");
        spinnerData.add("Requestor");
        spinnerData.add("Worker");
        listSpinnerMutableLiveData.postValue(spinnerData);
        isWorkerSelected.postValue(false);
        getSkillData();
    }

    private void getSkillData() {
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
                           dtoSkillsList.add(dtoSkills);
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

    public void getPreferenceSpinner(AdapterView<?> parent, View view, int pos, long id) {
        if (spinnerData.size() > 0 && pos>0)  {
            showToast(spinnerData.get(pos));
            prefeence.postValue(spinnerData.get(pos));
            if (spinnerData.get(pos).equalsIgnoreCase("Worker")) {
                isWorkerSelected.postValue(true);

            } else {
                isWorkerSelected.postValue(false);
            }
        }
    }

    public void getSkillSpinner(AdapterView<?> parent, View view, int pos, long id) {
        if (springSkills.size() > 0 && pos>0) {
            skillId= pos;
        }
    }
    public void onItemChecked(View view,boolean isChecked){
        isCheckTandC.postValue(isChecked);
    }
    public void signUpClick(){
        if(isValidFeilds()){
            setShowProgress(true);
            saveUserInformation();
        }
    }

    private void saveUserInformation() {
        Call call = iapiCall.signUp(userName.getValue(),emaildId.getValue(),1,password.getValue(),
                mobileNumber.getValue(),Double.parseDouble((rate.getValue() != null)?rate.getValue():"0"),dtoSkillsList.size()>0?dtoSkillsList.get(skillId).getId():0,"");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(retrofit2.Call call, Response response) {
                try {
                    setShowProgress(false);
                    JSONObject jsonObjectResponse = new JSONObject(new Gson().toJson(response.body()));
                    if (JSONUtils.checkJSONSbool(jsonObjectResponse, "Status")) {
                        JSONArray jsonArray = jsonObjectResponse.getJSONArray(Constants.RESULT);
                        if (jsonArray.length() > 0) {

                            UserInfo.saveUserInfoInSharedPref(jsonArray);
                            isSuccessSignUp.postValue(true);
                        } else {
                            isSuccessSignUp.postValue(false);
                            showToast("Try Again! Some problem occure");
                        }
                    } else {
                        isSuccessSignUp.postValue(false);
                        showToast(JSONUtils.checkJSONStr(jsonObjectResponse,Constants.MESSAGE));
                    }

                } catch (JSONException e) {
                    isSuccessSignUp.postValue(false);
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

    private boolean isValidFeilds() {
        if (prefeence.getValue() == null) {
            showToast("Please select prefrence");
            return false;
        }
        else if (userName.getValue() == null || userName.getValue().trim().isEmpty()) {
            showToast("Please enter Name");
            return false;
        }
        else if (emaildId.getValue() == null || emaildId.getValue().trim().isEmpty()) {
            showToast("Please enter email address");
            return false;
        }
        else if (!ValidationUtils.isValidEmail(emaildId.getValue())) {
            showToast("Please enter correct email");
            return false;
        }
        else if (mobileNumber.getValue() == null || mobileNumber.getValue().trim().isEmpty()) {
            showToast("Please enter mobile number");
            return false;
        }
        else if (mobileNumber.getValue().trim().length()!= 10) {
            showToast("Please enter correct mobile number");
            return false;
        }
        else if (password.getValue() == null || password.getValue().trim().isEmpty()) {
            showToast("Please enter password");
            return false;
        }
        else if (rePassword.getValue() == null || rePassword.getValue().trim().isEmpty()) {
            showToast("Please confirm password");
            return false;
        }
        else if (!password.getValue().equalsIgnoreCase(rePassword.getValue())) {
            showToast("Password do not match");
            return false;
        }
        else if (prefeence.getValue().equalsIgnoreCase("Worker")) {
            if (skillId < 1) {
                showToast("Please select skill");
                return false;
            } else if (rate.getValue() == null || rate.getValue().trim().isEmpty()) {
                showToast("Pelase enter rate");
                return false;
            }
        }
        else if (!isCheckTandC.getValue()) {
            showToast("Plese check Terms and Condition");
            return false;
        }
        return true;
    }
}
