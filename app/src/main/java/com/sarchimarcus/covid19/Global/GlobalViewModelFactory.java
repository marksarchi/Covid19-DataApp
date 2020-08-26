package com.sarchimarcus.covid19.Global;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.sarchimarcus.covid19.Network.CoronaDataRepository;

public class GlobalViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private CoronaDataRepository repository;

    public GlobalViewModelFactory(CoronaDataRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new GlobalViewModel(repository);
    }
}
