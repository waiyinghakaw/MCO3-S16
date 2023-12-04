package com.project.demo4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    Button signup_signUpBtn;
    Button signup_googleBtn;
    TextView signup_loginBtn;
    EditText username;
    EditText password;
    EditText full_name;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup_signUpBtn = findViewById(R.id.signup_signupBtn);
        signup_googleBtn = findViewById(R.id.signup_googleBtn);
        signup_loginBtn = findViewById(R.id.signup_loginBtn);
        username = findViewById(R.id.signup_email_input);
        password = findViewById(R.id.signup_pass_input);
        full_name = findViewById(R.id.signup_fullname_input);
        DB = new DBHelper(this);

        signup_signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // parse all data from input box to variable
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String fname = full_name.getText().toString();

//                check if fields are not null
                if(user.equals("")||pass.equals("")||fname.equals("")){
                    Toast.makeText(SignUpActivity.this, "Please enter information on all fields!", Toast.LENGTH_SHORT).show();
                }else{
//                    check if username exist
                    boolean checker = DB.checkUsername(user);
                    if(!checker){
//                        if username doesn't exist insert data to DB
                        boolean insert = DB.insertData(user,pass,fname);
                        if(insert){
                            Toast.makeText(SignUpActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }else {
                            Toast.makeText(SignUpActivity.this, "Registration Failed! Please contact Administrator.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        //user already exist
                        Toast.makeText(SignUpActivity.this, "User already exist! Please login.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signup_googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event
                Toast.makeText(SignUpActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        signup_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event

                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}