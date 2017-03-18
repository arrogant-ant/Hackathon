package com.jaya.hackthaonproject;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DependentDemand extends Fragment{

    TextView priorityView;
    TextView deadlineView;
    String priority, deadline;

    private final String priorityKey="priority";

    public DependentDemand() {
        // Required empty public constructor
    }




   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v= inflater.inflate(R.layout.fragment_dependent_demand, container, false);
       deadlineView = (TextView) v.findViewById(R.id.deadlineText);
       priorityView = (TextView) v.findViewById(R.id.priorityText);
       setText("a","c");
       return v;
   }

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        deadlineView = (TextView) getActivity().findViewById(R.id.deadlineText);
        priorityView = (TextView) getActivity().findViewById(R.id.priorityText);

    }*/

    void setText(String deadline, String priority)
    {


        priorityView.setText("5");
        deadlineView.setText("6");

    }


}
