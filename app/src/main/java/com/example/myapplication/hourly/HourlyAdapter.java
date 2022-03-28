package com.example.myapplication.hourly;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.helper.HelperClass;
import com.example.myapplication.weather.WeatherData;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyViewHolder> {

    private List<HourlyForecast>  hourlyForecastsList;
    private MainActivity mainActivity;
    private WeatherData weatherData;
    String unit = "";

    public HourlyAdapter(List<HourlyForecast> hourlyForecasts, MainActivity mainActivity, WeatherData weatherData, String unit) {
        this.hourlyForecastsList = hourlyForecasts;
        this.mainActivity = mainActivity;
        this.weatherData = weatherData;
        this.unit = unit;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_view_items, parent,
                false);

        view.setOnClickListener(mainActivity);

        return new HourlyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        HourlyForecast hourlyForecast = hourlyForecastsList.get(position);

        String pattern = "HH:MM a";
        String timePattern = "EEEE";

        LocalDateTime ldt = LocalDateTime.ofEpochSecond(hourlyForecast.getDate().getTime() + weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);
        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern(pattern, Locale.getDefault());

        DateTimeFormatter tf =
                DateTimeFormatter.ofPattern(timePattern, Locale.getDefault());
        // Logic for Today
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        Date date = new Date();
        String dayOfTheWeek = simpleDateFormat.format(date);
        if(dayOfTheWeek.equals(ldt.format(tf))){
            holder.day.setText("Today");
        } else {
            holder.day.setText(ldt.format(tf));
        }

        holder.time.setText(ldt.format(dtf));
        holder.temperature.setText(String.format("%s%s", hourlyForecast.getTemp(), HelperClass.formatUnit(unit)));

        String iconCode = "_" + hourlyForecast.getWeatherDetailsList().get(0).getIcon();
        int iconResId = mainActivity.getResources().getIdentifier(iconCode,
                "drawable",
                mainActivity.getPackageName());
        holder.weatherIcon.setImageResource(iconResId);

        holder.weatherDesc.setText(HelperClass.capitalFirstChar(hourlyForecast.
                getWeatherDetailsList().get(0).getDescription()));
    }

    @Override
    public int getItemCount() {
        return hourlyForecastsList.size();
    }
}
