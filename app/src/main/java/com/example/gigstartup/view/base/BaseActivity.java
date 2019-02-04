package com.example.gigstartup.view.base;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.gigstartup.utils.CustomDialog;
import com.example.gigstartup.utils.ToastUtils;
import com.example.gigstartup.viewModel.BaseViewModel;

public abstract class BaseActivity <VM extends BaseViewModel, DB extends ViewDataBinding> extends AppCompatActivity {
    private DB binding;
    private VM viewModel;
    public CustomDialog customDialog;
    protected abstract int containerId();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customDialog=new CustomDialog(this);
        binding = DataBindingUtil.setContentView(this, getLayoutID());
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this,factory()).get(viewModelClass());
        viewModel.getToast().observe(this, message -> {
            if (message != null) {
                viewModel.setShowProgress(null);
                showToast(message);
                viewModel.setShowProgress(null);
            }
        });
        viewModel.getProgress().observe(this, message -> {
            if (message != null) {
                viewModel.getToast().setValue(null);
                showHideProgress(message);
                viewModel.getToast().setValue(null);
            }
        });
    }
    public void showHideProgress(Boolean isShow) {
        if(isShow)
            customDialog.showDialog();
        else
            customDialog.dismissDialog();
    }
    public void showToast(String message) {
        ToastUtils.toast(this,message);
    }

    public void showToast(String message,boolean flag) {
        if ((flag)) {
            ToastUtils.toastSuccses(this, message);
        } else {
            ToastUtils.toastFailure(this, message);
        }
    }

    public  void intentStartActivity(Intent intent) {
        startActivity(intent);
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
    public void replaceFragment(Class fragmentClass, String extraTag, boolean addToBackStack, Bundle bundle, Fragment fragmentForResult, int targetRequestCode, boolean replaceWithAnimation) {

        if (containerId() == 0) throw new NullPointerException("ContainerId cannot be null");
        try {
            String finalTag;
            if (extraTag != null && extraTag.equals(""))
                finalTag = fragmentClass.getSimpleName() + extraTag;
            else finalTag = fragmentClass.getSimpleName();
            boolean isPopBackStack = getSupportFragmentManager().popBackStackImmediate(finalTag, 0);
            if (!isPopBackStack) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(finalTag);
                if (fragment == null) try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (fragment != null) {
                    if (fragment instanceof BaseFragment && bundle != null) {
                        ((BaseFragment) fragment).setBundle(bundle);
                    } else if(!(fragment instanceof BaseFragment) && bundle != null) {
                        fragment.setArguments(bundle);
                    }
                    if (fragmentForResult != null) {
                        fragment.setTargetFragment(fragmentForResult, targetRequestCode);
                    }
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(containerId(), fragment, finalTag);

                    if (addToBackStack) fragmentTransaction.addToBackStack(finalTag);
                    getSupportFragmentManager().executePendingTransactions();
                    fragmentTransaction.commit();

                }
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
    public BaseFragment getCurrentFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(containerId());
        if (fragment == null) return null;
        else if(fragment instanceof BaseFragment) return (BaseFragment) fragment;
        else return null;
    }
}
