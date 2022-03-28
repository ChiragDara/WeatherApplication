package com.example.myapplication.helper;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.myapplication.R;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HelperClass {
    private static final String METRIC = "metric";
    public static final String MORNING = "8am";
    public static final String DAY  ="1pm";
    public static final String EVENING ="5pm";
    public static final String NIGHT = "11pm";


    // For Checking Network Connection is Available or Not
    public static Boolean isNetworkConnected(Activity activity){
        ConnectivityManager connectivityManager = activity.getSystemService(ConnectivityManager.class);
        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
        if(network != null && network.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }


    public static String currentLocationName(Activity activity, double latitude, double longitude){
        Geocoder geocoder = new Geocoder(activity);
        try{
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses == null){
                return null;
            }
            //String country = addresses.get(0).getCountryCode();
            String part1;
            String part2;
            if(addresses.get(0).getCountryCode().equalsIgnoreCase("US")){
                part1 = addresses.get(0).getLocality();
                part2 = addresses.get(0).getAdminArea();
            } else {
                part1 = addresses.get(0).getLocality();
                if(part1 == null){
                    part1 = addresses.get(0).getSubAdminArea();
                }
                part2 = addresses.get(0).getCountryName();
            }
            return  part1+", "+part2;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static double[] getLatLon(String userProvidedLocation, Activity activity) {
        Geocoder geocoder = new Geocoder(activity); // Here, “this” is an Activity
        double lat, lon;
        try {
            List<Address> address =
                    geocoder.getFromLocationName(userProvidedLocation, 1);
            if (address == null || address.isEmpty()) {
                // Nothing returned!
                return null;
            }
            lat = address.get(0).getLatitude();
            lon = address.get(0).getLongitude();

            return new double[]{lat, lon};
        } catch (IOException e) {
            Toast.makeText(activity, "Invalid city/state", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String capitalFirstChar(String text){
        String result = "";
        if (text == null || text.isEmpty()){
            return result;
        }

        return Arrays
                .stream(text.split(" "))
                .map(word -> word == null || word.isEmpty()
                        ? word
                        : word.substring(0, 1).toUpperCase() + word
                        .substring(1)
                        .toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public static String getDirection(Double degrees){
        if (degrees >= 337.5 || degrees < 22.5)
            return "N";
        if (degrees >= 22.5 && degrees < 67.5)
            return "NE";
        if (degrees >= 67.5 && degrees < 112.5)
            return "E";
        if (degrees >= 112.5 && degrees < 157.5)
            return "SE";
        if (degrees >= 157.5 && degrees < 202.5)
            return "S";
        if (degrees >= 202.5 && degrees < 247.5)
            return "SW";
        if (degrees >= 247.5 && degrees < 292.5)
            return "W";
        if (degrees >= 292.5 && degrees < 337.5)
            return "NW";
        return "X";
    }

    public static String formatRange(String unit, Double visibility) {
        return unit.equals(METRIC) ? String.format("%.1f km", visibility / 1000) :
                String.format("%.1f mi", visibility / 1609.34);
    }

    public static String formatUnit(String unit){
        return unit.equals(METRIC) ? "°C" : "°F";
    }


    public static void toggleUnit(MenuItem homeMenuToggleUnits, String unit) {
        if (unit.equals(METRIC)) {
            homeMenuToggleUnits.setIcon(R.drawable.units_c);
        } else {
            homeMenuToggleUnits.setIcon(R.drawable.units_f);
        }
    }

}
