package com.sarchimarcus.covid19;

import android.content.Context;

import com.sarchimarcus.covid19.Network.CoronaDataRepository;
import com.sarchimarcus.covid19.Network.CoronaNetworkDataSource;

public class InjectorUtils {
    public static CoronaDataRepository provideRepository(Context context) {
        CoronaDataBase database = CoronaDataBase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        CoronaNetworkDataSource networkDataSource =
                CoronaNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return CoronaDataRepository.getInstance(database.coronaDao(),database.countryCoronaDao(), networkDataSource, executors);
    }
    public static CoronaNetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service - in this
        // case the repository will not exist unless it is specifically created.
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return CoronaNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }
}
