package com.sarchimarcus.covid19.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "countries")
public class Country {


    @PrimaryKey(autoGenerate = false)
    @SerializedName("country")
    @Expose
    @NonNull
    private String country;

    @SerializedName("cases")
    @Expose
    private Integer cases;

    @SerializedName("todayCases")
    @Expose
    private Integer todayCases;

    @SerializedName("deaths")
    @Expose
    private Integer deaths;

    @SerializedName("todayDeaths")
    @Expose
    private Integer todayDeaths;

    @SerializedName("recovered")
    @Expose
    private Integer recovered;

    @SerializedName("active")
    @Expose
    private Integer active;

    @SerializedName("critical")
    @Expose
    private Integer critical;







    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(Integer todayCases) {
        this.todayCases = todayCases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(Integer todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getCritical() {
        return critical;
    }

    public void setCritical(Integer critical) {
        this.critical = critical;
    }







}
