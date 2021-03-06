package com.example.gigstartup.view.work.complete;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.FragmentCompleteWorkBinding;
import com.example.gigstartup.databinding.FragmentHireWorkBinding;
import com.example.gigstartup.dtos.DtoUserInfo;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.interfaces.IWorkStatus;
import com.example.gigstartup.model.APICall;
import com.example.gigstartup.view.base.BaseFragment;
import com.example.gigstartup.view.main.MainActivity;
import com.example.gigstartup.view.work.denied.DeniedWorkFragment;
import com.example.gigstartup.viewModel.BaseViewModel;
import com.example.gigstartup.viewModel.WorkViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteWorkFragment extends BaseFragment<FragmentCompleteWorkBinding, WorkViewModel> implements IWorkStatus {
    private Context mContext;
    private IAPICall iapiCall;

    public static CompleteWorkFragment newInstance() {
        return new CompleteWorkFragment();
    }

    @Override
    protected int fragmentId() {
        return R.layout.fragment_complete_work;
    }

    @Override
    protected Class<WorkViewModel> viewModelClass() {
        return WorkViewModel.class;
    }

    @Override
    protected BaseViewModel.Factory factory() {
        iapiCall = APICall.getClient(mContext).create(IAPICall.class);
        return new BaseViewModel.Factory() {
            @Override
            public BaseViewModel getClassInstance() {
                return new WorkViewModel(iapiCall,"completed");
            }
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
    @Override
    public void onStop() {
        super.onStop();
        if(getActivity() != null) {
            ((MainActivity) getActivity()).getBinding().appBarHome.tvTital.setText(getString(R.string.app_name));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            ((MainActivity) getActivity()).getBinding().appBarHome.tvTital.setText(getString(R.string.menu_completed_work));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBinding().setViewModel(getViewModel());
        getBinding().swipeRefreshLayout.setOnRefreshListener(() -> {
            if(getBinding().swipeRefreshLayout.isRefreshing()) {
                getViewModel().listUser.clear();
                getViewModel().workStatusAdapter.notifyDataSetChanged();
                getViewModel().getWorkData("completed");
                getViewModel().workStatusAdapter.notifyDataSetChanged();
                getBinding().swipeRefreshLayout.setRefreshing(false);
            }
        });
        getViewModel().workStatusAdapter.setItemClickListner(this::onItemClick);
    }

    @Override
    public void onItemClick(DtoUserInfo dtoUserInfo) {

    }
}

