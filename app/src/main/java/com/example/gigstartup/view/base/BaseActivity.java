package com.example.gigstartup.view.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.gigstartup.viewModel.BaseViewModel;

public abstract class BaseActivity <VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity {
    private DB binding;
    private VM viewModel;
    protected abstract int containerId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
    }

    protected abstract int getLayoutID();

    public DB getBinding() {
        return binding;
    }

    public VM getViewModel() {
        return viewModel;
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    protected abstract Class<VM> viewModelClass();

    protected abstract BaseViewModel.Factory factory();


}
