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


public class ContactAdapterFulfill extends ArrayAdapter {
    List list=new ArrayList();
    public ContactAdapterFulfill(Context context, int resource) {
        super(context, resource);
    }


    public void add(Contacts_demand object) {
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
            row=layoutInflater.inflate(R.layout.layout_individual_fulfil,parent,false);
            ch=new ContactHolders();
            ch.Resource_Type=(TextView)row.findViewById(R.id.Resource_Type_fulfill);
            ch.No_Of_Resources=(TextView)row.findViewById(R.id.No_Of_Resources_fulfill);
            ch.Demand_Id=(TextView)row.findViewById(R.id.Demand_Id_fulfill);
            ch.Date_Of_Demand=(TextView)row.findViewById(R.id.date_of_demand);

            row.setTag(ch);
        }
        else
        {
            ch=(ContactHolders)row.getTag();

        }
        Contacts_demand contacts=(Contacts_demand)this.getItem(position);
        ch.Resource_Type.setText(contacts.getDemand_id());
        ch.No_Of_Resources.setText(contacts.getResource_type());
        ch.Demand_Id.setText(contacts.getNo_of_resources());
        ch.Date_Of_Demand.setText(contacts.getCompletion_time());
        return row;
    }
    static class ContactHolders
    {
        TextView Resource_Type;
        TextView No_Of_Resources;
        TextView Demand_Id;
        TextView Date_Of_Demand;


    }
}

