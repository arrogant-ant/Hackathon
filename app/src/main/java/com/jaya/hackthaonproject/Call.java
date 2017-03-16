package com.jaya.hackthaonproject;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Call extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        TextView tv=(TextView)findViewById(R.id.call);
        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/KaushanScript-Regular.otf");
        tv.setTypeface(tf);
        TextView tv2=(TextView)findViewById(R.id.call_);
        Typeface tf2=Typeface.createFromAsset(getAssets(),"fonts/Capture_it_2.ttf");
        tv2.setTypeface(tf2);
        TextView tv3=(TextView)findViewById(R.id.call_1);
        Typeface tf3=Typeface.createFromAsset(getAssets(),"fonts/Capture_it.ttf");
        tv3.setTypeface(tf3);
    }
}
