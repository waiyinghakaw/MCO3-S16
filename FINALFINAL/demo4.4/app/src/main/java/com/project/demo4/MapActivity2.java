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
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.List;

public class MapActivity2 extends AppCompatActivity implements OnMapReadyCallback {

    private CardView shopCardBtn, mapCardBtn, weatherCardButton;
    private String PH_location;
    private GoogleMap myMap;
    private final int FINE_PERMISSION_CODE = 1;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private String latitudeString, longitudeString;
    private List<Shop> shopsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);

        initializeViews();
        initializeShopsList();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void initializeViews() {
        shopCardBtn = findViewById(R.id.m_cardShopBtn);
        mapCardBtn = findViewById(R.id.m_cardMapBtn);
        weatherCardButton = findViewById(R.id.m_cardWeatherBtn);
        EditText searchInput = findViewById(R.id.home_search);

        shopCardBtn.setOnClickListener(v -> navigateToShopActivity());
        mapCardBtn.setOnClickListener(v -> refreshMap());
        weatherCardButton.setOnClickListener(v -> navigateToWeatherActivity());
        searchInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == KeyEvent.KEYCODE_ENDCALL) {
                performSearch(searchInput.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void initializeShopsList() {
        shopsList = new ArrayList<>();
        shopsList.add(new Shop("Healthy Options", 14.4236, 121.0296));
        shopsList.add(new Shop("Salad Stop", 14.6042, 120.9822));
        shopsList.add(new Shop("Good Munch", 14.5648, 120.9932));
        shopsList.add(new Shop("Starbucks", 14.5585560991, 120.989571042));
        shopsList.add(new Shop("Jollibee", 14.4254, 121.0274));
        // Add more shops as needed
    }

    private void performSearch(String query) {
        myMap.clear();
        for (Shop shop : shopsList) {
            if (shop.getName().toLowerCase().contains(query.toLowerCase())) {
                LatLng shopLocation = new LatLng(shop.getLatitude(), shop.getLongitude());
                myMap.addMarker(new MarkerOptions().position(shopLocation).title(shop.getName()));
                myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(shopLocation, 16));
            }
        }
    }

    private void navigateToShopActivity() {
        Intent i = new Intent(MapActivity2.this, ShopActivity.class);
        i.putExtra("EXTRA_TEXT", PH_location);
        i.putExtra("EXTRA_LAT", latitudeString);
        i.putExtra("EXTRA_LONG", longitudeString);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }

    private void refreshMap() {
        Toast.makeText(MapActivity2.this, "Refreshed", Toast.LENGTH_SHORT).show();
    }

    private void navigateToWeatherActivity() {
        Intent i = new Intent(MapActivity2.this, WeatherActivity.class);
        i.putExtra("EXTRA_TEXT", PH_location);
        i.putExtra("EXTRA_LAT", latitudeString);
        i.putExtra("EXTRA_LONG", longitudeString);
        startActivity(i);
        overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        myMap = googleMap;
        handleIntentExtras();
    }

    private void handleIntentExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("EXTRA_LATITUDE") && extras.containsKey("EXTRA_LONGITUDE")) {
            double latitude = extras.getDouble("EXTRA_LATITUDE");
            double longitude = extras.getDouble("EXTRA_LONGITUDE");
            updateMapWithLocation(latitude, longitude);
        } else {
            getLastLocation();
        }
    }

    private void updateMapWithLocation(double latitude, double longitude) {
        LatLng location = new LatLng(latitude, longitude);
        myMap.addMarker(new MarkerOptions().position(location).title("Shop Location"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16f));
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(this::onLocationSuccess);
    }

    private void onLocationSuccess(Location location) {
        if (location != null) {
            currentLocation = location;
            updateCurrentLocation();
        }
    }

    private void updateCurrentLocation() {
        LatLng PH = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        PH_location = String.valueOf(PH);
        latitudeString = String.valueOf(PH.latitude);
        longitudeString = String.valueOf(PH.longitude);
        myMap.addMarker(new MarkerOptions().position(PH).title("Current Location"));
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PH, 16f));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission is denied! Please allow permission.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
