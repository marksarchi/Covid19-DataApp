package com.sarchimarcus.covid19.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sarchimarcus.covid19.model.Country;
import com.sarchimarcus.covid19.model.Global;

import java.util.List;

@Dao
public interface CoronaDao {
    @Query("SELECT * FROM GLOBAL ORDER BY cases")
    LiveData<Global> loadGlobalData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGlobalData(Global global);



}
