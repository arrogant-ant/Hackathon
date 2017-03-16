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

/**
 * Created by shubham on 3/12/2017.
 */

public class ContactAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Contacts object) {
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
        ContactHolder ch;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.individual_layout,parent,false);
            ch=new ContactHolder();
            ch.tx_empid=(TextView)row.findViewById(R.id.adapter);
            ch.tx_password=(TextView)row.findViewById(R.id.pass);
            ch.tx_email=(TextView)row.findViewById(R.id.email);
            ch.tx_phone_no=(TextView)row.findViewById(R.id.phone);
            row.setTag(ch);
        }
        else
        {
          ch=(ContactHolder)row.getTag();

        }
       Contacts contacts=(Contacts)this.getItem(position);
        ch.tx_empid.setText(contacts.getEmpid());
        ch.tx_password.setText(contacts.getPassword());
        ch.tx_email.setText(contacts.getEmailid());
        ch.tx_phone_no.setText(contacts.getPhone_no());
       return row;
    }
    static class ContactHolder
    {
        TextView tx_empid;
        TextView tx_password;
        TextView tx_email;
        TextView tx_phone_no;
    }
}

