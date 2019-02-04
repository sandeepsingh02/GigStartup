package com.example.gigstartup.adapters;

import android.support.annotation.NonNull;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.WorkStatusItemRowBindingImpl;
import com.example.gigstartup.dtos.DtoUserInfo;
import com.example.gigstartup.interfaces.IWorkStatus;
import com.example.gigstartup.view.base.BaseRecyclerview;

public class WorkStatusAdapter extends BaseRecyclerview<WorkStatusItemRowBindingImpl, DtoUserInfo> {
    private IWorkStatus iWorkStatus;
    public WorkStatusAdapter() {
        super(R.layout.work_status_item_row);
    }

    public void setItemClickListner(IWorkStatus iWorkStatus){
        this.iWorkStatus=iWorkStatus;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerview.BaseRecyclerViewHolder baseRecyclerViewHolder, int i) {
        DtoUserInfo dtoUserInfo = getItem(i);
        ((WorkStatusItemRowBindingImpl) baseRecyclerViewHolder.getBinding()).setItem(dtoUserInfo);
        baseRecyclerViewHolder.getBinding().getRoot().setOnClickListener(v -> iWorkStatus.onItemClick(dtoUserInfo));
    }

}
