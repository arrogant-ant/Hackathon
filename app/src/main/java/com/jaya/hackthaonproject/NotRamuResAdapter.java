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


public class NotRamuResAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public NotRamuResAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(NotRamuRes object) {
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
            row=layoutInflater.inflate(R.layout.not_ramu_row,parent,false);
            ch=new ContactHolders();
            ch.Resource_Type=(TextView)row.findViewById(R.id.resource_type_preempt);
            ch.resource_id=(TextView)row.findViewById(R.id.resource_id_preempt);

            row.setTag(ch);
        }
        else
        {
            ch=(ContactHolders)row.getTag();

        }
        NotRamuRes contacts=(NotRamuRes) this.getItem(position);
        ch.Resource_Type.setText(contacts.getResource_Type());
        ch.resource_id.setText(contacts.getResource_id());
        return row;
    }
    static class ContactHolders
    {
        TextView Resource_Type;
        TextView resource_id;



    }
}

