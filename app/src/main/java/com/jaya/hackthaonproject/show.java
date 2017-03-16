package com.jaya.hackthaonproject;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class show extends AppCompatActivity {
    String result;
    TextView tx;
    ContactAdapter ca;
    ListView listView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ca=new ContactAdapter(this,R.layout.individual_layout);
        listView=(ListView)findViewById(R.id.listitem1);
        listView.setAdapter(ca);
        Showviews show =new Showviews(this);
        show.execute();
    }
    class Showviews extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        Showviews(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.16mb.com/try.php";

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

         /*  Intent i=new Intent(ctx,result.class);
            i.putExtra("result",result);
            startActivity(i);*/


        }
    }

    void parse(Context ctx) {
        JSONObject jsonObject;
        JSONArray jsonArray;


        try {
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String empid;
            String password;
            String emailid;
            String phone_no;

            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);
                empid = jo.getString("empid");
                password = jo.getString("password");
                emailid = jo.getString("emailid");
                phone_no = jo.getString("phone_no");
                Contacts c = new Contacts(empid, password, emailid, phone_no);
                ca.add(c);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
