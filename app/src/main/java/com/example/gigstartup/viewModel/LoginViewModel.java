package com.example.gigstartup.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.util.Patterns;

import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.utils.Constants;
import com.example.gigstartup.utils.JSONUtils;
import com.example.gigstartup.utils.UserInfo;
import com.example.gigstartup.utils.ValidationUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel {
    public MutableLiveData<String> emailId = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();
    public MutableLiveData<Boolean> isSignUpClick = new MutableLiveData<>();
    public MutableLiveData<Boolean> isValidFeilds = new MutableLiveData<>();
    private IAPICall iapiCall;

    public LoginViewModel(IAPICall iapiCall) {
        this.iapiCall = iapiCall;
        isSignUpClick.postValue(null);
        emailId.postValue(null);
        password.postValue(null);
        isValidFeilds.postValue(null);
    }

    public void signupClick() {
        showToast("SignUp Click");
        isSignUpClick.postValue(true);
    }

    public void signInClick() {
        if (isValidFeilds()) {
            setShowProgress(true);
            checkUserDetails();
        }

    }

    private boolean isValidFeilds() {
        if (emailId.getValue() == null || emailId.getValue().equalsIgnoreCase("")) {
            showToast("Email Id/ Phone Number can't be blank ");
            return false;
        } else if (password.getValue() == null || password.getValue().equalsIgnoreCase("")) {
            showToast("Password can't be blank ");
            return false;
        } else if ((emailId.getValue().equalsIgnoreCase("sandeep@gmail.com")
                && password.getValue().equalsIgnoreCase("12345"))
                || (emailId.getValue().equalsIgnoreCase("8299870797")
                && password.getValue().equalsIgnoreCase("12345"))) {
            return true;
        } else {
            if (ValidationUtils.checkFeildContainkEmailOrMobile(emailId.getValue())) {
                if (ValidationUtils.isValidEmail(emailId.getValue())) {
                    return true;
                } else {
                    showToast("Please Enter valid email id");
                    return false;
                }

            } else {
                if (emailId.getValue().trim().length() == 10 && Patterns.PHONE.matcher(emailId.getValue()).matches()) {
                    return true;
                } else {
                    showToast("Please enter valid mobile number");
                    return false;
                }
            }
        }
    }

    private void checkUserDetails() {
        Call call = iapiCall.checkLogin(emailId.getValue(), password.getValue());
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
                            isValidFeilds.postValue(true);
                        } else {
                            isValidFeilds.postValue(false);
                            showToast("Try Again! Some problem occure");
                        }
                    } else {
                        isValidFeilds.postValue(false);
                        showToast(JSONUtils.checkJSONStr(jsonObjectResponse, Constants.MESSAGE));
                    }

                } catch (JSONException e) {
                    isValidFeilds.postValue(false);
                    setShowProgress(false);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                setShowProgress(false);
                showToast(t.getMessage());
                isValidFeilds.postValue(false);
            }
        });

    }
}
