package com.example.myapplication.weeklyForecast;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dailyForecast.Daily;
import com.example.myapplication.helper.HelperClass;
import com.example.myapplication.weather.WeatherData;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeeklyForecastAdapter extends RecyclerView.Adapter<WeeklyForecastViewHolder> {

    private final List<Daily> dailyList;
    private WeeklyActivity weeklyActivity;
    private String unit = "";
    private WeatherData weatherData;

    public WeeklyForecastAdapter(List<Daily> dailyList, WeeklyActivity weeklyActivity, String unit, WeatherData weatherData) {
        this.dailyList = dailyList;
        this.weeklyActivity = weeklyActivity;
        this.unit = unit;
        this.weatherData = weatherData;
    }

    @NonNull
    @Override
    public WeeklyForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedLayout = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_weekly, parent, false);
        return new WeeklyForecastViewHolder(inflatedLayout);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull WeeklyForecastViewHolder holder, int position) {

        Daily daily = dailyList.get(position);

        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(daily.getDt().getTime() +
                        weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MM/dd", Locale.getDefault());

        holder.weeklyForecastTime.setText(dateTimeFormatter.format(localDateTime));


        String dailyForecastTempRange = String.format("%s / %s", daily.getTemperature().getMaximum() +
                        HelperClass.formatUnit(unit), daily.getTemperature().getMinimum() +
                        HelperClass.formatUnit(unit));
        holder.weeklyForecastTemp.setText(dailyForecastTempRange);

        holder.weeklyForecastWeatherDesc.setText(HelperClass.capitalFirstChar(daily.getWeatherDetailsList().get(0).
                getDescription()));

        String dailyForecastPrecipitation = String.format("(%s%% precip.)",
                daily.getPop());
        holder.weeklyForecastPrecipitation.setText(dailyForecastPrecipitation);

        String dailyForecastUvi = String.format("UV Index: %s", daily.getUvi());
        holder.weeklyForecastUvi.setText(dailyForecastUvi);

        String iconCode = "_" + daily.getWeatherDetailsList().get(0).getIcon();
        holder.weeklyForecastWeatherIcon.setImageResource(weeklyActivity.getResources().
                getIdentifier(iconCode, "drawable", weeklyActivity.getPackageName()));

        String dailyForecastMorningTemperature = String.format("%s%s",
                daily.getTemperature().getMorning(), HelperClass.formatUnit(unit));
        holder.weeklyForecastMorningTemperature.setText(dailyForecastMorningTemperature);

        String dailyForecastDayTemperature = String.format("%s%s",
                daily.getTemperature().getDay(), HelperClass.formatUnit(unit));
        holder.weeklyForecastDayTemperature.setText(dailyForecastDayTemperature);

        String dailyForecastEveningTemperature = String.format("%s%s",
                daily.getTemperature().getEvening(), HelperClass.formatUnit(unit));
        holder.weeklyForecastEveningTemperature.setText(dailyForecastEveningTemperature);

        String dailyForecastNightTemperature = String.format("%s%s",
                daily.getTemperature().getNight(), HelperClass.formatUnit(unit));
        holder.weeklyForecastNightTemperature.setText(dailyForecastNightTemperature);

        holder.weeklyForecastMorningTime.setText(HelperClass.MORNING);
        holder.weeklyForecastDayTime.setText(HelperClass.DAY);
        holder.weeklyForecastEveningTime.setText(HelperClass.EVENING);
        holder.weeklyForecastNightTime.setText(HelperClass.NIGHT);


    }

    @Override
    public int getItemCount() {
        return dailyList.size();
    }
}
