package com.project.demo4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {

    CardView shopCardBtn, mapCardBtn, weatherCardButton;
    private ListView shopListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopCardBtn = findViewById(R.id.s_cardShopBtn);
        mapCardBtn = findViewById(R.id.s_cardMapBtn);
        weatherCardButton = findViewById(R.id.s_cardWeatherBtn);

        Intent intent = getIntent();
        String PH_location = intent.getStringExtra("EXTRA_TEXT");
        TextView newLocationTextView = findViewById(R.id.textView_shopLocation);
        if(PH_location != null && !PH_location.isEmpty()) {
            newLocationTextView.setText("Location: " + PH_location);
        }

        shopListView = findViewById(R.id.shop_list_view);
        String[] shops = new String[]{"Healthy Options", "Salad Stop", "Good Munch", "Starbucks", "Jollibee"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shops);
        shopListView.setAdapter(adapter);

        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Sample coordinates for shops in Manila
                double[][] coordinates = {
                        {14.4236, 121.0296}, // Coordinates for Healthy Options
                        {14.6042, 120.9822}, // Coordinates for Salad Stop
                        {14.5648, 120.9932},  // Coordinates for Good Munch
                        {14.5585560991, 120.989571042},  // Coordinates for Starbucks
                        {14.4254, 121.0274}  // Coordinates for Jollibee
                };

                double latitude = coordinates[position][0];
                double longitude = coordinates[position][1];

                Intent mapIntent = new Intent(ShopActivity.this, MapActivity2.class);
                mapIntent.putExtra("EXTRA_LATITUDE", latitude);
                mapIntent.putExtra("EXTRA_LONGITUDE", longitude);
                startActivity(mapIntent);
            }
        });

        shopCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Refresh the current activity or perform relevant action
                recreate();
            }
        });

        mapCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MapActivity or MapActivity2
                Intent mapIntent = new Intent(ShopActivity.this, MapActivity2.class);
                startActivity(mapIntent);
            }
        });

        weatherCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to WeatherActivity
                Intent weatherIntent = new Intent(ShopActivity.this, WeatherActivity.class);
                startActivity(weatherIntent);
            }
        });
    }

}
