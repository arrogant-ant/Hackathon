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


public class TrackResAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public TrackResAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(TrackRes object) {
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
            row=layoutInflater.inflate(R.layout.track_row,parent,false);
            ch=new ContactHolders();
            ch.Resource_id=(TextView)row.findViewById(R.id.resource_id_track);
            ch.Resource_type=(TextView)row.findViewById(R.id.resource_type_track);
            ch.location_id=(TextView)row.findViewById(R.id.location_id_track);
            ch.Status=(TextView)row.findViewById(R.id.status_track);

            row.setTag(ch);
        }
        else
        {
            ch=(ContactHolders)row.getTag();

        }
        TrackRes contacts=(TrackRes) this.getItem(position);
        ch.Resource_id.setText(contacts.getResource_id());
        ch.Resource_type.setText(contacts.getResource_type());
        ch.location_id.setText(contacts.getLocation_id());
        ch.Status.setText(contacts.getStatus());

        return row;
    }
    static class ContactHolders
    {
        TextView Resource_id;
        TextView Resource_type;
        TextView location_id;
        TextView Status;


    }
}

