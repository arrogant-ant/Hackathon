package com.jaya.hackthaonproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.*;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shubham on 3/25/2017.
 */

public class ShowDemandResAdapter extends ArrayAdapter {
     List l=new ArrayList();
    public ShowDemandResAdapter(Context context, int resource) {
        super(context, resource);
    }
    public void add(ShowDemandRes c)
    {
       super.add(c);
        l.add(c);
    }

    @Override
    public int getCount() {
        return l.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return l.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        Contacthoder ch;
        if(row==null)
        {
            LayoutInflater li=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           row= li.inflate(R.layout.show_demand_row,parent,false);
             ch=new Contacthoder();
            ch.t1=(TextView)row.findViewById(R.id.demand_id_user_demand);
            ch.t2=(TextView)row.findViewById(R.id.resource_type_user_demand);
            ch.t3=(TextView)row.findViewById(R.id.no_of_resources_user_demand);
            ch.t4=(TextView)row.findViewById(R.id.completion_time_user_demand);
            ch.t5=(TextView)row.findViewById(R.id.priority_user_demand);
            ch.t6=(TextView)row.findViewById(R.id.location_id_user_demand);
            ch.t7=(TextView)row.findViewById(R.id.date_of_demand_user_demand);
            row.setTag(ch);




        }
        else
        {
         ch=(Contacthoder)row.getTag();
        }
        ShowDemandRes cus=(ShowDemandRes)this.getItem(position);
        ch.t1.setText(cus.getDemand_id());
        ch.t2.setText(cus.getResource_type());
        ch.t3.setText(cus.getNo_of_resources());
        ch.t4.setText(cus.getCompletion_time());
        ch.t5.setText(cus.getPriority());
        ch.t6.setText(cus.getLocation_id());
        ch.t7.setText(cus.getDate_of_demand());
        return row;




    }
    static class Contacthoder
    {
        TextView  t1;
        TextView  t2;
        TextView  t3;
        TextView  t4;
        TextView  t5;
        TextView  t6;
        TextView  t7;


    }
}
