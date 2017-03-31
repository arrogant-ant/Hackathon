package com.jaya.hackthaonproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

public class Free extends AppCompatActivity {
    ListView listView;
    String location_id;
    FreeResAdapter f;
    String resID, resType;
    TextView id_tx, type_tx;
    String url="http://www.wangle.website/list_of_resources_user.php";
    String removeUrl="http://www.wangle.website/dealocation_user_resources.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Free Demands");

        listView = (ListView) findViewById(R.id.listview_free);
        f = new FreeResAdapter(getApplicationContext(), R.layout.granted_row);
        listView.setAdapter(f);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id_tx = (TextView) view.findViewById(R.id.id_free);
                type_tx = (TextView) view.findViewById(R.id.type_free);
                resID= id_tx.getText().toString();
                resType=type_tx.getText().toString();
                freeConfirmation(resID,resType);
            }
        });
        location_id = LoginActivity.loc_id;

       // ShowRes showRes = new ShowRes();
        //showRes.execute(location_id);
//////////////////////////

        StringRequest stringRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(Free.this,"got response",Toast.LENGTH_SHORT).show();
                parse(response);

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("loc_id",location_id);
                return params;
            }

        };

        VolleySingleton.getInstance(getApplicationContext()).addToReqQueue(stringRequest);

    }






        //////////////


    private void freeConfirmation(final String resID, final String resType) {
        AlertDialog.Builder builder =new AlertDialog.Builder(Free.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure to free "+resID+" ?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               /*RemoveRes remove= new RemoveRes();
                remove.execute(location_id,resType,resID);*/
                StringRequest stringRequest= new StringRequest(Request.Method.POST, removeUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
                        startActivity(getIntent());

                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params = new HashMap<String, String>();

                        params.put("loc_id",location_id);
                        params.put("resource_type",resType);
                        params.put("resource_id", resID);
                        return params;
                    }
                   /*@Override
                   public byte[] getBody() throws AuthFailureError {

                       //Toast.makeText(Free.this, "in volley body", Toast.LENGTH_LONG).show();
                       JSONObject params = new JSONObject();
                       try {
                           params.put("loc_id",location_id);
                           params.put("resource_type",resType);
                           params.put("resource_id", resID);
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }

                       return params.toString().getBytes();
                   }*/

                };

                VolleySingleton.getInstance(getApplicationContext()).addToReqQueue(stringRequest);

            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void parse(String result) {
        Toast.makeText(Free.this,"parsing response",Toast.LENGTH_SHORT).show();

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
                resource_Type = jo.getString("Resource_Type");
                res_Id = jo.getString("Resource_id");
                FreeRes c = new FreeRes(res_Id, resource_Type);
                f.add(c);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    //getting data
    /*private class ShowRes extends AsyncTask<String, String, String> {
        StringBuffer buffer = new StringBuffer();

        @Override
        protected String doInBackground(String... params) {
            try {
                String first = params[0];
                URL url = new URL("http://www.wangle.website/list_of_resources_user.php");
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
    }*/

    //parsing JSON
    /*void parse(String j) {
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
                res_Id = jo.getString("demand_id");
                FreeRes c = new FreeRes(res_Id, resource_Type);
                f.add(c);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    //to free res
   /* private class RemoveRes extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String loc_id = params[0];
                String resource_type = params[1];
                String resource_id = params[2];

                URL url = new URL("http://www.wangle.website/dealocation_user_resources.php");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("loc_id", "UTF-8") + "=" + URLEncoder.encode(loc_id, "UTF-8")
                        + URLEncoder.encode("resource_type", "UTF-8") + "=" + URLEncoder.encode(resource_type, "UTF-8")
                        + URLEncoder.encode("resource_id", "UTF-8") + "=" + URLEncoder.encode(resource_id, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                // reading from the server

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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            finish();
            startActivity(getIntent());
            Toast.makeText(Free.this,"activity restarted",Toast.LENGTH_SHORT).show();

        }
    }*/
}
