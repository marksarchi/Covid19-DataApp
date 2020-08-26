package com.sarchimarcus.covid19.Network;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.sarchimarcus.covid19.AppExecutors;
import com.sarchimarcus.covid19.CoronaDataBase;
import com.sarchimarcus.covid19.CoronaWorker;
import com.sarchimarcus.covid19.Dao.CoronaDao;
import com.sarchimarcus.covid19.Dao.CountryCoronaDao;
import com.sarchimarcus.covid19.model.Country;
import com.sarchimarcus.covid19.model.Global;


import org.threeten.bp.ZonedDateTime;

import java.time.ZoneOffset;

import java.util.List;



public class CoronaDataRepository {
    private static final String LOG_TAG =CoronaDataRepository.class.getSimpleName();
private static CoronaDataRepository sInstance;
    private final CoronaDao coronaDao;
    private  final CountryCoronaDao countryCoronaDao;
    private final CoronaNetworkDataSource dataSource;
    private final AppExecutors mExecutors;
    private static final Object LOCK = new Object();
    private boolean mInitialized = false;
    private Context mContext;
    private CoronaDataBase coronaDataBase = CoronaDataBase.getInstance(mContext);

    public CoronaDataRepository(CoronaDao coronaDao,CountryCoronaDao countryCoronaDao, CoronaNetworkDataSource dataSource, AppExecutors mExecutors) {
        this.coronaDao = coronaDao;
        this.dataSource = dataSource;
        this.mExecutors = mExecutors;
        this.countryCoronaDao = countryCoronaDao;

        LiveData <Global> globalNetworkData = dataSource.getCurrentGlobal();
        LiveData<List<Country>>  countryNetworkData = dataSource.getCurrentCountries();

            globalNetworkData.observeForever(newGlobal->{
                mExecutors.diskIO().execute(()->{
                    if(newGlobal!=null){
                        coronaDataBase.coronaDao().insertGlobalData(newGlobal);
                        Log.e(LOG_TAG, "New values inserted");
                    }


                });
            });

            countryNetworkData.observeForever(newCountries->{
                mExecutors.diskIO().execute(()->{
                    if(newCountries!=null){
                        coronaDataBase.countryCoronaDao().insertCountryData(newCountries);}


                });
            });


    }
    public synchronized static CoronaDataRepository getInstance(
            CoronaDao weatherDao,CountryCoronaDao countryCoronaDao, CoronaNetworkDataSource weatherNetworkDataSource,
            AppExecutors executors) {
        Log.e(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new CoronaDataRepository(weatherDao,countryCoronaDao, weatherNetworkDataSource,
                        executors);
                Log.e(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }
    public LiveData<Global> getCurrentGlobal(){
        Log.e(LOG_TAG, "getCurrentGlobal");
        mExecutors.networkIO().execute(() -> {
            if(isFetchNeeded(ZonedDateTime.now().minusMinutes(2))){
                initializeWorker();
           }


        });

        return coronaDao.loadGlobalData();
    }

    public  LiveData<List<Country>> getCurrentCountry(){
        return  countryCoronaDao.loadCountryData();
    }
    private void initializeWorker(){
        Constraints constraints = new Constraints.Builder()
                // The Worker needs Network connectivity
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(CoronaWorker.class)
                .setConstraints(constraints)
                .build();
        WorkManager.getInstance(mContext)
                .enqueue(request);

    }


    public  boolean isFetchNeeded(ZonedDateTime lastFetchTime){
        ZonedDateTime twoMinsAgo = ZonedDateTime.now().minusMinutes(2);
        return  lastFetchTime.isBefore(twoMinsAgo);
    }








}
