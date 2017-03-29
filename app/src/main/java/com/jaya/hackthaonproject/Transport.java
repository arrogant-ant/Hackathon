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

public class Transport extends AppCompatActivity {
   // String result;
    ListView listView;
    TransportResAdapter ca;
    String des_id,demand_id, res_type,no;
    TextView type_tx, id_tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Track Resources");

        ca = new TransportResAdapter(this, R.layout.transport_row);
        listView = (ListView) findViewById(R.id.list_view_transport);
        listView.setAdapter(ca);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String type,dem_id;
                type_tx= (TextView) view.findViewById(R.id.res_type_transport);
                id_tx= (TextView) view.findViewById(R.id.demand_id_transport);
                dem_id= id_tx.getText().toString();
                type= type_tx.getText().toString();
                transport(dem_id,type);


            }
        });
        TrackResources show = new TrackResources(this);
        show.execute();


    }
    void transport(String dem_id, String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(Transport.this);
        builder.setTitle("Confirmation").setMessage("Proceed to process Demand ID : "+dem_id);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel",null);

        AlertDialog dialog= builder.create();
        dialog.show();

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
