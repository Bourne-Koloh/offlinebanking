package com.ekenya.rnd.offlinebanking.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    @Inject
    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public MutableLiveData<String> getText() {
        return mText;
    }
}