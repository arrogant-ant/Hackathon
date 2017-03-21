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


public class ContactsAdapteravailabe extends ArrayAdapter {
    List list=new ArrayList();
    public ContactsAdapteravailabe(Context context, int resource) {
        super(context, resource);
    }


    public void add(contacts_available object) {
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
            row=layoutInflater.inflate(R.layout.layout_individula_available,parent,false);
            ch=new ContactHolders();
            ch.Resource_Type=(TextView)row.findViewById(R.id.resource_type_available);
            ch.No_Of_Resources=(TextView)row.findViewById(R.id.no_of_resources_available);

            row.setTag(ch);
        }
        else
        {
            ch=(ContactHolders)row.getTag();

        }
        contacts_available contacts=(contacts_available) this.getItem(position);
        ch.Resource_Type.setText(contacts.getResource_Type());
        ch.No_Of_Resources.setText(contacts.getNo_Of_Resources());
        return row;
    }
    static class ContactHolders
    {
        TextView Resource_Type;
        TextView No_Of_Resources;



    }
}

