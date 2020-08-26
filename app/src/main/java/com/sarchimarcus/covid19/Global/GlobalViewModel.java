package com.sarchimarcus.covid19.Global;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sarchimarcus.covid19.Network.CoronaDataRepository;
import com.sarchimarcus.covid19.model.Global;

public class GlobalViewModel extends ViewModel {

    private static final String LOG_TAG =GlobalViewModel.class.getSimpleName();
    private LiveData <Global> mutableLiveData;

    public GlobalViewModel(CoronaDataRepository globalRepository) {
        mutableLiveData = null;
        mutableLiveData = globalRepository.getCurrentGlobal();

    }
    public  LiveData<Global> getGlobalData(){
        Log.e(LOG_TAG, "Viewmodel get globaldata");
        return mutableLiveData;
    }
}
