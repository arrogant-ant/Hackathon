package com.jaya.hackthaonproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabita_Sant on 30/03/17.
 */

public class ResLocResAdapter extends ArrayAdapter<ResLocRes> {
    List<ResLocRes> list=new ArrayList();
    public ResLocResAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(ResLocRes object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public ResLocRes getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        ContactHolders ch;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.res_loc_row,parent,false);
            ch= new ContactHolders();
            ch.res_no =(TextView)row.findViewById(R.id.no_res_loc);
            ch.src_id =(TextView)row.findViewById(R.id.src_res_loc);
            ch.res_type= (TextView) row.findViewById(R.id.type_res_loc);
            row.setTag(ch);
        }
        else
        {
            ch=(ResLocResAdapter.ContactHolders)row.getTag();

        }
        ResLocRes contacts= this.getItem(position);
        ch.res_no.setText(contacts.getRes_no());
        ch.src_id.setText(contacts.getSrc_id());
        ch.res_type.setText(contacts.getRes_type());
        return row;
    }
    static class ContactHolders
    {
        TextView res_no;
        TextView src_id;
        TextView res_type;



    }
}
