package com.example.myapplication.weather;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CurrentTempData implements Serializable{
    private Integer temp;
    private Integer feelLike;
    private Integer pressure;
    private Double deg;
    private Double gust;
    private Double rain;
    private Double snow;
    private Integer humidity;
    private Integer dewPoint;
    private Integer uv;
    private Integer cloud;
    private Long visibility;
    private Double speed;
    private Date date;
    private Date sunriseTime;
    private Date sunsetTime;
    private List<CurrentWeatherDetails> weatherDetailsList;

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public Integer getFeelLike() {
        return feelLike;
    }

    public void setFeelLike(Integer feelLike) {
        this.feelLike = feelLike;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

    public Double getGust() {
        return gust;
    }

    public void setGust(Double gust) {
        this.gust = gust;
    }

    public Double getRain() {
        return rain;
    }

    public void setRain(Double rain) {
        this.rain = rain;
    }

    public Double getSnow() {
        return snow;
    }

    public void setSnow(Double snow) {
        this.snow = snow;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Integer getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Integer dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Integer getUv() {
        return uv;
    }

    public void setUv(Integer uv) {
        this.uv = uv;
    }

    public Integer getCloud() {
        return cloud;
    }

    public void setCloud(Integer cloud) {
        this.cloud = cloud;
    }

    public Long getVisibility() {
        return visibility;
    }

    public void setVisibility(Long visibility) {
        this.visibility = visibility;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getSunriseTime() {
        return sunriseTime;
    }

    public void setSunriseTime(Date sunriseTime) {
        this.sunriseTime = sunriseTime;
    }

    public Date getSunsetTime() {
        return sunsetTime;
    }

    public void setSunsetTime(Date sunsetTime) {
        this.sunsetTime = sunsetTime;
    }

    public List<CurrentWeatherDetails> getWeatherDetailsList() {
        return weatherDetailsList;
    }

    public void setWeatherDetailsList(List<CurrentWeatherDetails> weatherDetailsList) {
        this.weatherDetailsList = weatherDetailsList;
    }

    public CurrentTempData(Integer temp, Integer feelLike, Integer pressure, Double deg, Double gust,
                           Double rain, Double snow, Integer humidity, Integer dewPoint,
                           Integer uv, Integer cloud, Long visibility, Double speed,
                           Date date, Date sunriseTime, Date sunsetTime,
                           List<CurrentWeatherDetails> weatherDetailsList) {
        this.temp = temp;
        this.feelLike = feelLike;
        this.pressure = pressure;
        this.deg = deg;
        this.gust = gust;
        this.rain = rain;
        this.snow = snow;
        this.humidity = humidity;
        this.dewPoint = dewPoint;
        this.uv = uv;
        this.cloud = cloud;
        this.visibility = visibility;
        this.speed = speed;
        this.date = date;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.weatherDetailsList = weatherDetailsList;
    }
}
