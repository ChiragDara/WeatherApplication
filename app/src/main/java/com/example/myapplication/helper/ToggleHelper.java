package com.example.myapplication.helper;

import android.content.Intent;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.weather.WeatherData;
import com.example.myapplication.weeklyForecast.WeeklyActivity;

public class ToggleHelper {




    public static void dailyForcastActivity(MainActivity mainActivity, WeatherData weatherData){
        if(HelperClass.isNetworkConnected(mainActivity)){
            Intent intent = new Intent(mainActivity, WeeklyActivity.class);
            intent.putExtra(String.valueOf(R.string.weather), weatherData);
            //intent.putExtra(String.valueOf(R.string.unit), unit);
            //intent.putExtra(String.valueOf(R.string.locale), locationName);
            //startActivity(intent);
        } else {
            Toast.makeText(mainActivity, "No Network Connection", Toast.LENGTH_LONG).show();
        }
    }

    public static void locationChange(MainActivity mainActivity){

    }
}
