package com.example.gigstartup.view.work.hire;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.gigstartup.dtos.DtoUserInfo;
import com.example.gigstartup.viewModel.BaseViewModel;

public class UserDeailsViewModel extends BaseViewModel {
    public MutableLiveData<DtoUserInfo> dtoUserInfoMutableLiveData=new MutableLiveData<>();

}
