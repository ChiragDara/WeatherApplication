package com.example.myapplication.dailyForecast;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Daily implements Serializable {
    private Date dt;
    private Integer pop;
    private Integer uvi;
    private Temp temperature;
    private List<WeatherDetails> weatherDetailsList;

    public Daily(Date dt, Integer pop, Integer uvi, Temp temperature, List<WeatherDetails> weatherDetailsList) {
        this.dt = dt;
        this.pop = pop;
        this.uvi = uvi;
        this.temperature = temperature;
        this.weatherDetailsList = weatherDetailsList;
    }


    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Integer getPop() {
        return pop;
    }

    public void setPop(Integer pop) {
        this.pop = pop;
    }

    public Integer getUvi() {
        return uvi;
    }

    public void setUvi(Integer uvi) {
        this.uvi = uvi;
    }

    public Temp getTemperature() {
        return temperature;
    }

    public void setTemperature(Temp temperature) {
        this.temperature = temperature;
    }

    public List<WeatherDetails> getWeatherDetailsList() {
        return weatherDetailsList;
    }

    public void setWeatherDetailsList(List<WeatherDetails> weatherDetailsList) {
        this.weatherDetailsList = weatherDetailsList;
    }
}