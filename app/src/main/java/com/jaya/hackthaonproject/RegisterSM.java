package com.jaya.hackthaonproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class RegisterSM extends AppCompatActivity {
    EditText et;
    EditText et2;
    EditText et3;
    EditText et4;
    Registersm g;
    String result;

    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView t1 = (TextView) findViewById(R.id.textheading);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/KaushanScript-Regular.otf");
        t1.setTypeface(tf);
        Button b1 = (Button) findViewById(R.id.btnRegister);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/Capture_it.ttf");
        b1.setTypeface(tf2);

    }

    public void click(View v) {
        et = (EditText)findViewById(R.id.empid);
        et2 = (EditText)findViewById(R.id.email);
        et3 = (EditText)findViewById(R.id.password);
        et4 = (EditText)findViewById(R.id.phone);

        String s1 = et.getText().toString();
        String s2 = et2.getText().toString();
        String s3 = et3.getText().toString();
        String s4 = et4.getText().toString();
        g = new Registersm(this);
        g.execute(s1, s2, s3, s4);


    }

    class Registersm extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        Registersm(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.techdrona.net/tech/Hack/anyone.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String first = params[0];
            String second = params[1];
            String third = params[2];
            String fourth = params[3];

            try {

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("empID", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8") + "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(second, "UTF-8")  + "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(third, "UTF-8") + "&" +URLEncoder.encode("mob", "UTF-8") + "=" + URLEncoder.encode(fourth, "UTF-8");
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

            result = s;
           /* TextView t= (TextView)findViewById(R.id.textshow);
            t.setText(result);*/


         /* Intent i=new Intent(ctx,Insertion_succesful.class);
            i.putExtra("result",result);
            startActivity(i);*/
            parse(ctx);


        }
    }

    void parse(Context ctx) {


        try {
            JSONObject jp = new JSONObject(result);



            String inserted;


            inserted = jp.getString("is_inserted");

            if(inserted.equals("true")) {
                Intent i = new Intent(ctx, SMRegSuccess.class);
                ctx.startActivity(i);


            } else
            {
                Intent i=new Intent(this,SMRegFailure.class);
                ctx.startActivity(i);

            }




        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}




