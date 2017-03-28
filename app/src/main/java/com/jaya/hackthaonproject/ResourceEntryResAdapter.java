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

public class ResourceEntryResAdapter extends ArrayAdapter {
    List l=new ArrayList();
    public ResourceEntryResAdapter(Context context, int resource) {
        super(context, resource);
    }
    public void add(ResourceEntryRes c)
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
            row= li.inflate(R.layout.resource_entry_row,parent,false);
            ch=new Contacthoder();
            ch.t1=(TextView)row.findViewById(R.id.resource_id_entry);
            ch.t2=(TextView)row.findViewById(R.id.resource_type_entry);

            row.setTag(ch);




        }
        else
        {
            ch=(Contacthoder)row.getTag();
        }
        ResourceEntryRes cus=(ResourceEntryRes) this.getItem(position);
        ch.t1.setText(cus.getResource_id());
        ch.t2.setText(cus.getResource_type());

        return row;




    }
    static class Contacthoder
    {
        TextView  t1;
        TextView  t2;



    }
}
