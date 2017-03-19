package com.jaya.hackthaonproject;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.jaya.hackthaonproject.AllocatedTable;
import com.jaya.hackthaonproject.R;

public class Resources extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        Button b=(Button)findViewById(R.id.button);
        b.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.app.FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                AllocatedTable at=new AllocatedTable();
                ft.replace(R.id.frame,at);
                ft.commit();
            }
        });





    }}
