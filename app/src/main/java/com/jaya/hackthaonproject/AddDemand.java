package com.jaya.hackthaonproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddDemand extends AppCompatActivity implements AdapterView.OnItemSelectedListener ,Communicator{

    Spinner type_spinner ;
    ArrayAdapter<String> type_adapter;

    EditText no_of_resources, completion_time;
    String type,no,time, deadline, priority;
    @NonNull
    Bundle bundle=new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_demand);
        String[] resource_type={"Cranes","Cement Mixture","Labour","JCB"};

        no_of_resources= (EditText)findViewById(R.id.no_of_resources);
        completion_time= (EditText)findViewById(R.id.completion_time);
        type_spinner= (Spinner)findViewById(R.id.typeSpinner);
        no=null;
        time= null;


        //for setting res type
        type_adapter = new ArrayAdapter<String>(AddDemand.this,android.R.layout.simple_spinner_dropdown_item,resource_type);
        type_spinner.setAdapter(type_adapter);
        type_spinner.setOnItemSelectedListener(AddDemand.this);


        //setting independent demand fragment

        IndependentDemand independentDemand= new IndependentDemand();
        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frag,independentDemand).commit();

    }


    public void addMore(View view) {
        DependentDemand dependentDemand = new DependentDemand();
        FragmentManager fragmentManager= getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frag, dependentDemand,"dep_frag");
        fragmentTransaction.commit();


    }

    public void submit(View view) {
        no= no_of_resources.getText().toString();
        time= completion_time.getText().toString();
        Toast.makeText(this,no+time+type,Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       type = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void response(String deadline, String priority) {
        this.deadline=deadline;
        this.priority=priority;
       ;
       // bundle.putString("deadline",deadline);
        bundle.putString("priority",priority);
        DependentDemand dependent = new DependentDemand();
        dependent.setArguments(bundle);

    }
}
