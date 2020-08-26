package com.sarchimarcus.covid19.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private  static  String BASEURL = "https://corona.lmao.ninja/";
    private  static ApiClient apiClient;
    private  static Retrofit retrofit;

    private ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public  static  synchronized  ApiClient getInstance(){
        if(apiClient == null){
            apiClient = new ApiClient();
        }
        return apiClient;
    }
    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }


}
