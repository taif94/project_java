package com.example.authenticationcrud.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.authenticationcrud.LoginActivity;
import com.example.authenticationcrud.Model.User;



//create  class to save user info
public class SessionManager {

    private static final String SHARED_PREF_NAME = "userToken";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = " token";
    private static final String KEY_ID = " user_id";



    private static SessionManager mInstance;
    private static Context mCtx;



    public SessionManager( Context Context)
    {
        mCtx = Context;
    }

//function of data synchronization
    public  static  synchronized SessionManager getInstance(Context context){

        if(mInstance == null){

            mInstance = new SessionManager(context);
        }
                 return mInstance;
    }

//user info
    public void userLogin( User user){
        SharedPreferences  sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID,user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.apply();
    }

//if it was login?
    public boolean isLoggedIn(){

        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN , null) != null;
    }

//take back the information
    public User getToken(){
        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_TOKEN , null)
        );
    }

//clear user data
    public void userLogout(){
        SharedPreferences  sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent( mCtx, LoginActivity.class));
    }


//its return you information
    public User getUser( User user) {
        SharedPreferences sharedPreferences =
                mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_TOKEN, null)

        );
    }



    }














