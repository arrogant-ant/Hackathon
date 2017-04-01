package com.jaya.hackthaonproject;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NewDemand extends AppCompatActivity implements AdapterView.OnItemSelectedListener,Communicator {

    Spinner priority_sp;
    ArrayAdapter<String> priority_ad;
    ArrayList<Resource> demand;
    EditText dealine_et;
    String priority, deadline;
    static String empID,location_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_demand);
        String[] priority={"1","2","3","4","5"};
        demand= new ArrayList<>();
        dealine_et= (EditText) findViewById(R.id.deadline);
        empID=LoginActivity.emp_id;
        location_id= LoginActivity.loc_id;
        priority_ad=new ArrayAdapter<>(NewDemand.this,android.R.layout.simple_spinner_dropdown_item,priority);
        priority_sp= (Spinner) findViewById(R.id.prioritySpinner);
        priority_sp.setAdapter(priority_ad);
        priority_sp.setOnItemSelectedListener(NewDemand.this);


    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        priority= (String) parent.getItemAtPosition(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void response(String type, String no, String time) {
        ReqResource resource = new ReqResource();
        FragmentManager manager= getFragmentManager();
        FragmentTransaction transaction= manager.beginTransaction();
        transaction.add(R.id.req_res,resource).commit();
        //Toast.makeText(NewDemand.this,"got response",Toast.LENGTH_SHORT).show();
        ReqResource res = (ReqResource) manager.findFragmentById(R.id.req_res);
        res.setText(type,no,time);

        //to add to arraylist
        Resource r= new Resource(type,no,time);
        demand.add(r);



    }

    public void submit(View view) {
        deadline= dealine_et.getText().toString();
        GetID d_ID= new GetID();
        d_ID.execute();


    }

    //to upload all demands
    void uploadDemand(String s) throws JSONException {

        AddDemand add= new AddDemand();
        String demand_id="";

        JSONObject jsonObject;
        jsonObject = new JSONObject(s);
        JSONArray jsonArray = jsonObject.getJSONArray("server_response");
        int count = 0;

        while (count < jsonArray.length()) {

            JSONObject jo = jsonArray.getJSONObject(count);
            demand_id= jo.getString("demand_id");
            count++;

        }


        Toast.makeText(NewDemand.this,"demand "+demand_id,Toast.LENGTH_SHORT).show();
        add.execute(priority,deadline,demand_id,location_id);
    }

    void display(String result)
    {
        if(result.equals("true"))
        {
            AlertDialog.Builder builder= new AlertDialog.Builder(NewDemand.this);
            builder.setTitle("Demand Placed");
            builder.setMessage("Do you want to place another independent demand?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(NewDemand.this, NewDemand.class);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(NewDemand.this, SM.class);
                    startActivity(i);
                }
            });
            AlertDialog alertDialog=builder.create();
            alertDialog.show();

        }
        else
            Toast.makeText(NewDemand.this,"demand NOT placed, plz retry",Toast.LENGTH_LONG).show();
    }



    //to get demand id n loc id
    class GetID extends AsyncTask<String,String,String>
    {
        StringBuffer buffer = new StringBuffer();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String url_string="http://www.wangle.website/demand_id.php";
            try {
                URL url = new URL(url_string);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data=URLEncoder.encode("emp_ID","UTF-8")+"="+URLEncoder.encode(location_id,"UTF-8");
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




            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
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
            try {
                uploadDemand(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //pass the data
    class AddDemand extends AsyncTask<String,String,String> {

        String url_string,data;
        String type, time,no;
        int i;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();



        }

        @Override
        protected String doInBackground(String... params) {

            StringBuffer buffer = new StringBuffer();

            url_string="http://www.wangle.website/place_demand.php";
            String priority = params[0];
            String deadline = params[1];
            String demand_id = params[2];
            String location_id = params[3];

            try
            {
                //sending the demands
                for (i=0;i<demand.size();i++) {
                    URL url = new URL(url_string);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.connect();
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    Resource r = demand.get(0);
                    type = r.getType();
                    no = r.getNo();
                    time = r.getTime();
                    data = URLEncoder.encode("Type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") +
                            "&" + URLEncoder.encode("No", "UTF-8") + "=" + URLEncoder.encode(no, "UTF-8") +
                            "&" + URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") +
                            "&" + URLEncoder.encode("Priority", "UTF-8") + "=" + URLEncoder.encode(priority, "UTF-8") +
                            "&" + URLEncoder.encode("demand_id", "UTF-8") + "=" + URLEncoder.encode(demand_id, "UTF-8") +
                            "&" + URLEncoder.encode("location_id", "UTF-8") + "=" + URLEncoder.encode(location_id, "UTF-8") +
                            "&" + URLEncoder.encode("Deadline", "UTF-8") + "=" + URLEncoder.encode(deadline, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    // reading from the server
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String line = "";
                    buffer = new StringBuffer();
                    while ((line = bufferedReader.readLine()) != null) {
                        buffer.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                }
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
            String result= new String();
            JSONObject js= null;
            try {
                js = new JSONObject(s);
                result= js.getString("result");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            display(result);

        }
    }
}

