package com.example.eun_a.daum_map;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static com.example.eun_a.daum_map.R.id.tv1;

public class MainActivity extends AppCompatActivity {

    public static String hospital=null;
    public String name = "Seoul Approve Medical";

    boolean isNetworkEnabled = false;

    LocationManager locationManager;

    private GPSInfo gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.eun_a.daum_map.R.layout.activity_main);

        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        gps = new GPSInfo(this);

        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isNetworkEnabled){
            gps.showNetworkSettingsAlert();
        }

        TextView textView = (TextView) findViewById(tv1);

        textView.setTypeface(Typeface.createFromAsset(getAssets(), "JUA.ttf"));

        textView.setText(name);
    }

    public void onButton1Clicked(View v) {
        hospital = "Dermatology";
        Intent intent = new Intent(getApplicationContext(),Map.class);
        startActivity(intent);

    }

    public void onButton2Clicked(View v) {
        hospital = "Dental";
        Intent intent = new Intent(getApplicationContext(), Map.class);
        startActivity(intent);

    }

    public void onButton3Clicked(View v) {
        hospital = "Urology";
        Intent intent = new Intent(getApplicationContext(), Map.class);
        startActivity(intent);

    }

    public void onButton4Clicked(View v) {
        hospital = "Eye";
        Intent intent = new Intent(getApplicationContext(), Map.class);
        startActivity(intent);

    }

    public void onButton5Clicked(View v) {
        hospital = "Plastic Surgery";
        Intent intent = new Intent(getApplicationContext(), Map.class);
        startActivity(intent);

    }

    public void onButton6Clicked(View v) {
        hospital = "Hospital";
        Intent intent = new Intent(getApplicationContext(), Map.class);
        startActivity(intent);

    }

    public void onButton7Clicked(View v) {
        hospital = "Womans";
        Intent intent = new Intent(getApplicationContext(), Map.class);
        startActivity(intent);

    }

    public void onButton8Clicked(View v) {
        hospital = "Oriental Medicine";
        Intent intent = new Intent(getApplicationContext(),Map.class);
        startActivity(intent);

    }

    public void onButton9Clicked(View v) {
        hospital = "Radiology";
        Intent intent = new Intent(getApplicationContext(),Map.class);
        startActivity(intent);

    }

    public void onButton10Clicked(View v) {
        hospital = "Clinic";
        Intent intent = new Intent(getApplicationContext(), Map.class);
        startActivity(intent);

    }

    public String getInfo(){
        return hospital;
    }
}
