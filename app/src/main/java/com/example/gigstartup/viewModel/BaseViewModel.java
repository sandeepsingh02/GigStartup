package com.example.gigstartup.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class BaseViewModel extends ViewModel {

    private MutableLiveData<String> toast = new MutableLiveData<>();
    private MutableLiveData<Boolean> showProgress = new MutableLiveData<>();


    public  MutableLiveData<String> getToast() {
        return toast;
    }

    public void showToast(String message) {
        toast.postValue(message);
    }

    public void setShowProgress(Boolean message) {
        showProgress.postValue(message);
    }


    public abstract static class Factory extends ViewModelProvider.NewInstanceFactory {

        public abstract BaseViewModel getClassInstance();

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) getClassInstance();
        }
    }
}
