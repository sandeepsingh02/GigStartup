package com.example.gigstartup.view.feedback;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.FeedbackFragmentBinding;
import com.example.gigstartup.utils.Constants;
import com.example.gigstartup.view.base.BaseFragment;
import com.example.gigstartup.viewModel.BaseViewModel;

public class FeedbackFragment extends BaseFragment<FeedbackFragmentBinding,FeedbackViewModel> {

    public static FeedbackFragment newInstance() {
        return new FeedbackFragment();
    }
    private Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=mContext;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBinding().setViewModel(getViewModel());
        getViewModel().title.postValue(getBundle().getString(Constants.TITLE));
    }

    @Override
    protected int fragmentId() {
        return R.layout.feedback_fragment;
    }

    @Override
    protected Class viewModelClass() {
        return FeedbackViewModel.class;
    }

    @Override
    protected BaseViewModel.Factory factory() {
        return new BaseViewModel.Factory(){

            @Override
            public BaseViewModel getClassInstance() {
                return new FeedbackViewModel();
            }
        };
    }
}
