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
 * Created by Sabita_Sant on 18/03/17.
 */

public class ResourceAdapter extends ArrayAdapter<Resource> {
    List<Resource> list= new ArrayList();
    Context ctx;

    public ResourceAdapter(Context context, int resource, List<Resource> objects) {
        super(context, resource, objects);
        list=objects;
    }
   /* public ResourceAdapter(Context context, int resource) {
        super(context, resource);
    }
    */

    public void add(Resource object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    public Resource getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        ResourceAdapter.ResourceHolder rh;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.req_res_row,parent,false);
            rh=new ResourceAdapter.ResourceHolder();
            rh.tx_type=(TextView)row.findViewById(R.id.type);
            rh.tx_no=(TextView)row.findViewById(R.id.no);
            rh.tx_time=(TextView)row.findViewById(R.id.time);

            row.setTag(rh);
        }
        else
        {
            rh=(ResourceAdapter.ResourceHolder)row.getTag();

        }
        Resource resource= (Resource) list.get(position);
        rh.tx_type.setText(resource.getType());
        rh.tx_no.setText(resource.getNo());
        rh.tx_time.setText(resource.getTime());
        return row;
    }
    static class ResourceHolder
    {
        TextView tx_type;
        TextView tx_no;
        TextView tx_time;

    }
}
