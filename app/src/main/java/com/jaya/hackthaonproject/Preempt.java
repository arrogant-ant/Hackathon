package com.jaya.hackthaonproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

public class Preempt extends AppCompatActivity {
String accept_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prempt);
        final String s=getIntent().getExtras().getString("demand_id");
       final String s2=getIntent().getExtras().getString("resource_type");
       final  String s3=getIntent().getExtras().getString("no_of_resources");
        Button b=(Button)findViewById(R.id.success_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Accept a=new Accept(Preempt.this);
                a.execute(s,s2,s3);
            }
        });


    }
    class Accept extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        Accept(Context ctx) {
            this.ctx = ctx;
            //


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.website/delete.php";

        }

        @Override
        protected String doInBackground(String... params) {

            String first = params[0];
            String second=params[1];
            String third=params[2];


            try {

                URL url = new URL(json_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("demand_id", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8")+ "&" +  URLEncoder.encode("resource_type", "UTF-8") + "=" + URLEncoder.encode(second, "UTF-8")+ "&" +  URLEncoder.encode("no_of_resources", "UTF-8") + "=" + URLEncoder.encode(third, "UTF-8") ;
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
           Intent i=new Intent(ctx,SMRegSuccess.class);
            startActivity(i);


        }



    }
}
