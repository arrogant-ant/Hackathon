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

public class Admin extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String result;
    TextView tx;
    ContactAdapterDemand ca;
    ListView listView;
    String delete;
    String accept_data;


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
                TextView t=(TextView)view.findViewById(R.id.demand_id);
                String demand_id=t.getText().toString();
                TextView t1=(TextView)view.findViewById(R.id.resource_type);
                String resource_type=t1.getText().toString();
                TextView t2=(TextView)view.findViewById(R.id.no_of_resources);
                String no_of_resources=t2.getText().toString();
                alert(demand_id,resource_type,no_of_resources);

            }
        });


    }

    //alert dialog generation
    void alert(final String demand_id,final String resource_type,final String no_of_resources)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
        builder.setTitle("Request from:"+demand_id);
        builder.setMessage("what should be done?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Accept a=new Accept(Admin.this);
                a.execute(demand_id,resource_type,no_of_resources);
            }
        });
        builder.setNegativeButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Delete d=new Delete(Admin.this);
                d.execute(demand_id);



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
                String demand_id;
                String resource_type;
                String no_of_resources;
                String completion_time;
                String Deadline;
                String location_id;
                String date_of_demand;
                String priority;
                String date;
                while (count < jsonArray.length()) {

                    JSONObject jo = jsonArray.getJSONObject(count);
                    demand_id=jo.getString("demand_id");
                    resource_type = jo.getString("resource_type");
                    no_of_resources=jo.getString("no_of_resources");
                    completion_time=jo.getString("completion_time");
                    priority=jo.getString("priority_arr");
                    Deadline=jo.getString("Deadline");
                    location_id=jo.getString("location_id");
                    date=jo.getString("date_of_demand");
                    date_of_demand=date.substring(0,10);


                    Contacts_demand c = new Contacts_demand( resource_type, no_of_resources, completion_time,priority, Deadline,location_id,date_of_demand,demand_id);
                    ca.add(c);
                    count++;

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class Delete extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        Delete(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.16mb.com/accept.php";

        }

        @Override
        protected String doInBackground(String... params) {

            String first = params[0];



            try {

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("demand_id", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8");
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
            delete=s;
            parse(ctx);


        }

        void parse(Context ctx) {
            try {
                JSONObject jp = new JSONObject(delete);
                Boolean b=jp.getBoolean("final");


                Intent i=new Intent(ctx,Admin.class);
                startActivity(i);







            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    class Accept extends AsyncTask<String, String, String> {
        String json_string;
        String json_url;
        Context ctx;

        Accept(Context ctx) {
            this.ctx = ctx;


        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://www.wangle.16mb.com/delete.php";

        }

        @Override
        protected String doInBackground(String... params) {

            String first = params[0];
            String second=params[1];
            String third=params[2];


            try {

                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("demand_id", "UTF-8") + "=" + URLEncoder.encode(first, "UTF-8")+ "&" +  URLEncoder.encode("resource_type", "UTF-8") + "=" + URLEncoder.encode(second, "UTF-8")+ "&" +  URLEncoder.encode("no_of_resources", "UTF-8") + "=" + URLEncoder.encode(third, "UTF-8") ;
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
            accept_data=s;
            parse(ctx);


        }

        void parse(Context ctx) {
            try {
                JSONObject jp = new JSONObject(accept_data);
                Boolean b=jp.getBoolean("final");






                  Intent i=new Intent(ctx,Admin.class);
                    startActivity(i);






            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    }

