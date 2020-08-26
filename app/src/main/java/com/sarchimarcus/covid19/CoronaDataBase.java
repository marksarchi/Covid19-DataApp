package com.sarchimarcus.covid19;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sarchimarcus.covid19.Dao.CoronaDao;
import com.sarchimarcus.covid19.Dao.CountryCoronaDao;
import com.sarchimarcus.covid19.model.Country;
import com.sarchimarcus.covid19.model.Global;

@Database(entities = {Global.class, Country.class}, version = 1)
public abstract class CoronaDataBase extends RoomDatabase {
    private static final  String LOG_TAG =CoronaDataBase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private  static  final  String  DATABASE_NAME = "globaldata";
    private  static  CoronaDataBase sInstance;


    public  static  CoronaDataBase getInstance(Context context){

        if(sInstance ==null){
            synchronized (LOCK){
                Log.e(LOG_TAG,"Creating new DataBase Instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CoronaDataBase.class,
                        CoronaDataBase.DATABASE_NAME)

                        .build();
            }
        }
        Log.e(LOG_TAG,"Getting the database instance");
        return sInstance;
    }
    public  abstract CoronaDao coronaDao();

    public  abstract CountryCoronaDao countryCoronaDao();
}
