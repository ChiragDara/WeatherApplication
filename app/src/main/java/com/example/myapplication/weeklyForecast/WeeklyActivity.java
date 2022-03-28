package com.example.myapplication.weeklyForecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.dailyForecast.Daily;
import com.example.myapplication.weather.WeatherData;

import java.util.ArrayList;
import java.util.List;

public class WeeklyActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Daily> dailyList = new ArrayList<>();
    private WeeklyForecastAdapter weeklyForecastAdapter;
    private WeatherData weatherData;
    private String unit;
    private String locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekely_forecast_recyclerview);
        recyclerView = findViewById(R.id.dailyForecast);

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.weatherData))
                && intent.hasExtra(String.valueOf(R.string.unit))
                && intent.hasExtra(String.valueOf(R.string.locale))) {
            this.weatherData = (WeatherData) intent.getSerializableExtra("weatherData");
            this.unit = (String) intent.getSerializableExtra(String.valueOf(R.string.unit));
            this.locale = intent.getStringExtra(String.valueOf(R.string.locale));
            this.setTitle(locale);
            //if(weatherData != null){
                this.dailyList.addAll(weatherData.getDailyForecastList());
                this.weeklyForecastAdapter = new WeeklyForecastAdapter(dailyList,
                        this, unit, weatherData);
            //}
            recyclerView.setAdapter(weeklyForecastAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false));
        }
    }
}