package com.example.gigstartup.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.example.gigstartup.interfaces.IAPICall;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<String> toast = new MutableLiveData<>();
    private MutableLiveData<Boolean> showProgress = new MutableLiveData<>();

    public  MutableLiveData<String> getToast() {
        return toast;
    }

    public  MutableLiveData<Boolean> getProgress() {
        return showProgress;
    }
    public void showToast(String message) {
        toast.postValue(message);
    }

    MutableLiveData<Boolean> getProgressStatus() {
        return showProgress;
    }

    public void setShowProgress(Boolean message) {
        showProgress.postValue(message);
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    public abstract static class Factory extends ViewModelProvider.NewInstanceFactory {

        public abstract BaseViewModel getClassInstance();

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) getClassInstance();
        }
    }
    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
