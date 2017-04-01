package com.jaya.hackthaonproject;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewDemandFrag extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Button btn;
    Spinner type_spinner ;
    ArrayAdapter<String> type_adapter;
    String[] resource_type={"cranes","cement mixture","labour","JCB","truck","engineer"};
    EditText no_of_resources, completion_time;
    String type,no,time;
    Communicator comm;
    public NewDemandFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_new_demand, container, false);
        no_of_resources= (EditText)v.findViewById(R.id.no_of_resources);
        completion_time= (EditText)v.findViewById(R.id.completion_time);
        btn= (Button) v.findViewById(R.id.more);
        type_spinner= (Spinner)v.findViewById(R.id.typeSpinner);
        type_adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,resource_type);
        type_spinner.setAdapter(type_adapter);
        type_spinner.setOnItemSelectedListener(NewDemandFrag.this);
        btn.setOnClickListener(NewDemandFrag.this);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm= (Communicator) getActivity();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        no = no_of_resources.getText().toString();
        time = completion_time.getText().toString();
        comm.response(type,no,time);
        //Toast.makeText(getActivity(),"data"+type+no+time,Toast.LENGTH_SHORT).ShowSM();

    }
}
