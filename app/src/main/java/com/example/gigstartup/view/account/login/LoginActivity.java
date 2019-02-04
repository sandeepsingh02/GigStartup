package com.example.gigstartup.view.account.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.ActivityLoginBinding;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.model.APICall;
import com.example.gigstartup.view.account.signup.SignupActivity;
import com.example.gigstartup.view.base.BaseActivity;
import com.example.gigstartup.view.main.MainActivity;
import com.example.gigstartup.viewModel.BaseViewModel;
import com.example.gigstartup.viewModel.LoginViewModel;

public class LoginActivity extends BaseActivity<LoginViewModel,ActivityLoginBinding> {
    private Context mContext=this;
    @Override
    protected int containerId() {
        return 0;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected Class viewModelClass() {
        return LoginViewModel.class;
    }
    @Override
    protected BaseViewModel.Factory factory() {
        return new BaseViewModel.Factory() {
            @Override
            public BaseViewModel getClassInstance() {
                IAPICall iapiCall= APICall.getClient(mContext).create(IAPICall.class);
                return new LoginViewModel(iapiCall);
            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBinding().setViewModel(getViewModel());
        getViewModel().isSignUpClick.observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                Intent intent=new Intent(this, SignupActivity.class);
                intentStartActivity(intent);
                getViewModel().isSignUpClick.postValue(null);
            }
        });
        getViewModel().isValidFeilds.observe(this, aBoolean -> {
            if (aBoolean != null && aBoolean) {
                getViewModel().isSignUpClick.postValue(null);
                Intent intent=new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentStartActivity(intent);
                finish();
            }
        });
    }
}

