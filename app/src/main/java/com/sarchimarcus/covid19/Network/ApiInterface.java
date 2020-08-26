package com.sarchimarcus.covid19.Network;

import com.sarchimarcus.covid19.model.Country;
import com.sarchimarcus.covid19.model.Global;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("v2/all")
    Call <Global> getGlobalData();

    @GET("v2/countries")
    Call <List<Country>> getAllCountryStats();


}
