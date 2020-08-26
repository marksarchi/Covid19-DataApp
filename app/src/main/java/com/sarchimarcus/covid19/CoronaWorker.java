package com.sarchimarcus.covid19;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.sarchimarcus.covid19.Dao.CoronaDao;
import com.sarchimarcus.covid19.Network.CoronaNetworkDataSource;

public class CoronaWorker extends Worker {
    private static final String TAG = CoronaWorker.class.getSimpleName();
    private CoronaDao coronaDao;
    CoronaNetworkDataSource coronaNetworkDataSource= InjectorUtils.provideNetworkDataSource(getApplicationContext());
    public CoronaWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        coronaNetworkDataSource.fetchAllData();
        return Result.success();
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.i(TAG, "OnStopped called for this worker");
    }
}
