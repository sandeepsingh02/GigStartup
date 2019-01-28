package com.example.gigstartup.view.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.gigstartup.viewModel.BaseViewModel;

public abstract class BaseActivity <VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity {
    private DB binding;
    private VM viewModel;
    protected abstract int containerId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutID());
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this,factory()).get(viewModelClass());
        viewModel.getToast().observe(this, message -> {
            if (message != null) {
                viewModel.getToast().setValue(null);
                 showToast(message);
                viewModel.getToast().setValue(null);
            }
        });
    }
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
