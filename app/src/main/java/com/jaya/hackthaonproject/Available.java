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

public class Available extends AppCompatActivity {
    String result;
    AvailableResAdapter ca;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Available Resources");
        ca = new AvailableResAdapter(this, R.layout.available_row);
        listView = (ListView) findViewById(R.id.list_view_available);
        listView.setAdapter(ca);
        ShowviewssDatas show = new ShowviewssDatas(this);
        show.execute();
    }
    class ShowviewssDatas extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        ShowviewssDatas(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.website/available.php";

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
                String Resource_Type;
                String No_Of_Resources;

                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    Resource_Type=jo.getString("Resource_Type");
                    No_Of_Resources = jo.getString("No_Of_Resources");

                    AvailableRes c = new AvailableRes( Resource_Type, No_Of_Resources);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
