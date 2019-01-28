package com.example.gigstartup.view.main;

import android.os.Bundle;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.ActivityMainBinding;
import com.example.gigstartup.view.base.BaseActivity;
import com.example.gigstartup.viewModel.BaseViewModel;
import com.example.gigstartup.viewModel.MainActivityViewModel;

public class MainActivity extends BaseActivity<MainActivityViewModel, ActivityMainBinding> {

    @Override
    protected int containerId() {
        return R.id.content_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected Class viewModelClass() {
        return MainActivityViewModel.class;
    }

    @Override
    protected BaseViewModel.Factory factory() {
        return new BaseViewModel.Factory() {
            @Override
            public BaseViewModel getClassInstance() {
                return new  MainActivityViewModel();
            }
        };
    }
}
