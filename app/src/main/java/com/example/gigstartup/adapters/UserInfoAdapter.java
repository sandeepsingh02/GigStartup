package com.example.gigstartup.adapters;

import android.support.annotation.NonNull;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.UserInfoRowBinding;
import com.example.gigstartup.dtos.DtoUserInfo;
import com.example.gigstartup.interfaces.IWorkStatus;
import com.example.gigstartup.view.base.BaseRecyclerview;

public class UserInfoAdapter extends BaseRecyclerview<UserInfoRowBinding, DtoUserInfo> {
    private IWorkStatus iWorkStatus;
    public UserInfoAdapter() {
        super(R.layout.user_info_row);
    }

    public void  setOnItemClicListent(IWorkStatus iWorkStatus){
        this.iWorkStatus=iWorkStatus;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerview.BaseRecyclerViewHolder baseRecyclerViewHolder, int i) {
        DtoUserInfo dtoUserInfo=getItem(i);
        ((UserInfoRowBinding)baseRecyclerViewHolder.getBinding()).setItem(dtoUserInfo);
        baseRecyclerViewHolder.getBinding().getRoot().setOnClickListener(v -> iWorkStatus.onItemClick(dtoUserInfo));
    }

}
