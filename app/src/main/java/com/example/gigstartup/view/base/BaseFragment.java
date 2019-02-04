package com.example.gigstartup.view.base;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gigstartup.viewModel.BaseViewModel;

public abstract class BaseFragment  <DB extends ViewDataBinding,VM extends BaseViewModel> extends Fragment {
    private DB binding;
    private VM viewModel;
    private Bundle bundle;

    public DB getBinding() {
        return binding;
    }

    public VM getViewModel() {
        return viewModel;
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    protected abstract int fragmentId();

    protected abstract Class<VM> viewModelClass();

    protected abstract BaseViewModel.Factory factory();

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, fragmentId(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setLifecycleOwner(this);
        viewModel = ViewModelProviders.of(this, factory()).get(viewModelClass());
    }
    protected void replaceFragment(Class fragmentClass, String extraTag, boolean addToBackStack, Bundle bundle, Fragment fragmentForResult, int targetRequestCode, boolean replaceWithAnimation) {
        if (getActivity() != null) {
            ((BaseActivity) getActivity()).replaceFragment(fragmentClass, extraTag, addToBackStack, bundle, fragmentForResult, targetRequestCode, replaceWithAnimation);
        }
    }
}
