package com.jaya.hackthaonproject;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

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

public class Timeline extends AppCompatActivity {
    ContactAdapterTimeline ca;
    ListView listView;
    String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ca = new ContactAdapterTimeline(this, R.layout.layout_individual_timeline_report);
        listView = (ListView) findViewById(R.id.list_view_timeline);
        listView.setAdapter(ca);
        Timelineview show = new Timelineview(this);
        show.execute();
    }
    class Timelineview extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        Timelineview(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.website/timeline.php";

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

                String Demand_id;
                String Resource_type;
                String No_of_resources;
                String Status;
                String Modified_by;
                String Modified_on;
                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    Demand_id = jo.getString("Demand_id");
                    Resource_type = jo.getString("Resource_type");
                    No_of_resources = jo.getString("No_of_resources");
                    Status = jo.getString("Status");
                    Modified_by = jo.getString("Modified_by");
                    Modified_on = jo.getString("Modified_on");


                    Contacts_timeline c = new Contacts_timeline(Demand_id, Resource_type, No_of_resources, Status,Modified_by,Modified_on);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }}
