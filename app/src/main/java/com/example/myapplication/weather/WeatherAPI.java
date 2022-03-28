package com.example.myapplication.weather;

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.MainActivity;
import com.example.myapplication.dailyForecast.Daily;
import com.example.myapplication.dailyForecast.Temp;
import com.example.myapplication.dailyForecast.WeatherDetails;
import com.example.myapplication.hourly.HourlyForecast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherAPI {
    private static final String TAG = "WEATHER API CLASS";

    private static MainActivity mainActivity;
    //Weather URL
    private static final String WEATHERURL ="https://api.openweathermap.org/data/2.5/onecall";
    private static final String ICONURL = "https://api.openweathermap.org/img/w";

    /* API KEY */
    private static final String APIKEY = "50fadb0bcb8012117c99ca608bf9fb31";

    // Volley Queue
    private static RequestQueue queue;

    private static WeatherData weatherData;

    public static WeatherData parseJSON(String s) {
        try{
            JSONObject jsonObject = new JSONObject(s);
            double latitude = jsonObject.getDouble("lat");
            double longitude = jsonObject.getDouble("lon");

            String timeZone = jsonObject.getString("timezone");
            long timezone_offset = jsonObject.getLong("timezone_offset");

            // Parsing Current Data Temperature
            CurrentTempData currentTempData = null;
            if (jsonObject.has("current")){
                JSONObject currentObj = jsonObject.getJSONObject("current");
                JSONArray currentWeatherJsonArray = currentObj.getJSONArray("weather");
                List<CurrentWeatherDetails> currentWeatherInfo = new ArrayList<>();
                if (currentWeatherJsonArray.length() > 0) {
                    JSONObject currentWeather = currentWeatherJsonArray.getJSONObject(0);
                    CurrentWeatherDetails weatherInfo = new CurrentWeatherDetails(
                            currentWeather.getLong("id"),
                            currentWeather.getString("main"),
                            currentWeather.getString("description"),
                            currentWeather.getString("icon")
                    );
                    currentWeatherInfo.add(weatherInfo);
                }
                Double gust = null;
                if(currentObj.has("wind_gust")) {
                    gust = currentObj.getDouble("wind_gust");
                }

                Double rain = null;
                if(currentObj.has("rain")) {
                    JSONObject rainObject = currentObj.getJSONObject("rain");
                    rain = rainObject.getDouble("1h");
                }

                Double snow = null;
                if(currentObj.has("snow")) {
                    JSONObject snowObject = currentObj.getJSONObject("snow");
                    snow = snowObject.getDouble("1h");
                }


                currentTempData = new CurrentTempData(
                        currentObj.getInt("temp"),
                        currentObj.getInt("feels_like"),
                        currentObj.getInt("pressure"),
                        currentObj.getDouble("wind_deg"),
                        gust,
                        rain, snow,
                        currentObj.getInt("humidity"),
                        currentObj.getInt("dew_point"),
                        currentObj.getInt("uvi"),
                        currentObj.getInt("clouds"),
                        currentObj.getLong("visibility"),
                        currentObj.getDouble("wind_speed"),

                        new Date(currentObj.getLong("dt")),
                        new Date(currentObj.getLong("sunrise")),
                        new Date(currentObj.getLong("sunset")),
                        currentWeatherInfo
                );
            }

            // Hourly Forecast Data
            List<HourlyForecast> hourlyForecastList = new ArrayList<>();
            if (jsonObject.has("hourly")) {
                JSONArray hourlyJsonArray = jsonObject.getJSONArray("hourly");

                for (int i = 0; i < hourlyJsonArray.length(); i++) {
                    JSONObject jo = hourlyJsonArray.getJSONObject(i);
                    JSONArray hourlyWeatherJsonArray = jo.getJSONArray("weather");

                    List<WeatherDetails> hourlyWeatherInfo = new ArrayList<>();
                    if (hourlyWeatherJsonArray.length() > 0) {
                        JSONObject hourlyWeather = hourlyWeatherJsonArray.getJSONObject(0);
                        WeatherDetails weatherInfo = new WeatherDetails(
                                hourlyWeather.getLong("id"),
                                hourlyWeather.getString("main"),
                                hourlyWeather.getString("description"),
                                hourlyWeather.getString("icon")
                        );
                        hourlyWeatherInfo.add(weatherInfo);
                    }

                    HourlyForecast hourlyForecast = new HourlyForecast(
                            new Date(jo.getLong("dt")),
                            jo.getInt("temp"),
                            hourlyWeatherInfo,
                            jo.getDouble("pop")
                    );

                    hourlyForecastList.add(i, hourlyForecast);
                }
            }

            // Daily Forecast Data
            List<Daily> dailyForecast = new ArrayList<>();
            if (jsonObject.has("daily")) {
                JSONArray dailyJsonArray = jsonObject.getJSONArray("daily");

                for (int i = 0; i < dailyJsonArray.length(); i++) {
                    JSONObject jo = dailyJsonArray.getJSONObject(i);
                    JSONArray dailyWeatherJsonArray = jo.getJSONArray("weather");

                    List<WeatherDetails> dailyWeatherInfo = new ArrayList<>();
                    if (dailyWeatherJsonArray.length() > 0) {
                        JSONObject dailyWeather = dailyWeatherJsonArray.getJSONObject(0);
                        WeatherDetails weatherInfo = new WeatherDetails(
                                dailyWeather.getLong("id"),
                                dailyWeather.getString("main"),
                                dailyWeather.getString("description"),
                                dailyWeather.getString("icon")
                        );
                        dailyWeatherInfo.add(weatherInfo);
                    }


                    Temp temperature = null;
                    if (jo.has("temp")) {
                        JSONObject j = jo.getJSONObject("temp");
                        temperature = new Temp(
                                j.getInt("day"),
                                j.getInt("min"),
                                j.getInt("max"),
                                j.getInt("night"),
                                j.getInt("eve"),
                                j.getInt("morn")
                        );
                    }

                    Daily daily = new Daily(
                            new Date(jo.getLong("dt")),
                            jo.getInt("pop"),
                            jo.getInt("uvi"),
                            temperature,
                            dailyWeatherInfo
                    );

                    dailyForecast.add(i, daily);
                }
            }

            return weatherData = new WeatherData(latitude, longitude, timeZone, timezone_offset, currentTempData, hourlyForecastList, dailyForecast);
        } catch (Exception e){
            e.printStackTrace();
        }
        return weatherData;
    }
}
