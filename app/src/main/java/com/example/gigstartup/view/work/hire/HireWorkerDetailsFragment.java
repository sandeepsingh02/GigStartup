package com.example.gigstartup.view.work.hire;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gigstartup.R;
import com.example.gigstartup.databinding.FragmentHireWorkBinding;
import com.example.gigstartup.databinding.FragmentHireWorkerDetailsBinding;
import com.example.gigstartup.dtos.DtoUserInfo;
import com.example.gigstartup.interfaces.IAPICall;
import com.example.gigstartup.interfaces.IWorkStatus;
import com.example.gigstartup.model.APICall;
import com.example.gigstartup.utils.Constants;
import com.example.gigstartup.view.base.BaseFragment;
import com.example.gigstartup.view.main.MainActivity;
import com.example.gigstartup.viewModel.BaseViewModel;
import com.example.gigstartup.viewModel.HomeViewModel;

public class HireWorkerDetailsFragment extends BaseFragment<FragmentHireWorkerDetailsBinding, HomeViewModel> implements IWorkStatus {
    private Context mContext;
    private IAPICall iapiCall;

    public static HireWorkFragment newInstance() {
        return new HireWorkFragment();
    }

    @Override
    protected int fragmentId() {
        return R.layout.fragment_hire_worker_details;
    }

    @Override
    protected Class<HomeViewModel> viewModelClass() {
        return HomeViewModel.class;
    }

    @Override
    protected BaseViewModel.Factory factory() {
        iapiCall = APICall.getClient(mContext).create(IAPICall.class);
        return new BaseViewModel.Factory() {
            @Override
            public BaseViewModel getClassInstance() {
                return new HomeViewModel(iapiCall,getBundle().getString(Constants.KEY));
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
            ((MainActivity) getActivity()).getBinding().appBarHome.tvTital.setText(getString(R.string.hire));
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getBinding().setViewModel(getViewModel());
        getViewModel().title.postValue(getBundle().getString(Constants.TITLE));
        getViewModel().userInfoAdapter.setOnItemClicListent(this::onItemClick);
    }

    @Override
    public void onItemClick(DtoUserInfo dtoUserInfo) {
        replaceFragment(UserDeailsFragment.class, null, true, null, null, 0, false);
    }
}