package com.sarchimarcus.covid19.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.sarchimarcus.covid19.model.Country;

import java.util.List;

@Dao
public interface CountryCoronaDao {
    @Query("SELECT * FROM countries ORDER BY cases DESC")
    LiveData<List<Country>> loadCountryData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountryData(List<Country> countryList);

}
