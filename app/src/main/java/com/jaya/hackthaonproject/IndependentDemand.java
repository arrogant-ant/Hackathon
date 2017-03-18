package com.jaya.hackthaonproject;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class IndependentDemand extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner priority_spinner;
    ArrayAdapter<String> priority_adapter;
    EditText deadline_view;
    String priority, deadline;
    Communicator comm;
    String[] priority_arr = {"1", "2", "3", "4", "5"};
    public IndependentDemand() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_independent_demand, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        priority_spinner= (Spinner)getActivity().findViewById(R.id.prioritySpinner);
        deadline_view= (EditText) getActivity().findViewById(R.id.deadline);
        //for passing data
        comm= (Communicator) getActivity();

        //for setting priority_arr
        priority_adapter =new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item, priority_arr);
        priority_spinner.setAdapter(priority_adapter);
        priority_spinner.setOnItemSelectedListener(IndependentDemand.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(),"yo baby",Toast.LENGTH_SHORT).show();
        deadline= deadline_view.getText().toString();
        priority= parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        comm.response(deadline,priority);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
