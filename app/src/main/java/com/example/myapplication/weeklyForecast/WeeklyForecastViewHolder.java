package com.example.myapplication.weeklyForecast;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class WeeklyForecastViewHolder extends RecyclerView.ViewHolder {

    TextView weeklyForecastTime, weeklyForecastTemp,weeklyForecastWeatherDesc, weeklyForecastPrecipitation,weeklyForecastUvi
            ,weeklyForecastMorningTemperature,weeklyForecastDayTemperature,weeklyForecastEveningTemperature,
            weeklyForecastMorningTime,weeklyForecastDayTime, weeklyForecastEveningTime,weeklyForecastNightTime, weeklyForecastNightTemperature;
    ImageView weeklyForecastWeatherIcon;


    public WeeklyForecastViewHolder(@NonNull View itemView) {
        super(itemView);
        weeklyForecastTime = itemView.findViewById(R.id.weeklyForecastTime);
        weeklyForecastTemp = itemView.findViewById(R.id.weeklyForecastTemp);
        weeklyForecastWeatherDesc = itemView.findViewById(R.id.weeklyForecastWeatherDesc);
        weeklyForecastPrecipitation= itemView.findViewById(R.id.weeklyForecastPrecipitation);
        weeklyForecastUvi = itemView.findViewById(R.id.weeklyForecastUvi);
        weeklyForecastMorningTemperature= itemView.findViewById(R.id.weeklyForecastMorningTemperature);
        weeklyForecastDayTemperature = itemView.findViewById(R.id.weeklyForecastDayTemperature);
        weeklyForecastEveningTemperature = itemView.findViewById(R.id.weeklyForecastEveningTemperature);
        weeklyForecastMorningTime = itemView.findViewById(R.id.weeklyForecastMorningTime);
        weeklyForecastDayTime = itemView.findViewById(R.id.weeklyForecastDayTime);
        weeklyForecastEveningTime = itemView.findViewById(R.id.weeklyForecastEveningTime);
        weeklyForecastNightTime = itemView.findViewById(R.id.weeklyForecastNightTime);
        weeklyForecastNightTemperature =itemView.findViewById(R.id.weeklyForecastNightTemperature);
        weeklyForecastWeatherIcon = itemView.findViewById(R.id.weeklyForecastWeatherIcon);

    }
}
