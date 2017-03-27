package com.jaya.hackthaonproject;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Manua extends AppCompatActivity {
    String result;
    ContactAdapterDemand ca;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demands);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Demands");
        ca = new ContactAdapterDemand(this, R.layout.activity_layout_individual_admin);
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
                CheckAvailability check=new CheckAvailability(Manua.this);
                check.execute(no_of_resources,resource_type);
            }
        });
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
                    location_id = jo.getString("location_id");
                    date = jo.getString("date_of_demand");
                    date_of_demand = date.substring(0, 10);


                    Contacts_demand c = new Contacts_demand(resource_type, no_of_resources, completion_time, priority, Deadline, location_id, date_of_demand, demand_id);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class CheckAvailability extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        CheckAvailability(Context ctx) {
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
                    location_id = jo.getString("location_id");
                    date = jo.getString("date_of_demand");
                    date_of_demand = date.substring(0, 10);


                    Contacts_demand c = new Contacts_demand(resource_type, no_of_resources, completion_time, priority, Deadline, location_id, date_of_demand, demand_id);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
