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

/**
 * Created by Sabita_Sant on 01/04/17.
 */

public class InTransientResAdapter extends ArrayAdapter<TransportRes> {
    List<TransportRes> list=new ArrayList();
    public InTransientResAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(TransportRes object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public TransportRes getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        TransportResAdapter.ContactHolders ch;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.transport_row,parent,false);
            ch=new TransportResAdapter.ContactHolders();
            ch.demand_id =(TextView)row.findViewById(R.id.demand_id_transport);
            ch.des_id =(TextView)row.findViewById(R.id.des_id_transport);
            ch.no =(TextView)row.findViewById(R.id.res_no_transport);
            ch.res_type =(TextView)row.findViewById(R.id.res_type_transport);

            row.setTag(ch);
        }
        else
        {
            ch=(TransportResAdapter.ContactHolders)row.getTag();

        }
        TransportRes contacts= this.getItem(position);
        ch.demand_id.setText(contacts.getDemand_id());
        ch.des_id.setText(contacts.getDes_id());
        ch.no.setText(contacts.getNo());
        ch.res_type.setText(contacts.getRes_type());

        return row;
    }
    static class ContactHolders
    {
        TextView demand_id;
        TextView des_id;
        TextView res_type;
        TextView no;


    }
}
