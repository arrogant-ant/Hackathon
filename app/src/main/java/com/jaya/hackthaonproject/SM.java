package com.jaya.hackthaonproject;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import com.github.clans.fab.FloatingActionMenu;
//import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;


public class SM extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
  //  FloatingActionMenu materialDesignFAM;
    //com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    ListView listView;
    String location_id;
    String url = "http://www.wangle.website/GrantedResource.php";
    GrantedResAdapter f;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);


        ImageView im=new ImageView(this);
        im.setImageResource(R.drawable.plus);

        //Floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(SM.this, NewDemand.class);
                startActivity(intent);
            }
        });

        /* FloatingActionButton actionButton = new FloatingActionButton.Builder(this).setContentView(im).setBackgroundDrawable(R.drawable.selector_button_pink).build();
      SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
      itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_blue));
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageResource(R.drawable.bookopen);
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();
        ImageView itemIcon1 = new ImageView(this);
        itemIcon1.setImageResource(R.drawable.email);
        SubActionButton button2 = itemBuilder.setContentView(itemIcon1).build();
        ImageView itemIcon2 = new ImageView(this);
        itemIcon2.setImageResource(R.drawable.phone);
        SubActionButton button3 = itemBuilder.setContentView(itemIcon2).build();
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this).addSubActionView(button1)
                .addSubActionView(button2).addSubActionView(button3).attachTo(actionButton)
                .build();
        */


       /* materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

            }
        });*/


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_user);
        navigationView.setNavigationItemSelectedListener(this);
        listView = (ListView) findViewById(R.id.listview_granted);
        f = new GrantedResAdapter(getApplicationContext(), R.layout.granted_row);
        listView.setAdapter(f);
        location_id = LoginActivity.loc_id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("loc_id", location_id);
                return params;
            }

        };

        VolleySingleton.getInstance(getApplicationContext()).addToReqQueue(stringRequest);
    }


    public void demands(View v)
    {

        Intent i=new Intent(this,NewDemand.class);
        startActivity(i);
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify first parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_user) {
            Intent i = new Intent(this, LoginActivity.class);
            i.putExtra("error"," ");
            finish();
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_release_resource) {
            Intent i=new Intent(this,Free.class);
            startActivity(i);


        } else if (id == R.id.nav_requested_demands) {
            Intent i=new Intent(this,ShowDemand.class);
            startActivity(i);

        } else if (id == R.id.nav_granted_demands) {

            Intent i=new Intent(this,Granted.class);
            startActivity(i);
        } else if (id == R.id.nav_guidelines_user) {


        }
        else if(id == R.id.nav_resource_entry)
        {
            Intent i=new Intent(this,ResourceEntry.class);
            startActivity(i);
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_user);
            drawer.closeDrawer(GravityCompat.START);
            return true;



    }
    void parse(String j) {
        JSONObject jsonObject;
        JSONArray jsonArray;


        try {
            jsonObject = new JSONObject(j);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count = 0;
            String Resource_Type;
            String No_Of_Resources;
            String Demand_Id;
            String Date_Of_Demand;

            while (count < jsonArray.length()) {

                JSONObject jo = jsonArray.getJSONObject(count);
                Resource_Type = jo.getString("Resource_Type");
                No_Of_Resources = jo.getString("No_Of_Resources");
                Demand_Id = jo.getString("Demand_Id");
                Date_Of_Demand = jo.getString("Date_Of_Demand");
                GrantedRes c = new GrantedRes(Demand_Id, Resource_Type, No_Of_Resources, Date_Of_Demand);
                f.add(c);
                count++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}