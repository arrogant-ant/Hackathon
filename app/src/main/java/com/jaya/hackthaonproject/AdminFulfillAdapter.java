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


public class AdminFulfillAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public AdminFulfillAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(AdminFulfillRes object) {
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
            row=layoutInflater.inflate(R.layout.fulfill_row,parent,false);
            ch=new ContactHolders();
            ch.Resource_Type=(TextView)row.findViewById(R.id.Resource_Type_fulfill);
            ch.No_Of_Resources=(TextView)row.findViewById(R.id.No_Of_Resources_fulfill);
            ch.Demand_Id=(TextView)row.findViewById(R.id.Demand_Id_fulfill);
            ch.Date_Of_Demand=(TextView)row.findViewById(R.id.Date_Of_Demand_fulfil);

            row.setTag(ch);
        }
        else
        {
            ch=(ContactHolders)row.getTag();

        }
        AdminFulfillRes contacts=(AdminFulfillRes) this.getItem(position);
        ch.Resource_Type.setText(contacts.getResource_Type());
        ch.No_Of_Resources.setText(contacts.getNo_Of_Resources());
        ch.Demand_Id.setText(contacts.getDemand_Id());
        ch.Date_Of_Demand.setText(contacts.getDate_Of_Demand());
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

