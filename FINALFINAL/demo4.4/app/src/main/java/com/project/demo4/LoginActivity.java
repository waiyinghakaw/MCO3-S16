package com.project.demo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
Button loginBtn;
Button googleBtn;
TextView forgetPasswordBtn;
TextView signupBtn;
EditText username;
EditText password;

DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.login_loginBtn);
        googleBtn = findViewById(R.id.login_googleBtn);
        forgetPasswordBtn = findViewById(R.id.login_forget_passBtn);
        signupBtn = findViewById(R.id.login_signBtn);
        username = (EditText) findViewById(R.id.login_email_input);
        password = (EditText) findViewById(R.id.login_pass_input);

        DB = new DBHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event
                String user = username.getText().toString();
                String pass = password.getText().toString();

                //if user is admin
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                    Toast.makeText(LoginActivity.this, "Welcome Admin!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MapActivity2.class);
                    startActivity(i);
                    finish();

                } else { //if user is not admin
                    if(user.equals("")||pass.equals("")){
                        Toast.makeText(LoginActivity.this, "Please enter information on all fields!", Toast.LENGTH_SHORT).show();
                    }else{
                        boolean checker = DB.checkUsernamePassword(user, pass);
                        if(checker){ //user exist
                            Toast.makeText(LoginActivity.this, "Log in Successful!", Toast.LENGTH_SHORT).show();

                            //check if location is enabled
                            if (!isLocationEnabled()) {
                                // Prompt the user to enable GPS
                                Intent i = new Intent(LoginActivity.this, Location_Config_Activity.class);
                                startActivity(i);
                                finish();

                                //showLocationSettings();
                            } else {
                                // GPS is already enabled, you can proceed with your logic
                                Intent i = new Intent(LoginActivity.this, MapActivity2.class);
                                startActivity(i);
                                finish();
                            }

                        }else{ //user not existing
                            Toast.makeText(LoginActivity.this, "Email Address or Password is incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event
                Toast.makeText(LoginActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        forgetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event
                Toast.makeText(LoginActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}