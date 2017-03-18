package com.jaya.hackthaonproject;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
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
    @NonNull
    Bundle b= new Bundle();
    private final String priorityKey="priority";

    public DependentDemand() {
        // Required empty public constructor
    }




   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View v= inflater.inflate(R.layout.fragment_dependent_demand, container, false);
       b = getArguments();
       return v;
   }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        deadlineView = (TextView) getActivity().findViewById(R.id.deadlineText);
        priorityView = (TextView) getActivity().findViewById(R.id.priorityText);
        setText();
    }

    void setText()
    {

        b=getArguments();
        priority=b.getString(priorityKey);
       // deadline=b.getString(deadline);
        priorityView.setText(priority);
       // deadlineView.setText(deadline);

    }


}
