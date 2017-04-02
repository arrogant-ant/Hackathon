package com.jaya.hackthaonproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.jaya.hackthaonproject.NewDemand.location_id;

public class Purpose extends AppCompatActivity {
    TextView title_tx;
    EditText id_tx, purpose_tx;
    String purpose, id;
    String url="http://www.wangle.website/return_purpose.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purpose);
        id_tx = (EditText) findViewById(R.id.id_purpose);
        title_tx = (TextView) findViewById(R.id.title_purpose);
        purpose_tx = (EditText) findViewById(R.id.purpose);
        id = id_tx.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                display(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("demand_id", id);

                return params;
            }

        };

        VolleySingleton.getInstance(getApplicationContext()).addToReqQueue(stringRequest);
    }


    private void display(String response) {

        JSONObject jsonObject;
        JSONArray jsonArray;


        try {
            jsonObject = new JSONObject(response);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String resource_Type;
            String res_Id;


            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);
                purpose = jo.getString("purpose");
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        title_tx.setText("Purpose");
        purpose_tx.setText(purpose);
    }
}
