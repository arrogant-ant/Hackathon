package com.jaya.hackthaonproject;


import android.app.ListFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReqResource extends ListFragment {


   // ListView listView;
    ResourceAdapter ra;

    public ReqResource() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_req_resource, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ra=new ResourceAdapter(getActivity(),R.layout.req_res_row);
        //listView= getListView();
        setListAdapter(ra);
    }

    public void setText(String type, String no, String time){
       /* typeView.setText(type);
        noView.setText(no);
        typeView.setText(time);*/
        Resource resource= new Resource(type,no,time);
        ra.add(resource);
        Toast.makeText(getActivity(),"data"+type+no+time,Toast.LENGTH_SHORT).show();
    }

}
