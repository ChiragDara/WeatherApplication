package com.example.myapplication.weather;

import android.graphics.Bitmap;

import com.example.myapplication.dailyForecast.Daily;
import com.example.myapplication.hourly.HourlyForecast;

import java.io.Serializable;
import java.util.List;

public class WeatherData implements Serializable {

    private Double latitude;

    private Double longitude;

    private String timeZone;

    private Long timeZoneOffset;

    private List<HourlyForecast> hourlyList;

    private List<Daily> dailyForecastList;

    private Bitmap bitmap;

    private CurrentTempData currentTempData;

    public WeatherData(Double latitude, Double longitude, String timeZone, Long timeZoneOffset,
                       CurrentTempData currentTempData, List<HourlyForecast> hourlyList, List<Daily> dailyForecastList) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZone = timeZone;
        this.timeZoneOffset = timeZoneOffset;
        this.currentTempData = currentTempData;
        this.hourlyList = hourlyList;
        this.dailyForecastList = dailyForecastList;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Long getTimeZoneOffset() {
        return timeZoneOffset;
    }

    public void setTimeZoneOffset(Long timeZoneOffset) {
        this.timeZoneOffset = timeZoneOffset;
    }

    public CurrentTempData getCurrentTempData() {
        return currentTempData;
    }

    public void setCurrentTempData(CurrentTempData currentTempData) {
        this.currentTempData = currentTempData;
    }

    public List<HourlyForecast> getHourlyList() {
        return hourlyList;
    }

    public void setHourlyList(List<HourlyForecast> hourlyList) {
        this.hourlyList = hourlyList;
    }

    public List<Daily> getDailyForecastList() {
        return dailyForecastList;
    }

    public void setDailyForecastList(List<Daily> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }
}
