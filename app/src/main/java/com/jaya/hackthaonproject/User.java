package com.jaya.hackthaonproject;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

//import com.github.clans.fab.FloatingActionMenu;
//import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;


public class User extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
  //  FloatingActionMenu materialDesignFAM;
    //com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);

        ImageView im=new ImageView(this);
        im.setImageResource(R.drawable.plus);
        FloatingActionButton actionButton = new FloatingActionButton.Builder(this).setContentView(im).setBackgroundDrawable(R.drawable.selector_button_pink).build();
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
            Intent i=new Intent(this,ShowUserDemand.class);
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
}