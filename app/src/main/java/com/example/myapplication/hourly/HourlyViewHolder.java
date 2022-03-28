package com.example.myapplication.hourly;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class HourlyViewHolder extends RecyclerView.ViewHolder {

    TextView day;
    TextView time;
    ImageView weatherIcon;
    TextView temperature;
    TextView weatherDesc;

    public HourlyViewHolder(@NonNull View itemView, TextView day, TextView time, ImageView weatherIcon, TextView temperature, TextView weatherDesc) {
        super(itemView);
        this.day = day;
        this.time = time;
        this.weatherDesc = weatherDesc;
        this.weatherIcon = weatherIcon;
        this.temperature = temperature;
    }

    public HourlyViewHolder(View view) {
        super(view);
        day = view.findViewById(R.id.today);
        time = view.findViewById(R.id.timeDisplay);
        weatherIcon = view.findViewById(R.id.weatherImage);
        temperature = view.findViewById(R.id.tempDegree);
        weatherDesc = view.findViewById(R.id.tempDescr);
    }

}
