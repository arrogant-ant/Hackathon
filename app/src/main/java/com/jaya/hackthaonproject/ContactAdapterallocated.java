package com.jaya.hackthaonproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ContactAdapterallocated extends ArrayAdapter {
    List list=new ArrayList();
    public ContactAdapterallocated(Context context, int resource) {
        super(context, resource);
    }


    public void add(Contacts_allocated object) {
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
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.layout_individual_allocated_demand,parent,false);
            ch=new ContactHolders();
            ch.Resource_Type=(TextView)row.findViewById(R.id.resource_type_allocated);
            ch.No_Of_Resources=(TextView)row.findViewById(R.id.no_of_resources_allocated);
            row.setTag(ch);
        }
        else
        {
            ch=(ContactHolders)row.getTag();

        }
        Contacts_allocated contacts=(Contacts_allocated)this.getItem(position);
        ch.Resource_Type.setText(contacts.getResource_type());
        ch.No_Of_Resources.setText(contacts.getNo_of_resources());

        return row;
    }
    static class ContactHolders
    {
        TextView Resource_Type;
        TextView No_Of_Resources;

    }
}

