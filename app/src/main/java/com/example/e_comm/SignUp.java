package com.example.e_comm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText username=null,email=null,password=null,password2=null;
    Button signup=null;
    TextView textView3=null;
    String server_url=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        server_url=getString(R.string.server_url);
        textView3=findViewById(R.id.textView3);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        password2=findViewById(R.id.password2);
        signup=findViewById(R.id.signup);

        //////////////GET SERVER URL CODE STARTS//////////////////////////////
//        StringRequest r22 = new StringRequest(Request.Method.GET,getString(R.string.server_url)+"/get_server_url/", new Response.Listener<String>() {
//            public void onResponse(String response) {
//                try {
//                    JSONObject json2 = new JSONObject(response);
//                    SignUp.this.server_url=json2.getString("server_url");
//
//                } catch (JSONException e) {
//                    Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            /* access modifiers changed from: protected */
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("password", "mypassword123");
//                return params;
//            }
//        };
//        Volley.newRequestQueue(SignUp.this).add(r22);
        //////////////GET SERVER URL CODE ENDS////////////////////////////////

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////////////////////////////////////////////////
                StringRequest r2 = new StringRequest(Request.Method.POST,getString(R.string.server_url)+"/profiles_api/register_user/", new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject json2 = new JSONObject(response);
                            String token=json2.getString("token");
                            textView3.setText("SignUp successful and Logged in successfully"+token);
                            Intent intent123 = new Intent(SignUp.this, MainActivity.class);
                            intent123.putExtra("token", token);
                            startActivity(intent123);
                        } catch (JSONException e) {
                            textView3.setText("Try with different username or Passwords doesnt match");

                        }
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        textView3.setText("Try with different username or Passwords doesnt match");
                    }
                }) {
                    /* access modifiers changed from: protected */
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("username", username.getText().toString());
                        params.put("email", email.getText().toString());
                        params.put("password", password.getText().toString());
                        params.put("password2", password2.getText().toString());
                        return params;
                    }
                };
                Volley.newRequestQueue(SignUp.this).add(r2);
                //////////////////////////////////////////////////
            }
        });
    }
}
