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
 * Created by Sabita_Sant on 26/03/17.
 */

public class FreeResAdapter extends ArrayAdapter<FreeRes> {
    List<FreeRes> list= new ArrayList<>();

    public FreeResAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public void add(FreeRes object) {
        super.add(object);
        list.add(object);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row= convertView;
        Holder h;
        if(row==null)
        {
            LayoutInflater inflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(R.layout.free_res_row,parent);
            h= new Holder();
            h.id= (TextView) row.findViewById(R.id.id_free);
            h.type= (TextView) row.findViewById(R.id.type_free);
            row.setTag(h);
        }
        else
            h= (Holder) row.getTag();
        FreeRes res= getItem(position);
        h.id.setText(res.getResID());
        h.type.setText(res.getResType());
        return row;
    }

    class Holder
    {
        TextView id,type;

    }
}
