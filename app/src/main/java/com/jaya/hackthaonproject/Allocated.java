package com.jaya.hackthaonproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class Allocated extends AppCompatActivity {
   /* String result;
    ListView listView;
    ContactAdapterallocated ca;*/
    @Override
       protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocated);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Allocated Resources");
      /*  ca = new ContactAdapterallocated(this, R.layout.layout_individual_allocated_demand);
        listView = (ListView) findViewById(R.id.list_view_track);
        listView.setAdapter(ca);
        TracksResources show = new Track.TracksResources(this);
        show.execute();*/

    }
}
