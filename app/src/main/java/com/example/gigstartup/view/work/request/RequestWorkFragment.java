package com.example.gigstartup.view.work.request;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.FragmentHireWorkBinding;
import com.example.gigstartup.databinding.FragmentRequestWorkBinding;
import com.example.gigstartup.dtos.DtoUserInfo;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.interfaces.IWorkStatus;
import com.example.gigstartup.model.APICall;
import com.example.gigstartup.view.base.BaseFragment;
import com.example.gigstartup.view.main.MainActivity;
import com.example.gigstartup.view.work.denied.DeniedWorkFragment;
import com.example.gigstartup.viewModel.BaseViewModel;
import com.example.gigstartup.viewModel.WorkViewModel;


public class RequestWorkFragment extends BaseFragment<FragmentRequestWorkBinding, WorkViewModel> implements IWorkStatus {
    private Context mContext;
    private IAPICall iapiCall;

    public static RequestWorkFragment newInstance() {
        return new RequestWorkFragment();
    }

    @Override
    protected int fragmentId() {
        return R.layout.fragment_request_work;
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
                return new WorkViewModel(iapiCall,"pending");
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
            ((MainActivity) getActivity()).getBinding().appBarHome.tvTital.setText(getString(R.string.menu_requested_work));
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
                getViewModel().getWorkData("pending");
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
