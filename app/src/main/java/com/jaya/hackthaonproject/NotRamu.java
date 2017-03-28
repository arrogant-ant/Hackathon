package com.jaya.hackthaonproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotRamu extends AppCompatActivity {
TextView t1,t2;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_ramu);
        String res_no=getIntent().getExtras().getString("resource_no");
        String res_type=getIntent().getExtras().getString("resource_type");
        t1=(TextView)findViewById(R.id.textViews1);
        t2=(TextView)findViewById(R.id.textViews2);
        t1.setText(res_no);
        t2.setText(res_type);
      /*  b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowResource sr=new ShowResource();
                sr.execute();
            }
        });*/

    }
}
