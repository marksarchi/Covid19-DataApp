package com.sarchimarcus.covid19.Country;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sarchimarcus.covid19.Network.CoronaDataRepository;
import com.sarchimarcus.covid19.model.Country;

import java.util.List;

public class CountryViewModel extends ViewModel {
    private LiveData<List<Country>> mutableLiveData;

    public CountryViewModel(CoronaDataRepository coronaDataRepository) {
        mutableLiveData = null;
        mutableLiveData = coronaDataRepository.getCurrentCountry();
    }
    public LiveData<List<Country>> getCountryData(){
        return mutableLiveData;
    }
}
