package com.project.demo4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {

    CardView shopCardBtn, mapCardBtn, weatherCardButton;
    TextView new_location;
    String cityName;
    String tmpTemp = "";
    double temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        shopCardBtn = findViewById(R.id.w_cardShopBtn);
        mapCardBtn = findViewById(R.id.w_cardMapBtn);
        weatherCardButton = findViewById(R.id.w_cardWeatherBtn);

        Intent intent = getIntent();
        //set location text
        String PH_location = intent.getStringExtra("EXTRA_TEXT");
        TextView new_location = findViewById(R.id.weather_location);
        new_location.setText("XY" + PH_location.substring(7));

        //get longitude & latitude
        String new_x = intent.getStringExtra("EXTRA_LAT");
        String new_y = intent.getStringExtra("EXTRA_LONG");
        getCityNameFromLatLng(new_x, new_y);
        TextView new_city = findViewById(R.id.weather_city);
        new_city.setText(cityName);






        shopCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(WeatherActivity.this, ShopActivity.class);
                i.putExtra("EXTRA_TEXT", PH_location); //set current location to other activity
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        mapCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(WeatherActivity.this, MapActivity2.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        weatherCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(WeatherActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        //Weather API

        TextView tempTxt = findViewById(R.id.temperature);
        TextView result = findViewById(R.id.result);

        final String url = "http://api.openweathermap.org/data/2.5/weather";
        final String API_KEY = "413dfed6e2103396a5355d428e999e3d";
        DecimalFormat df = new DecimalFormat("#.#");
        String tempCity = cityName.replace(" ","");
        String tempURL = "http://api.openweathermap.org/data/2.5/weather?q="+ tempCity +"&appid=" + API_KEY;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.d("response",response);
                String output = "";

                try{
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String desc = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    temp = jsonObjectMain.getDouble("temp") - 273.15;
                    double feels_like = jsonObjectMain.getDouble("feels_like") - 273.15;
                    int humidity = jsonObjectMain.getInt("humidity");
                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                    String cloud = jsonObjectClouds.getString("all");

                    tempTxt.setText(df.format(temp) + " °C");
                    output += "\nFeels like: " + df.format(feels_like) + " °C"
                            + "\nHumidity: " + humidity + "%"
                            + "\nCloud: " + cloud + "%"
                            + "\nDescription: " + desc;

                    result.setText(output);




                }catch (JSONException e){
                    e.printStackTrace();
                    Toast.makeText(WeatherActivity.this, "JSON Error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "ERR:" + error.toString().trim(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);



    }

    private void getCityNameFromLatLng(String newX, String newY) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            // Get a list of Address objects from LatLng coordinates
            List<Address> addresses = geocoder.getFromLocation(Double.parseDouble(newX), Double.parseDouble(newY), 1);

            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);

                // Get the city name
                cityName = address.getLocality();

                // Do something with the city name (e.g., display or use in your app)
                //showToast("City name for LatLng (" + newX + ", " + newY + "): " + cityName);
            } else {
                showToast("No city name found for LatLng (" + newX + ", " + newY + ")");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}