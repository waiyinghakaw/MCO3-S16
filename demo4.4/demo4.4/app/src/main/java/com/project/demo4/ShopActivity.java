package com.project.demo4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {

    CardView shopCardBtn, mapCardBtn, weatherCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopCardBtn = findViewById(R.id.s_cardShopBtn);
        mapCardBtn = findViewById(R.id.s_cardMapBtn);
        weatherCardButton = findViewById(R.id.s_cardWeatherBtn);

        Intent intent = getIntent();
        String PH_location = intent.getStringExtra("EXTRA_TEXT");
        TextView new_location = findViewById(R.id.shop_location);
        new_location.setText("XY" + PH_location.substring(7));
        String latitudeString = intent.getStringExtra("EXTRA_LAT");
        String longitudeString = intent.getStringExtra("EXTRA_LONG");

        shopCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(ShopActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

        mapCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ShopActivity.this, MapActivity2.class);
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
            }
        });

        weatherCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShopActivity.this, WeatherActivity.class);
                i.putExtra("EXTRA_TEXT", PH_location); //set current location to other activity
                i.putExtra("EXTRA_LAT", latitudeString); //set current location to other activity
                i.putExtra("EXTRA_LONG", longitudeString); //set current location to other activity
                startActivity(i);
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
}