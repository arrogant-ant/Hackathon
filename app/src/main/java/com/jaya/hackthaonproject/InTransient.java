package com.jaya.hackthaonproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class InTransient extends AppCompatActivity {

    ListView listView;
    InTransientResAdapter ca;
    String des_id, demand_id, res_type, no;
    String url = "http://www.wangle.website/transport_transient.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_transient);
        ca = new InTransientResAdapter(this, R.layout.transport_row);
        listView = (ListView) findViewById(R.id.listview_transient);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Resources In Transient");
        listView.setAdapter(ca);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(Allocated.this," In volley",Toast.LENGTH_LONG).show();
                parse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(InTransient.this, "Error in volley", Toast.LENGTH_LONG).show();

            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToReqQueue(stringRequest);


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
