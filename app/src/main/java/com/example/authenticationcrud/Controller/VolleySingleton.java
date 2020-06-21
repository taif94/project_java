package com.example.authenticationcrud.Controller;

import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


//create class to request
public class VolleySingleton {

    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue = getRequestQueue();
    private static Context mCtx;

    public VolleySingleton(Context context)
    {
        this.mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public  static  synchronized VolleySingleton getInstance(View.OnClickListener context){

        if(mInstance == null){

            mInstance = new VolleySingleton(( Context )context);
        }
        return mInstance;
    }



    public RequestQueue getRequestQueue() {
        if(mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }







}
