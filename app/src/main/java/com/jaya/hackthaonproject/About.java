package com.jaya.hackthaonproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.jaya.hackthaonproject.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class About extends AppCompatActivity {


    CarouselView carouselView_about;

    int[] sampleImages = {R.drawable.sud, R.drawable.shubham, R.drawable.jaya,R.drawable.pinky,R.drawable.pragya,R.drawable.shiv};

TextView t1,t2,t3,t4,t5,t6,th;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        carouselView_about = (CarouselView) findViewById(R.id.carouselView_about);
        carouselView_about.setPageCount(sampleImages.length);

        carouselView_about.setImageListener(imageListener);
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageResource(R.drawable.bookopen);
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this).setContentView(itemIcon).setBackgroundDrawable(R.drawable.selector_button_pink).build();
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_blue));
        ImageView itemIco = new ImageView(this);
        itemIco.setImageResource(R.drawable.bookopen);
        SubActionButton button1 = itemBuilder.setContentView(itemIco).build();
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageResource(R.drawable.email);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon1).build();
        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.drawable.phone);
        SubActionButton button3 = itemBuilder.setContentView(itemIcon2).build();
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this).addSubActionView(button1)
                .addSubActionView(button2).addSubActionView(button3).attachTo(actionButton)
                .build();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(About.this,Call.class);
                startActivity(i);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("mailto:"));
                String to="shubhamjpc@gmail.com";
                i.putExtra(Intent.EXTRA_EMAIL,to);
                i.putExtra(Intent.EXTRA_SUBJECT,"Wangle");
                i.setType("message/rfc822");
                Intent.createChooser(i,"send Email");
                startActivity(i);

            }
        });
        th=(TextView)findViewById(R.id.th);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);
        t5=(TextView)findViewById(R.id.t5);
        t6=(TextView)findViewById(R.id.t6);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/KaushanScript-Regular.otf");
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Capture_it.ttf");
        t1.setTypeface(tf2);
        t2.setTypeface(tf2);
        t3.setTypeface(tf2);
        t4.setTypeface(tf2);
        t5.setTypeface(tf2);
        t6.setTypeface(tf2);
        th.setTypeface(tf);


    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

}

