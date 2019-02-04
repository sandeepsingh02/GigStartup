package com.example.gigstartup.view.work.hire;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.UserDeailsFragmentBinding;
import com.example.gigstartup.view.base.BaseFragment;
import com.example.gigstartup.viewModel.BaseViewModel;

public class UserDeailsFragment extends BaseFragment<UserDeailsFragmentBinding,UserDeailsViewModel> {


    public static UserDeailsFragment newInstance() {
        return new UserDeailsFragment();
    }

    @Override
    protected int fragmentId() {
        return R.layout.user_deails_fragment;
    }

    @Override
    protected Class viewModelClass() {
        return UserDeailsViewModel.class;
    }

    @Override
    protected BaseViewModel.Factory factory() {
        return new BaseViewModel.Factory() {
            @Override
            public BaseViewModel getClassInstance() {
                return new UserDeailsViewModel();
            }
        };
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBinding().setViewModel(getViewModel());
    }

}
