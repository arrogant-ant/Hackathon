package com.jaya.hackthaonproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.*;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabita_Sant on 25/03/17.
 */

public class GrantedResAdapter extends ArrayAdapter<GrantedRes> {
    List<GrantedRes> list =new ArrayList<>();

    public GrantedResAdapter(Context context, int resource, List<GrantedRes> objects) {
        super(context, resource, objects);
        list= objects;
    }



    @Override
    public void add(GrantedRes object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        Holder h;
        if(row==null)
        {
            LayoutInflater inflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.granted_row,parent,false);
            h = new Holder();
            h.date = (TextView) row.findViewById(R.id.date_granted);
            h.id = (TextView) row.findViewById(R.id.id_granted);
            h.type = (TextView) row.findViewById(R.id.type_granted);
            h.no = (TextView) row.findViewById(R.id.no_granted);
            row.setTag(h);

        }
        else h= (Holder) row.getTag();
        GrantedRes res = list.get(position);
        h.date.setText(res.getDate());
        h.id.setText(res.getDemand_id());
        h.type.setText(res.getRes_type());
        h.no.setText(res.getNo());
        return row;
    }

    static class Holder
    {
        TextView date, id, type,no;

    }
}
