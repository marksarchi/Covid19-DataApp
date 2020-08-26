package com.sarchimarcus.covid19.Network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.sarchimarcus.covid19.AppExecutors;
import com.sarchimarcus.covid19.model.Country;
import com.sarchimarcus.covid19.model.Global;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoronaNetworkDataSource {
    private static final String LOG_TAG =CoronaNetworkDataSource.class.getSimpleName() ;

    private final AppExecutors appExecutors;
    private final Context mContext;
    private static final Object LOCK = new Object();
    private static CoronaNetworkDataSource sInstance;

    private MutableLiveData<Global> globalMutableLiveData = new MutableLiveData<>();
    private  MutableLiveData<List<Country>> countryMutableLiveData = new MutableLiveData<>();

    public CoronaNetworkDataSource( Context mContext,AppExecutors appExecutors) {

        this.appExecutors = appExecutors;
        this.mContext = mContext;
    }
    public static CoronaNetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.e(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new CoronaNetworkDataSource(context.getApplicationContext(), executors);
                Log.e(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }

    public MutableLiveData <Global> getCurrentGlobal(){
        return globalMutableLiveData;
    }
    public  MutableLiveData<List<Country>>getCurrentCountries(){
        return countryMutableLiveData;
    }

  public   void fetchGlobal(){
      Log.e(LOG_TAG, "Fetch global called");
        appExecutors.networkIO().execute(() -> {
            try {
                Call<Global> call;
                call = ApiClient.getInstance().getApi().getGlobalData();
                call.enqueue(new Callback<Global>() {
                    @Override
                    public void onResponse(Call<Global> call, Response<Global> response) {
                        Log.e(LOG_TAG, "GLOBAL FETCH SUCCESSFUL ");
                        if(response.isSuccessful()){
                            globalMutableLiveData.postValue(response.body());

                        }
                    }

                    @Override
                    public void onFailure(Call<Global> call, Throwable t) {
                        Log.e(LOG_TAG, "FETCH FAILED ");
                        Log.e(LOG_TAG,"error"+t.getMessage());
                        globalMutableLiveData.postValue(null);

                    }
                });
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

    }
    public  void fetchCountries(){
        Call<List<Country>> call;
        call = ApiClient.getInstance().getApi().getAllCountryStats();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if(response.isSuccessful()&& response.body().size()>0){
                    countryMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                countryMutableLiveData.postValue(null);

            }
        });
    }

    public  void fetchAllData(){
        fetchGlobal();
        fetchCountries();
    }



}
