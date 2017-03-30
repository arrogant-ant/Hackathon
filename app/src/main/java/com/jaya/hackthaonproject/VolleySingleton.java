package com.jaya.hackthaonproject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Created by Sabita_Sant on 30/03/17.
 */

public class VolleySingleton {
    private static VolleySingleton singleton;
    private static Context ctx;
    private static RequestQueue requestQueue;
    //Cache cache= new DiskBasedCache()

    public   RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
            requestQueue= Volley.newRequestQueue(ctx.getApplicationContext());
        return  requestQueue;
    }

    private VolleySingleton(Context context) {
        ctx=context;
        requestQueue=getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context)
    {
        if(singleton==null)
            singleton= new VolleySingleton(context);
        return singleton;
    }

    public<T> void addToReqQueue(Request<T> request)
    {
        requestQueue.add(request);
    }
}
