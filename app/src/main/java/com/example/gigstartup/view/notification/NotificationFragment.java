package com.example.gigstartup.view.notification;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Window;

import com.example.gigstartup.R;
import com.example.gigstartup.adapters.NotificationAdapter;
import com.example.gigstartup.databinding.NotificationFragmentBinding;
import com.example.gigstartup.dtos.DtoNotification;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.model.APICall;
import com.example.gigstartup.view.base.BaseFragment;
import com.example.gigstartup.viewModel.BaseViewModel;

public class NotificationFragment extends BaseFragment<NotificationFragmentBinding,NotificationViewModel> implements NotificationAdapter.NotificationItemClickListner {

    private IAPICall iapiCall;
    private Context mContext;
    public static NotificationFragment newInstance() {
        return new NotificationFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @Override
    protected int fragmentId() {
        return R.layout.notification_fragment;
    }

    @Override
    protected Class viewModelClass() {
        return NotificationViewModel.class;
    }

    @Override
    protected BaseViewModel.Factory factory() {
        return new BaseViewModel.Factory() {
            @Override
            public BaseViewModel getClassInstance() {
                iapiCall = APICall.getClient(mContext).create(IAPICall.class);
                return new NotificationViewModel(iapiCall);
            }
        };
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBinding().setViewModel(getViewModel());
        getBinding().swipeRefreshLayout.setOnRefreshListener(() -> {
            if(getBinding().swipeRefreshLayout.isRefreshing()) {
                getViewModel().dtoNotificationList.clear();
                getViewModel().notificationAdapter.notifyDataSetChanged();
                getViewModel().getNotification();
                getViewModel().notificationAdapter.notifyDataSetChanged();
                getBinding().swipeRefreshLayout.setRefreshing(false);
            }
        });
        getBinding().getViewModel().notificationAdapter.setNotificationItemClickListner(this::onItemClick);
    }

    @Override
    public void onItemClick(DtoNotification dtoNotification) {

    }
}
