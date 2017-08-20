package com.jaya.hackthaonproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
import java.util.HashMap;
import java.util.Map;

public class ResLoc extends AppCompatActivity {

    ResLocResAdapter adapter;
    ListView listView;
    TextView src_tx, available_tx;
    String type;
    String no,demand_id;
    String url = "http://www.techdrona.net/tech/Hack/set_on_the_way.php";
    String src, available;
    TextView no_tx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_loc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Resources Location");
        type = getIntent().getExtras().getString("type");
        no = getIntent().getExtras().getString("no");
        demand_id = getIntent().getExtras().getString("demand_id");
        no_tx= (TextView) findViewById(R.id.no);
        no_tx.setText(no);
        adapter = new ResLocResAdapter(this, R.layout.res_loc_row);
        listView = (ListView) findViewById(R.id.listview_res_loc);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                src_tx = (TextView) view.findViewById(R.id.src_res_loc);
                src = src_tx.getText().toString();
                available_tx = (TextView) view.findViewById(R.id.src_res_loc);
                available = available_tx.getText().toString();
                transport(src);


            }
        });

        GetSource show = new GetSource();
        show.execute(type);


    }

    void transport(final String src) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResLoc.this);
        builder.setTitle("Confirmation").setMessage("Move resource from : " + src);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        getLeft(response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("rtype", type);
                        params.put("loc", src);
                        params.put("ipno", available);
                        params.put("expno", no);
                        params.put("demand_id", demand_id);
                        return params;
                    }

                };

                VolleySingleton.getInstance(getApplicationContext()).addToReqQueue(stringRequest);

            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    class GetSource extends AsyncTask<String, String, String> {

        String json_url;
        String type;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.techdrona.net/tech/Hack/res_loc.php";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                type = params[0];

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
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


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            parse(s);


        }


        void parse(String result) {
            JSONObject jsonObject;
            JSONArray jsonArray;
            String no, src_id;


            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    src_id = jo.getString("Source_id");
                    no = jo.getString("Available_no");


                    ResLocRes c = new ResLocRes(no, src_id, type);
                    adapter.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    void getLeft(String result) {
        JSONObject jsonObject;
        JSONArray jsonArray;


        try {
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String resource_Type;
            String res_Id;


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);
                no = jo.getString("Resource_Type");
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (no.equals("0")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ResLoc.this);
            builder.setTitle("Info");
            builder.setMessage("Resource in transient");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(ResLoc.this, Transport.class);
                    finish();
                    startActivity(i);
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            Intent intent = new Intent(ResLoc.this, ResLoc.class);
            intent.putExtra("type",type);
            intent.putExtra("no",no);
            intent.putExtra("demand_id",demand_id);
            finish();
            startActivity(intent);
        }
    }

}
