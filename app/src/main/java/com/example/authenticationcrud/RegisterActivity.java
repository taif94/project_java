package com.example.authenticationcrud;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.authenticationcrud.Controller.SessionManager;
import com.example.authenticationcrud.Controller.VolleySingleton;
import com.example.authenticationcrud.Server.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity<User> extends AppCompatActivity {
    private EditText name, email, password;
    private Button buttonRegister;
    private ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
//define variables
        name = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        buttonRegister = findViewById(R.id.registerbutton);
        progressBar = findViewById(R.id.registerprogressBar);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }


    public void registerUser() {
        final String myName = name.getText().toString().trim();
        final String myEmail = email.getText().toString().trim();
        final String myPassword = password.getText().toString().trim();

        if (TextUtils.isEmpty(myName)) {
            name.setError("Enter your name please");
            name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(myEmail)) {
            email.setError("Enter your email please");
            email.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(myPassword)) {
            password.setError("Enter your password please");
            password.requestFocus();
            return;
        }
//request
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLs.URL_REGISTER, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.VISIBLE);

//response
                try {
                    JSONObject obj = new JSONObject(response);

                    if (obj.getBoolean("success")) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), obj.getString("message successfully"),
                                Toast.LENGTH_LONG).show();


                        JSONObject userJson = obj.getJSONObject("data");

                       com.example.authenticationcrud.Model.User user = new com.example.authenticationcrud.Model.User
                               (userJson.getString("token"));

                        SessionManager.getInstance(getApplicationContext()).userLogin(user);

                        finish();
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "Register failed", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }

        ) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("content_type", "application/Json");
                params.put("name", myName);
                params.put("email", myEmail);
                params.put("password", myPassword);
                params.put("c_password", myPassword);
                return params;
            }


        };

//request to server
        VolleySingleton.getInstance((View.OnClickListener) this).addToRequestQueue(stringRequest);


    }


}