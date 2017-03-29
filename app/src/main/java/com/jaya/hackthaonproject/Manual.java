package com.jaya.hackthaonproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.regex.Pattern;

public class Manual extends AppCompatActivity {
    String result;
    ViewDemandsResAdapter ca;
    ListView listView;
    String accept_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demands);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Demands");
        ca = new ViewDemandsResAdapter(this, R.layout.view_demands_row);
        listView = (ListView) findViewById(R.id.listitems);
        listView.setAdapter(ca);

        Showviewss show = new Showviewss(this);
        show.execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView t=(TextView)view.findViewById(R.id.no_of_resources);
                String no_of_resources=t.getText().toString();
                TextView t1=(TextView)view.findViewById(R.id.resource_type);
                String resource_type=t1.getText().toString();
                TextView t2=(TextView)view.findViewById(R.id.demand_id);
                String demand_id=t2.getText().toString();
                alert(no_of_resources,resource_type,demand_id);
            }
        });
    }
    void alert(final String no_of_resources,final String resource_type,final String demand_id)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(Manual.this);
        builder.setTitle(no_of_resources+" Instance of Resource:"+resource_type+"  is Required");
        builder.setMessage("Click Proceed to check availability of resource");
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 Accept a=new Accept(Manual.this,demand_id,resource_type,no_of_resources);
                a.execute(resource_type,no_of_resources);
            }
        });

        builder.setNeutralButton("cancel",null);
        AlertDialog alertDialog= builder.create();
        alertDialog.show();

    }
    class Showviewss extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        Showviewss(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.website/demand.php";

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return buffer.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            result = s;


            parse(ctx);


        }


        void parse(Context ctx) {
            JSONObject jsonObject;
            JSONArray jsonArray;


            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String demand_id;
                String resource_type;
                String no_of_resources;
                String completion_time;
                String Deadline;
                String location_id;
                String date_of_demand;
                String priority;
                String date;
                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    demand_id = jo.getString("demand_id");
                    resource_type = jo.getString("resource_type");
                    no_of_resources = jo.getString("no_of_resources");
                    completion_time = jo.getString("completion_time");
                    priority = jo.getString("priority");
                    Deadline = jo.getString("Deadline");
                    location_id = jo.getString("no");
                    date = jo.getString("date_of_demand");
                    date_of_demand = date.substring(0, 10);


                    ViewDemandsRes c = new ViewDemandsRes(resource_type, no_of_resources, completion_time, priority, Deadline, location_id, date_of_demand, demand_id);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class Accept extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;
        String demand_id_individual;
        String resource_type_individual;
        String no_of_resources_individual;

        Accept(Context ctx,String demand_id,String resource_type,String no_of_resources) {
            this.ctx = ctx;
            demand_id_individual=demand_id;
            resource_type_individual=resource_type;
            no_of_resources_individual=no_of_resources;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.website/like.php";

        }

        @Override
        protected String doInBackground(String... params) {

            String first = params[0];
            String second=params[1];



            try {

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("resource_type", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8")+ "&" +  URLEncoder.encode("resource_no", "UTF-8") + "=" + URLEncoder.encode(second, "UTF-8") ;
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // reading from the server

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return buffer.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            accept_data=s;
            parse(ctx);


        }

        void parse(Context ctx) {
            JSONObject jsonObject;
            JSONArray jsonArray;
            String demand_id;
            try {

                jsonObject = new JSONObject(accept_data);
                jsonArray = jsonObject.getJSONArray("server_response");

                    JSONObject jo = jsonArray.getJSONObject(0);
                    demand_id = jo.getString("demand_id");
                     String[] datas=demand_id.split(Pattern.quote("$"));
                String resource=datas[0];
              /*  String result_needed=datas[1];

                if(result_needed.equals("1"))
                {
                    Intent i=new Intent(ctx,Preempt.class);
                    i.putExtra("demand_id",demand_id_individual);
                    i.putExtra("resource_type",resource_type_individual);
                    i.putExtra("no_of_resources",no_of_resources_individual);
                    startActivity(i);
                }
                 else
                {*/
                  Intent i=new Intent(ctx,NotRamu.class);
                    i.putExtra("resource_no",resource);
                    i.putExtra("resource_type",resource_type_individual);
                    startActivity(i);
                //}





            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
