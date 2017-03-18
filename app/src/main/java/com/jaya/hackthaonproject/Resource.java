package com.jaya.hackthaonproject;

<<<<<<< Updated upstream
/**
 * Created by Sabita_Sant on 18/03/17.
 */

public class Resource {
    private String type;
    private String no;
    private String time;

    public Resource(String type, String no, String time) {

        this.type = type;
        this.no = no;
        this.time = time;
    }
    public String getType() {
        return type;
    }

    public String getNo() {
        return no;
    }

    public String getTime() {
        return time;
    }


}
=======
import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Resource extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);
        Button b=(Button)findViewById(R.id.button);
        b.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.app.FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                AllocatedTable at=new AllocatedTable();
                ft.replace(R.id.frame,at);
                ft.commit();
            }
        });





}}
>>>>>>> Stashed changes
