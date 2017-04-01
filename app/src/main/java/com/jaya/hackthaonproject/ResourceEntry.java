package com.jaya.hackthaonproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ResourceEntry extends AppCompatActivity {
    String result;
    ResourceEntryResAdapter ca;
    ListView listView;
    String loc_id;
    TextView t;
    Button b;
    EditText e;
    String res_id;
    String emp_id;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_entry);
        ca = new ResourceEntryResAdapter(this, R.layout.resource_entry_row);
        listView = (ListView) findViewById(R.id.resource_entry);
        listView.setAdapter(ca);
        loc_id=LoginActivity.loc_id;
        emp_id=LoginActivity.emp_id;
        ShowviewssDatas show = new ShowviewssDatas(this);
        show.execute(loc_id);
        b=(Button)findViewById(R.id.confirm);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e=(EditText)findViewById(R.id.editText3);
                res_id=e.getText().toString();
                SubmitData sub=new SubmitData(ResourceEntry.this);
                sub.execute(res_id,loc_id,emp_id);
            }
        });
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
            json_url = "http://www.wangle.website/ResourceEntry.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String first=params[0];
            try {

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                /////////////////
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("loc_id", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();





                /////////////
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
            parse(s);


        }


        void parse(String reslt) {
            JSONObject jsonObject;
            JSONArray jsonArray;



            try {
                jsonObject = new JSONObject(reslt);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String Resource_Type;
                String resource_id;

                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    Resource_Type=jo.getString("Resource_Type");
                    resource_id = jo.getString("resource_id");

                    ResourceEntryRes c = new ResourceEntryRes( Resource_Type, resource_id);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class SubmitData extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        SubmitData(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.website/AddREsourceData.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String res_id=params[0];
            String loc_id=params[1];
            String emp_id=params[2];
            try {

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                /////////////////
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("res_id", "UTF-8") + "=" + URLEncoder.encode(res_id, "UTF-8")+ "&" + URLEncoder.encode("loc_id", "UTF-8") + "=" + URLEncoder.encode(loc_id, "UTF-8")+ "&" + URLEncoder.encode("emp_id", "UTF-8") + "=" + URLEncoder.encode(emp_id, "UTF-8");
                bufferedWriter.write(data);;
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();





                /////////////
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


            Intent i=new Intent(ctx,ResourceEntry.class);
            startActivity(i);


        }



    }
}


