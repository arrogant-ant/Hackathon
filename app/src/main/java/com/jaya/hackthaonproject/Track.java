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

public class Track extends AppCompatActivity {
    String result;
      ListView listView;
    TrackResAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Track Resources");

        ca = new TrackResAdapter(this, R.layout.track_row);
        listView = (ListView) findViewById(R.id.list_view_track);
        listView.setAdapter(ca);
      TracksResources show = new TracksResources(this);
        show.execute();


    }

    class TracksResources extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        TracksResources(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.website/Track.php";

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
                String Resource_id;
                String Resource_type;
                String location_id;
                String Status;
                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    Resource_id = jo.getString("demand_id");
                    Resource_type = jo.getString("des_id");
                    location_id = jo.getString("no");
                    Status = jo.getString("res_type");


                    TrackRes c = new TrackRes(Resource_id, Resource_type, location_id, Status);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

