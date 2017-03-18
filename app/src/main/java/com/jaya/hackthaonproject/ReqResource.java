package com.jaya.hackthaonproject;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReqResource extends Fragment {

    TextView typeView, noView,timeView;

    public ReqResource() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_req_resource, container, false);
        typeView= (TextView) v.findViewById(R.id.type);
        noView= (TextView) v.findViewById(R.id.no);
        timeView= (TextView) v.findViewById(R.id.time);
        return v;
    }
    public void setText(String type,String no, String time){
        typeView.setText(type);
        noView.setText(no);
        typeView.setText(time);
        Toast.makeText(getActivity(),"data"+type+no+time,Toast.LENGTH_SHORT).show();
    }

}
