package com.jaya.hackthaonproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.*;
import android.view.View;
import android.widget.ArrayAdapter;

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
    }

    static class holder
    {

    }
}