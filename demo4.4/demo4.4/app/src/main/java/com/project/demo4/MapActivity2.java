package com.project.demo4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    CardView shopCardBtn, mapCardBtn, weatherCardButton;
    String PH_location;

    private GoogleMap myMap;
    private final int FINE_PERMISSION_CODE = 1;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    String latitudeString, longitudeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        shopCardBtn = findViewById(R.id.m_cardShopBtn);
        mapCardBtn = findViewById(R.id.m_cardMapBtn);
        weatherCardButton = findViewById(R.id.m_cardWeatherBtn);


        shopCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MapActivity2.this, ShopActivity.class);
                i.putExtra("EXTRA_TEXT", PH_location); //set current location to other activity
                i.putExtra("EXTRA_LAT", latitudeString); //set current location to other activity
                i.putExtra("EXTRA_LONG", longitudeString); //set current location to other activity
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();

            }
        });

        mapCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MapActivity2.this, "Refreshed", Toast.LENGTH_SHORT).show();

            }
        });

        weatherCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MapActivity2.this, WeatherActivity.class);
                i.putExtra("EXTRA_TEXT", PH_location); //set current location to other activity
                i.putExtra("EXTRA_LAT", latitudeString); //set current location to other activity
                i.putExtra("EXTRA_LONG", longitudeString); //set current location to other activity
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        //MAP CODES STARTS HERE
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();


    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
                    mapFragment.getMapAsync(MapActivity2.this);
                }
            }
        });
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myMap = googleMap;

        LatLng PH = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        PH_location = String.valueOf(PH);
        latitudeString = String.valueOf(PH.latitude);
        longitudeString = String.valueOf(PH.longitude);
        myMap.addMarker(new MarkerOptions().position(PH).title("Current Location"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PH,16f));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == FINE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this,"Location permission is denied! Please allow permission.",Toast.LENGTH_SHORT).show();
            }
        }
    }
}