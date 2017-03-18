package com.jaya.hackthaonproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.jaya.hackthaonproject.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class About extends AppCompatActivity {


    CarouselView carouselView_about;

    int[] sampleImages = {R.drawable.pragya, R.drawable.shubham, R.drawable.jaya, R.drawable.shiv,R.drawable.pragya};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        carouselView_about = (CarouselView) findViewById(R.id.carouselView_about);
        carouselView_about.setPageCount(sampleImages.length);

        carouselView_about.setImageListener(imageListener);
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };



    public void add(View view) {
        Intent i=new Intent(this,NewDemand.class);
        startActivity(i);
    }
}

