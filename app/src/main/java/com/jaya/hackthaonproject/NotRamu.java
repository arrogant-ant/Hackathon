package com.jaya.hackthaonproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class NotRamu extends AppCompatActivity {
    TextView t1, t2;
    String result;
    NotRamuResAdapter ca;
    ListView listView;
    Button B;
    String emp_id;
    String loc_id;

    ArrayList<String> al = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_ramu);
        final String res_no = getIntent().getExtras().getString("resource_no");
        final String res_type = getIntent().getExtras().getString("resource_type");
        final String demand_id = getIntent().getExtras().getString("demand_id");
        t1 = (TextView) findViewById(R.id.textViews1);
        t2 = (TextView) findViewById(R.id.textViews2);
        t1.setText(res_no);
        t2.setText(res_type);
        emp_id = LoginActivity.emp_id;
        loc_id = LoginActivity.loc_id;
        ca = new NotRamuResAdapter(this, R.layout.not_ramu_row);
        listView = (ListView) findViewById(R.id.listdata_not_ramu);
        listView.setAdapter(ca);
        ShowviewssDatas show = new ShowviewssDatas(this);
        show.execute(res_type);
        B = (Button) findViewById(R.id.premptdata);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = "";
                for (String x : al) {
                    Toast.makeText(NotRamu.this, "al = " + x, Toast.LENGTH_SHORT).show();
                    PreemptRes preemptRes = new PreemptRes();
                    preemptRes.execute(emp_id, x, res_type, loc_id);

                }
                // AllocatedDemand ad=new AllocatedDemand();
                //ad.execute(res_no,res_type,demand_id);
                display();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.check);
                TextView tt = (TextView) view.findViewById(R.id.resource_id_preempt);
                String resource_id = tt.getText().toString();
                if (cb.isChecked()) {
                    cb.setChecked(false);
                    if (al.contains(resource_id)) {
                        al.remove(resource_id);
                    }

                } else {
                    cb.setChecked(true);
                    al.add(resource_id);
                }
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
            json_url = "http://www.techdrona.net/tech/Hack/ResourcesPreempt.php";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String first = params[0];
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                ////////////////////////////////
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("Resource_Type", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                ///////////////
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
                String resource_id;
                String Resource_Type;

                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    resource_id = jo.getString("resource_id");
                    Resource_Type = jo.getString("Resource_Type");

                    NotRamuRes c = new NotRamuRes(Resource_Type, resource_id);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    //Async to preempt
    class PreemptRes extends AsyncTask<String, String, String> {

        String json_url;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.techdrona.net/tech/Hack/ResourceIndividualPreempt.php";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                String emp_id = params[0];
                String resource_id = params[1];
                String resource_type = params[2];
                String location_id = params[3];
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                ////////////////////////////////
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("emp_id", "UTF-8") + "=" + URLEncoder.encode(emp_id, "UTF-8") + "&" + URLEncoder.encode("resource_id", "UTF-8") + "=" + URLEncoder.encode(resource_id, "UTF-8") + "&" + URLEncoder.encode("resource_type", "UTF-8") + "=" + URLEncoder.encode(resource_type, "UTF-8") + "&" + URLEncoder.encode("location_id", "UTF-8") + "=" + URLEncoder.encode(location_id, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                ///////////////
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


    }

    class AllocatedDemand extends AsyncTask<String, String, String> {

        String json_url;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.techdrona.net/tech/Hack/allocate_preempt.php";

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                String res_no = params[0];
                String res_type = params[1];
                String demand_id = params[2];
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                ////////////////////////////////
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("res_no", "UTF-8") + "=" + URLEncoder.encode(res_no, "UTF-8") + "&" + URLEncoder.encode("res_type", "UTF-8") + "=" + URLEncoder.encode(res_type, "UTF-8") + "&" + URLEncoder.encode("demand_id", "UTF-8") + "=" + URLEncoder.encode(demand_id, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                ///////////////
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


    }

    void display() {

        AlertDialog.Builder builder = new AlertDialog.Builder(NotRamu.this);
        builder.setTitle("Premption Placed");
        builder.setMessage("Resource has been preempted");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(NotRamu.this, PC.class);
                startActivity(i);
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


}
