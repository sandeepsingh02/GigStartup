package com.example.gigstartup.view.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.ProfileFragmentBinding;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.model.APICall;
import com.example.gigstartup.utils.Constants;
import com.example.gigstartup.utils.SharedPref;
import com.example.gigstartup.view.base.BaseFragment;
import com.example.gigstartup.viewModel.BaseViewModel;

public class ProfileFragment extends BaseFragment<ProfileFragmentBinding,ProfileViewModel> {

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private Context mContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBinding().setViewModel(getViewModel());
        getViewModel().title.postValue(getBundle().getString(Constants.TITLE));
        getViewModel().name.postValue(SharedPref.read(Constants.NAME,""));
        getViewModel().email.postValue(SharedPref.read(Constants.EMAIL,""));
        getViewModel().phone.postValue(SharedPref.read(Constants.MOBILE_NUMBER,""));
        getViewModel().imageUrl.postValue(SharedPref.read(Constants.IMAGE_URL,""));
        getViewModel().skillName.postValue(null);
        getViewModel().rate.postValue(3.0f);

    }

    @Override
    protected int fragmentId() {
        return R.layout.profile_fragment;
    }

    @Override
    protected Class viewModelClass() {
        return ProfileViewModel.class;
    }

    @Override
    protected BaseViewModel.Factory factory() {
        return new BaseViewModel.Factory(){

            @Override
            public BaseViewModel getClassInstance() {
                IAPICall iapiCall= APICall.getClient(mContext).create(IAPICall.class);
                return new ProfileViewModel(iapiCall,SharedPref.read(Constants.SKILL_ID,""));
            }
        };
    }
}
