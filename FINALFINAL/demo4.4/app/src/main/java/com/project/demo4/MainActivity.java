package com.project.demo4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.demo4.Adapters.OnBoardingAdapter;

public class MainActivity extends AppCompatActivity {

    Button nextButton;
    Button skipButton;
    LinearLayout dotsLayout;
    ViewPager viewPager;

    TextView[] dots;
    int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextButton = findViewById(R.id.nextBtn);
        dotsLayout = findViewById(R.id.dotsLayout);
        viewPager = findViewById(R.id.slider);
        skipButton = findViewById(R.id.skipBtn);
        dotsFunction(0);


        OnBoardingAdapter adapter = new OnBoardingAdapter(this);
        viewPager.setAdapter(adapter);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LandingActivity.class);
                startActivity(i);
                finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPosition+1,true);
            }
        });

        viewPager.setOnPageChangeListener(onPageChangeListener);
    }

    private void dotsFunction(int pos){
        dots = new TextView[3];
        dotsLayout.removeAllViews();

        for(int i = 0; i < dots.length; i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("."));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i].setTextColor(getColor(R.color.dotsInactive)); //non-selection color (non-active)
            }
            dots[i].setTextSize(50);

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[pos].setTextColor(getColor(R.color.dotsActive)); //selection color (active)
            }
            dots[pos].setTextSize(70); //selection size
        }
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            dotsFunction(position);
            currentPosition = position;
            if(currentPosition <= 1) {
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(currentPosition + 1);
                    }
                });
            }else{
                nextButton.setText("Finish");
                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this, LandingActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };

}