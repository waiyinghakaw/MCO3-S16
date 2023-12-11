package com.project.demo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;


public class Location_Config_Activity extends AppCompatActivity {

    Button enable_locBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_config);

        enable_locBtn = findViewById(R.id.home_enable_location);

        enable_locBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event
                showLocationSettings();
                if (!isLocationEnabled()) {
                    // Prompt the user to enable GPS
                    showLocationSettings();
                } else {
                    // GPS is already enabled, you can proceed with your logic
                    Intent i = new Intent(Location_Config_Activity.this, HomeActivity2.class);
                    startActivity(i);
                    finish();
                }

            }
        });

    }

    private void showLocationSettings() {
        // Open the device location settings
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}