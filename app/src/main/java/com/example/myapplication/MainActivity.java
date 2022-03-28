package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.dailyForecast.Daily;
import com.example.myapplication.helper.HelperClass;
import com.example.myapplication.hourly.HourlyAdapter;
import com.example.myapplication.hourly.HourlyForecast;
import com.example.myapplication.weather.CurrentTempData;
import com.example.myapplication.weather.WeatherAPI;
import com.example.myapplication.weather.WeatherData;
import com.example.myapplication.weeklyForecast.WeeklyActivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView currentLocation, dateAndTime, currentTemperature, currentFeelsLike, weatherDesc, winds, humidity, UVIndex, visibility, currentSunrise, currentSunset, snowing;
    private TextView morningTemperature, dayTimeTemperature,eveningTemperature,nightTemperature,morningTime, dayTime, eveningTime, nightTime;
    RecyclerView weatherRecycler;
    private SwipeRefreshLayout refreshScreen;
    private ImageView currentWeatherIcon;
    // Default Latitude and Altitude
    private Double latitude = 41.8675766;
    private Double longitude = -87.616232;
    private static final String WEATHERURL ="https://api.openweathermap.org/data/2.5/onecall";
    private static final String APIKEY = "50fadb0bcb8012117c99ca608bf9fb31";
    private WeatherData weatherData;
    private HourlyAdapter hourlyAdapter ;
    String locationName="";
    private RequestQueue queue;
    private static final String IMPERIAL = "imperial";
    private static final String METRIC = "metric";
    private String unit = IMPERIAL;
    private static final String MORNING = "8am";
    private static final String DAY = "1pm";
    private static final String EVENING = "5pm";
    private static final String NIGHT= "11pm";
    private MenuItem menuToggle;
    private List<HourlyForecast> hourlyForecastsList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Setting Title of the Application
        this.setTitle("OpenWeather App");
        getUserSetting();
        // Recycler View
        weatherRecycler = findViewById(R.id.weatherRecycler);
        currentLocation = findViewById(R.id.currentLocation);
        dateAndTime = findViewById(R.id.dateAndTime);
        currentTemperature = findViewById(R.id.currentTemperature);
        currentFeelsLike = findViewById(R.id.currentFeelsLike);
        weatherDesc = findViewById(R.id.weatherDesc);
        winds = findViewById(R.id.winds);
        humidity = findViewById(R.id.humidity);
        UVIndex = findViewById(R.id.UVIndex);
        visibility = findViewById(R.id.visibility);
        currentSunrise = findViewById(R.id.currentSunrise);
        currentSunset = findViewById(R.id.currentSunset);
        snowing = findViewById(R.id.snowing);
        morningTemperature = findViewById(R.id.morningTemperature);
        dayTimeTemperature = findViewById(R.id.dayTimeTemperature);
        eveningTemperature = findViewById(R.id.eveningTemperature);
        nightTemperature = findViewById(R.id.nightTemperature);
        dayTime = findViewById(R.id.dayTimeTime);
        morningTime = findViewById(R.id.morningTime);
        eveningTime = findViewById(R.id.eveningTime);
        nightTime = findViewById(R.id.nightTime);
        // Image View
        currentWeatherIcon = findViewById(R.id.currentWeatherIcon);
        // Swipe Refresh
        refreshScreen = findViewById(R.id.refreshScreen);
        refreshScreen.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromAPI();
                refreshScreen.setRefreshing(false);
            }
        });

        getDataFromAPI();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void getDataFromAPI() {
        if (HelperClass.isNetworkConnected(this)) {
            queue = Volley.newRequestQueue(getApplicationContext());
            Uri.Builder buildURL = Uri.parse(WEATHERURL).buildUpon();
            buildURL.appendQueryParameter("lat", String.format("%s", latitude));
            buildURL.appendQueryParameter("lon", String.format("%s", longitude));
            buildURL.appendQueryParameter("units", this.unit);
            buildURL.appendQueryParameter("appid", APIKEY);
            buildURL.appendQueryParameter("lang", "en");
            buildURL.appendQueryParameter("exclude", "minutely");
            String urlToUse = buildURL.build().toString();

            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(Request.Method.GET, urlToUse,
                            null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Toast.makeText(MainActivity.this, "" + response.toString(), Toast.LENGTH_LONG).show();
                            weatherData = WeatherAPI.parseJSON(response.toString());
                            setWeatherData(weatherData);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "" + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest);
        } else {
            currentLocation.setText("");
            dateAndTime.setText(String.format("%s", "No Network Connection"));
            currentTemperature.setText("");
            currentFeelsLike.setText("");
            weatherDesc.setText("");
            winds.setText("");
            humidity.setText("");
            UVIndex.setText("");
            snowing.setText("");
            visibility.setText("");
            morningTemperature.setText("");
            dayTimeTemperature.setText("");
            eveningTemperature.setText("");
            nightTemperature.setText("");
            dayTime.setText("");
            morningTime.setText("");
            eveningTime.setText("");
            nightTime.setText("");
            currentSunrise.setText("");
            currentSunset.setText("");
            currentWeatherIcon.setImageResource(0);
            hourlyForecastsList.clear();
            hourlyAdapter = new HourlyAdapter(hourlyForecastsList, this, weatherData, unit);
            weatherRecycler.setAdapter(hourlyAdapter);
            weatherRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }
    }

    // Setting Data
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setWeatherData(WeatherData weatherData) {
        if(weatherData == null){
            Toast.makeText(this, "", Toast.LENGTH_LONG).show();
            return;
        }
        CurrentTempData currentTempData = weatherData.getCurrentTempData();
        // Setting Location Name
        locationName = HelperClass.currentLocationName(this, weatherData.getLatitude(), weatherData.getLongitude());
        currentLocation.setText(locationName);

        // Setting location time and Date
        LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(currentTempData.getDate().getTime() + weatherData.getTimeZoneOffset(), 0 , ZoneOffset.UTC);
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("EEE MMM dd h:mm a, yyyy", Locale.getDefault());
        dateAndTime.setText(localDateTime.format(dateTimeFormat));

        // Setting Current Temperature
        currentTemperature.setText(String.format("%s%s", currentTempData.getTemp(), formatUnit(unit)));

        // Setting Feels Like
        currentFeelsLike.setText(String.format("Feels Like %s%s", currentTempData.getFeelLike(), formatUnit(unit)));

        // Weather Description
        weatherDesc.setText(String.format("%s (%s clouds)", HelperClass.capitalFirstChar(currentTempData.getWeatherDetailsList().get(0)
        .getDescription()), currentTempData.getCloud() + "%"));

        // Setting Winds
        String speed = formatSpeed(unit);
        winds.setText(String.format("Winds: %s at %s%s", HelperClass.
                        getDirection(currentTempData.getDeg()), currentTempData.getSpeed(),
                speed) + (currentTempData.getGust() != null ?
                String.format(" gusting to %s%s", currentTempData.getGust(), speed) : ""));

        // Setting Humidity
        humidity.setText(String.format("Humidity: %s%%", currentTempData.getHumidity()));

        // UVIndex
        UVIndex.setText(String.format("UV Index: %s", currentTempData.getUv()));

        // Setting Snow or Rain
        String snowAndRain = "";
        if(currentTempData.getRain() != null) {
            snowAndRain =  String.format("Last Hour Rain %s mm", currentTempData.getRain());
        }
        if(currentTempData.getSnow() != null) {
            snowAndRain =String.format("Last Hour Snow %s mm", currentTempData.getSnow());
        }
        snowing.setText(snowAndRain);

        // Setting Visibility - May get Error
        visibility.setText("Visibility: " +
                HelperClass.formatRange(unit,(double) currentTempData.getVisibility()));

        // ICon Code
        String iconCode = "_" + currentTempData.getWeatherDetailsList().get(0).getIcon();
        currentWeatherIcon.setImageResource(getResources().getIdentifier(iconCode, "drawable", getPackageName()));

        // Daily
        Daily daily = weatherData.getDailyForecastList().get(0);
        morningTemperature.setText(String.format("%s%s", daily.getTemperature().getMorning(), formatUnit(unit)));
        dayTimeTemperature.setText(String.format("%s%s", daily.getTemperature().getDay(),formatUnit(unit)));
        eveningTemperature.setText(String.format("%s%s", daily.getTemperature().getEvening(), formatUnit(unit)));
        nightTemperature.setText(String.format("%s%s", daily.getTemperature().getNight(), formatUnit(unit)));
        morningTime.setText(MORNING);
        dayTime.setText(DAY);
        eveningTime.setText(EVENING);
        nightTime.setText(NIGHT);

        // Hourly
        hourlyAdapter = new HourlyAdapter(weatherData.getHourlyList(), MainActivity.this, weatherData, unit);
        weatherRecycler.setAdapter(hourlyAdapter);
        weatherRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        setWeatherRecyclerData(weatherData.getHourlyList());

        // Sunrise and Sunset Data
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());

        LocalDateTime sunriseTime =
                LocalDateTime.ofEpochSecond(currentTempData.getSunriseTime().getTime() + weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);
        currentSunrise.setText(String.format("Sunrise: %s", sunriseTime.format(dateTimeFormatter)));
        // Sunset Time
        LocalDateTime sunsetTime =
                LocalDateTime.ofEpochSecond(currentTempData.getSunsetTime().getTime() + weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);

        currentSunset.setText(String.format("Sunset: %s", sunsetTime.format(dateTimeFormatter)));
    }


    private void setWeatherRecyclerData(List<HourlyForecast> hourlyForecasts){
        this.hourlyForecastsList.addAll(hourlyForecasts);
        this.hourlyAdapter.notifyDataSetChanged();
    }

    private static String formatUnit(String unit){
        return unit.equals(IMPERIAL) ? "°F" : "°C";
    }

    public static String formatSpeed(String unit) {
        return unit.equals(METRIC) ? " m/s" : " mph";
    }


    // Menu Functionality
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int menuID = item.getItemId();
        if(menuID == R.id.tempMenu){
            // F to C and C to F call
            this.unit = unit.equals(METRIC) ? IMPERIAL: METRIC;
            setUserSetting();
            HelperClass.toggleUnit(menuToggle, unit);
            getDataFromAPI();
            return true;
        }
        if(menuID == R.id.dailyForcast){
            dailyForecastActivity();
            return true;
        }
        if(menuID == R.id.changeLocation){
            locationChange();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Change Location Functionality
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void locationChange() {
        if(HelperClass.isNetworkConnected(this)) {
            LayoutInflater inflater = LayoutInflater.from(this);
            final View view = inflater.inflate(R.layout.location_change, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Enter location");
            builder.setMessage("For US location, enter as 'City' or 'City, State' \n\n" + "For international location, enter as 'City, Country' ");
            builder.setView(view);
            builder.setPositiveButton("OK", (dialog, id) -> {
                EditText alertDialogLocation = view.findViewById(R.id.changeLocation);
                double[] latitudeLongitude = HelperClass.getLatLon(alertDialogLocation.getText().toString(), this);
                if (latitudeLongitude != null) {
                    latitude = latitudeLongitude[0];
                    longitude = latitudeLongitude[1];
                    setUserSetting();
                    getDataFromAPI();
                } else {
                    Toast.makeText(this, "Invalid city/state", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("CANCEL", (dialog, id) -> {
                Toast.makeText(MainActivity.this, "Don't want to change the place?",
                        Toast.LENGTH_SHORT).show();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void dailyForecastActivity() {
        if(HelperClass.isNetworkConnected(this)) {
            Intent intent = new Intent(MainActivity.this, WeeklyActivity.class);
            intent.putExtra("weatherData", this.weatherData);
            intent.putExtra(String.valueOf(R.string.unit), unit);
            intent.putExtra(String.valueOf(R.string.locale), locationName);
            this.startActivity(intent);
        } else {
            Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuToggle = menu.findItem(R.id.tempMenu);
        HelperClass.toggleUnit(menuToggle, unit);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
        int pos = weatherRecycler.getChildLayoutPosition(view);
        HourlyForecast hourlyForecast = this.hourlyForecastsList.get(pos);

        LocalDateTime localDateTime =
                LocalDateTime.ofEpochSecond(hourlyForecast.getDate().getTime() +
                        weatherData.getTimeZoneOffset(), 0, ZoneOffset.UTC);

        Calendar cal = new GregorianCalendar();
        cal.setTime(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        Uri.Builder builder =
                CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");

        builder.appendPath(Long.toString(cal.getTime().getTime()));
        Intent intent = new Intent(Intent.ACTION_VIEW, builder.build());
        startActivity(intent);
    }

    public void setUserSetting() {
        SharedPreferences sharedPreferences = getSharedPreferences("WEATHER", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(String.valueOf(R.string.unit), unit);
        sharedPreferencesEditor.putString(String.valueOf(R.string.latitude), latitude.toString());
        sharedPreferencesEditor.putString(String.valueOf(R.string.longitude), longitude.toString());
        sharedPreferencesEditor.apply();
    }

    public void getUserSetting() {
        SharedPreferences sharedPreferences = getSharedPreferences("WEATHER", Context.MODE_PRIVATE);
        String unit = sharedPreferences.getString(String.valueOf(R.string.unit), getString(R.string.metric));
        String latitude = sharedPreferences.getString(String.valueOf(R.string.latitude), this.latitude.toString());
        this.latitude = Double.parseDouble(latitude);
        String longitude = sharedPreferences.getString(String.valueOf(R.string.longitude), this.longitude.toString());
        this.longitude = Double.parseDouble(longitude);
    }
}