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

public class ShowDemand extends AppCompatActivity {
    ShowDemandResAdapter ca;
    ListView listView;
    String result;
    String location_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sm_demand);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Requested Demands");
        ca = new ShowDemandResAdapter(this,R.layout.show_demand_row);
        listView = (ListView) findViewById(R.id.list_view_show_user_demand);
        listView.setAdapter(ca);
        ShowUserView show = new ShowUserView(this);
       location_id=LoginActivity.loc_id;
        show.execute(location_id);


    }
    class ShowUserView extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;


        ShowUserView(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.website/UserDemands.php";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
               String first=params[0];
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

               OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("location_id", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
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
                String completion_time;
                String priority;
                String location_id;
                 String date_of_demand;
                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    Demand_id = jo.getString("demand_id");
                    Resource_type = jo.getString("resource_type");
                    No_of_resources = jo.getString("no_of_resources");
                    completion_time = jo.getString("completion_time");
                    priority = jo.getString("priority");
                    location_id = jo.getString("location_id");
                    date_of_demand=jo.getString("date_of_demand");


                    ShowDemandRes c = new ShowDemandRes(Demand_id, Resource_type, No_of_resources, completion_time,priority,location_id,date_of_demand);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
