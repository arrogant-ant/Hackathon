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

public class Transport extends AppCompatActivity {
   // String result;
    ListView listView;
    TransportResAdapter ca;
    String des_id,demand_id, res_type,no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Track Resources");

        ca = new TransportResAdapter(this, R.layout.transport_row);
        listView = (ListView) findViewById(R.id.list_view_transport);
        listView.setAdapter(ca);
        TrackResources show = new TrackResources(this);
        show.execute();


    }

    class TrackResources extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        TrackResources(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.website/transporter.php";

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


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parse(s);


        }


        void parse(String result) {
            JSONObject jsonObject;
            JSONArray jsonArray;


            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    demand_id = jo.getString("Demand_Id");
                    des_id = jo.getString("Destination_Id");
                    no = jo.getString("No_Of_Resources");
                    res_type = jo.getString("Resource_Type");


                    TransportRes c = new TransportRes(demand_id, res_type, no, des_id);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
