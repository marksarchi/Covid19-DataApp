package com.sarchimarcus.covid19.Country;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sarchimarcus.covid19.Network.CoronaDataRepository;

public class CountryViewModelFactory extends ViewModelProvider.NewInstanceFactory{


    private CoronaDataRepository coronaDataRepository;
    CountryViewModelFactory(CoronaDataRepository coronaDataRepository) {
        this.coronaDataRepository = coronaDataRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CountryViewModel(coronaDataRepository);
    }
}
