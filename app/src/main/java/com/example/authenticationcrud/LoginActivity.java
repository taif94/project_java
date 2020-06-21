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
import com.example.authenticationcrud.Model.User;
import com.example.authenticationcrud.Server.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText email , password;
    private Button buttonRegister, buttonLogin;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.Loginemail);
        password = findViewById(R.id.Loginpassword);
        buttonRegister = findViewById(R.id.registerbutton2);
        buttonLogin = findViewById(R.id.Loginbutton);
        progressBar = findViewById(R.id.LoginprogressBar2);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(LoginActivity.this
                        , RegisterActivity.class));


                buttonLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userLogin();
                    }

                });
            }


            private void userLogin() {

                final String myEmail = email.getText().toString().trim();
                final String myPassword = password.getText().toString().trim();


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


                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        URLs.URL_LOGIN, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.VISIBLE);


                        try {
                            JSONObject obj = new JSONObject(response);

                            if (obj.getJSONObject("success") != null) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(), "welcome"
                                        , Toast.LENGTH_LONG).show();

                                JSONObject userJson = obj.getJSONObject("success");

                                User user = new User(userJson.getString("token"));
                                SessionManager.getInstance(getApplicationContext()).userLogin(user);

                                finish();
                                startActivity(new Intent(getApplicationContext(),
                                        MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Login failed", Toast.LENGTH_LONG).show();
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
                        params.put("email", myEmail);
                        params.put("password", myPassword);

                        return params;
                    }

                };


                VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);


            }
        });
    }

}
