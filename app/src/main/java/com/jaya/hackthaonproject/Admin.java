package com.jaya.hackthaonproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String result;
    TextView tx;
    ContactAdapterDemand ca;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_admin);
        setSupportActionBar(toolbar);
        ////////////////////////////////////
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_admin);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_admin);
        navigationView.setNavigationItemSelectedListener(this);
///////////////////////////////////////////////////////////////
        ca = new ContactAdapterDemand(this, R.layout.activity_layout_individual_admin);
        listView = (ListView) findViewById(R.id.listitems);
        listView.setAdapter(ca);
        Showviewss show = new Showviewss(this);
        show.execute();

        //listview listener setup
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();

                alert();

            }
        });


    }

    //alert dialog generation
    void alert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
        builder.setTitle("Request");
        builder.setMessage("what should be done?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"confirmed",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"deleted",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("cancel",null);
        AlertDialog alertDialog= builder.create();
        alertDialog.show();

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_admin);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_admin) {
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_timeline_report) {


        } else if (id == R.id.nav_view_database) {

        } else if (id == R.id.nav_track_resources) {

        } else if (id == R.id.nav_allocate_demand) {


        } else if (id == R.id.nav_create_sm) {
            Intent i = new Intent(this, Register.class);
            startActivity(i);
        } else if (id == R.id.nav_view_sm_database) {
            Intent i = new Intent(this, show.class);
            startActivity(i);

        } else if (id == R.id.nav_guidelines) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_admin);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class Showviewss extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        Showviewss(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.16mb.com/demand.php";

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


        }


        void parse(Context ctx) {
            JSONObject jsonObject;
            JSONArray jsonArray;


            try {
                jsonObject = new JSONObject(result);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;

                String resource_type;
                String no_of_resources;
                String completion_time;
                String Deadline;
                String location_id;
                String date_of_demand;
                String priority;
                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    resource_type = jo.getString("resource_type");
                    no_of_resources=jo.getString("no_of_resources");
                    completion_time=jo.getString("completion_time");
                    priority=jo.getString("priority");
                    Deadline=jo.getString("Deadline");
                    location_id=jo.getString("location_id");
                    date_of_demand=jo.getString("date_of_demand");


                    Contacts_demand c = new Contacts_demand( resource_type, no_of_resources, completion_time,priority, Deadline,location_id,date_of_demand);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
