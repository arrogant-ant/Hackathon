package com.jaya.hackthaonproject;

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

public class Free extends AppCompatActivity {
    ListView listView;
    String location_id;
    FreeResAdapter f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Granted Demands");

        listView = (ListView) findViewById(R.id.listview_free);
        f = new FreeResAdapter(getApplicationContext(), R.layout.granted_row);
        listView.setAdapter(f);
        location_id = LoginActivity.loc_id;
        ShowRes showRes = new ShowRes();
        showRes.execute(location_id);
    }


    //getting data
    private class ShowRes extends AsyncTask<String, String, String> {
        StringBuffer buffer = new StringBuffer();

        @Override
        protected String doInBackground(String... params) {
            try {
                String first = params[0];
                URL url = new URL("http://www.wangle.website/GrantedResource.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("loc_id", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // reading from the server

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    buffer.append(line);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString().trim();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parse(s);
        }
    }

    //parsing JSON
    void parse(String j) {
        JSONObject jsonObject;
        JSONArray jsonArray;


        try {
            jsonObject = new JSONObject(j);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String resource_Type;
            String res_Id;


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);
                resource_Type = jo.getString("Resource_Type");
                res_Id = jo.getString("Demand_Id");
                FreeRes c = new FreeRes(res_Id, resource_Type);
                f.add(c);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
