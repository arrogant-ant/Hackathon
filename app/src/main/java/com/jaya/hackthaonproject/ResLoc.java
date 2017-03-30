package com.jaya.hackthaonproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ResLoc extends AppCompatActivity {

    ResLocResAdapter adapter;
    ListView listView;
    TextView src_tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_loc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Transport Resources");

        adapter = new ResLocResAdapter(this, R.layout.res_loc_row);
        listView = (ListView) findViewById(R.id.listview_res_loc);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String src;
                src_tx= (TextView) view.findViewById(R.id.srcID_res_loc);
                src=src_tx.getText().toString();
                transport(src);


            }
        });
        GetSource show = new GetSource();
        show.execute();


    }
    void transport(String src){
        AlertDialog.Builder builder = new AlertDialog.Builder(ResLoc.this);
        builder.setTitle("Confirmation").setMessage("Move resource from : "+src);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel",null);

        AlertDialog dialog= builder.create();
        dialog.show();

    }


    class GetSource extends AsyncTask<String, String, String> {

        String json_url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.website/res_loc.php";

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
            String res_id,src_id;


            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    src_id = jo.getString("src_id");
                    res_id = jo.getString("res_id");



                    ResLocRes c = new ResLocRes(res_id,src_id);
                    adapter.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
