package com.example.gigstartup.view.account.signup;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.ActivitySignupBinding;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.model.APICall;
import com.example.gigstartup.utils.ActionBarUtils;
import com.example.gigstartup.view.base.BaseActivity;
import com.example.gigstartup.view.main.MainActivity;
import com.example.gigstartup.viewModel.BaseViewModel;
import com.example.gigstartup.viewModel.SignupViewModel;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends BaseActivity<SignupViewModel,ActivitySignupBinding> {
    private Context mContext=this;
    @Override
    protected int containerId() {
        return 0;
    }
    @Override
    protected int getLayoutID() {
        return R.layout.activity_signup;
    }

    @Override
    protected Class viewModelClass() {
        return SignupViewModel.class;
    }

    @Override
    protected BaseViewModel.Factory factory() {
        return new BaseViewModel.Factory() {
            @Override
            public BaseViewModel getClassInstance() {
                IAPICall iapiCall= APICall.getClient(mContext).create(IAPICall.class);
                return new SignupViewModel(iapiCall);
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        getBinding().setViewModel(getViewModel());
        getBinding().setActivity(this);
        getBinding().getViewModel().isSuccessSignUp.observe(this,aBoolean -> {
            if (aBoolean != null && aBoolean) {
                getBinding().getViewModel().isSuccessSignUp.postValue(null);
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                ActionBarUtils.finishActivity(this);
            }
        });
    }
}

