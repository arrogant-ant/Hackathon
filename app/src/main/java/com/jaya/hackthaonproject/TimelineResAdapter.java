package com.jaya.hackthaonproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TimelineResAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public TimelineResAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(TimelineRes object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        ContactHolders ch;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.timeline_row,parent,false);
            ch=new ContactHolders();
            ch.Demand_id=(TextView)row.findViewById(R.id.demand_id_timeline);
            ch.Resource_type=(TextView)row.findViewById(R.id.resource_type_timeline);
            ch.No_of_resources=(TextView)row.findViewById(R.id.no_of_resources_timeline);
            ch.Status=(TextView)row.findViewById(R.id.status_timeline);
            ch.Modified_by=(TextView)row.findViewById(R.id.modified_by_timeline);
            ch.Modified_on=(TextView)row.findViewById(R.id.modified_on_timeline);

            row.setTag(ch);
        }
        else
        {
            ch=(ContactHolders)row.getTag();

        }
        TimelineRes contacts=(TimelineRes) this.getItem(position);
        ch.Demand_id.setText(contacts.getDemand_id());
        ch.Resource_type.setText(contacts.getResource_type());
        ch.No_of_resources.setText(contacts.getNo_of_resources());
        ch.Status.setText(contacts.getStatus());
        ch.Modified_by.setText(contacts.getModified_by());
        ch.Modified_on.setText(contacts.getModified_on());

        return row;
    }
    static class ContactHolders
    {
        TextView Demand_id;
        TextView Resource_type;
        TextView No_of_resources;
        TextView Status;
        TextView Modified_by;
        TextView Modified_on;


    }
}

