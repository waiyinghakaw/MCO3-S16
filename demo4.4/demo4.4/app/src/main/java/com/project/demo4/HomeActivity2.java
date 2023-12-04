package com.project.demo4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.project.demo4.databinding.ActivityHome2Binding;

public class HomeActivity2 extends AppCompatActivity {


    ActivityHome2Binding binding;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.shop){
                replaceFragment(new ShopFragment());
            } else if (item.getItemId() == R.id.map) {
                replaceFragment(new MapFragment());
            }else{
                replaceFragment(new WeatherFragment());
            }

            return true;
        });


        binding.homeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the text when the EditText is clicked
                Toast.makeText(HomeActivity2.this, "homesearch", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void replaceFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.homeContainer, fragment);
        fragmentTransaction.commit();
    }
}