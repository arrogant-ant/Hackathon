package com.jaya.hackthaonproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NewDemand extends AppCompatActivity implements AdapterView.OnItemSelectedListener,Communicator {

    Spinner priority_sp;
    ArrayAdapter<String> priority_ad;
    ArrayList<Resource> demand;
    EditText dealine_et;
    String priority, deadline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_demand);
        String[] priority={"1","2","3","4","5"};
        demand= new ArrayList<Resource>();
        dealine_et= (EditText) findViewById(R.id.deadline);

        priority_ad=new ArrayAdapter<String>(NewDemand.this,android.R.layout.simple_spinner_dropdown_item,priority);
        priority_sp= (Spinner) findViewById(R.id.prioritySpinner);
        priority_sp.setAdapter(priority_ad);
        priority_sp.setOnItemSelectedListener(NewDemand.this);


    }







    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        priority= (String) parent.getItemAtPosition(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void response(String type, String no, String time) {
        ReqResource resource = new ReqResource();
        FragmentManager manager= getFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.add(R.id.req_res,resource).commit();
        //Toast.makeText(NewDemand.this,"got response",Toast.LENGTH_SHORT).show();
        ReqResource res = (ReqResource) manager.findFragmentById(R.id.req_res);
        res.setText(type,no,time);

        //to add to arraylist
        Resource r= new Resource(type,no,time);
        demand.add(r);


    }

    public void submit(View view) {
        deadline= dealine_et.getText().toString();
        int total=demand.size();
        AddDemand add= new AddDemand();
        add.execute(priority,deadline);
    }

    //pass the data
    class AddDemand extends AsyncTask<String,String,Void> {

        String json_url;
        String type, time,no;
        int i;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.16mb.com/another_try.php";

        }

        @Override
        protected Void doInBackground(String... params) {
            String priority=params[0];
            String deadline=params[1];

            try {
                for (i=0;i<demand.size();i++) {
                    URL url = new URL(json_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    Resource r = demand.get(i);
                    type = r.getType();
                    no = r.getNo();
                    time = r.getTime();
                    String data = URLEncoder.encode("Type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") +
                            "&" + URLEncoder.encode("No", "UTF-8") + "=" + URLEncoder.encode(no, "UTF-8") +
                            "&" + URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") +
                            "&" + URLEncoder.encode("Priority", "UTF-8") + "=" + URLEncoder.encode(priority, "UTF-8") +
                            "&" + URLEncoder.encode("Deadline", "UTF-8") + "=" + URLEncoder.encode(deadline, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();

                    bufferedWriter.close();
                    outputStream.close();


                    // reading from the server

                    InputStream inputStream = httpURLConnection.getInputStream();
                    inputStream.close();
                    httpURLConnection.disconnect();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }



    }
}

