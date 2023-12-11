package com.project.demo4.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.project.demo4.R;

public class OnBoardingAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public OnBoardingAdapter(Context context){
        this.context = context;
    }

    int subtitles[] = {
            R.string.desc1,
            R.string.desc2,
            R.string.desc3
    };

    int images[] = {
            R.drawable.slider_1,
            R.drawable.slider_2,
            R.drawable.slider_3
    };


    @Override
    public int getCount() {
        return subtitles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.slide, container, false);

        ImageView image = v.findViewById(R.id.slideImg);
        TextView subtitle = v.findViewById(R.id.sliderSubtitle);

        image.setImageResource(images[position]);
        subtitle.setText(subtitles[position]);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
