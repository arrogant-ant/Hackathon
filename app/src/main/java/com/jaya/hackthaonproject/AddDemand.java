package com.jaya.hackthaonproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddDemand extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner type_spinner ,priority_spinner;
    ArrayAdapter<String> type_adapter;
    ArrayAdapter<Integer> priority_adapter;
    EditText no_of_resources, completion_time;
    String no, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_demand);
        String[] resource_type={"Cranes","Cement Mixture","Labour","JCB"};
        Integer priority[]={1,2,3,4,5};
        type_spinner= (Spinner)findViewById(R.id.typeSpinner);
        priority_spinner= (Spinner)findViewById(R.id.prioritySpinner);
        no_of_resources= (EditText)findViewById(R.id.no_of_resources);
        completion_time= (EditText)findViewById(R.id.completion_time);
        no=null;
        time= null;

        //for setting res type
        type_adapter = new ArrayAdapter<String>(AddDemand.this,android.R.layout.simple_spinner_dropdown_item,resource_type);
        type_spinner.setAdapter(type_adapter);
        type_spinner.setOnItemSelectedListener(AddDemand.this);

        //for setting priority
        priority_adapter = new ArrayAdapter<Integer>(AddDemand.this,android.R.layout.simple_spinner_dropdown_item,priority);
        priority_spinner.setAdapter(priority_adapter);
        priority_spinner.setOnItemSelectedListener(AddDemand.this);
    }


    public void addMore(View view) {


    }

    public void submit(View view) {
        no= no_of_resources.getText().toString();
        time= completion_time.getText().toString();
        Toast.makeText(this,no+time,Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent==type_spinner)
            Toast.makeText(this,"type",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"else part",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
